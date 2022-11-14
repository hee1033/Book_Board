package project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import project.ConnectionProvider;
import project.SocketClient;
import project.DTO.UserDTO;

public class UserDAO {
	Connection conn = ConnectionProvider.getConnection();
	
	public UserDTO selectUserData(SocketClient sender){
		UserDTO userDto = new UserDTO();
		try {
			String sql="select users_id, users_password, users_name, users_email, "
					+ "users_address, users_tel, users_indate, users_age, users_sex from users where users_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sender.userID);
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
			rs.close();
			pstmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return userDto;
	}
	
	//회원 정보 업로드
	public boolean updateUserUpdate(UserDTO userDto) {
		boolean result=false;
		
		try {
			String sql = "update users set users_password =?, users_name=?, users_email=?, "
					+ "users_address=?, users_tel=?, users_age=? where users_id=? ";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,userDto.getUserPassword());
			pstmt.setString(2, userDto.getUserName());
			pstmt.setString(3, userDto.getUserEmail());
			pstmt.setString(4, userDto.getUserAddress());
			pstmt.setString(5, userDto.getUserTel());
			pstmt.setInt(6, userDto.getUserAge());
			pstmt.setString(7, userDto.getUserId());
			pstmt.executeUpdate();
			pstmt.close();
			result=true;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("DAOUser error");
			result=false;
		}
		return result;
	}
	
	//받아온 아이디와 sql문에 있는 값 비교
	public UserDTO selectCompareId(SocketClient sender, String id) {
		sender.flag = true;
		UserDTO userDto = new UserDTO();
		
		try {
			String sql = "select users_id, users_password from users where users_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				userDto.setUserId(rs.getString("users_id"));
				userDto.setUserPassword(rs.getString("users_password"));
			}
			else {
				sender.flag = false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return userDto;
	}
	
	//회원가입
	public void insertJoin(SocketClient sender, UserDTO userDto) {
		try {
			String sql = "insert into users values(?,?,?,?,?,?,sysdate,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userDto.getUserId());
			pstmt.setString(2, userDto.getUserPassword());
			pstmt.setString(3, userDto.getUserName());
			pstmt.setString(4, userDto.getUserEmail());
			pstmt.setString(5, userDto.getUserAddress());
			pstmt.setString(6, userDto.getUserTel());
			pstmt.setInt(7, userDto.getUserAge());
			pstmt.setString(8, userDto.getUserSex());
			pstmt.executeUpdate();
			pstmt.close();
			
			String sql2="insert into carts values(seq_cart_id.nextval, ?)";
			PreparedStatement pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setString(1, userDto.getUserId());
			pstmt2.executeUpdate();
			pstmt2.close();
			sender.flag=true;
		}catch(Exception e){
			//e.printStackTrace();
			sender.flag=false;
		}
	}
	
	//회원탈퇴
	public boolean deleteWithdrawal(SocketClient sender ) {
		boolean result=true;
		try {
			/*
			String sql1="delete from carts where users_id=?";
			PreparedStatement pstmt1 = conn.prepareStatement(sql1);
			pstmt1.setString(1, sender.userID);
			pstmt1.executeUpdate();
			pstmt1.close();
			
			String sql2="delete from orders where users_id=?";
			PreparedStatement pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setString(1, sender.userDTO.getUserId());
			pstmt2.executeUpdate();
			pstmt2.close();*/
			String sql="delete from users where users_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sender.userID);
			pstmt.executeUpdate();
			pstmt.close();
			result =false;		
		}catch (Exception e) {
			result=true;
			e.printStackTrace();
			System.out.println("UserDAO out error");
		}
		return result;
	}

	//모든유저 정보 
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
			//System.out.println(list);
			rs.close();
			pstmt.close();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("USerDAO AlluserData error");
		}
		return list;
	}
	
	//특정 유저 정보
	public UserDTO selectDetailData(String userId){
		
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
			rs.close();
			pstmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return userDto;
	}
	
}










