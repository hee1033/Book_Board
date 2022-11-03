package View;

import java.io.IOException;
import java.util.Scanner;

import org.json.JSONObject;

import project.ChatClient;

public class QnAView {
	static Scanner scanner = new Scanner(System.in);
	
	static void seeBoard(ChatClient chatClient) {
		System.out.println();
		System.out.println("---------------------------------------------------------------");
		System.out.println("1. 도서 문의 | 2. 이벤트 문의 | 3. 기타 문의 | 4. 질문 올리기 | 5. 돌아가기");
		System.out.println("---------------------------------------------------------------");
		System.out.print("listNo = ");
		String listNo = scanner.nextLine();
		switch (listNo) {
		case "1":
			seeBookQuestions(chatClient);
			break;
		case "2":
			seeEvenQuestions(chatClient);
			break;
		case "3":
			seeOthersQuestions(chatClient);
			break;
		case "4":
			writeQuestions(chatClient);
			break;
		case "5":
			break;
		}
	}

	static void seeBookQuestions(ChatClient chatClient) {
		
		System.out.println("----------------------------------------------");
		System.out.println("도서 문의 조회");
		System.out.println("----------------------------------------------");
		
		try {
			JSONObject jsonObject = new JSONObject();
			JSONObject updata = new JSONObject();
			updata.put("updata", "0");
			
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "6");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata",updata);
			
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();
			
			for(int i=0; i<chatClient.jsonArray.length();i++) {
				JSONObject jo =  chatClient.jsonArray.getJSONObject(i);
				int qnaid = jo.getInt("qnaId");
				String qnaTitle = jo.getString("qnaTitle");
				
				System.out.println("No. "+ qnaid + ": " + qnaTitle);
			}
			
			seeDetail(chatClient);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	
	static void seeEvenQuestions(ChatClient chatClient) {
		System.out.println("----------------------------------------------");
		System.out.println("이벤트 문의 조회");
		System.out.println("----------------------------------------------");
		
		try {
			JSONObject jsonObject = new JSONObject();
			JSONObject updata = new JSONObject();
			
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "6");
			jsonObject.put("listNo", "2");
			jsonObject.put("updata",updata);
			
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();
			
			for(int i=0; i<chatClient.jsonArray.length();i++) {
				JSONObject jo =  chatClient.jsonArray.getJSONObject(i);
				int qnaid = jo.getInt("qnaId");
				String qnaTitle = jo.getString("qnaTitle");
				
				System.out.println("No. "+ qnaid + ": " + qnaTitle);
			}
			
			seeDetail(chatClient);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void seeOthersQuestions(ChatClient chatClient) {
		
	}
	
	
	static void seeDetail(ChatClient chatClient) {
		System.out.println("----------------------------------------------");
		System.out.print("클릭해서 자세히 보기(뒤로가기:q): ");
		String detailNum = scanner.nextLine();
		
		try {
			if(detailNum.toLowerCase().equals("q")) {
				seeBoard(chatClient);
			}
			JSONObject jsonObject = new JSONObject();
			JSONObject updata = new JSONObject();
			updata.put("updata", detailNum);
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "6");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata",updata);
			
			String json = jsonObject.toString();
			chatClient.send(json);
			chatClient.receive();
			
			for(int i=0; i<chatClient.jsonArray.length();i++) {
				JSONObject jo =  chatClient.jsonArray.getJSONObject(i);
				int qna_id = jo.getInt("QNA_ID");
				String maneger_answer = jo.getString("MANAGER_ANSWER");
				System.out.println("No. "+ qna_id + ": " + maneger_answer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	
	
	
	
	
	static void writeQuestions(ChatClient chatClient) {
		
	}
}

