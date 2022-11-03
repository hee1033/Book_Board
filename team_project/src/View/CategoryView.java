package View;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;

import DTO.BookDTO;
import project.ChatClient;


public class CategoryView {
	static Scanner scan = new Scanner(System.in);
	static JSONObject bookId;
	
	// 카테고리 목록
	public static void category(ChatClient chatClient) {
		System.out.println("[카테고리]");
		System.out
				.println("1.소설 | 2.시/에세이 | 3. 인문 | 4. 컴퓨터/IT | 5.가정/육아\n6.요리 | 7.건강 | 8.취미/실용/스포츠 | 9.경제/경영 | 10.자기계발");
		System.out.println();
		System.out.print("listNo = ");
		String listNo = scan.nextLine();
		switch (listNo) {
		case "1":
			seeNovel(chatClient);
			break;
		case "2":
			seeAssay(chatClient);
			break;
		case "3":
			seeHumanities(chatClient);
			break;
		case "4":
			seeIT(chatClient);
			break;
		case "5":
			seeFamily(chatClient);
			break;
		case "6":
			seeCook(chatClient);
			break;
		case "7":
			seeHealth(chatClient);
			break;
		case "8":
			seeHobby(chatClient);
			break;
		case "9":
			seeManagement(chatClient);
			break;
		case "10":
			seeSelfImprovement(chatClient);
			break;
		}
	}

	// 카테고리 - 소설
	public static void seeNovel(ChatClient chatClient) {
		System.out.print("pageNo  : ");
		int pageNo=Integer.parseInt(scan.nextLine());
		System.out.println("==============책 목록==============");
		System.out.println("카테고리\t 책 번호\t 책 이름");
		try {
			JSONObject updata = new JSONObject();
			updata.put("categoryId", "1");
			updata.put("pageNo", pageNo);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "1");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();
			
			
			for (int i = 0; i < chatClient.jsonArray.length(); i++) {
				JSONObject jo = chatClient.jsonArray.getJSONObject(i);
				System.out.print(jo.getString("categoryName") + "\t");
				System.out.print(jo.getInt("bookId") + "\t");
				System.out.println(jo.getString("bookName"));
			}

			System.out.println("================================");
			System.out.println("--------------------------------");
			System.out.println("1.책 선택 | 2.구매하기 | 3.장바구니 | 4.메인 페이지로 돌아가기");
			System.out.println("--------------------------------");
			System.out.print("선택 : ");
			String select = scan.nextLine();
			switch (select) {
			case "1":
				bookDetail(chatClient, bookId);
				break;
			case "2":
				bookDetail(chatClient, bookId);
				break;
			case "3":
				bookDetail(chatClient, bookId);
				break;
			case "4":
				chatClient.choice= "first";
				break;
			default : 
				System.out.println();
				System.out.println("다시 입력해주세요");	
				System.out.println("--------------------------------");
				seeNovel(chatClient);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserView error");
		}

	}

	// 카테고리 - 시/에세이
	public static void seeAssay(ChatClient chatClient) {
		System.out.println("==============책 목록==============");
		System.out.println("카테고리\t 책 번호\t 책 이름");
		try {
			JSONObject updata = new JSONObject();
			updata.put("categoryId", "2");

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "1");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();


			for (int i = 0; i < chatClient.jsonArray.length(); i++) {
				JSONObject jo = chatClient.jsonArray.getJSONObject(i);
				System.out.print(jo.getString("categoryName") + "\t");
				System.out.print(jo.getString("bookId") + "\t");
				System.out.println(jo.getString("bookName"));
			}

			System.out.println("================================");
			System.out.println("--------------------------------");
			System.out.println("1.책 선택 | 2.메인 페이지로 돌아가기");
			System.out.println("--------------------------------");
			System.out.print("선택 : ");
			String select = scan.nextLine();
			switch (select) {
			case "1":
				bookDetail(chatClient, bookId);
				break;
			case "2":
				chatClient.choice= "first";
			default : 
				System.out.println();
				System.out.println("다시 입력해주세요");	
				System.out.println("--------------------------------");
				seeAssay(chatClient);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserView error");
		}


	}

	// 카테고리 - 인문
	public static void seeHumanities(ChatClient chatClient) {
		System.out.println("==============책 목록==============");
		System.out.println("카테고리\t 책 번호\t 책 이름");
		try {
			JSONObject updata = new JSONObject();
			updata.put("categoryId", "3");

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "1");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();

			for (int i = 0; i < chatClient.jsonArray.length(); i++) {
				JSONObject jo = chatClient.jsonArray.getJSONObject(i);
				System.out.print(jo.getString("categoryName") + "\t");
				System.out.print(jo.getString("bookId") + "\t");
				System.out.println(jo.getString("bookName"));
			}

			System.out.println("================================");
			System.out.println("--------------------------------");
			System.out.println("1.책 선택 | 2.메인 페이지로 돌아가기");
			System.out.println("--------------------------------");
			System.out.print("선택 : ");
			String select = scan.nextLine();
			switch (select) {
			case "1":
				bookDetail(chatClient, bookId);
				break;
			case "2":
				chatClient.choice= "first";
			default : 
				System.out.println();
				System.out.println("다시 입력해주세요");	
				System.out.println("--------------------------------");
				seeAssay(chatClient);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserView error");
		}

	}

	// 카테고리 - 컴퓨터/IT
	public static void seeIT(ChatClient chatClient) {

		System.out.println("==============책 목록==============");
		System.out.println("카테고리\t 책 번호\t 책 이름");
		try {
			JSONObject updata = new JSONObject();
			updata.put("categoryId", "4");

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "1");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();

			for (int i = 0; i < chatClient.jsonArray.length(); i++) {
				JSONObject jo = chatClient.jsonArray.getJSONObject(i);
				System.out.print(jo.getString("categoryName") + "\t");
				System.out.print(jo.getString("bookId") + "\t");
				System.out.println(jo.getString("bookName"));
			}

			System.out.println("================================");
			System.out.println("--------------------------------");
			System.out.println("1.책 선택 | 2.메인 페이지로 돌아가기");
			System.out.println("--------------------------------");
			System.out.print("선택 : ");
			String select = scan.nextLine();
			switch (select) {
			case "1":
				bookDetail(chatClient, bookId);
				break;
			case "2":
				chatClient.choice= "first";
			default : 
				System.out.println();
				System.out.println("다시 입력해주세요");	
				System.out.println("--------------------------------");
				seeAssay(chatClient);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserView error");
		}

	}

	// 카테고리 - 가정/육아
	public static void seeFamily(ChatClient chatClient) {
		System.out.println("==============책 목록==============");
		System.out.println("카테고리\t 책 번호\t 책 이름");
		try {
			JSONObject updata = new JSONObject();
			updata.put("categoryId", "5");

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "1");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();

			for (int i = 0; i < chatClient.jsonArray.length(); i++) {
				JSONObject jo = chatClient.jsonArray.getJSONObject(i);
				System.out.print(jo.getString("categoryName") + "\t");
				System.out.print(jo.getString("bookId") + "\t");
				System.out.println(jo.getString("bookName"));
			}

			System.out.println("================================");
			System.out.println("--------------------------------");
			System.out.println("1.책 선택 | 2.메인 페이지로 돌아가기");
			System.out.println("--------------------------------");
			System.out.print("선택 : ");
			String select = scan.nextLine();
			switch (select) {
			case "1":
				bookDetail(chatClient, bookId);
				break;
			case "2":
				chatClient.choice= "first";
			default : 
				System.out.println();
				System.out.println("다시 입력해주세요");	
				System.out.println("--------------------------------");
				seeAssay(chatClient);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserView error");
		}


	}

	// 카테고리 - 요리
	public static void seeCook(ChatClient chatClient) {
		System.out.println("==============책 목록==============");
		System.out.println("카테고리\t 책 번호\t 책 이름");
		try {
			JSONObject updata = new JSONObject();
			updata.put("categoryId", "6");

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "1");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();

			for (int i = 0; i < chatClient.jsonArray.length(); i++) {
				JSONObject jo = chatClient.jsonArray.getJSONObject(i);
				System.out.print(jo.getString("categoryName") + "\t");
				System.out.print(jo.getString("bookId") + "\t");
				System.out.println(jo.getString("bookName"));
			}

			System.out.println("================================");
			System.out.println("--------------------------------");
			System.out.println("1.책 선택 | 2.메인 페이지로 돌아가기");
			System.out.println("--------------------------------");
			System.out.print("선택 : ");
			String select = scan.nextLine();
			switch (select) {
			case "1":
				bookDetail(chatClient, bookId);
				break;
			case "2":
				chatClient.choice= "first";
			default : 
				System.out.println();
				System.out.println("다시 입력해주세요");	
				System.out.println("--------------------------------");
				seeAssay(chatClient);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserView error");
		}


	}

	// 카테고리 - 건강
	public static void seeHealth(ChatClient chatClient) {
		System.out.println("==============책 목록==============");
		System.out.println("카테고리\t 책 번호\t 책 이름");
		try {
			JSONObject updata = new JSONObject();
			updata.put("categoryId", "7");

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "1");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();

			for (int i = 0; i < chatClient.jsonArray.length(); i++) {
				JSONObject jo = chatClient.jsonArray.getJSONObject(i);
				System.out.print(jo.getString("categoryName") + "\t");
				System.out.print(jo.getString("bookId") + "\t");
				System.out.println(jo.getString("bookName"));
			}

			System.out.println("================================");
			System.out.println("--------------------------------");
			System.out.println("1.책 선택 | 2.메인 페이지로 돌아가기");
			System.out.println("--------------------------------");
			System.out.print("선택 : ");
			String select = scan.nextLine();
			switch (select) {
			case "1":
				bookDetail(chatClient, bookId);
				break;
			case "2":
				chatClient.choice= "first";
			default : 
				System.out.println();
				System.out.println("다시 입력해주세요");	
				System.out.println("--------------------------------");
				seeAssay(chatClient);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserView error");
		}


	}

	// 카테고리 - 취미/스포츠
	public static void seeHobby(ChatClient chatClient) {
		System.out.println("==============책 목록==============");
		System.out.println("카테고리\t 책 번호\t 책 이름");
		try {
			JSONObject updata = new JSONObject();
			updata.put("categoryId", "8");

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "1");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();

			for (int i = 0; i < chatClient.jsonArray.length(); i++) {
				JSONObject jo = chatClient.jsonArray.getJSONObject(i);
				System.out.print(jo.getString("categoryName") + "\t");
				System.out.print(jo.getString("bookId") + "\t");
				System.out.println(jo.getString("bookName"));
			}

			System.out.println("================================");
			System.out.println("--------------------------------");
			System.out.println("1.책 선택 | 2.메인 페이지로 돌아가기");
			System.out.println("--------------------------------");
			System.out.print("선택 : ");
			String select = scan.nextLine();
			switch (select) {
			case "1":
				bookDetail(chatClient, bookId);
				break;
			case "2":
				chatClient.choice= "first";
			default : 
				System.out.println();
				System.out.println("다시 입력해주세요");	
				System.out.println("--------------------------------");
				seeAssay(chatClient);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserView error");
		}

	}

	// 카테고리 - 경영/경제
	public static void seeManagement(ChatClient chatClient) {

		System.out.println("==============책 목록==============");
		System.out.println("카테고리\t 책 번호\t 책 이름");
		try {
			JSONObject updata = new JSONObject();
			updata.put("categoryId", "9");

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "1");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();

			for (int i = 0; i < chatClient.jsonArray.length(); i++) {
				JSONObject jo = chatClient.jsonArray.getJSONObject(i);
				System.out.print(jo.getString("categoryName") + "\t");
				System.out.print(jo.getString("bookId") + "\t");
				System.out.println(jo.getString("bookName"));
			}

			System.out.println("================================");
			System.out.println("--------------------------------");
			System.out.println("1.책 선택 | 2.메인 페이지로 돌아가기");
			System.out.println("--------------------------------");
			System.out.print("선택 : ");
			String select = scan.nextLine();
			switch (select) {
			case "1":
				bookDetail(chatClient, bookId);
				break;
			case "2":
				chatClient.choice= "first";
			default : 
				System.out.println();
				System.out.println("다시 입력해주세요");	
				System.out.println("--------------------------------");
				seeAssay(chatClient);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserView error");
		}

	}

	// 카테고리 - 자기계발
	public static void seeSelfImprovement(ChatClient chatClient) {
		System.out.println("==============책 목록==============");
		System.out.println("카테고리\t 책 번호\t 책 이름");
		try {
			JSONObject updata = new JSONObject();
			updata.put("categoryId", "10");

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "1");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();

			for (int i = 0; i < chatClient.jsonArray.length(); i++) {
				JSONObject jo = chatClient.jsonArray.getJSONObject(i);
				System.out.print(jo.getString("categoryName") + "\t");
				System.out.print(jo.getString("bookId") + "\t");
				System.out.println(jo.getString("bookName"));
			}

			System.out.println("================================");
			System.out.println("--------------------------------");
			System.out.println("1.책 선택 | 2.메인 페이지로 돌아가기");
			System.out.println("--------------------------------");
			System.out.print("선택 : ");
			String select = scan.nextLine();
			switch (select) {
			case "1":
				bookDetail(chatClient, bookId);
				break;
			case "2":
				chatClient.choice= "first";
			default : 
				System.out.println();
				System.out.println("다시 입력해주세요");	
				System.out.println("--------------------------------");
				seeAssay(chatClient);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserView error");
		}


	}

	// 북 상세정보
	public static void bookDetail(ChatClient chatClient, JSONObject bookId) {
		System.out.print("책 번호를 입력하세요 : ");
		String bookNo = scan.nextLine();
		System.out.println("\n==============책 상세 정보==============");

		try {
			JSONObject updata = new JSONObject();
			updata.put("bookId", bookNo);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "1");
			jsonObject.put("listNo", "2");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();

			for (int i = 0; i < chatClient.jsonArray.length(); i++) {
				JSONObject jo = chatClient.jsonArray.getJSONObject(i);
				System.out.print( "책이름 : "+ jo.getString("bookName") + "\t");
				System.out.print("재고수량 :"+jo.getInt("bookStock") + "\n");
				System.out.print("원가 : "+jo.getInt("bookPrice") + "\t");
				System.out.print("-> 할인가 : "+jo.getInt("bookSellingPrice") + "\n");
				System.out.print("작가 : "+jo.getString("bookWriter") + "\t");
				System.out.print("출판사 : "+jo.getString("companyName") + "\n");
				System.out.print("줄거리 : "+jo.getString("bookContent"));
			}

			System.out.println("\n===================================");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserView error");
		}
	}
	
	//구매하기
	public static void buyBook() {
		
	}
	
	//장바구니
	public static void cartBook() {
		
	}

}
