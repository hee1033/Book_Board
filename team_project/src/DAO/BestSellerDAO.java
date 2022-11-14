package project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import project.ConnectionProvider;
import project.DTO.BookDTO;

public class BestSellerDAO {
	Connection conn = ConnectionProvider.getConnection();
	
	//전체 베스트셀러
	public List<BookDTO> selectAllBestSeller(int bookId) {
		
		List<BookDTO> AllbookList = new ArrayList<>();
		try {
			String sql = "" 
					+ "SELECT BOOK_ID, BOOK_NAME "
					+ "FROM ( "
					+ "SELECT ROWNUM, BOOK_ID, BOOK_NAME "
					+ "FROM ( "
					+ "SELECT ROWNUM, B.BOOK_ID, B.BOOK_NAME "
					+ "FROM BOOKS B, BESTSELLER BS "
					+ "WHERE B.BOOK_ID = BS.BOOK_ID "
					+ "ORDER BY BS.BOOK_AMOUNT DESC) "
					+ "WHERE ROWNUM <= 3 ) "
					+ "WHERE ROWNUM <= 3";
					
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				BookDTO bookDTO = new BookDTO();
				bookDTO.setBookId(rs.getInt("BOOK_ID"));
				bookDTO.setBookName(rs.getString("BOOK_NAME"));
				AllbookList.add(bookDTO);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return AllbookList;
	}
	
	//연령별 베스트셀러 
	public List<BookDTO> selectAgeBestSeller(int bookId) {
		List<BookDTO> ageList= new ArrayList<>();
		try {
			String sql="";
			if(bookId==1) {
				sql = "" 
						+ "SELECT B.BOOK_ID, B.BOOK_NAME "
						+ "FROM BOOKS B, BESTSELLER BS " 
						+ "WHERE B.BOOK_ID = BS.BOOK_ID " 
						+ "AND AGE_10_20 IS NOT NULL "
						+ "AND ROWNUM <= 3 "
						+ "ORDER BY BS.BOOK_AMOUNT DESC";
			}else if(bookId==2){
				sql = "" 
						+ "SELECT B.BOOK_ID, B.BOOK_NAME "
						+ "FROM BOOKS B, BESTSELLER BS " 
						+ "WHERE B.BOOK_ID = BS.BOOK_ID " 
						+ "AND AGE_20_40 IS NOT NULL "
						+ "AND ROWNUM <= 3 "
						+ "ORDER BY BS.BOOK_AMOUNT DESC";
			}else {
				sql = "" 
						+ "SELECT B.BOOK_ID, B.BOOK_NAME "
						+ "FROM BOOKS B, BESTSELLER BS " 
						+ "WHERE B.BOOK_ID = BS.BOOK_ID " 
						+ "AND AGE_OVER40 IS NOT NULL "
						+ "AND ROWNUM <= 3 "
						+ "ORDER BY BS.BOOK_AMOUNT DESC";
			}
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				BookDTO bookDTO = new BookDTO();
				bookDTO.setBookId(rs.getInt("BOOK_ID"));
				bookDTO.setBookName(rs.getString("BOOK_NAME"));
				ageList.add(bookDTO);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ageList;
	}
	//성별 베스트셀러
	public List<BookDTO> selectGenderBestSeller(int bookId) {
		
		List<BookDTO> genderList = new ArrayList<>();
		try {
			String sql="";
			if(bookId==1) {
				sql = "" 
						+ "SELECT B.BOOK_ID, B.BOOK_NAME "
						+ "FROM BOOKS B, BESTSELLER BS " 
						+ "WHERE B.BOOK_ID = BS.BOOK_ID " 
						+ "AND SEX_MAN IS NOT NULL "
						+ "AND ROWNUM <= 3 "
						+ "ORDER BY BS.BOOK_AMOUNT DESC";
			}else if(bookId==2){
				sql = "" 
						+ "SELECT B.BOOK_ID, B.BOOK_NAME "
						+ "FROM BOOKS B, BESTSELLER BS " 
						+ "WHERE B.BOOK_ID = BS.BOOK_ID " 
						+ "AND SEX_WOMAN IS NOT NULL "
						+ "AND ROWNUM <= 3 "
						+ "ORDER BY BS.BOOK_AMOUNT DESC";
			}
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				BookDTO bookDTO = new BookDTO();
				bookDTO.setBookId(rs.getInt("BOOK_ID"));
				bookDTO.setBookName(rs.getString("BOOK_NAME"));
				genderList.add(bookDTO);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return genderList;
	}
	
}
