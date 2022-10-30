package day06;

import java.util.Scanner;

import org.json.JSONObject;

public class UserView {
	static Scanner sc= new Scanner(System.in);
	
	static void board(ChatClient chatClient)
	{
		System.out.println("\n----------------");
		System.out.println("1.로그인 | 2.회원가입");
		System.out.print("선택 : ");
		String number=sc.nextLine();
		switch(number) {
			case "1":
				login(chatClient);
				break;
			case "2":
				in(chatClient);
				break;
			default :
				System.out.println("잘못 입력하셨습니다.");
				board(chatClient);
				break;
		}
	}
	
	//로그인
	static void login(ChatClient chatClient) {
		System.out.println("\n---------로그인---------");
		System.out.print("ID : ");
		String id = sc.nextLine();
		System.out.print("PASSWORD : ");
		String password = sc.nextLine();
		if(id.equals("")||password.equals("")) {
			System.out.println("아이디 또는 비밀번호를 입력하지 않으셨습니다.");
			login(chatClient);
		}
		try {
			//Scanner로 값을 받지 않으면 오류가 생긴다. (오류수정 필요)
			JSONObject updata = new JSONObject();
			updata.put("id",id);
			updata.put("password",password);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("check", "menuNo");
			jsonObject.put("menuNo", "7");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata",updata);
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();
			if(chatClient.flag==false) {
				login(chatClient);
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("UserView error");
		}
		
	}
	
	//로그아웃
	static void logout(ChatClient chatClient) {
		System.out.println("\n-------로그아웃하시겠습니까?(y/n)-------");
		String result=sc.nextLine();
		if(!result.equals("y")&&!result.equals("n")) {
			System.out.println("다시 선택해주세요.");
			logout(chatClient);
		}
		try {
			if(result.equals("y")) {
				JSONObject updata = new JSONObject();
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("check", "menuNo");
				jsonObject.put("menuNo", "7");
				jsonObject.put("listNo", "2");
				jsonObject.put("updata",updata);
				String json = jsonObject.toString();
				chatClient.send(json);
				chatClient.receive();
			}else {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("check", "mainBoard");
				jsonObject.put("choice", "first");
				jsonObject.put("flag", chatClient.flag);
				String json = jsonObject.toString();
				chatClient.send(json);
				chatClient.receive();
			
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserView logout error");
		}
		
	}
	//회원가입
	static void in(ChatClient chatClient) {
		System.out.println("\n---------회원가입---------");
		System.out.print("생성할 아이디를 입력하세요\nID :");
		String id = sc.nextLine();
		System.out.print("생성할 비밀번호를 입력하세요\nPASSWORD : ");
		String password = sc.nextLine();
		System.out.print("이름을 입력하세요 : ");
		String name=sc.nextLine();
		System.out.print("이메일을 입력하세요 : ");
		String email=sc.nextLine();
		System.out.print("주소를 입력하세요 : ");
		String address=sc.nextLine();
		System.out.print("전화번호를 입력하세요 : ");
		String tel=sc.nextLine();
		System.out.print("나이를 입력하세요 : ");
		int age=sc.nextInt();
		System.out.print("성별을 입력하세요(man or woman) : ");
		String sex=sc.nextLine();
		if(id.equals("")||password.equals("")||name.equals("")||email.equals("")
				||tel.equals("")/*||age.equalse("")*/||!sex.equals("man")&&sex.equals("woman")) {
			System.out.println("아이디 또는 비밀번호를 입력하지 않으셨습니다.");
			in(chatClient);
		}
		try {
			JSONObject updata = new JSONObject();
			updata.put("id",id);
			updata.put("password",password);
			updata.put("name", name);
			updata.put("email", email);
			updata.put("address", address);
			updata.put("tel", tel);
			updata.put("age", age);
			updata.put("sex", sex);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("check", "menuNo");
			jsonObject.put("menuNo", "7");
			jsonObject.put("listNo", "3");
			jsonObject.put("updata",updata);
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();
			if(chatClient.flag==false) {
				login(chatClient);
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("UserView error");
		}
		
	}
	//회원탈퇴
	static void out() {
		
	}
	
}
