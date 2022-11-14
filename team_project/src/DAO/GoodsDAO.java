package project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import project.ConnectionProvider;
import project.SocketClient;
import project.DTO.GoodsDTO;

public class GoodsDAO {
	Connection conn = ConnectionProvider.getConnection();
	public List<GoodsDTO> GoodsDetail(int eventId) {
		List<GoodsDTO> goodslist = new ArrayList<>();
		try {
			String sql = "SELECT G.GOODS_NAME, E.EVENT_NAME, B.BOOK_NAME, E.EVENT_START_DATE, E.EVENT_END_DATE, E.EVENT_CONTENT, G.GOODS_PRICE, G.GOODS_CONTENT "
					+ "FROM EVENT E, GOODS G, BOOKS B "
					+ "WHERE E.EVENT_ID = G.EVENT_ID AND B.BOOK_ID = E.BOOK_ID AND E.EVENT_START_DATE <= SYSDATE AND E.EVENT_END_DATE >= SYSDATE AND E.EVENT_ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, eventId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				GoodsDTO goodsDTO = new GoodsDTO();
				goodsDTO.setEventName(rs.getString("EVENT_NAME"));
				goodsDTO.setGoodsName(rs.getString("GOODS_NAME"));
				goodsDTO.setBookName(rs.getString("BOOK_NAME"));
				goodsDTO.setEventStartDate(rs.getDate("EVENT_START_DATE"));
				goodsDTO.setEventEndDate(rs.getDate("EVENT_END_DATE"));
				goodsDTO.setEventContent(rs.getString("EVENT_CONTENT"));
				goodsDTO.setGoodsContent(rs.getString("GOODS_CONTENT"));
				goodsDTO.setGoodsPrice(rs.getInt("GOODS_PRICE"));
				goodslist.add(goodsDTO);
			}	
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("goodsDetail error");
		}
		return goodslist;
	}
	
	public boolean insertGoods(GoodsDTO goodsDto) {
		boolean result=false;
		try {
			String sql= "insert into goods values(?,?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, goodsDto.getGoodsId());
			pstmt.setString(2, goodsDto.getGoodsName());
			pstmt.setInt(3, goodsDto.getGoodsNum());
			pstmt.setInt(4, goodsDto.getEventId());
			pstmt.setString(5, goodsDto.getGoodsContent());
			pstmt.setInt(6, goodsDto.getGoodsPrice());
			pstmt.executeUpdate();
			pstmt.close();
			result = true;
		}catch(Exception e) {
			e.printStackTrace();
			result=false;
		}
		
		return result;
	}
	
	public boolean deleteGoods(int goodsId ) {
		   boolean result=false;
			try {
				String sql="delete from goods where goods_id = ?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, goodsId);
				pstmt.executeUpdate();
				pstmt.close();
				result=true;
			}catch(Exception e) {
				result= false;
				//e.printStackTrace();
			}
			return result;
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
	
	public boolean buyGoods(SocketClient sender, int goodsId, String address) {
		boolean result = true;
		try {

			String sql = "INSERT INTO ORDERS VALUES(SEQ_ORDERS_ID.NEXTVAL, ?, SYSDATE, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sender.userID);
			pstmt.setString(2, address);
			pstmt.setString(3, sender.userID);
			pstmt.executeUpdate();
			pstmt.close();

			String sql2 = "DELETE FROM DETAIL_CART WHERE CARTS_ID = " + "(SELECT CARTS_ID " + "FROM CARTS "
					+ "WHERE USERS_ID = ? " + "AND USERS_ID IS NOT NULL) ";
			PreparedStatement pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setString(1, sender.userID);
			pstmt2.executeUpdate();
			pstmt2.close();

			String sql3 = "UPDATE GOODS SET GOODS_NUM = GOODS_NUM-1 WHERE GOODS_ID = ? AND GOODS_NUM >=0";
			PreparedStatement pstmt3 = conn.prepareStatement(sql3);
			pstmt3.setInt(1, goodsId);
			pstmt3.executeUpdate();
			pstmt3.close();			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("buyGoods error");
		}
		return result;
	}
	
	
}
