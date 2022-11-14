
package project;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider {
	private static Connection conn;
	
	//방법1
	/*public static Connection getConnection() {
		if(conn == null) {
			try {
				Class.forName("oracle.driver.OrcleDriver");
				conn = DriverManager.getConnection("jdbc:oracle:thin:@kosa402.iptime.org:50011/orcl", "project1", "oracle");
			} catch(Exception e) {
				e.printStackTrace();
			}
		return conn;
	}*/
	
	//방법2
	/*public static synchronized Connection getConnection() {
		if(conn == null) {
			try {
				Class.forName("oracle.driver.OrcleDriver");
				conn = DriverManager.getConnection("jdbc:oracle:thin:@kosa402.iptime.org:50011/orcl", "project1", "oracle");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return conn;
	}*/
	
	//방법3
	/*static {
		try {
			Class.forName("oracle.driver.OrcleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@kosa402.iptime.org:50011/orcl", "project1", "oracle");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return conn;
	}*/
	
	
	public static Connection getConnection() {
		conn = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@kosa402.iptime.org:50041/orcl", "TEAM4", "oracle");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	/*
	//방법5
	private static BasicDataSource dataSource;
	static {
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@kosa402.iptime.org:50041/orcl");
		dataSource.setUsername("TEAM4");
		dataSource.setPassword("oracle");
		dataSource.setMaxTotal(5);
		dataSource.setInitialSize(1);
		dataSource.setMaxIdle(1);
	}
	
	public static Connection getConnection() {
		conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void main(String[] args) {
		conn = null;
		try {
			//Connection 대여
			conn = ConnectionProvider.getConnection();
			System.out.println("대여 성공");
		} catch(Exception e) {
			
		} finally {
			try {
				//Connection 반납
				conn.close();
				System.out.println("반납 성공");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}*/
}
