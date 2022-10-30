package day06;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import org.json.JSONObject;

import DTO.DTOUser;


public class MyPage {
	DTOUser user = new DTOUser();
	Scanner sc = new Scanner(System.in);
	Connection conn = ConnectionProvider.getConnection();
	
	// 마이페이지 개인정보 수정
		public void userUpdate(SocketClient sender, JSONObject jsonUpdate) {
	
			String password = jsonUpdate.getString("password");
			String name=jsonUpdate.getString("name");
			String email=jsonUpdate.getString("email");
			String address=jsonUpdate.getString("address");
			String tel=jsonUpdate.getString("tel");
			int age= jsonUpdate.getInt("age");
			try {
				String sql = "update users set users_password =?, users_name=?, users_email=?, "
						+ "users_address=?, users_tel=?, users_age=? where users_id=? ";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				// 비밀번호 수정
				if (password.equals("n")) {
					pstmt.setString(1, user.getUserPassword());
					user.setUserPassword(user.getUserPassword());
				} else {
					pstmt.setString(1, password);
					user.setUserPassword(password);
				}
				// 이름 수정
				if (name.equals("n")) {
					pstmt.setString(2, user.getUserName());
					user.setUserName(user.getUserName());
				} else {
					pstmt.setString(2, name);
					user.setUserName(name);
				}
				// 이메일 수정
				if (email.equals("n")) {
					pstmt.setString(3, user.getUserEmail());
					user.setUserEmail(user.getUserEmail());
				} else {
					pstmt.setString(3, email);
					user.setUserEmail(email);
				}
				// 주소 수정
				if (address.equals("n")) {
					pstmt.setString(4, user.getUserAddress());
					user.setUserAddress(user.getUserAddress());
				} else {
					pstmt.setString(4, address);
					user.setUserAddress(address);
				}
				// 전화번호 수정
				if (tel.equals("n")) {
					pstmt.setString(5, user.getUserTel());
					user.setUserTel(user.getUserTel());
				} else {
					pstmt.setString(5, tel);
					user.setUserTel(tel);
				}
				// 나이 수정
				if (age == 0) {
					pstmt.setInt(6, user.getUserAge());
					user.setUserAge(user.getUserAge());
				} else {
					pstmt.setInt(6, age);
					user.setUserAge(age);
				}
				pstmt.setString(7, user.getUserId());
				pstmt.executeUpdate();
				pstmt.close();
				JSONObject root=new JSONObject();
				root.put("check", "complete");
				root.put("message", "수정이 완료되었습니다.");
				root.put("choice","first");
				String json = root.toString();
				sender.send(json);
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("MyPage UpdateUSer error");
			}
		}
		public void orderList() {
			
		}
		public void cart() {

		}
		

}