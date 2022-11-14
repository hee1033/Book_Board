package project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import project.ConnectionProvider;
import project.DTO.Pager;
import project.DTO.SearchDTO;

public class SearchDAO {
	Connection conn = ConnectionProvider.getConnection();

	public List<SearchDTO> SelectAllSearch(String bookName, Pager pager) {
		List<SearchDTO> searchlist = new ArrayList<>();
		try {
			String sql = "SELECT RNUM, BOOK_ID, BOOK_NAME, CATEGORY_NAME " 
					+ "FROM( SELECT ROWNUM AS RNUM, BOOK_ID, BOOK_NAME, CATEGORY_NAME "
					+ "FROM ( SELECT BOOK_ID, BOOK_NAME, CATEGORY_NAME "
					+ "FROM BOOKS B, CATEGORY C " 
					+ "WHERE B.CATEGORY_ID=C.CATEGORY_ID "
					+ "AND BOOK_NAME LIKE ? ORDER BY BOOK_ID) WHERE ROWNUM <=? ) "
					+ "WHERE RNUM>=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, '%' + bookName + '%');
			pstmt.setInt(2, pager.getEndRowNo());
			pstmt.setInt(3, pager.getStartRowNo());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				SearchDTO searchDTO = new SearchDTO();
				searchDTO.setCategoryName(rs.getString("CATEGORY_NAME"));
				searchDTO.setBookId(rs.getInt("BOOK_ID"));
				searchDTO.setBookName(rs.getString("BOOK_NAME"));
				searchlist.add(searchDTO);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SearchList error");
		}
		return searchlist;
	}
	
	public int selectTotalRows(String bookName){
		int totalRows=0;
		try {
			String sql="select count(*) from books WHERE BOOK_NAME LIKE ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, '%' + bookName + '%');
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
