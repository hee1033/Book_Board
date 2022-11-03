package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import Controller.CategoryController;
import DTO.BookDTO;
import DTO.Pager;
import project.ConnectionProvider;
import project.SocketClient;

public class CategoryDAO {

	Connection conn = ConnectionProvider.getConnection();

	// 카테고리 목록
	public List<BookDTO> showCategory(int categoryId,Pager pager) {
		
		List<BookDTO> bookList = new ArrayList<>();

		// 카테고리 리스트 가져오기
		try {
			String sql = "select rnum, category_name, book_id, book_name "
					+ "from( select rownum as rnum, book_name, book_id, category_name "
					+ "from ( select book_name, book_id, category_name "
					+ "from books b , category c "
					+ "where b.category_id=c.category_id "
					+ "and c.category_id=? order by book_id) where rownum <=? ) "
					+ "where rnum>=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, categoryId*10);
			pstmt.setInt(2, pager.getEndRowNo());
			pstmt.setInt(3, pager.getStartRowNo());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				BookDTO bookDTO = new BookDTO();
				bookDTO.setCategoryName(rs.getString("CATEGORY_NAME"));
				bookDTO.setBookId(rs.getInt("BOOK_ID"));
				bookDTO.setBookName(rs.getString("BOOK_NAME"));
				bookList.add(bookDTO);
			}
		} catch (SQLException e) {
			System.out.println("작동안됨");
			e.printStackTrace();
		}
		return bookList;
	}

	// 책 상세 정보
	public List<BookDTO> showbookDetail(int bookId) {
		CategoryController categoryController = new CategoryController();
		
		List<BookDTO> bookList = new ArrayList<>();
		try {
			String sql = ""
					+ "SELECT B.BOOK_NAME, B.BOOK_STOCK, B.BOOK_PRICE, B.BOOK_SELLINGPRICE, B.BOOK_WRITER, C.COMPANY_NAME, BOOK_CONTENT "
					+ "FROM BOOKS B, COMPANY C " + "WHERE B.COMPANY_ID = C.COMPANY_ID " + "AND B.BOOK_ID = ?";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				BookDTO bookDTO = new BookDTO();
				bookDTO.setBookName(rs.getString("BOOK_Name"));
				bookDTO.setBookStock(rs.getInt("BOOK_STOCK"));
				bookDTO.setBookPrice(rs.getInt("BOOK_PRICE"));
				bookDTO.setBookSellingPrice(rs.getInt("BOOK_SELLINGPRICE"));
				bookDTO.setBookWriter(rs.getString("BOOK_WRITER"));
				bookDTO.setCompanyName(rs.getString("COMPANY_NAME"));
				bookDTO.setBookContent(rs.getString("BOOK_CONTENT"));
				bookList.add(bookDTO);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bookList;
	}

	public int selectTotalRows(){
		int totalRows=0;
		try {
			String sql="select count(*) from category";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				totalRows=rs.getInt("count(*)");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return totalRows;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}