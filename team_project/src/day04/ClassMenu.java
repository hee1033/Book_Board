package day04;
import java.io.IOException;
import java.util.Scanner;

import org.json.JSONObject;

public class ClassMenu {
	//필드
	private String no;
	
	
	// 메뉴
		public static void menu(String no) throws IOException {
			System.out.println();
			System.out.println("---------------------------------------------------------------------------");
			System.out.println("메인 메뉴 : 1.카테고리 | 2.베스트셀러 | 3.이벤트 | 4.검색 | 5.마이페이지 | 6.Q&A | 7.종료");
			System.out.println("---------------------------------------------------------------------------");
			System.out.print("메뉴 선택: ");
			String menuNo = no;
			System.out.println();
			
			switch (menuNo) {
			case "1" :
				ClassCategoery.category();
				break;
			case "2" :
				
				break;
			}
			
		
			
		}
}
