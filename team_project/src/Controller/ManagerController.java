package Controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import DTO.BookDTO;
import DTO.UserDTO;
import Service.ManagerService;
import project.SocketClient;

public class ManagerController {

	public void manager(SocketClient sender, String listNo, JSONObject jsonUpdata) {
		JSONObject root= new JSONObject();
		String json="";
		switch(listNo) {
			case "0":
				root.put("controller", "menuNo");
				root.put("data", "manager");
				json = root.toString();
				sender.send(json);
				break;
			case "1":
				showBookUpdata(sender,jsonUpdata);
				break;
			case "2":
				showBookDelete(sender,jsonUpdata);
				break;
			case "3":
				showUserData(sender);
				break;
			case "4":
				showDetailUserData(sender, jsonUpdata);
				break;
			case "5":
				showManagerLogout(sender);
				break;
			case "6":
				break;
			case "7":
				showManagerLogin(sender, jsonUpdata);
				break;
		}
	}

	public void showBookUpdata(SocketClient sender, JSONObject jsonUpdata) {
		ManagerService managerService = new ManagerService();
		BookDTO bookDTO= new BookDTO();
		bookDTO.setBookId(jsonUpdata.getInt("bookId"));
		bookDTO.setBookName(jsonUpdata.getString("bookName"));
		bookDTO.setBookPrice(jsonUpdata.getInt("bookPrice"));
		bookDTO.setBookWriter(jsonUpdata.getString("bookWriter"));
		bookDTO.setBookStock(jsonUpdata.getInt("bookStock"));
		bookDTO.setBookContent(jsonUpdata.getString("bookContent"));
		bookDTO.setCompanyID(jsonUpdata.getInt("bookCompany"));
		bookDTO.setCategoryId(jsonUpdata.getInt("categoryId"));
		bookDTO.setBookSellingPrice(jsonUpdata.getInt("bookSellingPrice"));
		System.out.println(bookDTO.toString());
		
		boolean result = managerService.showBookUpdata(bookDTO);
		sender.mflag=result;
		
		JSONObject root= new JSONObject();
		root.put("controller", "manager");
		root.put("choice", "x");
		root.put("mflag", sender.mflag);
		if(sender.mflag==true) {
			root.put("message","책 등록 되었습니다.");
		}else {
			root.put("message","존재하는 book id입니다. \n다른 id를 입력해주세요.");
		}
		String json=root.toString();
		sender.send(json);
		
	}

	public void showBookDelete(SocketClient sender, JSONObject jsonUpdata) {
		ManagerService managerService = new ManagerService();
		//json 파싱
		int bookId = jsonUpdata.getInt("bookId");
		//비지니스로직
		boolean result = managerService.showBookDelete(bookId);
		sender.mflag=result;
		JSONObject root= new JSONObject();
		root.put("controller", "manager");
		root.put("choice", "x");
		root.put("mflag", sender.mflag);
		if(result == true) {
			root.put("message","책 삭제 되었습니다.");
		}else {
			root.put("message","존재하지 않는 book id입니다. \n다시 입력해주세요.");
		}
		String json=root.toString();
		sender.send(json);
	}

	public void showUserData(SocketClient sender) {
		ManagerService managerService = new ManagerService();
		List<UserDTO> list = new ArrayList<>();
		list = managerService.showUserData();

		JSONArray jsonArray = new JSONArray();
		for (UserDTO du : list) {
			JSONObject jo = new JSONObject();
			jo.put("userId", du.getUserId());
			jo.put("userName", du.getUserName());
			jsonArray.put(jo);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("controller", "show");
		jsonObject.put("data", jsonArray);
		String json = jsonObject.toString();
		sender.send(json);
	}

	public void showDetailUserData(SocketClient sender, JSONObject jsonUpdata) {
		ManagerService managerService = new ManagerService();
		UserDTO userDto = new UserDTO();
		String userId=jsonUpdata.getString("userId");
		userDto = managerService.showDetailUserData(sender, userId);

		JSONArray jsonArray = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("userId", userDto.getUserId());
		jo.put("userPassword", userDto.getUserPassword());
		jo.put("userName", userDto.getUserName());
		jo.put("userEmail", userDto.getUserEmail());
		jo.put("userAddress", userDto.getUserAddress());
		jo.put("userTel", userDto.getUserTel());
		jo.put("userIndate", userDto.getUserIndate());
		jo.put("userAge", userDto.getUserAge());
		jo.put("userSex", userDto.getUserSex());
		jsonArray.put(jo);
		if (sender.mflag == true) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "show");
			jsonObject.put("data", jsonArray);
			String json = jsonObject.toString();
			sender.send(json);
		} else {
			JSONObject root = new JSONObject();
			root.put("controller", "manager");
			root.put("choice", "x");
			root.put("mflag", false);
			root.put("message", "존재하지 않는 user ID입니다. \n다시 입력해주세요.");
			String json = root.toString();
			sender.send(json);
		}
	}
	
	public void showManagerLogout(SocketClient sender) {
		ManagerService managerService = new ManagerService();
		boolean result =managerService.showManagerLogout();
		sender.mflag=result;
		sender.flag=result;

		JSONObject root = new JSONObject();
		root.put("controller", "manager");
		root.put("choice", "first");
		root.put("mflag", sender.mflag);
		root.put("message", "관리자 로그아웃 되었습니다.");
		String json = root.toString();
		sender.send(json);
	}

	

	public void showManagerLogin(SocketClient sender, JSONObject jsonUpdata) {
		ManagerService managerService = new ManagerService();
		String id=jsonUpdata.getString("id");
		String password=jsonUpdata.getString("password");
		
		managerService.showManagerLogin(sender, id, password);
		JSONObject root = new JSONObject();
		root.put("controller", "manager");
		root.put("choice", "x");
		root.put("mflag", true);
		if (sender.mflag == true) {
			root.put("message", "관리자 로그인에 성공하셨습니다.");
		} else {
			root.put("message", "아이디 또는 비밀번호가 틀렸습니다.");
		}
		String json = root.toString();
		sender.send(json);
	}
}
