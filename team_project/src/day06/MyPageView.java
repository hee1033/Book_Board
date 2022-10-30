package day06;


import java.util.Scanner;

import org.json.JSONObject;

public class MyPageView {
	static Scanner sc = new Scanner(System.in);
	
	static void board(ChatClient chatClient) {
		System.out.println("-------------------------------------------------");
//		System.out.println("[" + users.getUsersId() + "'s 페이지]");
		System.out.println("1.개인정보 수정 | 2.구매내역 | 3.장바구니 | 4.로그아웃 | 5.회원탈퇴");
		System.out.println("--------------------------------------------------");
		System.out.print("listNo = ");
		String listNo=sc.nextLine();
		switch(listNo) {
			case "1":
				userUpdate(chatClient);
				break;
			case "2":
				orderList(chatClient);
				break;
			case "3":
				cart(chatClient);
				break;
			case "4":
				UserView.logout(chatClient);
				break;
			case "5":
				UserView.out();
		}
	}
	
	public static void userUpdate(ChatClient chatClient) {
		System.out.println("[개인정보수정]");
		System.out.println("----------");
		// 비밀번호 수정
		System.out.print("수정 비밀번호(수정을 원하지않으면 n) = ");
		String password = sc.nextLine();
		// 이름 수정
		System.out.print("수정 이름(수정을 원하지않으면 n) = ");
		String name = sc.nextLine();
		// 이메일 수정
		System.out.print("수정 이메일(수정을 원하지않으면 n) = ");
		String email = sc.nextLine();
		// 주소 수정
		System.out.print("수정 주소(수정을 원하지않으면 n) = ");
		String address = sc.nextLine();
		// 전화번호 수정
		System.out.print("수정 전화번호(수정을 원하지않으면 n) = ");
		String tel = sc.nextLine();
		// 나이 수정
		System.out.print("수정 나이(수정을 원하지않으면 0) = ");
		int age = Integer.parseInt(sc.nextLine());
		try {
			JSONObject update = new JSONObject();
			update.put("password", password);
			update.put("name", name);
			update.put("email", email);
			update.put("address", address);
			update.put("tel", tel);
			update.put("age", age);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("check", "menuNo");
			jsonObject.put("menuNo","5");
			jsonObject.put("listNo", "1");
			jsonObject.put("update", update);
			String json = jsonObject.toString();
			chatClient.send(json);
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("UserView error");
		}
		
	}

	public static void cart(ChatClient chatClient) {
		// TODO Auto-generated method stub
		
	}

	public static void orderList(ChatClient chatClient) {
		// TODO Auto-generated method stub
		
	}

	
}
