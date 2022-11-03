package Service;


import java.sql.Connection;
import java.util.Scanner;

import project.ConnectionProvider;
import project.SocketClient;
import DAO.UserDAO;

public class UserService {
	Scanner sc= new Scanner(System.in);
	Connection conn = ConnectionProvider.getConnection();

	//로그인
	public void showLogin(SocketClient sender, String id, String password) {
		UserDAO userDAO= new UserDAO();
		//DB
		boolean result= userDAO.compareId(sender,id);
		if(result==true) {
			if (sender.userDTO.getUserId().equals(id) && sender.userDTO.getUserPassword().equals(password)) {
				sender.flag = true;
				sender.userID= sender.userDTO.getUserId();
			}else {
				sender.flag=false;
			}
		}else
			sender.flag=false;
		if (sender.flag == true) {
			userDAO.selectUserData(sender);
		}
	}
	
	//로그아웃
	public boolean showLogout() {
		return false;
	}
	
	//회원가입
	public void uploadJoin(SocketClient sender) {
		UserDAO userDAO= new UserDAO();
		//DB
		userDAO.uploadJoin(sender);
	}
	
	//회원탈퇴
	public void showWithdrawal(SocketClient sender ) {
		UserDAO userDAO= new UserDAO();
		//DB
		userDAO.showWithdrawal(sender);
		sender.userDTO.setUserId("");
		//로그아웃
		sender.flag=false;
	}
}
