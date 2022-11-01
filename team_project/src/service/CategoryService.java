package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import DTO.DTOBook;
import DTO.DTOCompany;
import day06.ChatClient;
import day06.ConnectionProvider;
import day06.SocketClient;

public class CategoryService {
	Connection conn = ConnectionProvider.getConnection();
	static Scanner scan = new Scanner(System.in);
	SocketClient sender;
	ResultSet rs;

	// 카테고리 목록
	public void showCategory(SocketClient sender, JSONObject jsonUpdata) {

		int categoryNum = Integer.parseInt(jsonUpdata.getString("categoryId")) * 10;
		
		// 카테고리 리스트 가져오기
		try {
			String sql = "" + "SELECT C.CATEGORY_NAME, B.BOOK_ID, B.BOOK_NAME " + "FROM CATEGORY C, BOOKS B "
					+ "WHERE C.CATEGORY_ID = B.CATEGORY_ID AND B.CATEGORY_ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, categoryNum);
			rs = pstmt.executeQuery();

			JSONArray jsonArray = new JSONArray();
			JSONObject root = new JSONObject();
			
			try {
				while (rs.next()) {
					JSONObject jo = new JSONObject();
					jo.put("categoryName", rs.getString("CATEGORY_NAME"));
					jo.put("booKId", rs.getInt("BOOK_ID"));
					jo.put("booKName", rs.getString("BOOK_NAME"));
					jsonArray.put(jo);
				}
				root.put("check", "show");
				root.put("data", jsonArray);
				String json = root.toString();
				sender.send(json);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			System.out.println("작동안됨");
			e.printStackTrace();
		}
	};

}
