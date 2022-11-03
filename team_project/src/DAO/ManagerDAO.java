package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import project.ConnectionProvider;
import project.SocketClient;

public class ManagerDAO {
	Connection conn = ConnectionProvider.getConnection();
	
	public boolean showManagerLogin(SocketClient sender, String id) {
		boolean result=true;
		try {
			String sql = "select managers_id, managers_password from managers where managers_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				sender.managerDTO.setManagerID(rs.getString("managers_id"));
				sender.managerDTO.setManagerPassword(rs.getString("managers_password"));
			}else {
				result=false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return result;
	}
	

}
