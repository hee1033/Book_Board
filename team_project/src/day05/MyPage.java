package day05;

import org.json.JSONObject;

public class MyPage {
	public void list(SocketClient sender, String listNo) {
		String number = listNo;
		switch(number) {
			case "1":
				Category category = new Category();
				category.toString();
				break;
			case "2":
				BestSeller bestSeller=new BestSeller();
				bestSeller.toString();
				break;
			case "3":
				Event event = new Event();
				event.toString();
				break;
			case "4":
				Search search = new Search();
				search.toString();
				break;
			case "5":
				JSONObject root=new JSONObject();
				root.put("check","complete");
				root.put("data1","==================================================");
			    root.put("data2","                   [마이 페이지]                     ");
			    root.put("data3","1.개인정보 수정 | 2.구매내역 | 3.장바구니 | 4.로그아웃 | 5.회원탈퇴");
			    root.put("data4","===================================================");
			    root.put("choice",sender.choice);
			    String json=root.toString();
				sender.send(json);
			    break;
			case "6":
				QnA qna=new QnA();
				qna.toString();
				break;
			case "7":
				Exit exit=new Exit();
				exit.toString();
				break;
		}
	}

}
