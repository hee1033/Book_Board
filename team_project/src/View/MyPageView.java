package View;

import java.util.Scanner;

import org.json.JSONObject;

import project.ChatClient;

public class MyPageView {
	static Scanner sc= new Scanner(System.in);
	
	public static void seeBoard(ChatClient chatClient) {
		System.out.println();
		System.out.println("-----------------------------------------------------------");
		System.out.println("1.개인정보 수정 | 2.구매내역 | 3.장바구니 | 4.로그아웃 | 5.회원탈퇴 | 6.메인화면");
		System.out.println("-----------------------------------------------------------");
		System.out.print("listNo = ");
		String listNo=sc.nextLine();
		switch(listNo) {
			case "1":
				writeUserUpdata(chatClient);
				break;
			case "2":
				seeOrderList(chatClient);
				break;
			case "3":
				seeCart(chatClient);
				break;
			case "4":
				UserView.writeLogout(chatClient);
				break;
			case "5":
				UserView.writeOut(chatClient);
				break;
			case "6":
				chatClient.choice="first";
				System.out.println();
				break;
			default :
				System.out.println("잘못 입력하셨습니다.");
				seeBoard(chatClient);
				break;
		}
	}
	
	//개인정보 수정
	public static void writeUserUpdata(ChatClient chatClient) {
		//빈칸이나 object를 못받는데... 확인 필요
		System.out.println("\n[개인정보수정]");
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
		if(password.equals("")||name.equals("")||email.equals("")||address.equals("")||tel.equals("")) {
			System.out.println("입력하지 않으신 답변이 있습니다. 다시 입력해주세요.");
			writeUserUpdata(chatClient);
		}
		try {
			//Scanner로 값을 받지 않으면 오류가 생긴다.(오류수정 필요)
			JSONObject jsonUpdata = new JSONObject();
			jsonUpdata.put("password", password);
			jsonUpdata.put("name", name);
			jsonUpdata.put("email", email);
			jsonUpdata.put("address", address);
			jsonUpdata.put("tel", tel);
			jsonUpdata.put("age", age);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("check", "menuNo");
			jsonObject.put("menuNo","5");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata", jsonUpdata);
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("UserView error");
		}
		
	}

	public static void seeCart(ChatClient chatClient) {
		
		
	}

	public static void seeOrderList(ChatClient chatClient) {
		
		
	}

	
}
