package project.View;

import java.util.Scanner;

import org.json.JSONObject;

import project.BookClient;

public class MyPageView {
	static Scanner sc= new Scanner(System.in);
	
	public static void seeBoard(BookClient bookClient) {
		System.out.println();
		System.out.println("-----------------------------------------------------");
		System.out.println("1.개인정보 수정 | 2.구매내역 | 3.장바구니 | 4.회원탈퇴 | 5.메인화면");
		System.out.println("-----------------------------------------------------");
		System.out.print("listNo = ");
		String listNo=sc.nextLine();
		switch(listNo) {
			case "1":
				writeUserUpdata(bookClient);
				break;
			case "2":
				seeOrderList(bookClient);
				break;
			case "3":
				seeCart(bookClient);
				break;
			case "4":
				UserView.writeOut(bookClient);
				break;
			case "5":
				bookClient.choice="first";
				System.out.println();
				break;
			default :
				System.out.println("잘못 입력하셨습니다.");
				seeBoard(bookClient);
				break;
		}
	}
	
	//개인정보 수정
	public static void writeUserUpdata(BookClient bookClient) {
		//빈칸이나 object를 못받는데... 확인 필요
		System.out.println("\n------------[개인정보수정]------------");
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
			writeUserUpdata(bookClient);
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
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo","5");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata", jsonUpdata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
			seeBoard(bookClient);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Mypage UserUpdata error");
		}
		
	}
	
	public static void seeOrderList(BookClient bookClient) {
		System.out.println("------------[주문내역]------------");
		try {
			JSONObject jsonUpdata = new JSONObject();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo","5");
			jsonObject.put("listNo", "2");
			jsonObject.put("updata", jsonUpdata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
			
			System.out.println("주문번호 | 책이름 | 책 갯수 | 책 가격");
			System.out.println("-------------------------------------------------");
			int[] cnt=new int[bookClient.jsonArray.length()];
			for(int i=0;i<bookClient.jsonArray.length();i++) {
				JSONObject jo=bookClient.jsonArray.getJSONObject(i);
				cnt[i]=jo.getInt("orderNum");
				System.out.print(jo.getInt("orderNum")+"\t"+jo.getString("bookName")+"\t");
				System.out.println(jo.getInt("bookAmount")+"\t"+jo.getInt("bookPrice"));
				System.out.println("-------------------------------------------------");
				
			}
			System.out.println();
			System.out.println();
			System.out.println("1.주문번호 자세히 보기 | 2.MyPage | 3.메인보드 ");
			System.out.println("-------------------------------------------------");
			System.out.print("listNo : ");
			String listNo = sc.nextLine();
			switch(listNo) {
				case "1":
					seeDetailOrderList(bookClient,cnt);
					break;
				case "2":
					seeBoard(bookClient);
					break;
				case "3":
					bookClient.choice="first";
					break;
				default:
					System.out.println("없는 번호입니다. 다시 선택해 주세요");
					seeOrderList(bookClient);
			}	
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Mypage orderList error");
		}
		
	}

	
	public static void seeDetailOrderList(BookClient bookClient, int[] cnt) {
		System.out.print("[자세히보기] orderNum : ");
		int orderNum = Integer.parseInt(sc.nextLine());
		boolean result=false;
		for(int i=0;i<cnt.length;i++) {
			if(cnt[i]==orderNum) {
				result=true;
				break;
			}else
				result=false;
		}
		if(result==false) {
			System.out.println("없는번호입니다. 다시 선택해주세요");
			seeDetailOrderList(bookClient, cnt);
		}
		try {
			JSONObject jsonUpdata = new JSONObject();
			jsonUpdata.put("orderNum", orderNum);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo","5");
			jsonObject.put("listNo", "6");
			jsonObject.put("updata", jsonUpdata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
			if(bookClient.flag==false) {
				System.out.println("없는 orderNum입니다. 다시 입력해주세요");
				seeDetailOrderList(bookClient, cnt);
			}
			for(int i=0;i<bookClient.jsonArray.length();i++) {
				JSONObject jo=bookClient.jsonArray.getJSONObject(i);
				System.out.println("--------------------------------------------------");
				System.out.println("책 이름 : "+jo.getString("bookName"));
				System.out.println("가격 : "+jo.getInt("orderPrice"));
				System.out.println("구매량 : "+jo.getInt("orderAmount"));
				System.out.println("주문 날짜 : "+jo.getString("orderDate"));
				System.out.println("배송지 : "+jo.getString("orderAddress"));
				System.out.println("받는사람 : "+jo.getString("person"));
				System.out.println("주문자 : "+jo.getString("userId"));
			}
			seeBoard(bookClient);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Mypage detailOrderList error");
		}
		
	} 
	
	public static void seeCart(BookClient bookClient) {
		System.out.println("------------------[장바구니]------------------");

		try {
			JSONObject jsonUpdata = new JSONObject();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo","5");
			jsonObject.put("listNo", "3");
			jsonObject.put("updata", jsonUpdata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
			for(int i=0;i<bookClient.jsonArray.length();i++) {
				JSONObject jo=bookClient.jsonArray.getJSONObject(i);
				System.out.println("Book Name: "+jo.getString("bookName"));
				System.out.println("Book Amount : "+jo.getInt("bookAmount"));
				System.out.println("Sum Price : "+jo.getInt("sumPrice"));
				System.out.println("-------------------------------------------");
			}
			seeBoard(bookClient);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Mypage cart error");
		}
		
	}

	
}
