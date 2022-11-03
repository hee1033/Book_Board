package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import project.ConnectionProvider;
import project.SocketClient;
import DTO.UserDTO;

public class UserDAO {
	Connection conn = ConnectionProvider.getConnection();
	
	//회원 정보 업로드
	public void showUserUpdate(SocketClient sender) {
		try {
			String sql = "update users set users_password =?, users_name=?, users_email=?, "
					+ "users_address=?, users_tel=?, users_age=? where users_id=? ";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,sender.getUserDTO().getUserPassword());
			pstmt.setString(2, sender.getUserDTO().getUserName());
			pstmt.setString(3, sender.getUserDTO().getUserEmail());
			pstmt.setString(4, sender.getUserDTO().getUserAddress());
			pstmt.setString(5, sender.getUserDTO().getUserTel());
			pstmt.setInt(6, sender.getUserDTO().getUserAge());
			pstmt.setString(7, sender.getUserDTO().getUserId());
			pstmt.executeUpdate();
			pstmt.close();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("DAOUser error");
		}
	}
	
	//받아온 아이디와 sql문에 있는 값 비교
	public boolean compareId(SocketClient sender, String id) {
		boolean result = true;
		try {
			String sql = "select users_id, users_password from users where users_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				sender.getUserDTO().setUserId(rs.getString("users_id"));
				sender.getUserDTO().setUserPassword(rs.getString("users_password"));
			}
			else {
				result = false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	//로그인 성공후 데이터 insert
	public void selectUserData(SocketClient sender) {
		try {
			String sql = "select users_id, users_password, users_name, users_email, users_address,"
					+ "users_tel, users_indate, users_age, users_sex from users where users_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sender.getUserDTO().getUserId());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				sender.getUserDTO().setUserId(rs.getString("users_id"));
				sender.getUserDTO().setUserPassword(rs.getString("users_password"));
				sender.getUserDTO().setUserName(rs.getString("users_name"));
				sender.getUserDTO().setUserEmail(rs.getString("users_email"));
				sender.getUserDTO().setUserAddress(rs.getString("users_address"));
				sender.getUserDTO().setUserTel(rs.getString("users_tel"));
				sender.getUserDTO().setUserIndate(rs.getDate("users_indate"));
				sender.getUserDTO().setUserAge(rs.getInt("users_age"));
				sender.getUserDTO().setUserSex(rs.getString("users_sex"));
			}
			rs.close();
			pstmt.close();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("UserDAO insert error");
		}
		
	}
	
	//회원가입
	public void uploadJoin(SocketClient sender) {
		try {
			String sql = "insert into users values(?,?,?,?,?,?,sysdate,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sender.getUserDTO().getUserId());
			pstmt.setString(2, sender.getUserDTO().getUserPassword());
			pstmt.setString(3, sender.getUserDTO().getUserName());
			pstmt.setString(4, sender.getUserDTO().getUserEmail());
			pstmt.setString(5, sender.getUserDTO().getUserAddress());
			pstmt.setString(6, sender.getUserDTO().getUserTel());
			pstmt.setInt(7, sender.getUserDTO().getUserAge());
			pstmt.setString(8, sender.getUserDTO().getUserSex());
			pstmt.executeUpdate();
			pstmt.close();
			pstmt.close();
			sender.flag=true;
		}catch(Exception e){
//			e.printStackTrace();
			sender.flag=false;
		}
	}
	
	//회원탈퇴
	public void showWithdrawal(SocketClient sender ) {
		try {
			String sql="delete from users where users_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sender.getUserDTO().getUserId());
			pstmt.executeUpdate();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserDAO out error");
		}
		
	}

	public List<UserDTO> selectAllUserData() {
		List<UserDTO> list =new ArrayList<>();
		try {
			String sql="select users_id, users_name from users ";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {	
				UserDTO userDto = new UserDTO();
				userDto.setUserId(rs.getString("users_id"));
				userDto.setUserName(rs.getString("users_name"));
				list.add(userDto);
			}
			System.out.println(list);
			rs.close();
			pstmt.close();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("USerDAO AlluserData error");
		}
		return list;
	}
	
	public UserDTO selectDetailData(SocketClient sender, String userId){
		UserDTO userDto = new UserDTO();
		try {
			String sql="select users_id, users_password, users_name, users_email, "
					+ "users_address, users_tel, users_indate, users_age, users_sex from users where users_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				userDto.setUserId(rs.getString("users_id"));
				userDto.setUserPassword(rs.getString("users_password"));
				userDto.setUserName(rs.getString("users_name"));
				userDto.setUserEmail(rs.getString("users_email"));
				userDto.setUserAddress(rs.getString("users_address"));
				userDto.setUserTel(rs.getString("users_tel"));
				userDto.setUserIndate(rs.getDate("users_indate"));
				userDto.setUserAddress(rs.getString("users_age"));
				userDto.setUserSex(rs.getString("users_sex"));
			}
			sender.mflag=true;
			rs.close();
			pstmt.close();
		}catch(Exception e) {
			sender.mflag=false;
			e.printStackTrace();
		}
		return userDto;
	}
	
}










