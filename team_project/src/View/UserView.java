package project.View;

import java.util.Scanner;

import org.json.JSONObject;

import project.BookClient;

public class UserView {
	static Scanner sc= new Scanner(System.in);
	
	public static void seeBoard(BookClient bookClient)
	{
		System.out.println("\n------☆-★-☆-★--------");
		System.out.println("1.로그인 | 2.회원가입");
		System.out.print("선택 : ");
		String number=sc.nextLine();
		switch(number) {
			case "1":
				writeLogin(bookClient);
				break;
			case "2":
				writeJoin(bookClient);
				break;
			default :
				System.out.println("잘못 입력하셨습니다.");
				seeBoard(bookClient);
				break;
		}
	}
	
	//로그인
	//카운트 세번했을때 메인보드로 토하는거 만들고 싶어
	public static void writeLogin(BookClient bookClient) {
		System.out.println("\n------------[로그인]------------");
		System.out.print("ID : ");
		String id = sc.nextLine();
		System.out.print("PASSWORD : ");
		String password = sc.nextLine();
		if(id.equals("")||password.equals("")) {
			System.out.println("아이디 또는 비밀번호를 입력하지 않으셨습니다.");
			writeLogin(bookClient);
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
			bookClient.send(json);
			bookClient.receive();
			if(bookClient.flag==false) {
				writeLogin(bookClient);
			}
			
			switch(bookClient.data) {
				case "category":
					CategoryView.bookDetail(bookClient);
					break;
				case "best":
					BestSellerView.seeBestSeller(bookClient);
					break;
				case "event":
					EventView.seeBoard(bookClient);
					break;
				case "search":
					SearchView.bookDetail(bookClient);
					break;
				case "mypage":
					MyPageView.seeBoard(bookClient);
					break;
				case "qna":
					QnAView.seeBoard(bookClient);
					break;
				case "user":
					bookClient.choice="first";
					break;
				default:
					System.out.println("다른 번호를 선택해주세요");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("UserView error");
		}
		
	}
	
	//로그아웃
	public static void writeLogout(BookClient bookClient) {
		System.out.println("\n[로그아웃하시겠습니까?(y/n)]");
		String result=sc.nextLine();
		if(!result.equals("y")&&!result.equals("n")) {
			System.out.println("다시 선택해주세요.");
			writeLogout(bookClient);
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
				bookClient.send(json);
				bookClient.receive();
			}else {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("controller", "mainBoard");
				jsonObject.put("choice", "first");
				jsonObject.put("flag", bookClient.flag);
				String json = jsonObject.toString();
				bookClient.send(json);
				bookClient.receive();
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserView logout error");
		}
		
	}
	//회원가입
	public static void writeJoin(BookClient bookClient) {
		System.out.println("\n---------[회원가입]---------");
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
				||tel.equals("")||!sex.equals("man")||sex.equals("woman")) {
			System.out.println("입력하지 않은 데이터가 있습니다.");
			writeJoin(bookClient);
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
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "7");
			jsonObject.put("listNo", "3");
			jsonObject.put("updata",jsonUpdata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
			//receive에서 받아온 flag값으로 비교, flag가 로그인 되기 전이므로 flag를 비교값으로 사용
			if(bookClient.flag==false) {
				System.out.println("id가 존재합니다.");
				writeJoin(bookClient);
			}else {
				//false를 저장하는 이유는 이때 flag는 로그인 여부를 정하기 위해서이다.
				bookClient.flag=false;
				writeLogin(bookClient);
			}
		}catch(Exception e) {
			System.out.println("나이를 입력하지 않으셨습니다.");
			writeJoin(bookClient);
		}
		
	}
	//회원탈퇴
	public static void writeOut(BookClient bookClient) {
		System.out.println("\n[회원탈퇴 하시겠습니까?(y/n)]");
		String result=sc.nextLine();
		if(!result.equals("y")&&!result.equals("n")) {
			System.out.println("다시 선택해주세요.");
			writeOut(bookClient);
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
				bookClient.send(json);
				bookClient.receive();
			}else {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("controller", "mainBoard");
				jsonObject.put("choice", "first");
				jsonObject.put("flag", bookClient.flag);
				String json = jsonObject.toString();
				bookClient.send(json);
				bookClient.receive();
			}
			if(bookClient.flag==true) {
				System.out.print("오류 발생 다시 해주세요");
				writeOut(bookClient);
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserView logout error");
		}
	}
	
}
