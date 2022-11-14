package project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import project.ConnectionProvider;
import project.SocketClient;
import project.DTO.DetailOrderDTO;
import project.DTO.OrderDTO;

public class OrderDAO {
	Connection conn = ConnectionProvider.getConnection();
	
	public List<OrderDTO> selectOrderList(SocketClient sender){
		List<OrderDTO> list = new ArrayList<>();
		try {
			String sql = "select distinct(o.orders_id), b.book_name, d.book_amount, b.book_price from orders o, users u, detail_order d , books b where u.users_id=o.users_id and o.orders_id = d.orders_id and d.book_id = b.book_id and u.users_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sender.userID);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				OrderDTO orderDto = new OrderDTO();
				orderDto.setOrderNum(rs.getInt("orders_id"));
				orderDto.setBookName(rs.getString("book_name"));
				orderDto.setOrdersBookAmount(rs.getInt("book_amount"));
				orderDto.setBookPrice(rs.getInt("book_price"));
				list.add(orderDto);
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("OderDAO select error");
		}
		return list;
	}
	
	public List<DetailOrderDTO> selectDetailOrderList(SocketClient sender,int orderNum){
		List<DetailOrderDTO> list = new ArrayList<>();
		try {
			//person을 receiver로 바꿔야함
			String sql = "select distinct(b.book_name), b.book_price, d.book_amount, o.orders_date, o.orders_address, o.orders_person, u.users_id from orders o, users u, detail_order d , books b where u.users_id=o.users_id and o.orders_id = d.orders_id and d.book_id = b.book_id and o.orders_id=? and u.users_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, orderNum);
			pstmt.setString(2, sender.userID);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				DetailOrderDTO detailOrderDto = new DetailOrderDTO();
				detailOrderDto.setBookName(rs.getString("book_name"));
				detailOrderDto.setOrderPrice(rs.getInt("book_price"));
				detailOrderDto.setOrderAmount(rs.getInt("book_amount"));
				detailOrderDto.setOrderDate(rs.getDate("orders_date"));
				detailOrderDto.setOrderAddress(rs.getString("orders_address"));
				detailOrderDto.setPerson(rs.getString("orders_person"));
				detailOrderDto.setUserId(rs.getString("users_id"));
				list.add(detailOrderDto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public String selectUSerAddress(SocketClient sender) {
		String result="";
		try {
			String sql = "select users_address from Users where users_id =?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sender.userID);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getString("users_address");
			}
			pstmt.executeUpdate();
			pstmt.close();
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//책 구매
	public boolean buyBook(SocketClient sender, int bookId, String address) {
		boolean result = false;
		try {
			
			String sql = ""
					+"INSERT INTO ORDERS ( "
					+ "ORDERS_ID, USERS_ID, ORDERS_ADDRESS, ORDERS_DATE, ORDERS_PERSON) "
					+ "SELECT SEQ_ORDERS_ID.NEXTVAL, ?, ?, SYSDATE, ?"
					+ "FROM DUAL WHERE NOT EXISTS( "
					+ "SELECT * FROM ORDERS WHERE USERS_ID = ?)";
					
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sender.userID);
			pstmt.setString(2, sender.userID);
			pstmt.setString(3, address);
			pstmt.setString(4, sender.userID);
			pstmt.executeUpdate();
			pstmt.close();
		
			String sql2 = "INSERT INTO DETAIL_ORDER VALUES(SEQ_DETAILORDER_ID.NEXTVAL, "
					+ "(SELECT ORDERS_ID "
					+ "FROM ORDERS "
					+ "WHERE USERS_ID =? ), "
					+ "?, ? )";
			PreparedStatement pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setString(1, sender.userID);
			pstmt2.setInt(2, bookId);
			pstmt2.setInt(3, 1);
			pstmt2.executeUpdate();
			pstmt2.close();
			
			String sql3 = "DELETE FROM DETAIL_CART WHERE CARTS_ID = "
					+"(SELECT CARTS_ID "
					+"FROM CARTS "
					+"WHERE USERS_ID = ? "
					+"AND USERS_ID IS NOT NULL) ";
			PreparedStatement pstmt3 = conn.prepareStatement(sql3);
			pstmt3.setString(1, sender.userID);
			pstmt3.executeUpdate();
			pstmt3.close();
			
			String sql4 = "UPDATE BOOKS SET BOOK_STOCK = BOOK_STOCK-1 WHERE BOOK_ID = ? AND BOOK_STOCK >=0";
			PreparedStatement pstmt4 = conn.prepareStatement(sql4);
			pstmt4.setInt(1, bookId);
			pstmt4.executeUpdate();
			pstmt4.close();
			result = true;
	
		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
}