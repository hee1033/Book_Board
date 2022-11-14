package project.Service;

import java.util.ArrayList;
import java.util.List;

import project.SocketClient;
import project.DAO.QnAContentDAO;
import project.DAO.QnADAO;
import project.DTO.Pager;
import project.DTO.QnAContentDTO;
import project.DTO.QnADTO;

public class QnAService {
	
	public List<QnADTO> showQuestions(int qnaType, Pager pager) {
		QnADAO qnadao = new QnADAO();
		List<QnADTO> list = new ArrayList<>();
		list = qnadao.showKindsOfQuestion(qnaType, pager);
		return list;
	}
	
	public List<QnAContentDTO> showQuestionDetail(int detailNum){
		QnAContentDAO qnaContentDao = new QnAContentDAO();
		
		List<QnAContentDTO> list = new ArrayList<>();
		list = qnaContentDao.showQuestionDetail(detailNum);
		return list;
	}

	public boolean registerQuestion(QnADTO qnaDTO) {
		QnADAO qnadao = new QnADAO();
		boolean	result = qnadao.registerQuestion(qnaDTO);
		
		return result;
	}

	public int getTotalRows(int qnaType ) {
		QnADAO qnadao = new QnADAO();
		int totalRows = qnadao.getTotalRows(qnaType);

		return totalRows;
	}
	
	public boolean modifyQuestion(QnADTO qnaDTO) {
		QnADAO qnaDAO = new QnADAO();
		QnADTO dbQnADTO = qnaDAO.selectQuesion(qnaDTO.getQnaId());
		boolean result = false;
		System.out.println(dbQnADTO.getUserId());
		if(dbQnADTO != null && dbQnADTO.getUserId().equals(qnaDTO.getUserId())) {
			result = qnaDAO.modifyQuestion(qnaDTO);
		}
		System.out.println(result);
		return result;
		
	}
	public boolean deleteQuestion(SocketClient sender, QnADTO qnaDTO) {
		QnADAO qnaDAO = new QnADAO();
		QnADTO dbQnADTO = qnaDAO.selectQuesion(qnaDTO.getQnaId());
		boolean result = false;
		if(dbQnADTO != null && dbQnADTO.getUserId().equals(qnaDTO.getUserId())) {
			result = qnaDAO.deleteQuestion(qnaDTO.getQnaId());
		}
		return result;
	}
	
	

}
