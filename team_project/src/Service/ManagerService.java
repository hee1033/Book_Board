package project.Service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import project.ConnectionProvider;
import project.SocketClient;
import project.DAO.BookDAO;
import project.DAO.EventDAO;
import project.DAO.GoodsDAO;
import project.DAO.ManagerDAO;
import project.DAO.QnAContentDAO;
import project.DAO.QnADAO;
import project.DAO.UserDAO;
import project.DTO.BookDTO;
import project.DTO.EventDTO;
import project.DTO.GoodsDTO;
import project.DTO.ManagerDTO;
import project.DTO.QnAContentDTO;
import project.DTO.QnADTO;
import project.DTO.UserDTO;

public class ManagerService {
	Scanner sc = new Scanner(System.in);
	Connection conn = ConnectionProvider.getConnection();
	
	//관리자 로그인
	public void showManagerLogin(SocketClient sender, String id, String password) {
		ManagerDAO managerDAO = new ManagerDAO();
		ManagerDTO managerDto = new ManagerDTO();
		managerDto= managerDAO.selectManagerLogin(sender, id);
		if(sender.mflag==true) {
			if(managerDto.getManagerID().equals(id) && managerDto.getManagerPassword().equals(password)) {
				sender.flag=true;
				sender.managerID=id;
			}else {
				sender.mflag=false;
			}
		}else
			sender.mflag=false;
	}
	
	//책 업데이트
	public boolean showBookInsert(BookDTO bookDTO) {
		BookDAO bookDAO = new BookDAO();
		boolean result=bookDAO.insertBook(bookDTO);
		return result;
	}
	
	public boolean showBookUpdate(int bookId, int stock) {
		BookDAO bookDAO = new BookDAO();
		boolean result=bookDAO.updateBook(bookId, stock);
		return result;
	}
	
	//책 삭제
	public boolean showDelete(int id,String num) {
		BookDAO bookDAO = new BookDAO();
		EventDAO eventDao = new EventDAO();
		GoodsDAO goodsDao = new GoodsDAO();
		boolean result=false;
		if(num.equals("1")) {
			result = bookDAO.deleteBook(id);
		}else if(num.equals("3")) {
			result=eventDao.deleteEvent(id);
		}else {
			result=goodsDao.deleteGoods(id);
		}
		
		return result;
	}
	
	//모든 유저 정보
	public List<UserDTO> showUserData() {
		UserDAO userDAO = new UserDAO();
		List<UserDTO> list = new ArrayList<>();
		list=userDAO.selectAllUserData();
		return list;
	}
	
	//특정 유저 정보
	public UserDTO showDetailUserData(String userId) {
		UserDAO userDAO = new UserDAO();
		UserDTO userDto = new UserDTO();
		userDto=userDAO.selectDetailData(userId);
		return userDto;
	}
	
	//관리자 로그아웃
	public boolean showManagerLogout() {
		return false;
	}
	
	//이벤트 업데이트
	public boolean showEventInsert(EventDTO eventDto) {
		EventDAO eventDao = new EventDAO();
		boolean result = eventDao.insertEvent(eventDto);
		return result;
	}
	
	//굿즈 업데이트
	public boolean showGoodsInsert(GoodsDTO goodsDto) {
		GoodsDAO goodsDao = new GoodsDAO();
		boolean result = goodsDao.insertGoods(goodsDto);
		return result;
	}
	
	//미답변 목록 조회
	   public List<QnADTO> showNotAnswer(int qna_type){
	      QnADAO qnaDAO = new QnADAO();
	      List<QnADTO> list = new ArrayList<>();
	      list = qnaDAO.showNotAnswer(qna_type);
	      return list;
	   }
	   
	   //미답변 상세 보기
	   public List<QnADTO> showNotAnswerDetail(int detailNum){
	      QnADAO qnaDAO = new QnADAO();
	      List<QnADTO> list = new ArrayList<>();
	      list = qnaDAO.showNotAnswerDetail(detailNum);
	      return list;
	   }
	   //미답변 답글 작성
	   public boolean replyNotAnswer(QnAContentDTO qnaContentDTO, int detailNum) {
	      QnAContentDAO qnaContentDAO = new QnAContentDAO();
	      boolean result = qnaContentDAO.replyNotAnswer(qnaContentDTO, detailNum);

	      return result;
	   }
		
}
