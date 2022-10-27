package day03;

import java.sql.Connection;

public class ClassExit {
	
	
	public static void exit() {
		Connection conn = ConnectionProvider.getConnection();
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		System.out.println("** 종료 **");
		System.exit(0);
	}
}
