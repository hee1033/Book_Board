package detailView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import day06.ConnectionProvider;
import day06.SocketClient;

public class BookDetailView {
	static Connection conn = ConnectionProvider.getConnection();

	public static void bookDetail(SocketClient sender, JSONObject bookId) {
		int bookNum = Integer.parseInt(bookId.getString("bookId"));

		try {
			String sql = ""
					+ "SELECT B.BOOK_NAME, B.BOOK_STOCK, B.BOOK_PRICE, B.BOOK_SELLINGPRICE, B.BOOK_WRITER, C.COMPANY_NAME, BOOK_DATE, BOOK_CONTENT "
					+ "FROM BOOKS B, COMPANY C " + "WHERE B.COMPANY_ID = C.COMPANY_ID " + "AND B.BOOK_ID = ?";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookNum);
			ResultSet rs = pstmt.executeQuery();

			JSONArray jsonArray = new JSONArray();
			JSONObject root = new JSONObject();
			while (rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("bookName", rs.getString("BOOK_NAME"));
				jo.put("booKStock", rs.getInt("BOOK_STOCK"));
				jo.put("booKPrice", rs.getInt("BOOK_PRICE"));
				jo.put("bookSellingPrice", rs.getInt("BOOK_SELLINGPRICE"));
				jo.put("bookWriter", rs.getString("BOOK_WRITER"));
				jo.put("companyName", rs.getString("COMPANY_NAME"));
				jo.put("bookContent", rs.getString("BOOK_CONTENT"));
				jsonArray.put(jo);
			}
			root.put("check", "show");
			root.put("data", jsonArray);
			String json = root.toString();
			sender.send(json);

			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	};
}
