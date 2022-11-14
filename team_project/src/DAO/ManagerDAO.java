package project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import project.ConnectionProvider;
import project.SocketClient;
import project.DTO.ManagerDTO;

public class ManagerDAO {
	Connection conn = ConnectionProvider.getConnection();
	
	public ManagerDTO selectManagerLogin(SocketClient sender, String id) {
		sender.mflag=true;
		ManagerDTO managerDto= new ManagerDTO();
		try {
			String sql = "select managers_id, managers_password "
					+ "from managers where managers_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				managerDto.setManagerID(rs.getString("managers_id"));
				managerDto.setManagerPassword(rs.getString("managers_password"));
			}else {
				sender.mflag=false;
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return managerDto;
	}
	

}
