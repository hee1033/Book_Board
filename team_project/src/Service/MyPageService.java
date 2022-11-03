package Service;

import java.util.Map;
import java.util.Scanner;

import project.SocketClient;
import DAO.UserDAO;

public class MyPageService {
	
	Scanner sc = new Scanner(System.in);
		// 마이페이지 개인정보 수정
		public void showUserUpdate(SocketClient sender, Map<String,String> map) {
			UserDAO daoUser = new UserDAO();
			String password = map.get("password");
			String name=map.get("name");
			String email=map.get("email");
			String address=map.get("address");
			String tel=map.get("tel");
			int age=Integer.parseInt(map.get("age"));
			
			
			// 비밀번호 수정
			if (password.equals("n")) {
				sender.getUserDTO().setUserPassword(sender.getUserDTO().getUserPassword());
			} else {
				sender.getUserDTO().setUserPassword(password);
			}
			// 이름 수정
			if (name.equals("n")) {
				sender.getUserDTO().setUserName(sender.getUserDTO().getUserName());
			} else {
				sender.getUserDTO().setUserName(name);
			}
			// 이메일 수정
			if (email.equals("n")) {
				sender.getUserDTO().setUserEmail(sender.getUserDTO().getUserEmail());
			} else {
				sender.getUserDTO().setUserEmail(email);
			}
			// 주소 수정
			if (address.equals("n")) {
				sender.getUserDTO().setUserAddress(sender.getUserDTO().getUserAddress());
			} else {
				sender.getUserDTO().setUserAddress(address);
			}
			// 전화번호 수정
			if (tel.equals("n")) {
				sender.getUserDTO().setUserTel(sender.getUserDTO().getUserTel());
			} else {
				sender.getUserDTO().setUserTel(tel);
			}
			// 나이 수정
			if (age == 0) {
				sender.getUserDTO().setUserAge(sender.getUserDTO().getUserAge());
			} else {
				sender.getUserDTO().setUserAge(age);
			}
			
			//List<DTOUser> list = new ArrayList<>();
			//list.add(dtoUser);
			
			daoUser.showUserUpdate(sender);
		}
		
		public void showOrderList() {
			
		}
		
		public void showCart() {

		}
		

}
