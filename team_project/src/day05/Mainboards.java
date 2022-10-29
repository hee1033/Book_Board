package day05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Mainboards {
	 //Filed
	   private Scanner sc = new Scanner(System.in);
	   private Connection conn;
	   Users users;
	   boolean flag;

	   //Constructor
	   //Constructor
	   public Mainboards() {
	      try {
	         // JDBC Driver 등록
	         Class.forName("oracle.jdbc.OracleDriver");

	         // DB 연결하기
	         conn = DriverManager.getConnection("jdbc:oracle:thin:@kosa402.iptime.org:50041/orcl", 
	        		 "TEAM4", 
	        		 "oracle");
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	         exit();
	      }
	   }
	   
	   //메인보드
	   public void homepage() {
	      System.out.println();
	      System.out.println("===========================================================================");
	      System.out.println("                          미니 북 서점                                    ");
	      System.out.println("1.Category | 2.Best Seller | 3.Event | 4.Search | 5.My Page | 6.QnA | 7.Exit ");
	      System.out.println("===========================================================================");
	      System.out.print("선택 : ");
	      String listNo = sc.nextLine();
	      System.out.println();

	      if(flag==false) {
	    	  switch(listNo) {
		      case "1" -> category();
		      case "2" -> bestSeller();
		      case "3" -> event();
		      case "4" -> search();
		      case "5" -> login();
		      case "6" -> qna();
		      case "7" -> exit();
		      }
	      }else {
	    	  switch(listNo) {
		      case "1" -> category();
		      case "2" -> bestSeller();
		      case "3" -> event();
		      case "4" -> search();
		      case "5" -> mypage();
		      case "6" -> qna();
		      case "7" -> exit();
		      }
	      }
	      
	   }
	   
	   //로그인
	   public void login() {
		  users = new Users();
	      System.out.println("==========로그인==========");
	      System.out.print("ID : ");
	      String id = sc.nextLine();
	      System.out.print("PASSWORD : ");
	      String password = sc.nextLine();
	      try {
	         String sql="select users_id, users_password from users where users_id=?";
	         PreparedStatement pstmt=conn.prepareStatement(sql);
	         pstmt.setString(1, id);
	         ResultSet rs=pstmt.executeQuery();
	         if(rs.next()) {
	        	 users.setUsersId(rs.getString("users_id"));
	        	 users.setUsersPassword(rs.getString("users_password"));
	         }
	         if(users.getUsersId().equals(id)&&users.getUsersPassword().equals(password)) {
	        	 flag=true;
	         }else {
	        	 System.out.println("아이디 또는 비밀번호가 틀렸습니다.");
	        	 login();
	         }
	         
	         if(flag=true) {
	        	 String sql1="select users_id, users_password, users_name, users_email, users_address,"
	        	 		+ "users_tel, users_indate, users_age, users_sex from users where users_id=?";
	        	 PreparedStatement pstmt1=conn.prepareStatement(sql1);
	        	 pstmt1.setString(1, id);
	        	 ResultSet rs1= pstmt1.executeQuery();
	        	 if(rs1.next()) {
	        		 users.setUsersId(rs1.getString("users_id"));
	        		 users.setUsersPassword(rs1.getString("users_password"));
	        		 users.setUsersName(rs1.getString("users_name"));
	        		 users.setUsersEmail(rs1.getString("users_email"));
	        		 users.setUsersAddress(rs1.getString("users_address"));
	        		 users.setUsersTel(rs1.getString("users_tel"));
	        		 users.setUsersIndate(rs1.getDate("users_indate"));
	        		 users.setUsersAge(rs1.getInt("users_age"));
	        		 users.setUsersSex(rs1.getString("users_sex"));
	        	 }
	        	 rs1.close();
	        	 pstmt1.close();
	         }
	         rs.close();
	         pstmt.close();
	         mypage();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }
	
	private Object category() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object bestSeller() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object event() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object search() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//마이페이지
	public void mypage() {
		System.out.println("["+users.getUsersId()+"'s 페이지]");
		System.out.println("-----------------------------------");
//		System.out.println(users);
		System.out.println("1.개인정보 수정 | 2.구매내역 | 3.장바구니 | 4.로그아웃 | 5.회원탈퇴");
		System.out.print("선택 : ");
		String listNo=sc.nextLine();
		
		switch(listNo) {
		case "1" -> userUpdate();
		case "2" -> orderList();
		case "3" -> cart();
		case "4" -> logout();
		case "5" -> outUser();
		}
	}
	//마이페이지 개인정보 수정
	public void userUpdate() {
		System.out.println("[개인정보수정]");
		try {
			String sql="update users set users_password =?, users_name=?, users_email=?, "
					+ "users_address=?, users_tel=?, users_age=? where users_id=? ";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			//비밀번호 수정
			System.out.print("수정 비밀번호(수정을 원하지않으면 n) = ");
			String password = sc.nextLine();
			if(password.equals("n")) {
				pstmt.setString(1, users.getUsersPassword());
				users.setUsersPassword(users.getUsersPassword());
			}else {
				pstmt.setString(1, password);
				users.setUsersPassword(password);
			}
			//이름 수정
			System.out.print("수정 이름(수정을 원하지않으면 n) = ");
			String name = sc.nextLine();
			if(name.equals("n")) {
				pstmt.setString(2, users.getUsersName());
				users.setUsersName(users.getUsersName());
			}else {
				pstmt.setString(2, name);
				users.setUsersName(name);
			}
			//이메일 수정
			System.out.print("수정 이메일(수정을 원하지않으면 n) = ");
			String email = sc.nextLine();
			if(email.equals("n")) {
				pstmt.setString(3, users.getUsersEmail());
				users.setUsersEmail(users.getUsersEmail());
			}else {
				pstmt.setString(3, email);
				users.setUsersEmail(email);
			}
			//주소 수정
			System.out.print("수정 주소(수정을 원하지않으면 n) = ");
			String address = sc.nextLine();
			if(address.equals("n")) {
				pstmt.setString(4, users.getUsersAddress());
				users.setUsersAddress(users.getUsersAddress());
			}else {
				pstmt.setString(4,address);
				users.setUsersAddress(address);
			}
			//전화번호 수정
			System.out.print("수정 전화번호(수정을 원하지않으면 n) = ");
			String tel = sc.nextLine();
			if(tel.equals("n")) {
				pstmt.setString(5, users.getUsersTel());
				users.setUsersTel(users.getUsersTel());
			}else {
				pstmt.setString(5, tel);
				users.setUsersTel(tel);
			}
			//나이 수정
			System.out.print("수정 나이(수정을 원하지않으면 0) = ");
			int age = Integer.parseInt(sc.nextLine());
			if(age==0) {
				pstmt.setInt(6, users.getUsersAge());
				users.setUsersAge(users.getUsersAge());
			}else {
				pstmt.setInt(6, age);
				users.setUsersAge(age);
			}	
			pstmt.setString(7, users.getUsersId());
			pstmt.executeUpdate();
			pstmt.close();
		}catch(Exception e){
			e.printStackTrace();
			exit();
		}	
		
	}
	//마이페이지 구매내역 조회
	public void orderList() {
		Order order =new Order();
		System.out.println("[구매내역]");
		try {
			String sql="select orders_date, book_name, book_amount, users_id, orders_address, orders_person" //orders_receiver
					+ " from orders o, detail_order d, books b "
					+ "where o.orders_id=d.orders_id and d.book_id=b.book_id, o.users_id=? "
					+ "order by orders_date desc ";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, users.getUsersId());
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				order.setOrdersDate(rs.getDate("orders_date"));
				order.setBookName(rs.getString("book_name"));
				order.setBookAmount(rs.getInt("book_amount"));
				System.out.println(order.getOrdersDate());
				System.out.println(order.getBookName());
				System.out.println(order.getBookAmount());
			}
			rs.close();
			pstmt.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			exit();
		}
		
	}

	public void cart() {
		
	}

	public void logout() {
		
	}

	public void outUser() {
		
	}

	private Object qna() {
		// TODO Auto-generated method stub
		return null;
	}

	private void exit() {
		// TODO Auto-generated method stub
		
	}


	public static void main(String[] args) {
		int a=0;
		Mainboards project=new Mainboards();
		while(a<3) {
			project.homepage();
			a++;
			
		}

	}

}
