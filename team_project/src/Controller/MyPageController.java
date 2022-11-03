package Controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import project.SocketClient;
import Service.MyPageService;

public class MyPageController {
	
	public void mypage(SocketClient sender, String listNo, JSONObject jsonUpdata) {
		MyPageService myPage = new MyPageService();
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
				myPage.showOrderList();
				break;
			case "3":
				myPage.showCart();
				break;
			case "5":
				userController.uploadJoin(sender, jsonUpdata);
				break;
				
		}
	}
	
	public void showUserUpdate(SocketClient sender, JSONObject jsonUpdata) {
		MyPageService myPageService = new MyPageService();
		
		Map<String,String> map=new HashMap<>();
		map.put("password",jsonUpdata.getString("password"));
		map.put("name", jsonUpdata.getString("name"));
		map.put("email", jsonUpdata.getString("email"));
		map.put("address", jsonUpdata.getString("address"));
		map.put("address", jsonUpdata.getString("tel"));
		map.put("age", String.valueOf(jsonUpdata.getInt("age")));
		
		myPageService.showUserUpdate(sender, map);
		
		JSONObject root=new JSONObject();
		root.put("controller", "complete");
		root.put("choice","first");
		root.put("flag", sender.flag);
		root.put("message", "수정이 완료되었습니다.");
		String json=root.toString();
		sender.send(json);
		
	}
}
