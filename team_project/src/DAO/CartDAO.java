package project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import project.ConnectionProvider;
import project.SocketClient;
import project.DTO.CartDTO;

public class CartDAO {
	Connection conn = ConnectionProvider.getConnection();
	
	public List<CartDTO> selectCart(SocketClient sender) {
		List<CartDTO> list = new ArrayList<>();
		try {
			String sql = "select b.book_name, count(d.book_amount)as book_amount,"
					+ "SUM(b.book_price) as sumPrice "
					+ "from carts c ,detail_cart d, users u, "
					+ "books b where c.carts_id=d.carts_id   "
					+ "and d.book_id =b.book_id "
					+ "and c.users_id =u.users_id "
					+ "and u.users_id=? "
					+ "group by b.book_name, d.book_amount";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sender.userID);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				CartDTO cartDto = new CartDTO();
				cartDto.setBookName(rs.getString("book_name"));
				cartDto.setBookAmount(rs.getInt("book_amount"));
				cartDto.setSumPrice(rs.getInt("sumPrice"));
				list.add(cartDto);
			}

		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("CartDAO select error");
		}
		return list;
	}
	
	public boolean insertCart(SocketClient sender, int bookId) {
		boolean result=false;
		//System.out.println(bookId);
		try {
			String sql = "INSERT INTO DETAIL_CART VALUES(SEQ_DETAILCART_ID.NEXTVAL, ?, ?,(select carts_id from carts where users_id = ?),null,null)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, bookId);
			pstmt.setInt(2, 1);
			pstmt.setString(3, sender.userID);
			pstmt.executeUpdate();
			pstmt.close();
			result=true;
		} catch (Exception e) {
			e.printStackTrace();
			result=false;
		}

		return result;
	}
}
