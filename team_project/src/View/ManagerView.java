package View;
import java.util.Scanner;

import org.json.JSONObject;

import project.ChatClient;

public class ManagerView {
	static Scanner sc= new Scanner(System.in);
	
	//manager login
	public static void writeManagerLogin(ChatClient chatClient) {
		System.out.println("\n-------관리자 로그인---------");
		System.out.print("ID : ");
		String id = sc.nextLine();
		System.out.print("PASSWORD : ");
		String password = sc.nextLine();
		if(id.equals("")||password.equals("")) {
			System.out.println("아이디 또는 비밀번호를 입력하지 않으셨습니다.");
			writeManagerLogin(chatClient);
		}
		try {
			//Scanner로 값을 받지 않으면 오류가 생긴다. (오류수정 필요)
			JSONObject jsonUpdata = new JSONObject();
			jsonUpdata.put("id",id);
			jsonUpdata.put("password",password);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("check", "menuNo");
			jsonObject.put("menuNo", "9");
			jsonObject.put("listNo", "7");
			jsonObject.put("updata",jsonUpdata);
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();
			//로그인 실패시
			if(chatClient.mflag==false) {
				writeManagerLogin(chatClient);
			}
			//로그인 성공시
			else {
				chatClient.flag=true;
				seeBoard(chatClient);
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("ManagerView error");
		}				
	}
	
	//로그인 후 seeBoard
	public static void seeBoard(ChatClient chatClient){
		System.out.println();
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("1.책 저장 | 2.책 삭제| 3.모든 유저 정보 | 4.유저 정보 | 5.로그아웃 | 6.qna답글달기 | 7.메인화면");
		System.out.println("--------------------------------------------------------------------------");
		System.out.print("listNo = ");
		String listNo=sc.nextLine();
		switch(listNo) {
			case "1":
				writeBookUpdata(chatClient);
				break;
			case "2":
				writeBookDelete(chatClient);
				break;
			case "3":
				seeUserData(chatClient);
				break;
			case "4":
				seeDetailUserData(chatClient);
			case "5":
				writeManagerLogout(chatClient);
				break;
			case "6":
				writeQnA(chatClient);
				break;
			case "7":
				chatClient.choice="first";
				System.out.println();
				break;
			default :
				System.out.println("잘못 입력하셨습니다.");
				seeBoard(chatClient);
				break;
		}
	}
	
	//책 업데이트
	public static void writeBookUpdata(ChatClient chatClient) {
				System.out.println("\n[Book 추가]");
				System.out.println("----------");
				System.out.print("BOOK ID : ");
				int bookId = Integer.parseInt(sc.nextLine());
				System.out.print("BOOK NAME = ");
				String bookName = sc.nextLine();
				System.out.print("BOOK Price = ");
				int bookPrice = Integer.parseInt(sc.nextLine());
				System.out.print("BOOK Writer = ");
				String bookWriter = sc.nextLine();
				System.out.print("BOOK Stock = ");
				int bookStock = Integer.parseInt(sc.nextLine());
				System.out.print("BOOK Content = ");
				String bookContent = sc.nextLine();
				System.out.print("BOOK Company = ");
				String bookCompany = sc.nextLine();
				System.out.print("BOOK Category ID = ");
				int categoryId = Integer.parseInt(sc.nextLine());
				System.out.print("BOOK Sellint Price = ");
				int bookSellingPrice = Integer.parseInt(sc.nextLine());

				try {
					//Scanner로 값을 받지 않으면 오류가 생긴다.(오류수정 필요)
					JSONObject jsonUpdata = new JSONObject();
					jsonUpdata.put("bookId",bookId );
					jsonUpdata.put("bookName", bookName);
					jsonUpdata.put("bookPrice", bookPrice);
					jsonUpdata.put("bookWriter", bookWriter);
					jsonUpdata.put("bookStock", bookStock);
					jsonUpdata.put("bookContent", bookContent);
					jsonUpdata.put("bookCompany", bookCompany);
					jsonUpdata.put("categoryId", categoryId);
					jsonUpdata.put("bookSellingPrice", bookSellingPrice);
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("check", "menuNo");
					jsonObject.put("menuNo","9");
					jsonObject.put("listNo", "1");
					jsonObject.put("updata", jsonUpdata);
					String json = jsonObject.toString();
					chatClient.send(json);
					chatClient.receive();
					if(chatClient.mflag==false) {
						writeBookUpdata(chatClient);
					}else {
						seeBoard(chatClient);
					}
				}catch(Exception e) {
					e.printStackTrace();
					System.out.println("ManaerView bookupdata error");
				}
				
		
	}
	
	//책 삭제
	public static void writeBookDelete(ChatClient chatClient) {
		System.out.print("삭제하고 싶은 책 id를 쓰세요");
		int bookId=Integer.parseInt(sc.nextLine());
		try {
			JSONObject jsonUpdata = new JSONObject();
			jsonUpdata.put("bookId",bookId);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo","9");
			jsonObject.put("listNo", "2");
			jsonObject.put("updata", jsonUpdata);
			String json=jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();
			if(chatClient.mflag==false) {
				writeBookDelete(chatClient);
			}else {
				seeBoard(chatClient);
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("ManagerView delete error");
		}
	}
	
	//모든 유저 정보 보기
	public static void seeUserData(ChatClient chatClient) {
		System.out.println("\n----유저정보------");
		try {
			JSONObject jsonUpdata = new JSONObject();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "9");
			jsonObject.put("listNo", "3");
			jsonObject.put("updata",jsonUpdata);
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();
			
			for(int i=0;i<chatClient.jsonArray.length();i++) {
				JSONObject jo=chatClient.jsonArray.getJSONObject(i);
				System.out.println("ID: "+jo.getString("userId")+" | NAME : "+jo.getString("userName"));
			}
			seeBoard(chatClient);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//원하는 id의 유저 정보 보기
	public static void seeDetailUserData(ChatClient chatClient) {
		System.out.println("\n----Detail 유저정보------");
		System.out.print("검색할 유저 id : ");
		String userId=sc.nextLine();
		try {
			JSONObject jsonUpdata = new JSONObject();
			jsonUpdata.put("userId", userId);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "9");
			jsonObject.put("listNo", "4");
			jsonObject.put("updata",jsonUpdata);
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();
			if(chatClient.mflag==false) {
				seeDetailUserData(chatClient);
			}else {
				chatClient.mflag=true;
				for(int i=0;i<chatClient.jsonArray.length();i++) {
					JSONObject jo=chatClient.jsonArray.getJSONObject(i);
					System.out.println(jo.getString("ID : "+"userId"));
					System.out.println(jo.getString("Password : "+"userPassword"));
					System.out.println(jo.getString("Name : "+"userName"));
					System.out.println(jo.getString("Email : "+"userEmail"));
					System.out.println(jo.getString("Address : "+"userAddress"));
					System.out.println(jo.getString("TEL : "+"userTel"));
					System.out.println(jo.getString("InDate : "+"userIndate"));
					System.out.println(jo.getInt("Age : "+"userAge"));
					System.out.println(jo.getString("Sex : "+"userSex"));
				}
				seeBoard(chatClient);
			}
			seeBoard(chatClient);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//관리자 로그아웃
	public static void writeManagerLogout(ChatClient chatClient) {
		System.out.println("\n-------관리자 로그아웃하시겠습니까?(y/n)-------");
		String result=sc.nextLine();
		if(!result.equals("y")&&!result.equals("n")) {
			System.out.println("다시 선택해주세요.");
			writeManagerLogout(chatClient);
		}
		try {
			if(result.equals("y")) {
				chatClient.flag=false;
				JSONObject jsonUpdata = new JSONObject();
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("controller", "menuNo");
				jsonObject.put("menuNo", "9");
				jsonObject.put("listNo", "5");
				jsonObject.put("updata",jsonUpdata);
				String json = jsonObject.toString();
				chatClient.send(json);
				chatClient.receive();
			}else {
				seeBoard(chatClient);
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("ManagerView logout error");
		}
		
	}
	
	public static void writeQnA(ChatClient chatClient) {
		// TODO Auto-generated method stub
		
	}

	

	

	

	
}
