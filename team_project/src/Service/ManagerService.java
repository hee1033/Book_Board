package Service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import DAO.BookDAO;
import DAO.ManagerDAO;
import DAO.UserDAO;
import DTO.BookDTO;
import DTO.UserDTO;
import project.ConnectionProvider;
import project.SocketClient;

public class ManagerService {
	Scanner sc = new Scanner(System.in);
	Connection conn = ConnectionProvider.getConnection();
	
	//관리자 로그인
	public void showManagerLogin(SocketClient sender, String id, String password) {
		ManagerDAO managerDAO = new ManagerDAO();
		boolean result= managerDAO.showManagerLogin(sender, id);
		if(result==true) {
			if(sender.managerDTO.getManagerID().equals(id) && sender.managerDTO.getManagerPassword().equals(password)) {
				sender.mflag=true;
				sender.flag=true;
			}else {
				sender.mflag=false;
			}
		}else
			sender.mflag=false;
	}
	
	//책 업데이트
	public boolean showBookUpdata(BookDTO bookDTO) {
		BookDAO bookDAO = new BookDAO();
		boolean result=bookDAO.InsertBook(bookDTO);
		return result;
	}
	
	//책 삭제
	public boolean showBookDelete(int bookId) {
		BookDAO bookDAO = new BookDAO();
		boolean result = bookDAO.DeleteBook(bookId);
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
	public UserDTO showDetailUserData(SocketClient sender, String userId) {
		UserDAO userDAO = new UserDAO();
		UserDTO userDto = new UserDTO();
		userDto=userDAO.selectDetailData(sender, userId);
		return userDto;
	}
	
	//관리자 로그아웃
	public boolean showManagerLogout() {
		return false;
	}
		
}
