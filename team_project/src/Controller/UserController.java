package Controller;

import org.json.JSONObject;

import project.SocketClient;
import Service.UserService;

public class UserController {

	public void user(SocketClient sender, String listNo, JSONObject jsonUpdata) {
		JSONObject root= new JSONObject();
		String json="";
		switch(listNo) {
			case "0":
				root.put("controller", "menuNo");
				root.put("data", "user");
				json = root.toString();
				sender.send(json);
				break;
			case "1":
				showLogin(sender, jsonUpdata);
				break;
			case "2":
				showLogout(sender);
				break;
			case "3":
				uploadJoin(sender,jsonUpdata);
				break;
			case "4":
				showWithdrawal(sender);
				break;
		}
		
	}
	
	//로그인
	public void showLogin(SocketClient sender , JSONObject jsonUpdata) {
		UserService userService = new UserService();
		String id = jsonUpdata.getString("id");
		String password = jsonUpdata.getString("password");
		
		userService.showLogin(sender,id,password);
		if(sender.flag==true) {
			JSONObject root= new JSONObject();
			root.put("controller", "complete");
			root.put("choice", "first");
			root.put("flag", sender.flag);
			root.put("message","로그인에 성공하셨습니다.");
			String json=root.toString();
			sender.send(json);
		}
		else {
			JSONObject root= new JSONObject();
			root.put("controller", "complete");
			root.put("flag", false);
			root.put("choice", "x");
			root.put("message","아이디 또는 비밀번호가 틀렸습니다.");
			String json=root.toString();
			sender.send(json);
		}
	}
	
	//로그아웃
	public void showLogout(SocketClient sender) {
		UserService userService = new UserService();
		boolean result = userService.showLogout();
		sender.mflag=result;
		sender.setUserID("");

		JSONObject root = new JSONObject();
		root.put("controller", "complete");
		root.put("choice", "first");
		root.put("flag", sender.flag);
		root.put("message", "로그아웃 되었습니다.");
		String json = root.toString();
		sender.send(json);
	}
	
	//회원가입
	public void uploadJoin(SocketClient sender, JSONObject jsonUpdata) {
		UserService userService = new UserService();
		
		sender.getUserDTO().setUserId(jsonUpdata.getString("id"));
		sender.getUserDTO().setUserPassword(jsonUpdata.getString("password"));
		sender.getUserDTO().setUserName(jsonUpdata.getString("name"));
		sender.getUserDTO().setUserEmail(jsonUpdata.getString("email"));
		sender.getUserDTO().setUserAddress(jsonUpdata.getString("address"));
		sender.getUserDTO().setUserTel(jsonUpdata.getString("tel"));
		sender.getUserDTO().setUserAge((jsonUpdata.getInt("age")));
		sender.getUserDTO().setUserSex(jsonUpdata.getString("sex"));;
		
		//비지니스로직 회원가입
		userService.uploadJoin(sender);
		
		JSONObject root = new JSONObject();
		root.put("controller", "complete");
		root.put("choice", "x");
		root.put("flag", sender.flag);
		if (sender.flag == true) {
			root.put("message", "회원가입이 완료되었습니다.\n로그인 후 사용 하십시오.");
		} else {
			root.put("message", "존재하는 id입니다. \n다른 id를 입력해주세요.");
		}
		String json=root.toString();
		sender.send(json);
	}
	
	//회원탈퇴
	public void showWithdrawal(SocketClient sender) {
		UserService userService = new UserService();
		//비지니스로직 회원탈퇴
		userService.showWithdrawal(sender);
		sender.setUserID("");
		if(sender.flag==false) {
			JSONObject root= new JSONObject();
			root.put("controller", "complete");
			root.put("choice", "first");
			root.put("flag", sender.flag);
			root.put("message","회원탈퇴 되었습니다.");
			String json=root.toString();
			sender.send(json);
		}
		
		
	}
	
}
