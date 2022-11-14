package project.Controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import project.SocketClient;
import project.DTO.BookDTO;
import project.DTO.EventDTO;
import project.DTO.GoodsDTO;
import project.DTO.QnAContentDTO;
import project.DTO.QnADTO;
import project.DTO.UserDTO;
import project.Service.ManagerService;

public class ManagerController {

	public void manager(SocketClient sender, String listNo, JSONObject jsonUpdata) {
		JSONObject root= new JSONObject();
		String json="";
		switch(listNo) {
			case "0":
				root.put("controller", "menuNo");
				root.put("data", "manager");
				json = root.toString();
				sender.send(json);
				break;
			case "1":
				showBookInsert(sender,jsonUpdata);
				break;
			case "2":
				showDelete(sender,jsonUpdata);
				break;
			case "3":
				showUserData(sender);
				break;
			case "4":
				showDetailUserData(sender, jsonUpdata);
				break;
			case "5":
				//매니저로그아웃
				showManagerLogout(sender);
				break;
			case "6":
				//이벤트 업데이트
				showEventInsert(sender,jsonUpdata);
				break;
			case "7":
				showGoodsInsert(sender, jsonUpdata);
				break;
			case "8":
				showManagerLogin(sender, jsonUpdata);
				break;
			case "9":
				showBookUpdate(sender,jsonUpdata);
				break;
			case "showNotAnswer":
				showNotAnswer(sender, jsonUpdata);
				break;
			case "showNotAnswerDetail":
				 showNotAnswerDetail(sender, jsonUpdata);
				 break;
			case "replyNotAnswer":
				replyNotAnswer(sender, jsonUpdata);
				break;
		}
	}

	//책 업데이트
	public void showBookInsert(SocketClient sender, JSONObject jsonUpdata) {
		ManagerService managerService = new ManagerService();
		BookDTO bookDTO= new BookDTO();
		bookDTO.setBookId(jsonUpdata.getInt("bookId"));
		bookDTO.setBookName(jsonUpdata.getString("bookName"));
		bookDTO.setBookPrice(jsonUpdata.getInt("bookPrice"));
		bookDTO.setBookWriter(jsonUpdata.getString("bookWriter"));
		bookDTO.setBookStock(jsonUpdata.getInt("bookStock"));
		bookDTO.setBookContent(jsonUpdata.getString("bookContent"));
		bookDTO.setCompanyId(jsonUpdata.getInt("bookCompany"));
		bookDTO.setCategoryId(jsonUpdata.getInt("categoryId"));
		bookDTO.setBookSellingPrice(jsonUpdata.getInt("bookSellingPrice"));
		System.out.println(bookDTO.toString());
		
		boolean result = managerService.showBookInsert(bookDTO);
		sender.mflag=result;
		
		JSONObject root= new JSONObject();
		root.put("controller", "manager");
		root.put("choice", "x");
		root.put("mflag", sender.mflag);
		if(sender.mflag==true) {
			root.put("message","책 등록 되었습니다.");
		}else {
			root.put("message","존재하는 book id입니다. \n다른 id를 입력해주세요.");
		}
		String json=root.toString();
		sender.send(json);
		
	}
	
	//책 수량 수정
	public void showBookUpdate(SocketClient sender, JSONObject jsonUpdata) {
		ManagerService managerService = new ManagerService();
		int bookId = jsonUpdata.getInt("bookId");
		int stock=jsonUpdata.getInt("stock");
		boolean result = managerService.showBookUpdate(bookId, stock);
		sender.mflag=result;
		JSONObject root= new JSONObject();
		root.put("controller", "manager");
		root.put("choice", "x");
		root.put("mflag", sender.mflag);
		if(result == true) {
			root.put("message","업데이트 되었습니다.");
		}else {
			root.put("message","존재하지 않는 id입니다. \n다시 입력해주세요.");
		}
		String json=root.toString();
		sender.send(json);
	}

	//책, 이벤트, 굿즈 삭제
	public void showDelete(SocketClient sender, JSONObject jsonUpdata) {
		ManagerService managerService = new ManagerService();
		//json 파싱
		int id = jsonUpdata.getInt("id");
		String num=jsonUpdata.getString("idNum");
		//비지니스로직
		boolean result = managerService.showDelete(id,num);
		sender.mflag=result;
		JSONObject root= new JSONObject();
		root.put("controller", "manager");
		root.put("choice", "x");
		root.put("mflag", sender.mflag);
		if(result == true) {
			root.put("message","삭제 되었습니다.");
		}else {
			root.put("message","존재하지 않는 id입니다. \n다시 입력해주세요.");
		}
		String json=root.toString();
		sender.send(json);
	}

	//유저 정보확인
	public void showUserData(SocketClient sender) {
		ManagerService managerService = new ManagerService();
		List<UserDTO> list = new ArrayList<>();
		list = managerService.showUserData();

		JSONArray jsonArray = new JSONArray();
		for (UserDTO du : list) {
			JSONObject jo = new JSONObject();
			jo.put("userId", du.getUserId());
			jo.put("userName", du.getUserName());
			jsonArray.put(jo);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("controller", "show");
		jsonObject.put("data", jsonArray);
		String json = jsonObject.toString();
		sender.send(json);
	}

	//디테일 유저정보 확인
	public void showDetailUserData(SocketClient sender, JSONObject jsonUpdata) {
		ManagerService managerService = new ManagerService();
		UserDTO userDto = new UserDTO();
		String userId=jsonUpdata.getString("userId");
		userDto = managerService.showDetailUserData(userId);

		JSONArray jsonArray = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("userId", userDto.getUserId());
		jo.put("userPassword", userDto.getUserPassword());
		jo.put("userName", userDto.getUserName());
		jo.put("userEmail", userDto.getUserEmail());
		jo.put("userAddress", userDto.getUserAddress());
		jo.put("userTel", userDto.getUserTel());
		jo.put("userIndate", userDto.getUserIndate());
		jo.put("userAge", userDto.getUserAge());
		jo.put("userSex", userDto.getUserSex());
		jsonArray.put(jo);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("controller", "show");
		jsonObject.put("data", jsonArray);
		String json = jsonObject.toString();
		sender.send(json);
	}
	
	//관리자 로그아웃
	public void showManagerLogout(SocketClient sender) {
		ManagerService managerService = new ManagerService();
		boolean result =managerService.showManagerLogout();
		sender.mflag=result;
		sender.flag=result;

		JSONObject root = new JSONObject();
		root.put("controller", "manager");
		root.put("choice", "first");
		root.put("mflag", sender.mflag);
		root.put("message", "관리자 로그아웃 되었습니다.");
		String json = root.toString();
		sender.send(json);
	}

	//관리자 로그인
	public void showManagerLogin(SocketClient sender, JSONObject jsonUpdata) {
		ManagerService managerService = new ManagerService();
		String id=jsonUpdata.getString("id");
		String password=jsonUpdata.getString("password");
		
		managerService.showManagerLogin(sender, id, password);
		JSONObject root = new JSONObject();
		root.put("controller", "manager");
		root.put("choice", "x");
		root.put("mflag", sender.mflag);
		if (sender.mflag == true) {
			root.put("message", "관리자 로그인에 성공하셨습니다.");
		} else {
			root.put("message", "아이디 또는 비밀번호가 틀렸습니다.");
		}
		String json = root.toString();
		sender.send(json);
	}
	
	//이벤트 업데이트
	public void showEventInsert(SocketClient sender, JSONObject jsonUpdata) {
		ManagerService managerService = new ManagerService();
		EventDTO eventDto = new EventDTO();
		eventDto.setEventId(jsonUpdata.getInt("eventId"));
		eventDto.setEventName(jsonUpdata.getString("eventName"));
		eventDto.setEventStart(jsonUpdata.getString("eventStart"));
		eventDto.setEventEnd(jsonUpdata.getString("eventEnd"));
		eventDto.setBookId(jsonUpdata.getInt("bookId"));
		eventDto.setEventContent(jsonUpdata.getString("eventContent"));
//		System.out.println(eventDto.toString());
		boolean result = managerService.showEventInsert(eventDto);

		sender.mflag=result;
		
		JSONObject root= new JSONObject();
		root.put("controller", "manager");
		root.put("choice", "x");
		root.put("mflag", sender.mflag);
		if(sender.mflag==true) {
			root.put("message","이벤트 등록 되었습니다.");
		}else {
			root.put("message","Event ID가 존재하거나 없는 Book ID입니다.");
		}
		String json=root.toString();
		sender.send(json);
	}
	
	//굿즈 업데이트
	public void showGoodsInsert(SocketClient sender, JSONObject jsonUpdata) {
		ManagerService managerService = new ManagerService();
		GoodsDTO goodsDto = new GoodsDTO();
		goodsDto.setGoodsId(jsonUpdata.getInt("goodsId"));
		goodsDto.setGoodsName(jsonUpdata.getString("goodsName"));
		goodsDto.setGoodsNum(jsonUpdata.getInt("goodsNum"));
		goodsDto.setEventId(jsonUpdata.getInt("eventId"));
		goodsDto.setGoodsContent(jsonUpdata.getString("goodsContent"));
		goodsDto.setGoodsPrice(jsonUpdata.getInt("goodsPrice"));
		//System.out.println(goodsDto.toString());
		boolean result = managerService.showGoodsInsert(goodsDto);
		sender.mflag=result;
		
		JSONObject root= new JSONObject();
		root.put("controller", "manager");
		root.put("choice", "x");
		root.put("mflag", sender.mflag);
		if(sender.mflag==true) {
			root.put("message","이벤트 등록 되었습니다.");
		}else {
			root.put("message","존재하는 goods id입니다. \n다른 id를 입력해주세요.");
		}
		String json=root.toString();
		sender.send(json);
	}
		
	public void showNotAnswer(SocketClient sender, JSONObject jsonUpdata) {
		int qna_type = Integer.parseInt(jsonUpdata.getString("qna_type"));
		ManagerService managerService = new ManagerService();
		List<QnADTO> list = new ArrayList<>();
		list = managerService.showNotAnswer(qna_type);

		JSONArray jsonArray = new JSONArray();
		for (QnADTO aNotAnswer : list) {
			JSONObject jo = new JSONObject();
			jo.put("qnaId", aNotAnswer.getQnaId());
			jo.put("qnaTitle", aNotAnswer.getQnaTitle());
			jsonArray.put(jo);
		}
		System.out.println("arraylist 담는 중");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("controller", "show");
		jsonObject.put("data", jsonArray);
		String json = jsonObject.toString();
		sender.send(json);
	}
		   
	// 미답변 항목 상세히 보기 (qna dao에서 데이터 뽑음)
	public void showNotAnswerDetail(SocketClient sender, JSONObject jsonUpdata) {
		int detailNum = Integer.parseInt(jsonUpdata.getString("updata"));
		ManagerService mService = new ManagerService();

		List<QnADTO> list = new ArrayList<>();
		list = mService.showNotAnswerDetail(detailNum);

		JSONArray jsonArray = new JSONArray();
		for (QnADTO qdto : list) {
			JSONObject jo = new JSONObject();
			jo.put("qnaId", qdto.getQnaId());
			jo.put("qnaTitle", qdto.getQnaTitle());
			jo.put("qnaContent", qdto.getQnaContent());
			jsonArray.put(jo);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("controller", "show");
		jsonObject.put("data", jsonArray);
		String json = jsonObject.toString();
		sender.send(json);

	}

	public void replyNotAnswer(SocketClient sender, JSONObject jsonUpdata){
		int detailNum = jsonUpdata.getInt("updata");

		QnAContentDTO qnaContentDTO = new QnAContentDTO();
		qnaContentDTO.setManagerAnswer(jsonUpdata.getString("MANAGER_ANSWER"));
		qnaContentDTO.setManagerId(sender.managerID);
		System.out.println(sender.managerID);
		ManagerService mService = new ManagerService();
		boolean result = mService.replyNotAnswer(qnaContentDTO, detailNum);

		if (result == true) {
			JSONObject root = new JSONObject();
			root.put("controller", "complete");
			root.put("flag", result);
			root.put("choice", "x");
			root.put("message", "**QnA 답변 완료되었습니다.**");
			String json = root.toString();
			sender.send(json);
		}
	}
			

}
