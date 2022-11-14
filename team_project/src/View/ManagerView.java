package project.View;

import java.io.IOException;
import java.util.Scanner;

import org.json.JSONObject;

import project.BookClient;

public class ManagerView {
	static Scanner sc = new Scanner(System.in);

	// manager login
	public static void writeManagerLogin(BookClient bookClient) {
		System.out.println("\n[관리자 로그인]");
		System.out.print("ID : ");
		String id = sc.nextLine();
		System.out.print("PASSWORD : ");
		String password = sc.nextLine();
		if (id.equals("") || password.equals("")) {
			System.out.println("아이디 또는 비밀번호를 입력하지 않으셨습니다.");
			writeManagerLogin(bookClient);
		}
		try {
			// Scanner로 값을 받지 않으면 오류가 생긴다. (오류수정 필요)
			JSONObject jsonUpdata = new JSONObject();
			jsonUpdata.put("id", id);
			jsonUpdata.put("password", password);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "9");
			jsonObject.put("listNo", "8");
			jsonObject.put("updata", jsonUpdata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
			// 로그인 실패시
			if (bookClient.mflag == false) {
				writeManagerLogin(bookClient);
			}
			// 로그인 성공시
			else {
				bookClient.flag = true;
				seeBoard(bookClient);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ManagerView error");
		}
	}

	// 로그인 후 seeBoard
	public static void seeBoard(BookClient bookClient) {
		System.out.println();
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("1.Book | 2.User | 3.Event | 4.Goods | 5.qna답글달기 | 6.관리자 로그아웃 | 7.메인화면");
		System.out.println("-------------------------------------------------------------------------");
		System.out.print("listNo = ");
		String listNo = sc.nextLine();
		switch (listNo) {
		case "1":
			System.out.println("-----------------------------------------------");
			System.out.println("1.BookInsert | 2.BookUpdate | 3.BookDelete ");
			System.out.print("bookNum : ");
			String bookNum = sc.nextLine();
			switch (bookNum) {
			case "1":
				writeBookInsert(bookClient);
			case "2":
				writeBookUpdate(bookClient);
			case "3":
				writeDelete(bookClient, listNo);
			default:
				System.out.println("없는 번호를 입력하셨습니다.");
				seeBoard(bookClient);
				break;
			}
			break;
		case "2":
			seeUserData(bookClient);
			break;
		case "3":
			System.out.println("------------------------------");
			System.out.println("1.EventInsert | 2.EventDelete");
			System.out.print("EventNum : ");
			String eventNum = sc.nextLine();
			switch (eventNum) {
			case "1":
				writeEvent(bookClient);
				break;
			case "2":
				writeDelete(bookClient, listNo);
				break;
			default:
				System.out.println("없는 번호를 입력하셨습니다.");
				seeBoard(bookClient);
				break;
			}
			break;
		case "4":
			System.out.println("------------------------------");
			System.out.println("1.GoodsInsert | 2.GoodsDelete");
			System.out.print("GoodsNum : ");
			String goodsNum = sc.nextLine();
			switch (goodsNum) {
			case "1":
				writeGoods(bookClient);
				break;
			case "2":
				writeDelete(bookClient, listNo);
				break;
			default:
				System.out.println("없는 번호를 입력하셨습니다.");
				seeBoard(bookClient);
				break;
			}
			break;
		case "5":
			seeQnaType(bookClient);
			break;
		case "6":
			writeManagerLogout(bookClient);
			break;
		case "7":
			bookClient.choice = "first";
			System.out.println();
			break;
		default:
			System.out.println("잘못 입력하셨습니다.");
			seeBoard(bookClient);
			break;
		}
	}

	// 책 업데이트
	public static void writeBookInsert(BookClient bookClient) {
		System.out.println("----------[Book 추가]----------");
		System.out.print("BOOK ID(숫자) : ");
		int bookId = Integer.parseInt(sc.nextLine());
		System.out.print("BOOK NAME = ");
		String bookName = sc.nextLine();
		System.out.print("BOOK Price(숫자) = ");
		int bookPrice = Integer.parseInt(sc.nextLine());
		System.out.print("BOOK Writer = ");
		String bookWriter = sc.nextLine();
		System.out.print("BOOK Stock(숫자) = ");
		int bookStock = Integer.parseInt(sc.nextLine());
		System.out.print("BOOK Content = ");
		String bookContent = sc.nextLine();
		System.out.print("BOOK Company(숫자) = ");
		int bookCompany = Integer.parseInt(sc.nextLine());
		System.out.print("BOOK Category ID(숫자) = ");
		int categoryId = Integer.parseInt(sc.nextLine());
		System.out.print("BOOK Sellint Price(숫자) = ");
		int bookSellingPrice = Integer.parseInt(sc.nextLine());

		try {
			// Scanner로 값을 받지 않으면 오류가 생긴다.(오류수정 필요)
			JSONObject jsonUpdata = new JSONObject();
			jsonUpdata.put("bookId", bookId);
			jsonUpdata.put("bookName", bookName);
			jsonUpdata.put("bookPrice", bookPrice);
			jsonUpdata.put("bookWriter", bookWriter);
			jsonUpdata.put("bookStock", bookStock);
			jsonUpdata.put("bookContent", bookContent);
			jsonUpdata.put("bookCompany", bookCompany);
			jsonUpdata.put("categoryId", categoryId);
			jsonUpdata.put("bookSellingPrice", bookSellingPrice);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "9");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata", jsonUpdata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
			if (bookClient.mflag == false) {
				writeBookInsert(bookClient);
			} else {
				seeBoard(bookClient);
			}
		} catch (Exception e) {
			e.printStackTrace();
			writeBookInsert(bookClient);

		}

	}
	
	//책 수량 정정
	public static void writeBookUpdate(BookClient bookClient) {
		System.out.print("[업데이트할 Book ID] : ");
		int bookId = Integer.parseInt(sc.nextLine());
		System.out.print("수량 : ");
		int stock = Integer.parseInt(sc.nextLine());
		try {
			JSONObject jsonUpdata = new JSONObject();
			jsonUpdata.put("bookId", bookId);
			jsonUpdata.put("stock", stock);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "9");
			jsonObject.put("listNo", "9");
			jsonObject.put("updata", jsonUpdata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
			if (bookClient.mflag == false) {
				writeBookUpdate(bookClient);
			} else {
				seeBoard(bookClient);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ManagerView update error");
		}
	}

	// 책, 이벤트, 굿즈 삭제
	public static void writeDelete(BookClient bookClient, String listNo) {
		if (listNo.equals("1")) {
			System.out.print("[삭제하고 싶은 책 ID를 쓰세요] : ");
		} else if (listNo.equals("3")) {
			System.out.print("[삭제하고 싶은 이벤트 ID를 쓰세요] : ");
		} else {
			System.out.print("[삭제하고 싶은 굿즈 ID를 쓰세요] : ");
		}
		int id = Integer.parseInt(sc.nextLine());
		try {
			JSONObject jsonUpdata = new JSONObject();
			jsonUpdata.put("id", id);
			jsonUpdata.put("idNum", listNo);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "9");
			jsonObject.put("listNo", "2");
			jsonObject.put("updata", jsonUpdata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
			if (bookClient.mflag == false) {
				writeDelete(bookClient, listNo);
			} else {
				seeBoard(bookClient);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ManagerView delete error");
		}
	}

	// 모든 유저 정보 보기
	public static void seeUserData(BookClient bookClient) {
		System.out.println("----------------[유저정보]----------------");
		try {
			JSONObject jsonUpdata = new JSONObject();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "9");
			jsonObject.put("listNo", "3");
			jsonObject.put("updata", jsonUpdata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();

			String[] cnt = new String[bookClient.jsonArray.length()];
			for (int i = 0; i < bookClient.jsonArray.length(); i++) {
				JSONObject jo = bookClient.jsonArray.getJSONObject(i);
				cnt[i] = jo.getString("userId");
				System.out.println("ID: " + jo.getString("userId") + " | NAME : " + jo.getString("userName"));
			}
			System.out.println("-----------------------------------------");
			System.out.print("1.유저정보 자세히 보기 | 2.되돌아가기 :");
			String listNo = sc.nextLine();
			switch (listNo) {
			case "1":
				seeDetailUserData(bookClient, cnt);
				break;
			case "2":
				seeBoard(bookClient);
			default:
				System.out.println("없는 번호를 선택하셨습니다. 다시 선택해 주세요");
				seeUserData(bookClient);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 원하는 id의 유저 정보 보기
	public static void seeDetailUserData(BookClient bookClient, String[] cnt) {
		System.out.println("----------------[Detail 유저정보]----------------");
		System.out.print("검색할 유저 id : ");
		String userId = sc.nextLine();
		/*
		for (int i = 0; i < cnt.length; i++) {
			if (cnt[i].equals(userId)) {
				break;
			} else if (!cnt[i].equals(userId)) {
				System.out.println("없는 userId입니다. 다시 선택해주세요");
				seeDetailUserData(bookClient, cnt);
				break;
			} else
				continue;
		}*/
		try {
			JSONObject jsonUpdata = new JSONObject();
			jsonUpdata.put("userId", userId);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "9");
			jsonObject.put("listNo", "4");
			jsonObject.put("updata", jsonUpdata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
			for (int i = 0; i < bookClient.jsonArray.length(); i++) {
				JSONObject jo = bookClient.jsonArray.getJSONObject(i);
				System.out.println("ID : " + jo.getString("userId"));
				System.out.println("Password : " + jo.getString("userPassword"));
				System.out.println("Name : " + jo.getString("userName"));
				System.out.println("Email : " + jo.getString("userEmail"));
				System.out.println("Address : " + jo.getString("userAddress"));
				System.out.println("TEL : " + jo.getString("userTel"));
				System.out.println("InDate : " + jo.getString("userIndate"));
				System.out.println("Age : " + jo.getInt("userAge"));
				System.out.println("Sex : " + jo.getString("userSex"));
			}
			seeBoard(bookClient);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 관리자 로그아웃
	public static void writeManagerLogout(BookClient bookClient) {
		System.out.println("\n[관리자 로그아웃하시겠습니까?(y/n)]");
		String result = sc.nextLine();
		if (!result.equals("y") && !result.equals("n")) {
			System.out.println("다시 선택해주세요.");
			writeManagerLogout(bookClient);
		}
		try {
			if (result.equals("y")) {
				bookClient.flag = false;
				JSONObject jsonUpdata = new JSONObject();
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("controller", "menuNo");
				jsonObject.put("menuNo", "9");
				jsonObject.put("listNo", "5");
				jsonObject.put("updata", jsonUpdata);
				String json = jsonObject.toString();
				bookClient.send(json);
				bookClient.receive();
			} else {
				seeBoard(bookClient);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ManagerView logout error");
		}

	}

	// 이벤트 업데이트
	public static void writeEvent(BookClient bookClient) {
		System.out.println("----------------[Event 추가]----------------");
		System.out.print("Event ID(두자리 숫자) : ");
		int eventId = Integer.parseInt(sc.nextLine());
		System.out.print("Event NAME = ");
		String eventName = sc.nextLine();
		System.out.print("Event Start Date(날짜형태) = ");
		String eventStart = sc.nextLine();
		System.out.print("Event End Date(날짜형태) = ");
		String eventEnd = sc.nextLine();
		System.out.print("Book ID(숫자) = ");
		int bookId = Integer.parseInt(sc.nextLine());
		System.out.print("Event Content = ");
		String eventConetent = sc.nextLine();

		try {
			JSONObject jsonUpdata = new JSONObject();
			jsonUpdata.put("eventId", eventId);
			jsonUpdata.put("eventName", eventName);
			jsonUpdata.put("eventStart", eventStart);
			jsonUpdata.put("eventEnd", eventEnd);
			jsonUpdata.put("bookId", bookId);
			jsonUpdata.put("eventContent", eventConetent);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "9");
			jsonObject.put("listNo", "6");
			jsonObject.put("updata", jsonUpdata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
			if (bookClient.mflag == false) {
				writeEvent(bookClient);
			} else {
				seeBoard(bookClient);
			}
		} catch (Exception e) {
			e.printStackTrace();
			writeEvent(bookClient);

		}
	}

	// 굿즈 업데이트
	public static void writeGoods(BookClient bookClient) {
		System.out.println("----------------[Goods 추가]----------------");
		System.out.print("Goods ID(숫자) : ");
		int goodsId = Integer.parseInt(sc.nextLine());
		System.out.print("Goods NAME = ");
		String goodsName = sc.nextLine();
		System.out.print("Goods Num(숫자) = ");
		int goodsNum = Integer.parseInt(sc.nextLine());
		System.out.print("Event ID(숫자) = ");
		int eventId = Integer.parseInt(sc.nextLine());
		System.out.print("Goods Content = ");
		String goodsConetent = sc.nextLine();
		System.out.print("Goods price(숫자) = ");
		int goodsPrice = Integer.parseInt(sc.nextLine());

		try {
			JSONObject jsonUpdata = new JSONObject();
			jsonUpdata.put("goodsId", goodsId);
			jsonUpdata.put("goodsName", goodsName);
			jsonUpdata.put("goodsNum", goodsNum);
			jsonUpdata.put("eventId", eventId);
			jsonUpdata.put("goodsContent", goodsConetent);
			jsonUpdata.put("goodsPrice", goodsPrice);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "9");
			jsonObject.put("listNo", "7");
			jsonObject.put("updata", jsonUpdata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
			if (bookClient.mflag == false) {
				writeGoods(bookClient);
			} else {
				seeBoard(bookClient);
			}
		} catch (Exception e) {
			e.printStackTrace();
			writeGoods(bookClient);

		}
	}
	
	public static void seeQnaType(BookClient bookClient) {
	      System.out.println("\n-------------------- [미답변 QnA 타입 선택] --------------------");
	      System.out.println("1. 도서 문의 | 2. 이벤트 문의 | 3. 기타 문의 | 4. 뒤로가기");
	      System.out.println("------------------------------------------------------------");
	      String button = sc.nextLine();
	      switch (button) {
	      case "1":
	         seeNotAnswer_B(bookClient);
	         break;
	      case "2":
	         seeNotAnswer_E(bookClient);
	         break;
	      case "3":
	         seeNotAnswer_O(bookClient);
	         break;
	      case "4":
	         seeBoard(bookClient);
	         break;
	      }
	   }


	// 관리자 qna답글 달기
	public static void seeNotAnswer_B(BookClient bookClient) {
		try {
			System.out.println("\n--------------[미답변 책 문의 조회]--------------");
			String qna_type = "1";
			JSONObject jsonUpdata = new JSONObject();
			jsonUpdata.put("qna_type", qna_type);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "9");
			jsonObject.put("listNo", "showNotAnswer");
			jsonObject.put("updata", jsonUpdata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();

			// qna_id 배열에 저장
			int qnaIdStore[] = new int[bookClient.jsonArray.length()];

			for (int i = 0; i < bookClient.jsonArray.length(); i++) {
				JSONObject jo = bookClient.jsonArray.getJSONObject(i);
				int qnaid = jo.getInt("qnaId");
				String qnaTitle = jo.getString("qnaTitle");
				qnaIdStore[i] = qnaid;
				System.out.println("No. " + qnaid + ": " + qnaTitle);
			}
			// 게시글 번호 받기
			System.out.println();
			System.out.print("답변할 번호 입력(뒤로가기:q): ");
			String detailNum = sc.nextLine();

			if (detailNum.toLowerCase().equals("q")) {
				seeQnaType(bookClient);
			}

			// 입력한 번호가 목록에 있는 번호에 해당하는지 확인
			boolean right = false;
			for (int i = 0; i < qnaIdStore.length; i++) {
				if (Integer.parseInt(detailNum) == qnaIdStore[i]) {
					right = true;
					break;
				} else {
					continue;
				}
			}
			if (right == false) {
				System.out.println("없는 번호입니다.");
			}
			seeDetailNotAnswer(bookClient, qnaIdStore, detailNum);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void seeNotAnswer_E(BookClient bookClient) {
		try {
			System.out.println("\n-------------- [미답변 이벤트 문의 조회] --------------");

			String qna_type = "2";

			JSONObject jsonUpdata = new JSONObject();
			jsonUpdata.put("qna_type", qna_type);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "9");
			jsonObject.put("listNo", "showNotAnswer");
			jsonObject.put("updata", jsonUpdata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();

			// qna_id 배열에 저장
			int qnaIdStore[] = new int[bookClient.jsonArray.length()];

			for (int i = 0; i < bookClient.jsonArray.length(); i++) {
				JSONObject jo = bookClient.jsonArray.getJSONObject(i);
				int qnaid = jo.getInt("qnaId");
				String qnaTitle = jo.getString("qnaTitle");
				qnaIdStore[i] = qnaid;
				System.out.println("No. " + qnaid + ": " + qnaTitle);
			}
			// 게시글 번호 받기
			System.out.println();
			System.out.print("답변할 번호 입력(뒤로가기:q): ");
			String detailNum = sc.nextLine();

			if (detailNum.toLowerCase().equals("q")) {
				seeQnaType(bookClient);
			}
			// 입력한 번호가 목록에 있는 번호에 해당하는지 확인
			boolean right = false;
			for (int i = 0; i < qnaIdStore.length; i++) {
				if (Integer.parseInt(detailNum) == qnaIdStore[i]) {
					right = true;
					break;
				} else {
					continue;
				}
			}
			if (right == false) {
				System.out.println("없는 번호다.");
			}
			seeDetailNotAnswer(bookClient, qnaIdStore, detailNum);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void seeNotAnswer_O(BookClient bookClient) {
		try {
			System.out.println("\n-------------- [미답변 기타 문의 조회] --------------");
			String qna_type = "3";

			JSONObject jsonUpdata = new JSONObject();
			jsonUpdata.put("qna_type", qna_type);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "9");
			jsonObject.put("listNo", "showNotAnswer");
			jsonObject.put("updata", jsonUpdata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();

			// qna_id 배열에 저장
			int qnaIdStore[] = new int[bookClient.jsonArray.length()];

			for (int i = 0; i < bookClient.jsonArray.length(); i++) {
				JSONObject jo = bookClient.jsonArray.getJSONObject(i);
				int qnaid = jo.getInt("qnaId");
				String qnaTitle = jo.getString("qnaTitle");
				qnaIdStore[i] = qnaid;
				System.out.println("No. " + qnaid + ": " + qnaTitle);
			}
			// 게시글 번호 받기
			System.out.println();
			System.out.print("답변할 번호 입력(뒤로가기:q): ");
			String detailNum = sc.nextLine();

			if (detailNum.toLowerCase().equals("q")) {
				seeQnaType(bookClient);
			}
			// 입력한 번호가 목록에 있는 번호에 해당하는지 확인
			boolean right = false;
			for (int i = 0; i < qnaIdStore.length; i++) {
				if (Integer.parseInt(detailNum) == qnaIdStore[i]) {
					right = true;
					break;
				} else {
					continue;
				}
			}
			if (right == false) {
				System.out.println("없는 번호다.");
			}
			seeDetailNotAnswer(bookClient, qnaIdStore, detailNum);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void seeDetailNotAnswer(BookClient bookClient, int qnaIdStore[], String detailNum) {

		// 목록에 있는 번호 검증완료. 상세 게시글 보이기
		try {
			JSONObject jsonObject = new JSONObject();
			JSONObject updata = new JSONObject();
			updata.put("updata", detailNum);
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "9");
			jsonObject.put("listNo", "showNotAnswerDetail"); // listNo의 1~3까지 같은 메소드 showQuestions()
			jsonObject.put("updata", updata);

			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();

			for (int i = 0; i < bookClient.jsonArray.length(); i++) {
				JSONObject jo = bookClient.jsonArray.getJSONObject(i);
				int qnaId = jo.getInt("qnaId");
				String qnaTitle = jo.getString("qnaTitle");
				String qnaContent = jo.getString("qnaContent");
				System.out.println(qnaId + ": " + qnaTitle);
				System.out.println("내용: " + qnaContent);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		replyNotAnswer(bookClient, detailNum);
	}

	public static void replyNotAnswer(BookClient bookClient, String detailNum) {
		try {
			// 버튼 누르면 해당 상세 내용 조회 하기
			System.out.println("\n-------------- [답변 작성하기] --------------");
			System.out.print("[ 내용 ]: ");
			String answerContent = sc.nextLine();

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "9");
			jsonObject.put("listNo", "replyNotAnswer");

			JSONObject updata = new JSONObject();
			updata.put("MANAGER_ANSWER", answerContent);
			updata.put("updata", detailNum);
			jsonObject.put("updata", updata);

			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();

			seeQnaType(bookClient);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
