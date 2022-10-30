package day06;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider {
	private static Connection conn;
	
	public static Connection getConnection() {
		conn = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@kosa402.iptime.org:50041/orcl", 
	        		 "TEAM4", 
	        		 "oracle");
//			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:50041/orcl",
//					"TEAM4", 
//					"oracle");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("실패");
		}
		return conn;
	}
}
