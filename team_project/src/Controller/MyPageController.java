package project.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import project.SocketClient;
import project.DTO.CartDTO;
import project.DTO.DetailOrderDTO;
import project.DTO.OrderDTO;
import project.Service.MyPageService;

public class MyPageController {
	
	public void mypage(SocketClient sender, String listNo, JSONObject jsonUpdata) {
		UserController userController = new UserController();
		JSONObject root= new JSONObject();
		String json="";
		switch(listNo){
			case "0":
				root.put("controller", "menuNo");
				root.put("data", "mypage");
				json = root.toString();
				sender.send(json);
				break;
			case "1":
				showUserUpdate(sender, jsonUpdata);
				break;
			case "2":
				showOrderList(sender);
				break;
			case "3":
				showCart(sender);
				break;
			case "5":
				userController.uploadJoin(sender, jsonUpdata);
				break;
			case "6":
				showDetailOrderList(sender, jsonUpdata);
				
		}
	}

	public void showUserUpdate(SocketClient sender, JSONObject jsonUpdata) {
		MyPageService myPageService = new MyPageService();
		
		Map<String,String> map=new HashMap<>();
		map.put("password",jsonUpdata.getString("password"));
		map.put("name", jsonUpdata.getString("name"));
		map.put("email", jsonUpdata.getString("email"));
		map.put("address", jsonUpdata.getString("address"));
		map.put("tel", jsonUpdata.getString("tel"));
		map.put("age", String.valueOf(jsonUpdata.getInt("age")));
		
		boolean result = myPageService.showUserUpdate(sender, map);
		
		JSONObject root=new JSONObject();
		root.put("controller", "complete");
		root.put("choice","first");
		if(result==true) {
			root.put("message", "수정이 완료되었습니다.");
		}else {
			root.put("message", "수정이 안되었습니다.");
		}
		root.put("flag", sender.flag);
		
		String json=root.toString();
		sender.send(json);
	}
	
	public void showOrderList(SocketClient sender) {
		MyPageService myPageService = new MyPageService();
		List<OrderDTO> list = new ArrayList<>();
		list = myPageService.showOrderList(sender);
		JSONArray jsonArray = new JSONArray();
		for(OrderDTO od : list) {
			JSONObject jo = new JSONObject();
			jo.put("orderNum", od.getOrderNum());
			jo.put("bookName", od.getBookName());
			jo.put("bookAmount", od.getOrdersBookAmount());
			jo.put("bookPrice", od.getBookPrice());
			jsonArray.put(jo);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("controller", "show");
		jsonObject.put("data", jsonArray);
		String json = jsonObject.toString();
		sender.send(json);
	}
	
	public void showDetailOrderList(SocketClient sender, JSONObject jsonUpdata) {
		int orderNum = jsonUpdata.getInt("orderNum");
		MyPageService myPageService = new MyPageService();
		List<DetailOrderDTO> list = new ArrayList<>();
		list=myPageService.showDetailOrderList(sender,orderNum);
		
		JSONArray jsonArray = new JSONArray();
		for(DetailOrderDTO od : list) {
			JSONObject jo = new JSONObject();
			jo.put("bookName", od.getBookName());
			jo.put("orderPrice", od.getOrderPrice());
			jo.put("orderAmount", od.getOrderAmount());
			jo.put("orderDate", od.getOrderDate());
			jo.put("orderAddress",	od.getOrderAddress());
			jo.put("person", od.getPerson());
			jo.put("userId", od.getUserId());
			jsonArray.put(jo);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("controller", "show");
		jsonObject.put("data", jsonArray);
		String json = jsonObject.toString();
		sender.send(json);
	}
	
	public void showCart(SocketClient sender) {
		MyPageService myPageService = new MyPageService();
		List<CartDTO> list = new ArrayList<>();
		list = myPageService.showCart(sender);
		JSONArray jsonArray = new JSONArray();
		for(CartDTO cd : list) {
			JSONObject jo = new JSONObject();
			jo.put("bookName", cd.getBookName());
			jo.put("bookAmount", cd.getBookAmount());
			jo.put("sumPrice", cd.getSumPrice());
			jsonArray.put(jo);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("controller", "show");
		jsonObject.put("data", jsonArray);
		String json = jsonObject.toString();
		sender.send(json);
	
		
	}
	
	
}
