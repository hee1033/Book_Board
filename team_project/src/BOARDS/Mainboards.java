package BOARDS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Mainboards {
	
	//Filed
	private Scanner sc = new Scanner(System.in);
	private Connection conn;

	//Constructor
	public Mainboards() {
		try {
			// JDBC Driver 등록
			Class.forName("oracle.jdbc.OracleDriver");

			// DB 연결하기
			conn = DriverManager.getConnection("jdbc:oracle:thin:@kosa402.iptime.org:50041/orcl", "TEAM4", "oracle");
			
		} catch (Exception e) {
			e.printStackTrace();
			exit();
		}
	}
	
	//홈페이지
	public void homepage() {
		System.out.println();
		System.out.println("=======================================================================");
		System.out.println("                          미니 북 서점                                    ");
		System.out.println("                        1.로그인 | 2. 회원가입                             ");
		System.out.println("=======================================================================");
		System.out.print("선택 : ");
		String loginNo = sc.nextLine();
		System.out.println();
		
		switch(loginNo) {
		case "1" -> login();
		case "2" -> signUp();
		}
		
		
		menu();
	}
	
	//로그인
	public void login() {
		Users user = new Users();
		System.out.println("==========로그인==========");
		System.out.print("ID : ");
		String id = sc.nextLine();
		System.out.print("PASSWORD : ");
		String password = sc.nextLine();
		
		
//		try {
//			String sql = ""+
//					"SELECT users_Id, users_password "+"FROM USERS "+ "WHERE users_id=?, users_password=? ";
//			PreparedStatement pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1,id);
//			pstmt.setString(2, password);
//			ResultSet rs = pstmt.executeQuery();
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		
		

	}
	
	//회원가입
	public void signUp() {
		Users user = new Users();
		System.out.print("(필수)아이디를 입력하세요\nID :");
		user.setUsers_id(sc.nextLine());
		System.out.print("(필수)비밀번호를 입력하세요\nPASSWORD : ");
		user.setUsers_password(sc.nextLine());
		System.out.print("(필수)이름을 입력하세요 : ");
		user.setUsers_name(sc.nextLine());
		System.out.print("(필수)이메일을 입력하세요 : ");
		user.setUsers_email(sc.nextLine());
		System.out.print("(필수)주소를 입력하세요 : ");
		user.setUsers_address(sc.nextLine());
		System.out.print("(필수)전화번호를 입력하세요 : ");
		user.setUsers_tel(sc.nextLine());
		System.out.print("나이를 입력하세요 : ");
		user.setUsers_age(Integer.parseInt(sc.nextLine()));
		System.out.print("성별을 입력하세요(man or woman) : ");
		user.setUsers_sex(sc.nextLine());
		
		String sql = ""+
				"INSERT INTO users (users_id, users_password, users_name, users_email, users_address, users_tel, users_indate, users_age, users_sex) "
				+ "VALUES (?, ?, ?, ?, ?, ?, SYSDATE, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsers_id());
			pstmt.setString(2, user.getUsers_password());
			pstmt.setString(3, user.getUsers_name());
			pstmt.setString(4, user.getUsers_email());
			pstmt.setString(5, user.getUsers_address());
			pstmt.setString(6, user.getUsers_tel());
			pstmt.setInt(7, user.getUsers_age());
			pstmt.setString(8, user.getUsers_sex());
			pstmt.executeUpdate();
			pstmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		login();
	}
	
	//메뉴
	public void menu() {
		System.out.println();
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("메인 메뉴 : 1.전체 | 2.베스트셀러 | 3.이벤트 | 4.검색 | 5.주문내역 | 6.Q&A | 7.종료");
		System.out.println("-----------------------------------------------------------------------");
		System.out.print("메뉴 선택: ");
		String menuNo = sc.nextLine();
		System.out.println();
		
		switch(menuNo) {
		case "1" -> all();
		case "2" -> bestSeller();
		case "3" -> event();
		case "4" -> serch();
		case "5" -> orderList();
		case "6" -> qna();
		case "7" -> exit();
		}
	}
	public void all() {};
	public void bestSeller() {};
	public void event() {};
	public void serch() {};
	public void orderList() {};
	public void qna() {};
	
	//종료
	public void exit() {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		System.out.println("** 종료 **");
		System.exit(0);
	}

	public static void main(String[] args) {
		Mainboards mb = new Mainboards();
		mb.homepage();
	}
}
