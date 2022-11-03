package View;

import java.util.Scanner;

import org.json.JSONObject;

import project.ChatClient;

public class UserView {
	static Scanner sc= new Scanner(System.in);
	
	public static void seeBoard(ChatClient chatClient)
	{
		System.out.println("\n----------------");
		System.out.println("1.로그인 | 2.회원가입");
		System.out.print("선택 : ");
		String number=sc.nextLine();
		switch(number) {
			case "1":
				writeLogin(chatClient);
				break;
			case "2":
				writeIn(chatClient);
				break;
			default :
				System.out.println("잘못 입력하셨습니다.");
				seeBoard(chatClient);
				break;
		}
	}
	
	//로그인
	public static void writeLogin(ChatClient chatClient) {
		System.out.println("\n---------로그인---------");
		System.out.print("ID : ");
		String id = sc.nextLine();
		System.out.print("PASSWORD : ");
		String password = sc.nextLine();
		if(id.equals("")||password.equals("")) {
			System.out.println("아이디 또는 비밀번호를 입력하지 않으셨습니다.");
			writeLogin(chatClient);
		}
		try {
			//Scanner로 값을 받지 않으면 오류가 생긴다. (오류수정 필요)
			JSONObject jsonUpdata = new JSONObject();
			jsonUpdata.put("id",id);
			jsonUpdata.put("password",password);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "7");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata",jsonUpdata);
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();
			if(chatClient.flag==false) {
				writeLogin(chatClient);
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("UserView error");
		}
		
	}
	
	//로그아웃
	public static void writeLogout(ChatClient chatClient) {
		System.out.println("\n-------로그아웃하시겠습니까?(y/n)-------");
		String result=sc.nextLine();
		if(!result.equals("y")&&!result.equals("n")) {
			System.out.println("다시 선택해주세요.");
			writeLogout(chatClient);
		}
		try {
			if(result.equals("y")) {
				JSONObject jsonUpdata = new JSONObject();
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("controller", "menuNo");
				jsonObject.put("menuNo", "7");
				jsonObject.put("listNo", "2");
				jsonObject.put("updata",jsonUpdata);
				String json = jsonObject.toString();
				chatClient.send(json);
				chatClient.receive();
			}else {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("controller", "mainBoard");
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
	public static void writeIn(ChatClient chatClient) {
		System.out.println("\n---------회원가입---------");
		System.out.print("ID :");
		String id = sc.nextLine();
		System.out.print("PASSWORD : ");
		String password = sc.nextLine();
		System.out.print("NAME : ");
		String name=sc.nextLine();
		System.out.print("Email : ");
		String email=sc.nextLine();
		System.out.print("Address : ");
		String address=sc.nextLine();
		System.out.print("TEL : ");
		String tel=sc.nextLine();
		System.out.print("Age : ");
		int age=Integer.parseInt(sc.nextLine());
		System.out.print("SEX(man or woman) : ");
		String sex=sc.nextLine();
		if(id.equals("")||password.equals("")||name.equals("")||email.equals("")
				||tel.equals("")/*||age.equalse("")*/||!sex.equals("man")||sex.equals("woman")) {
			System.out.println("입력하지 않은 데이터가 있습니다.");
			writeIn(chatClient);
		}
		try {
			JSONObject jsonUpdata = new JSONObject();
			jsonUpdata.put("id",id);
			jsonUpdata.put("password",password);
			jsonUpdata.put("name", name);
			jsonUpdata.put("email", email);
			jsonUpdata.put("address", address);
			jsonUpdata.put("tel", tel);
			jsonUpdata.put("age", age);
			jsonUpdata.put("sex", sex);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("check", "menuNo");
			jsonObject.put("menuNo", "7");
			jsonObject.put("listNo", "3");
			jsonObject.put("updata",jsonUpdata);
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();
			//receive에서 받아온 flag값으로 비교, flag가 로그인 되기 전이므로 flag를 비교값으로 사용
			if(chatClient.flag==false) {
				System.out.println("id가 존재합니다.");
				writeIn(chatClient);
			}else {
				//false를 저장하는 이유는 이때 flag는 로그인 여부를 정하기 위해서이다.
				chatClient.flag=false;
				writeLogin(chatClient);
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("UserView error");
		}
		
	}
	//회원탈퇴
	public static void writeOut(ChatClient chatClient) {
		//회원탈퇴후 db업데이트를 받아오지 않아 다시 로그인이 된다.
		System.out.println("\n-------회원탈퇴 하시겠습니까?(y/n)-------");
		String result=sc.nextLine();
		if(!result.equals("y")&&!result.equals("n")) {
			System.out.println("다시 선택해주세요.");
			writeOut(chatClient);
		}
		try {
			if(result.equals("y")) {
				JSONObject jsonUpdata = new JSONObject();
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("controller", "menuNo");
				jsonObject.put("menuNo", "7");
				jsonObject.put("listNo", "4");
				jsonObject.put("updata",jsonUpdata);
				String json = jsonObject.toString();
				chatClient.send(json);
				chatClient.receive();
			}else {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("controller", "mainBoard");
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
	
}
