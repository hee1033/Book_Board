package day03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

import DTO.Books;
import DTO.Category;
import DTO.Company;
import DTO.Search;
import DTO.Users;

public class MainBoards {

	// Filed
	private Scanner sc = new Scanner(System.in);
	private Connection conn;

	// Constructor
	public MainBoards() {
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

	// 홈페이지
	public void homepage() {
		System.out.println();
		System.out.println("=======================================================================");
		System.out.println("                          미니 북 서점                                    ");
		System.out.println("                         [ 베스트 셀러 ]                                    ");
		System.out.println("                          [ 오늘의 책 ]                                     ");
		System.out.println("=======================================================================");
		System.out.println();

		menu();
	}

	// 로그인
    public void login() {
        Users users = new Users();
        System.out.println("==========로그인==========");
        System.out.print("ID : ");
        String id = sc.nextLine();
        System.out.print("PASSWORD : ");
        String password = sc.nextLine();
        try {
           String sql="{?=call users_login(?,?)}";
           CallableStatement cstmt=conn.prepareCall(sql);
           cstmt.registerOutParameter(1, Types.INTEGER);
           cstmt.setString(2, id);
           cstmt.setString(3, password);
           
           cstmt.execute();
           cstmt.close();
        } catch (Exception e) {
           e.printStackTrace();
        }
     }

	// 회원가입

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

		String sql = ""
				+ "INSERT INTO users (users_id, users_password, users_name, users_email, users_address, users_tel, users_indate, users_age, users_sex) "
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

	// 메뉴
	public void menu() {
		System.out.println();
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("메인 메뉴 : 1.카테고리 | 2.베스트셀러 | 3.이벤트 | 4.검색 | 5.마이페이지 | 6.Q&A | 7.종료");
		System.out.println("---------------------------------------------------------------------------");
		System.out.print("메뉴 선택: ");
		String menuNo = sc.nextLine();
		System.out.println();

		switch (menuNo) {
		case "1" -> category();
		case "2" -> bestSeller();
		case "3" -> event();
		case "4" -> search();
		case "5" -> myPage();
		case "6" -> qna();
		case "7" -> exit();
		}
	}

	public void category() {
		System.out.println("[카테고리]");
		System.out.println("1.소설 | 2.시/에세이 | 3. 인문 | 4. 컴퓨터/IT | 5.가정/육아\n6.요리 | 7.건강 | 8.취미/실용/스포츠 | 9.경제/경영 | 10.자기계발");
		System.out.println();
		System.out.print("선택 : ");
		int categoryId = Integer.parseInt(sc.nextLine()) * 10;
		System.out.println();
		
		// 카테고리 리스트 가져오기
		try {
			String sql = "" + "SELECT C.CATEGORY_NAME, B.BOOK_ID, B.BOOK_NAME " + "FROM CATEGORY C, BOOKS B "
					+ "WHERE C.CATEGORY_ID = B.CATEGORY_ID AND B.CATEGORY_ID = ?";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, categoryId);
			ResultSet rs = pstmt.executeQuery();

			System.out.println("==============책 목록==============");
			System.out.println("카테고리\t 책 번호\t 책 이름");
			while (rs.next()) {

				Books book = new Books();
				Category category = new Category();

				book.setBookName(rs.getString("BOOK_NAME"));
				book.setBookId(rs.getInt("BOOK_ID"));
				category.setCategoryName(rs.getString("CATEGORY_NAME"));

				System.out.println(category.getCategoryName() + "\t " +book.getBookId()+"\t "+ book.getBookName());

			}
			System.out.println("================================");
			System.out.println("--------------------------------");
			System.out.println("1.책 선택 | 2.메인 페이지로 돌아가기");
			System.out.println("--------------------------------");
			System.out.print("선택 : ");
			String menuNo = sc.nextLine();
			
			switch(menuNo) {
			case "1" -> bookDetail();
			case "2" -> menu();
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	};
	
	//책 상세내용
	public void bookDetail() {
		try {
			System.out.print("책 번호를 입력하세요 : ");
			int bookId = Integer.parseInt(sc.nextLine());
			String sql = "" + "SELECT B.BOOK_NAME, B.BOOK_STOCK, B.BOOK_PRICE, B.BOOK_SELLINGPRICE, B.BOOK_WRITER, C.COMPANY_NAME, BOOK_DATE, BOOK_CONTENT " + 
					"FROM BOOKS B, COMPANY C "+
					"WHERE B.COMPANY_ID = C.COMPANY_ID "+
					"AND B.BOOK_ID = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,bookId);
			ResultSet rs = pstmt.executeQuery();

			System.out.println("==============책 상세 정보==============");
			if (rs.next()) {

				Books book = new Books();
				Company company = new Company();
				
				book.setBookName(rs.getString("BOOK_NAME"));
				book.setBookStock(rs.getInt("BOOK_STOCK"));
				book.setBookPrice(rs.getInt("BOOK_PRICE"));
				book.setBookSellingPrice(rs.getInt("BOOK_SELLINGPRICE"));
				book.setBookWriter(rs.getString("BOOK_WRITER"));
				company.setCompanyName(rs.getString("COMPANY_NAME"));
				book.setBookDate(rs.getDate("BOOK_DATE"));
				book.setBookContent(rs.getString("BOOK_CONTENT"));
				
				System.out.println("책이름 : "+book.getBookName()+"\t 재고수량 : "+book.getBookStock()+"\n");
				System.out.println("책 원가 : "+book.getBookPrice()+"\t-> 할인가 : "+book.getBookSellingPrice()+"\n");
				System.out.println("저자 : "+book.getBookWriter()+"\t 출판사 : "+company.getCompanyName()+"\n");
				System.out.println("줄거리 : "+book.getBookContent());
				
			}
			rs.close();
			pstmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
			}
		
	};
	
	//베스트 셀러
	public void bestSeller() {
		System.out.println("[베스트셀러]");
		System.out.println("1.전체 베스트 셀러 | 2.연령별 베스트 셀러 | 3.성별 베스트 셀러");
		System.out.println("선택 : ");
		String menuNo = sc.nextLine();
		
		switch(menuNo){
		case "1" -> allBest();
		case "2" -> ageBest();
		case "3" -> sexBest();
		}
	};
	
	//전체 베스트 셀러 조회
	public void allBest() {
		
		
	}
	//연령별 베스트 셀러 조회
	public void ageBest() {
		
	}
	//성별 베스트 셀러 조회
	public void sexBest() {
		
	}
	
	
	
	public void event() {
	};
	
	//검색
	public void search() {
	      int idx = 1;
	      System.out.print("통합 검색: ");
	      String search = sc.nextLine();
	      System.out.println("=======================================================================");
	        System.out.println("책 번호\t|"+"책 이름\t"); 
	      System.out.println("=======================================================================");
	      String sql = "" +
	            "SELECT BOOK_ID, BOOK_NAME " +
	            "FROM BOOKS " +
	            "WHERE BOOK_NAME LIKE ?";
	      try {
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1,  '%' + search + '%');
	         ResultSet rs = pstmt.executeQuery();
	         while(rs.next()) {
	            Search sb = new Search();
	            sb.setBook_Id(rs.getInt("Book_Id"));
	            sb.setBook_Name(rs.getString("Book_Name"));
	            System.out.print(sb.getBook_Id() + "\t|");
	            System.out.println(sb.getBook_Name());
	         }
	         System.out.println("---------------------------------------------");
	         System.out.println("1.책 선택 | 2.다른 책 검색 | 3.메인 페이지로 돌아가기");
	         System.out.println("---------------------------------------------");
	         System.out.print("선택: ");
	         String menuNo = sc.nextLine();
	         switch(menuNo) {
	         case "1" -> bookDetail();
	         case "2" -> search();
	         case "3" -> menu();
	         }
	         
	         
	         
	         
	         
	         
	         rs.close();
	         pstmt.close();
	         
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }
	   };

	   
	public void myPage() {
	};

	public void qna() {
	};

	// 종료
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
		MainBoards mb = new MainBoards();
		mb.homepage();
	}
}
