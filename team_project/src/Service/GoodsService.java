package project.Service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import project.ConnectionProvider;
import project.SocketClient;
import project.DAO.GoodsDAO;
import project.DTO.GoodsDTO;

public class GoodsService {
	Connection conn = ConnectionProvider.getConnection();

	public List<GoodsDTO> showGoodsUpdata(int eventId) {
		GoodsDAO goodsDAO = new GoodsDAO();
		List<GoodsDTO> goodsList = new ArrayList<>();
		goodsList = goodsDAO.GoodsDetail(eventId);
		return goodsList;
	}
	
	public boolean buyGoods(SocketClient sender,int goodsId) {
		GoodsDAO goodsDAO = new GoodsDAO();
		
		String address= goodsDAO.selectUSerAddress(sender);
		boolean result = goodsDAO.buyGoods(sender,goodsId, address);
		return result;
	}
}