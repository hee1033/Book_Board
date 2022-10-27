package day04;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BoardService {
	public List<MainBoards> getList() {
		
		Connection conn = null;
		
		try {
		
			conn = ConnectionProvider.getConnection();
			
			
		} catch(Exception e) {
			
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
