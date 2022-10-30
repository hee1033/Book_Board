package service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.json.JSONObject;

import DTO.DTOBook;
import DTO.DTOCategory;
import DTO.DTOCompany;
import day06.ConnectionProvider;
import day06.SocketClient;

public class CategoryService {
	Connection conn = ConnectionProvider.getConnection();
	static Scanner scan = new Scanner(System.in);
	DTOCategory dtoCategory;
	
	
	//카테고리 목록
	public void showCategory(String menuNo) {
		
		int categoryNum = Integer.parseInt(menuNo) * 10;

		// 카테고리 리스트 가져오기
		try {
			String sql = "" + "SELECT C.CATEGORY_NAME, B.BOOK_ID, B.BOOK_NAME " + "FROM CATEGORY C, BOOKS B "
					+ "WHERE C.CATEGORY_ID = B.CATEGORY_ID AND B.CATEGORY_ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql); 
			pstmt.setInt(1, categoryNum);
			ResultSet rs = pstmt.executeQuery();

			System.out.println("==============책 목록==============");
			System.out.println("카테고리\t 책 번호\t 책 이름");
			
			while (rs.next()) {
				DTOBook book = new DTOBook();
				DTOCategory category = new DTOCategory();

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
			String select = scan.nextLine();
			
			switch(select) {
			case "1" -> bookDetail();
			}
			
		} catch (SQLException e) {
			System.out.println("작동안됨");
			e.printStackTrace();
		}
	};
	public void bookDetail() {
		try {
			System.out.print("책 번호를 입력하세요 : ");
			int bookId = Integer.parseInt(scan.nextLine());
			String sql = "" + "SELECT B.BOOK_NAME, B.BOOK_STOCK, B.BOOK_PRICE, B.BOOK_SELLINGPRICE, B.BOOK_WRITER, C.COMPANY_NAME, BOOK_DATE, BOOK_CONTENT " + 
					"FROM BOOKS B, COMPANY C "+
					"WHERE B.COMPANY_ID = C.COMPANY_ID "+
					"AND B.BOOK_ID = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,bookId);
			ResultSet rs = pstmt.executeQuery();

			System.out.println("==============책 상세 정보==============");
			if (rs.next()) {

				DTOBook book = new DTOBook();
				DTOCompany company = new DTOCompany();
				
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
