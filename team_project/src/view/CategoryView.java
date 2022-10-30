package view;

import java.io.IOException;
import java.util.Scanner;

import org.json.JSONObject;

import day06.ChatClient;
import day06.SocketClient;
import service.CategoryService;

public class CategoryView {
	static Scanner sc = new Scanner(System.in);
	static SocketClient seder;
	//카테고리 목록 
	public static void seeCategory(ChatClient chatClient) {
		System.out.println("[카테고리]");
		System.out.println("1.소설 | 2.시/에세이 | 3. 인문 | 4. 컴퓨터/IT | 5.가정/육아\n6.요리 | 7.건강 | 8.취미/실용/스포츠 | 9.경제/경영 | 10.자기계발");
		System.out.println();
		System.out.print("listNo = ");
		String listNo=sc.nextLine();
		switch(listNo) {
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
	
	//카테고리 - 소설
	public static void seeNovel(ChatClient chatClient) {
 		CategoryService categoryService = new CategoryService();
 		JSONObject jsonUpdata = new JSONObject();
 		jsonUpdata.put("categoryId", "1");
 		
		categoryService.showCategory(jsonUpdata);
		System.out.println("===================================");
	}
	
	//카테고리 - 시/에세이
	public static void seeAssay(ChatClient chatClient) {
		CategoryService categoryService = new CategoryService();
 		JSONObject jsonUpdata = new JSONObject();
 		jsonUpdata.put("categoryId", "2");
 		
		categoryService.showCategory(jsonUpdata);
		System.out.println("===================================");
	}
	
	//카테고리 - 인문
	public static void seeHumanities(ChatClient chatClient) {
		CategoryService categoryService = new CategoryService();
 		JSONObject jsonUpdata = new JSONObject();
 		jsonUpdata.put("categoryId", "3");
 		
		categoryService.showCategory(jsonUpdata);
		System.out.println("===================================");
	}
	
	//카테고리 - 컴퓨터/IT
	public static void seeIT(ChatClient chatClient) {
		CategoryService categoryService = new CategoryService();
 		JSONObject jsonUpdata = new JSONObject();
 		jsonUpdata.put("categoryId", "4");
 		
		categoryService.showCategory(jsonUpdata);
		System.out.println("===================================");
	}
	
	//카테고리 - 가정/육아
	public static void seeFamily(ChatClient chatClient) {
		CategoryService categoryService = new CategoryService();
 		JSONObject jsonUpdata = new JSONObject();
 		jsonUpdata.put("categoryId", "5");
 		
		categoryService.showCategory(jsonUpdata);
		System.out.println("===================================");
	}
	
	//카테고리 - 요리
	public static void seeCook(ChatClient chatClient) {
		CategoryService categoryService = new CategoryService();
 		JSONObject jsonUpdata = new JSONObject();
 		jsonUpdata.put("categoryId", "6");
 		
		categoryService.showCategory(jsonUpdata);
		System.out.println("===================================");
	}
	
	
	//카테고리 - 건강
	public static void seeHealth(ChatClient chatClient) {
		CategoryService categoryService = new CategoryService();
 		JSONObject jsonUpdata = new JSONObject();
 		jsonUpdata.put("categoryId", "7");
 		
		categoryService.showCategory(jsonUpdata);
		System.out.println("===================================");
	}
	
	//카테고리 - 취미/스포츠	
	public static void seeHobby(ChatClient chatClient) {
		CategoryService categoryService = new CategoryService();
 		JSONObject jsonUpdata = new JSONObject();
 		jsonUpdata.put("categoryId", "8");
 		
		categoryService.showCategory(jsonUpdata);
		System.out.println("===================================");
	}
	
	//카테고리 - 경영/경제
	public static void seeManagement(ChatClient chatClient) {
		CategoryService categoryService = new CategoryService();
 		JSONObject jsonUpdata = new JSONObject();
 		jsonUpdata.put("categoryId", "9");
 		
		categoryService.showCategory(jsonUpdata);
		System.out.println("===================================");
	}
	
	//카테고리 - 자기계발
	public static void seeSelfImprovement(ChatClient chatClient) {
		CategoryService categoryService = new CategoryService();
 		JSONObject jsonUpdata = new JSONObject();
 		jsonUpdata.put("categoryId", "10");
 		
		categoryService.showCategory(jsonUpdata);
		System.out.println("===================================");
	}
	
}
