package project.Controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import project.SocketClient;
import project.DTO.Pager;
import project.DTO.QnAContentDTO;
import project.DTO.QnADTO;
import project.Service.QnAService;

public class QnAController {
	public void qna(SocketClient sender, String listNo, JSONObject jsonUpdata) {
		JSONObject root = new JSONObject();
		String json = "";
		switch (listNo) {
		case "0":
			root.put("controller", "menuNo");
			root.put("data", "qna");
			json = root.toString();
			sender.send(json);
			break;
		case "showQuestions":
			showQuestions(sender, jsonUpdata);
			break;
		case "showDetailQuestion":
			showQuestionDetail(sender, jsonUpdata);
			break;
		case "registerQuestion":
			registerQuestion(sender, jsonUpdata);
			break;
		case "modifyQuestion":
			modifyQuestion(sender, jsonUpdata);
			break;
		case "deleteQuestion":
			deleteQuestion(sender, jsonUpdata);
			break;
		}
	}

	public void showQuestions(SocketClient sender, JSONObject jsonUpdata) {
		QnAService qnaService = new QnAService();
		List<QnADTO> list = new ArrayList<>();
		JSONArray jsonArray = new JSONArray();

		int pageNo = jsonUpdata.getInt("pageNo");
		int qnaType = Integer.parseInt(jsonUpdata.getString("qna_type"));
		Pager pager = new Pager(2, 3, qnaService.getTotalRows(qnaType), pageNo);
		list = qnaService.showQuestions(qnaType, pager);
		
		for (QnADTO aBookQuestion : list) {
			JSONObject jo = new JSONObject();
			jo.put("qnaId", aBookQuestion.getQnaId());
			jo.put("qnaTitle", aBookQuestion.getQnaTitle());
			jo.put("rowsPerPage", pager.getRowsPerPage());
			jo.put("pagesPerGroup", pager.getPagesPerGroup());
			jo.put("totalRows", pager.getTotalRows());
			jo.put("pageNo", pager.getPageNo());
			jsonArray.put(jo);
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("controller", "show");
		jsonObject.put("data", jsonArray);
		String json = jsonObject.toString();
		sender.send(json);

	}

	public void showQuestionDetail(SocketClient sender, JSONObject jsonUpdata) {
		QnAService qnaService = new QnAService();
		List<QnAContentDTO> list = new ArrayList<>();
		JSONArray jsonArray;
		int detailNum = jsonUpdata.getInt("updata");

		list = qnaService.showQuestionDetail(detailNum);
		jsonArray = new JSONArray();
		for (QnAContentDTO qdetail : list) {
			JSONObject jo = new JSONObject();
			jo.put("qnaId", qdetail.getQnaId());
			jo.put("managerAnswer", qdetail.getManagerAnswer());
			jsonArray.put(jo);
		}

		// BookClient로 json 전송
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("controller", "show");
		jsonObject.put("data", jsonArray);
		String json = jsonObject.toString();
		sender.send(json);
	}

	public void registerQuestion(SocketClient sender, JSONObject jsonUpdata) {
		String qna_user_id = "";

		if (sender.flag == true) {
			qna_user_id = sender.userID;

			// json dto로 파싱
			QnADTO qnadto = new QnADTO();
			qnadto.setQnaTitle(jsonUpdata.getString("QNA_TITLE"));
			qnadto.setQnaContent(jsonUpdata.getString("QNA_CONTENT"));
			qnadto.setQnaType(Integer.parseInt(jsonUpdata.getString("QNA_TYPE")));
			qnadto.setBookId(Integer.parseInt(jsonUpdata.getString("BOOK_ID")));
			qnadto.setUserId(qna_user_id);

			QnAService qnaService = new QnAService();
			boolean result = qnaService.registerQuestion(qnadto);
			if (result == true) {
				JSONObject root = new JSONObject();
				root.put("controller", "complete");
				root.put("flag", result);
				root.put("choice", "x");
				root.put("message", "**작성 완료 되었습니다.**");
				String json = root.toString();
				sender.send(json);
			}

		} else {
			JSONObject root = new JSONObject();
			root.put("controller", "complete");
			root.put("flag", false);
			root.put("choice", "x");
			root.put("message", "로그인 하셔야 작성가능합니다.");
			String json = root.toString();
			sender.send(json);
		}
	}

	public void modifyQuestion(SocketClient sender, JSONObject jsonUpdata) {
		if (sender.flag == true) {
			// json dto로 파싱
			QnADTO qnadto = new QnADTO();
			qnadto.setQnaTitle(jsonUpdata.getString("modifiedTitle"));
			qnadto.setQnaContent(jsonUpdata.getString("modifiedContent"));
			qnadto.setQnaId(jsonUpdata.getInt("qnaId"));
			qnadto.setUserId(sender.userID);
			
			QnAService qnaService = new QnAService();
			boolean result = qnaService.modifyQuestion(qnadto);
			if (result == true) {
				JSONObject root = new JSONObject();
				root.put("controller", "complete");
				root.put("flag", result);
				root.put("choice", "x");
				root.put("message", "****수정 완료 되었습니다.****");
				String json = root.toString();
				sender.send(json);
			} else {
				JSONObject root = new JSONObject();
				root.put("controller", "complete");
				root.put("flag", true); // user_id 가 달라도 로그인은 되어 있음
				root.put("choice", "x");
				root.put("message", "*작성 아이디가 일치하지 않습니다.*");
				String json = root.toString();
				sender.send(json);
			}
		} else { // 로그인x 일 경우
			JSONObject root = new JSONObject();
			root.put("controller", "complete");
			root.put("flag", false);
			root.put("choice", "x");
			root.put("message", "로그인 하셔야 작성가능합니다.");
			String json = root.toString();
			sender.send(json);
		}
	}

	public void deleteQuestion(SocketClient sender, JSONObject jsonUpdata) {
		if (sender.flag == true) {
			QnADTO qnadto = new QnADTO();
			qnadto.setQnaId(jsonUpdata.getInt("qnaId"));
			qnadto.setUserId(sender.userID);
			QnAService qnaService = new QnAService();
			boolean result = qnaService.deleteQuestion(sender, qnadto);
			if (result == true) {
				JSONObject root = new JSONObject();
				root.put("controller", "complete");
				root.put("flag", result);
				root.put("choice", "x");
				root.put("message", "*삭제 되었습니다.*");
				String json = root.toString();
				sender.send(json);
			} else {
				JSONObject root = new JSONObject();
				root.put("controller", "complete");
				root.put("flag", true); // user_id 가 달라도 로그인은 되어 있음
				root.put("choice", "x");
				root.put("message", "*작성 아이디가 일치하지 않습니다.*");
				String json = root.toString();
				sender.send(json);
			}

		} else {
			JSONObject root = new JSONObject();
			root.put("controller", "complete");
			root.put("flag", false);
			root.put("choice", "x");
			root.put("message", "로그인 하셔야 작성가능합니다.");
			String json = root.toString();
			sender.send(json);
		}
	}

}
