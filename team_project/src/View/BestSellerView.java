package View;

import java.util.Scanner;

import org.json.JSONObject;

import project.ChatClient;

public class BestSellerView {
	static Scanner scan = new Scanner(System.in);

	// 베스트 셀러 목록
	public static void seeBestSeller1(ChatClient chatClient) {
		System.out.println("[베스트셀러]");
		System.out.println("1.전체 베스트셀러 | 2.연령별 베스트셀러 | 3.성별 베스트셀러");
		System.out.println();
		System.out.print("listNo = ");
		String listNo = scan.nextLine();
		switch (listNo) {
		case "1":
			seeAll(chatClient);
			break;
		case "2":
			seeAge(chatClient);
			break;
		}
	}

	public static void seeAll(ChatClient chatClient) {
		System.out.println("==============책 목록==============");
		System.out.println("책 번호\t\t 책 이름");
		try {
			JSONObject updata = new JSONObject();
			updata.put("best", "1");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "2");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();

			for (int i = 0; i < chatClient.jsonArray.length(); i++) {
				JSONObject jo = chatClient.jsonArray.getJSONObject(i);
				System.out.print(jo.getInt("bookId") + "\t");
				System.out.println(jo.getString("bookName"));
			}
			System.out.println("===================================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void seeAge(ChatClient chatClient) {
		System.out.println("==========연령대 베스트셀러==========");
		System.out.println("--------------------------------");
		System.out.println("1.10~20대 | 2.20~40대  | 3.40대 이상" );
		System.out.println("--------------------------------");
		System.out.print("선택 : ");
		String select = scan.nextLine();
		switch (select) {
		case "1":
			age1020(chatClient);
			break;
		case "2":
			age2040(chatClient);
			break;
		case "3":
			age40Over(chatClient);
			break;
		}

	}
	
	public static void age1020(ChatClient chatClient) {
		System.out.println("=======10~20대 베스트셀러 TOP3=======");
		System.out.println("책 번호\t\t 책 이름");
		try {
			JSONObject updata = new JSONObject();
			updata.put("best", "2");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "2");
			jsonObject.put("listNo", "2");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();

			for (int i = 0; i < chatClient.jsonArray.length(); i++) {
				JSONObject jo = chatClient.jsonArray.getJSONObject(i);
				System.out.print(jo.getInt("bookId") + "\t");
				System.out.println(jo.getString("bookName"));
			}
			System.out.println("===================================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void age2040(ChatClient chatClient) {
		System.out.println("=======20~40대 베스트셀러 TOP3=======");
		System.out.println("책 번호\t\t 책 이름");
		try {
			JSONObject updata = new JSONObject();
			updata.put("best", "3");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "2");
			jsonObject.put("listNo", "3");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();

			for (int i = 0; i < chatClient.jsonArray.length(); i++) {
				JSONObject jo = chatClient.jsonArray.getJSONObject(i);
				System.out.print(jo.getInt("bookId") + "\t");
				System.out.println(jo.getString("bookName"));
			}
			System.out.println("===================================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void age40Over(ChatClient chatClient) {
		System.out.println("=======40대 이상 베스트셀러 TOP3=======");
		System.out.println("책 번호\t\t 책 이름");
		try {
			JSONObject updata = new JSONObject();
			updata.put("best", "4");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "2");
			jsonObject.put("listNo", "4");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();

			for (int i = 0; i < chatClient.jsonArray.length(); i++) {
				JSONObject jo = chatClient.jsonArray.getJSONObject(i);
				System.out.print(jo.getInt("bookId") + "\t");
				System.out.println(jo.getString("bookName"));
			}
			System.out.println("===================================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

