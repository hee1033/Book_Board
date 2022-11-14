package project.View;

import java.io.IOException;
import java.util.Scanner;

import org.json.JSONObject;

import project.BookClient;

public class BestSellerView {
	static Scanner scan = new Scanner(System.in);
	static String bookId;
	static String listNo;
	// 베스트 셀러 목록
	public static void seeBestSeller(BookClient bookClient) {
		System.out.println("--------------------------★★★-[베스트셀러]-★★★--------------------------");
		System.out.println("1.전체 베스트셀러 | 2.연령별 베스트셀러 | 3.성별 베스트셀러 | 4.메인 페이지로 돌아가기");
		System.out.println("-----------------------------------------------------------------------");
		System.out.println();
		System.out.print("listNo = ");
		listNo = scan.nextLine();
		switch (listNo) {
		case "1":
			seeAll(bookClient);
			break;
		case "2":
			seeAge(bookClient);
			break;
		case "3":
			seeGender(bookClient);
			break;
		case "4":
			bookClient.choice= "first";
			break;
		default:
			System.out.println();
			System.out.println("다시 입력해주세요");	
			System.out.println("-----------------------------------------------------------------------");
			seeBestSeller(bookClient);
			break;
		}
	}

	//베스트 셀러 top 3
	public static void seeAll(BookClient bookClient) {
		System.out.println("------------------[전체 베스트셀러]------------------");
		System.out.println("책 번호\t\t 책 이름");
		System.out.println("-------------------------------------------------");
		try {
			JSONObject updata = new JSONObject();
			updata.put("best", "1");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "2");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();

			for (int i = 0; i < bookClient.jsonArray.length(); i++) {
				JSONObject jo = bookClient.jsonArray.getJSONObject(i);
				System.out.print(jo.getInt("bookId") + "\t");
				System.out.println(jo.getString("bookName"));
			}
			System.out.println("-------------------------------------------------");
			bookBoard(bookClient);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void seeAge(BookClient bookClient) {
		System.out.println("------------------[연령대 베스트셀러]------------------");
		System.out.println("1. 10 ~ 20대 | 2. 20 ~ 40대  | 3. 40대 이상" );
		System.out.println("-------------------------------------------------");
		System.out.print("선택 : ");
		String select = scan.nextLine();
		switch (select) {
		case "1":
			detailAge(bookClient,select);
			break;
		case "2":
			detailAge(bookClient,select);
			break;
		case "3":
			detailGender(bookClient,select);
			break;
		default :
			System.out.println("없는번호를 선택하셨습니다. 다시 선택해주세요");
			seeAge(bookClient);
			break;
		}

	}
	
	//베스트 셀러 top 3
		public static void seeGender(BookClient bookClient) {
			System.out.println("------------------[성별 베스트셀러]------------------");
			System.out.println("1. 남성 | 2. 여성 " );
			System.out.println("-------------------------------------------------");
			System.out.print("선택 : ");
			String select = scan.nextLine();
			switch (select) {
			case "1":
				detailGender(bookClient,select);
				break;
			case "2":
				detailGender(bookClient,select);
				break;
			default :
				System.out.println("없는번호를 선택하셨습니다. 다시 선택해주세요");
				seeGender(bookClient);
				break;
			}
		}
	//연령대별 bestseller
	public static void detailAge(BookClient bookClient,String select) {
		if(select.equals("1")){
			System.out.println("[10~20대 베스트셀러 TOP3]");
		}else if(select.equals("2")) {
			System.out.println("[20~40대 베스트셀러 TOP3]");
		}else {
			System.out.println("[40대 이상 베스트셀러 TOP3]");
		}
		System.out.println("책 번호\t\t 책 이름");
		System.out.println("-------------------------------------------------");
		try {
			JSONObject updata = new JSONObject();
			updata.put("best", select);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "2");
			jsonObject.put("listNo", "2");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
			for (int i = 0; i < bookClient.jsonArray.length(); i++) {
				JSONObject jo = bookClient.jsonArray.getJSONObject(i);
				System.out.print(jo.getInt("bookId") + "\t");
				System.out.println(jo.getString("bookName"));
			}
			bookBoard(bookClient);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//성별 bestseller
	public static void detailGender(BookClient bookClient,String select) {
		if(select.equals("1")){
			System.out.println("[남성 베스트셀러 TOP3]");
		}else if(select.equals("2")) {
			System.out.println("[여성 베스트셀러 TOP3]");
		}
		System.out.println("책 번호\t\t 책 이름");
		System.out.println("-------------------------------------------------");
		try {
			JSONObject updata = new JSONObject();
			updata.put("best", select);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "2");
			jsonObject.put("listNo", "3");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
			for (int i = 0; i < bookClient.jsonArray.length(); i++) {
				JSONObject jo = bookClient.jsonArray.getJSONObject(i);
				System.out.print(jo.getInt("bookId") + "\t");
				System.out.println(jo.getString("bookName"));
			}
			bookBoard(bookClient);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void bookBoard(BookClient bookClient) {
		//돌아가기
		System.out.println("-------------------------------------------------");
		System.out.println("1.책 선택 | 2.메인 페이지로 돌아가기");
		System.out.println("-------------------------------------------------");

		System.out.print("선택 : ");
		String select = scan.nextLine();
		switch (select) {
		case "1":
			System.out.print("책 번호를 입력하세요 : ");
			bookId = scan.nextLine();
			bookDetail(bookClient);
			break;
		case "2":
			bookClient.choice= "first";
			break;
		default : 
			System.out.println();
			System.out.println("다시 입력해주세요");	
			System.out.println("-----------");
			bookBoard(bookClient);
		}

	}


	// 북 상세정보
	public static void bookDetail(BookClient bookClient) {

		System.out.println("[책 상세 정보]");
		System.out.println("-----------------------------------------------");

		try {
			JSONObject updata = new JSONObject();
			updata.put("bookId", bookId);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "1");
			jsonObject.put("listNo", "2");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();

			for (int i = 0; i < bookClient.jsonArray.length(); i++) {
				JSONObject jo = bookClient.jsonArray.getJSONObject(i);
				System.out.print( "책이름 : "+ jo.getString("bookName") + "\t");
				System.out.print("재고수량 :"+jo.getInt("bookStock") + "\n");
				System.out.print("원가 : "+jo.getInt("bookPrice") + "\t");
				System.out.print("-> 할인가 : "+jo.getInt("bookSellingPrice") + "\n");
				System.out.print("작가 : "+jo.getString("bookWriter") + "\t");
				System.out.print("출판사 : "+jo.getString("companyName") + "\n");
				System.out.print("줄거리 : "+jo.getString("bookContent"));
			}

			System.out.println("\n------------------------------------");
			buyBoard(bookClient);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserView error");
		}
	}
	
	
	public static void buyBoard(BookClient bookClient) {
		System.out.println("--------------------------------");
		System.out.println("1.책 구매 | 2.장바구니 | 3. 메인페이지로 돌아가기");
		System.out.print("선택 : ");
		String select = scan.nextLine();
		
		switch (select) {
		case "1":
			if(bookClient.flag == false) {
				System.out.println("로그인이 필요합니다.");
				UserView.seeBoard(bookClient);
			}else {
				System.out.println("책이 구매되었습니다.");
				buyBook(bookClient);
			};
			break;
		case "2" :
			if(bookClient.flag == false) {
				System.out.println("로그인이 필요합니다.");
				UserView.seeBoard(bookClient);
			}else {
				System.out.println("장바구니에 담겼습니다.");
				insertCart(bookClient);
			}
			break;
		case "3":
			bookClient.choice= "first";
			break;
		default : 
			System.out.println();
			System.out.println("다시 입력해주세요");	
			System.out.println("-------------------------------------------------");
			seeBestSeller(bookClient);
		}
		
	}
	
	// 책 구매하기
	public static void buyBook(BookClient bookClient) {
		try {
			JSONObject updata = new JSONObject();
			updata.put("bookId", bookId);
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "1");
			jsonObject.put("listNo", "3");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//장바구니 담기
	public static void insertCart(BookClient bookClient) {
		try {
			JSONObject updata = new JSONObject();
			updata.put("bookId", bookId);
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "1");
			jsonObject.put("listNo", "4");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}