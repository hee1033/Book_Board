package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import project.ConnectionProvider;
import DTO.BookDTO;

public class BookDAO {
	Connection conn = ConnectionProvider.getConnection();
	
   public boolean InsertBook(BookDTO bookDTO) {
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
			pstmt.setInt(7, bookDTO.getCompanyID());
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
   
   public boolean DeleteBook(int bookId ) {
	   boolean result=false;
		try {
			String sql="delete from books where book_id = ?;";
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
   
}