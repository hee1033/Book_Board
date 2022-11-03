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

import Controller.BestSellerController;
import DTO.BookDTO;
import project.ConnectionProvider;
import project.SocketClient;

public class BestSellerDAO {
	Connection conn = ConnectionProvider.getConnection();
	
	//전체 베스트셀러
	public List<BookDTO> selectAllBestSeller(int bookId) {

		BestSellerController bestSellerController = new BestSellerController();
		
		List<BookDTO> AllbookList = new ArrayList<>();
		try {
			String sql = "" + "SELECT B.BOOK_ID, B.BOOK_NAME " + "FROM BOOKS B," + " (SELECT BS.BOOK_ID "
					+ "FROM BESTSELLER BS, DETAIL_ORDER D " + "WHERE BS.BOOK_AMOUNT = D.BOOK_AMOUNT "
					+ "ORDER BY D.BOOK_AMOUNT DESC " + ") BS " + "WHERE B.BOOK_ID = BS.BOOK_ID " + "AND ROWNUM <=3";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				BookDTO bookDTO = new BookDTO();
				bookDTO.setBookId(rs.getInt("BOOK_ID"));
				bookDTO.setBookName(rs.getString("BOOK_NAME"));
				AllbookList.add(bookDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return AllbookList;
	}
	
	//연령별 베스트셀러 
	//10~20대
	public List<BookDTO> selectAge1020BestSeller(int bookId) {

		BestSellerController bestSellerController = new BestSellerController();
		List<BookDTO> age1020 = new ArrayList<>();
		try {
			String sql = "" 
					+ "SELECT B.BOOK_ID, B.BOOK_NAME "
					+ "FROM BOOKS B, BESTSELLER BS " 
					+ "WHERE B.BOOK_ID = BS.BOOK_ID " 
					+ "AND AGE_10_20 IS NOT NULL "
					+ "AND ROWNUM <= 3 "
					+ "ORDER BY BS.BOOK_AMOUNT DESC";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				BookDTO bookDTO = new BookDTO();
				bookDTO.setBookId(rs.getInt("BOOK_ID"));
				bookDTO.setBookName(rs.getString("BOOK_NAME"));
				age1020.add(bookDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return age1020;
	}
	//20~40대
	public List<BookDTO> selectAge2040BestSeller(int bookId) {

		BestSellerController bestSellerController = new BestSellerController();
		List<BookDTO> age2040 = new ArrayList<>();
		try {
			String sql = "" 
					+ "SELECT B.BOOK_ID, B.BOOK_NAME "
					+ "FROM BOOKS B, BESTSELLER BS " 
					+ "WHERE B.BOOK_ID = BS.BOOK_ID " 
					+ "AND AGE_20_40 IS NOT NULL "
					+ "AND ROWNUM <= 3 "
					+ "ORDER BY BS.BOOK_AMOUNT DESC";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				BookDTO bookDTO = new BookDTO();
				bookDTO.setBookId(rs.getInt("BOOK_ID"));
				bookDTO.setBookName(rs.getString("BOOK_NAME"));
				age2040.add(bookDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return age2040;
	}
	
	
	public List<BookDTO> selectAgeOver40BestSeller(int bookId) {

		BestSellerController bestSellerController = new BestSellerController();
		List<BookDTO> Over40 = new ArrayList<>();
		try {
			String sql = "" 
					+ "SELECT B.BOOK_ID, B.BOOK_NAME "
					+ "FROM BOOKS B, BESTSELLER BS " 
					+ "WHERE B.BOOK_ID = BS.BOOK_ID " 
					+ "AND AGE_OVER40 IS NOT NULL "
					+ "AND ROWNUM <= 3 "
					+ "ORDER BY BS.BOOK_AMOUNT DESC";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				BookDTO bookDTO = new BookDTO();
				bookDTO.setBookId(rs.getInt("BOOK_ID"));
				bookDTO.setBookName(rs.getString("BOOK_NAME"));
				Over40.add(bookDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Over40;
	}
	
}
