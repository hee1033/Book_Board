package day03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import DTO.Books;
import DTO.Category;
import DTO.Company;

public class ClassCategoery {
	
	static Scanner scan = new Scanner(System.in);
	
	public static void category() {
		System.out.println("[카테고리]");
		System.out.println("1.소설 | 2.시/에세이 | 3. 인문 | 4. 컴퓨터/IT | 5.가정/육아\n6.요리 | 7.건강 | 8.취미/실용/스포츠 | 9.경제/경영 | 10.자기계발");
		System.out.println();
		System.out.print("선택 : ");
		int categoryId = Integer.parseInt(scan.nextLine()) * 10;
		System.out.println();
		
		// 카테고리 리스트 가져오기
		try {
			String sql = "" + "SELECT C.CATEGORY_NAME, B.BOOK_ID, B.BOOK_NAME " + "FROM CATEGORY C, BOOKS B "
					+ "WHERE C.CATEGORY_ID = B.CATEGORY_ID AND B.CATEGORY_ID = ?";
			Connection conn = ConnectionProvider.getConnection();
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
			String menuNo = scan.nextLine();
			
			switch(menuNo) {
			case "1" -> bookDetail();
			case "2" -> ClassMenu.menu();
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	};
	
	//책 상세내용
	public static void bookDetail() {
		try {
			System.out.print("책 번호를 입력하세요 : ");
			int bookId = Integer.parseInt(scan.nextLine());
			String sql = "" + "SELECT B.BOOK_NAME, B.BOOK_STOCK, B.BOOK_PRICE, B.BOOK_SELLINGPRICE, B.BOOK_WRITER, C.COMPANY_NAME, BOOK_DATE, BOOK_CONTENT " + 
					"FROM BOOKS B, COMPANY C "+
					"WHERE B.COMPANY_ID = C.COMPANY_ID "+
					"AND B.BOOK_ID = ?";
			Connection conn = ConnectionProvider.getConnection();
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
}
