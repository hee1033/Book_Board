package project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import project.ConnectionProvider;
import project.DTO.BookDTO;
import project.DTO.Pager;

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

	
	public int selectTotalRows(int categoryId){
		int totalRows=0;
		try {
			String sql="select count(*) from books where category_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, categoryId*10);
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