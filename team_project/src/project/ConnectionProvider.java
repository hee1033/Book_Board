package project;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionProvider {
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
	      Connection conn = null;
	      try {
	         conn = dataSource.getConnection();
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }
	      return conn;
	   }
	   
	   public static void main(String[] args) {
	      Connection conn = null;
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
	   }
}
