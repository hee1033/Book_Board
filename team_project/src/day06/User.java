package day06;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import org.json.JSONObject;

import DTO.DTOUser;

public class User {
	Scanner sc= new Scanner(System.in);
	Connection conn = ConnectionProvider.getConnection();
	
	
	public void login(SocketClient sender , JSONObject jsonUpdata) {
		//jsonUpdata 파싱
		String id=jsonUpdata.getString("id");
		String password=jsonUpdata.getString("password");
		
		try {
			String sql = "select users_id, users_password from users where users_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				sender.userD.setUserId(rs.getString("users_id"));
				sender.userD.setUserPassword(rs.getString("users_password"));
			}
			
			//입력받은 아이디와 비밀번호가 같은지확인
			if (sender.userD.getUserId().equals(id) && sender.userD.getUserPassword().equals(password)) {
				sender.flag = true;
			} else {
				JSONObject root= new JSONObject();
				root.put("check", "complete");
				root.put("flag", false);
				root.put("choice", "x");
				root.put("message","아이디 또는 비밀번호가 틀렸습니다.");
				String json=root.toString();
				sender.send(json);
			}
			
			if (sender.flag == true) {
				String sql1 = "select users_id, users_password, users_name, users_email, users_address,"
						+ "users_tel, users_indate, users_age, users_sex from users where users_id=?";
				PreparedStatement pstmt1 = conn.prepareStatement(sql1);
				pstmt1.setString(1, id);
				ResultSet rs1 = pstmt1.executeQuery();
				if (rs1.next()) {
					sender.userD.setUserId(rs1.getString("users_id"));
					sender.userD.setUserPassword(rs1.getString("users_password"));
					sender.userD.setUserName(rs1.getString("users_name"));
					sender.userD.setUserEmail(rs1.getString("users_email"));
					sender.userD.setUserAddress(rs1.getString("users_address"));
					sender.userD.setUserTel(rs1.getString("users_tel"));
					sender.userD.setUserIndate(rs1.getDate("users_indate"));
					sender.userD.setUserAge(rs1.getInt("users_age"));
					sender.userD.setUserSex(rs1.getString("users_sex"));
				}
				rs1.close();
				pstmt1.close();
				//확인을 위한 toString DB연결 성공
//				System.out.println(sender.userD.toString());
				JSONObject root= new JSONObject();
				root.put("check", "complete");
				root.put("choice", "first");
				root.put("flag", sender.flag);
				root.put("message","로그인에 성공하셨습니다.");
				String json=root.toString();
				sender.send(json);
			}
			rs.close();
			pstmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//로그아웃
	public void logout(SocketClient sender) {
		sender.flag=false;
		try {
			JSONObject root= new JSONObject();
			root.put("check", "complete");
			root.put("choice", "first");
			root.put("flag", sender.flag);
			root.put("message","로그아웃 되었습니다.");
			String json=root.toString();
			sender.send(json);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("User logout error");
		}
		
	}
	//회원가입
	public void in(SocketClient sender , JSONObject jsonUpdata) {
		String id=jsonUpdata.getString("id");
		String password=jsonUpdata.getString("password");
		String name=jsonUpdata.getString("name");
		String email=jsonUpdata.getString("email");
		String address=jsonUpdata.getString("address");
		String tel=jsonUpdata.getString("tel");
		int age=jsonUpdata.getInt("age");
		String sex=jsonUpdata.getString("sex");
		
		boolean flag1=false;
		
		try {
			String sql="select users_id from users";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String usersID=rs.getString("users_id");
				//같은아이디가 있다면 다시 
				
				//만약 같은 아이디가 없다면 
				flag1=true;
			}
			if(flag1==true) {
				String sql1="insert into users values(?,?,?,?,?,?,stsdate,?,?)";
				PreparedStatement pstmt1=conn.prepareStatement(sql1);
				pstmt1.setString(1, id);
				pstmt1.setString(2, password);
				pstmt1.setString(3, name);
				pstmt1.setString(4, email);
				pstmt1.setString(5, address);
				pstmt1.setString(6, tel);
				pstmt1.setInt(7, age);
				pstmt1.setString(8, sex);
				pstmt1.executeUpdate();
				pstmt1.close();
			}
			rs.close();
			pstmt.close();
			JSONObject root= new JSONObject();
			root.put("check", "complete");
			//바로 로그인 화면으로 가야되는데...
			root.put("choice", "first");
			root.put("flag", sender.flag);
			root.put("message","회원가입이 완료되었습니다.\n로그인 후 사용 하십시오.");
			String json=root.toString();
			sender.send(json);
			
		}catch(Exception e){
			
		}
	}
	//회원탈퇴
	public void out() {
		
	}
}
