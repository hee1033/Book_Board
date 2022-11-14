package project.Service;


import java.sql.Connection;
import java.util.Scanner;

import project.ConnectionProvider;
import project.SocketClient;
import project.DAO.UserDAO;
import project.DTO.UserDTO;

public class UserService {
	Scanner sc= new Scanner(System.in);
	Connection conn = ConnectionProvider.getConnection();

	//로그인
	public void showLogin(SocketClient sender, String id, String password) {
		UserDAO userDAO= new UserDAO();
		UserDTO userDto = new UserDTO();
		userDto = userDAO.selectCompareId(sender,id);
		if(sender.flag==true) {
			if (userDto.getUserId().equals(id) && userDto.getUserPassword().equals(password)) {
				sender.userID=userDto.getUserId();
			}else {
				sender.flag=false;
			}
		}else
			sender.flag=false;
	}
	
	//로그아웃
	public boolean showLogout() {
		return false;
	}
	
	//회원가입
	public void uploadJoin(SocketClient sender, UserDTO userDto) {
		UserDAO userDAO= new UserDAO();
		userDAO.insertJoin(sender, userDto);
	}
	
	//회원탈퇴
	public boolean showWithdrawal(SocketClient sender ) {
		UserDAO userDAO= new UserDAO();
		boolean result=userDAO.deleteWithdrawal(sender);
		sender.userID="";
		return result;
	}
}
