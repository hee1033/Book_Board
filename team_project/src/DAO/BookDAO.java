package project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import project.ConnectionProvider;
import project.DTO.BookDTO;

public class BookDAO {
	Connection conn = ConnectionProvider.getConnection();
	
   public boolean insertBook(BookDTO bookDTO) {
	   boolean result=false;
	   try {
			String sql="insert into books values(?,?,?,?,?,?,sysdate,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookDTO.getBookId());
			pstmt.setString(2,bookDTO.getBookName());
			pstmt.setInt(3, bookDTO.getBookPrice());
			pstmt.setString(4, bookDTO.getBookWriter());
			pstmt.setInt(5, bookDTO.getBookStock());
			pstmt.setString(6, bookDTO.getBookContent());
			pstmt.setInt(7, bookDTO.getCompanyId());
			pstmt.setInt(8, bookDTO.getCategoryId());
			pstmt.setInt(9,bookDTO.getBookSellingPrice());
			pstmt.executeUpdate();
			pstmt.close();
			pstmt.close();
			result=true;
		}catch(Exception e) {
			//e.printStackTrace();
			result=false;
		}
	   return result;
	}
   
   public boolean updateBook(int bookId, int stock) {
	   boolean result=false;
	   try {
		   String sql="UPDATE BOOKS SET BOOK_STOCK = BOOK_STOCK+? WHERE BOOK_ID = ?";
		   PreparedStatement pstmt = conn.prepareStatement(sql);
		   pstmt.setInt(1, stock);
		   pstmt.setInt(2, bookId);
		   pstmt.executeUpdate();
		   pstmt.close();
		   
		   String sql1="select count(*) from books where book_id=?";
		   PreparedStatement pstmt1 = conn.prepareStatement(sql1);
		   pstmt1.setInt(1, bookId);
		   ResultSet rs=pstmt1.executeQuery();
		   int num=0;
		   if(rs.next()) {
			   num=(rs.getInt("count(*)"));
		   }
		   if(num==0) {
			   throw new Exception();
		   }
		   pstmt1.close();
		   result=true;
	   }catch(Exception e) {
		   result=false;
	   }
	   System.out.println(result);
	   return result;
   }
   
   public boolean deleteBook(int bookId ) {
	   boolean result=false;
		try {
			String sql="delete from books where book_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookId);
			pstmt.executeUpdate();
			pstmt.close();
			result=true;
		}catch(Exception e) {
			result= false;
			//e.printStackTrace();
		}
		return result;
   }

// 책 상세 정보
	public List<BookDTO> showBookDetail(int bookId) {
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
   
}