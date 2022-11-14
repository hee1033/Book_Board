package project.View;

import java.io.IOException;
import java.sql.Connection;
import java.util.Scanner;

import org.json.JSONObject;

import project.BookClient;
import project.ConnectionProvider;
import project.DTO.Pager;

public class EventView {
	static Scanner sc = new Scanner(System.in);
	static Connection conn = ConnectionProvider.getConnection();
	static int[] eventcheck = new int[100];

	public static void paging(BookClient bookClient, Pager pager) {
		boolean pflag=false;
		System.out.print("pageNo : ");
		String pageNo = sc.nextLine();
		//다음은 Controller에서 지정한 그룹의 다음 첫번째 값 더하기 
		if(pageNo.equals("다음")||pageNo.equals("next")) {
			pageNo=String.valueOf(pager.getStartPageNo()+pager.getPagesPerGroup());
		//이전은 그룹의 첫페이지에서 한그룹 값을 빼기
		}else if(pageNo.equals("이전")||pageNo.equals("back")) {
			pageNo=String.valueOf(pager.getStartPageNo()-pager.getPagesPerGroup());
		//페이지가 1
		}else if(pageNo.equals("맨앞")||pageNo.equals("first")) {
			pageNo="1";
		//마지막 페이지 숫자 가져오기
		}else if(pageNo.equals("맨끝")||pageNo.equals("end")){
			pageNo=String.valueOf(pager.getTotalPageNo());
		}
		try {
			JSONObject updata = new JSONObject();
			updata.put("pageNo", pageNo);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "3");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
			System.out.println("------------------------[ 이벤트 게시판 ]-------------------------");
			System.out.printf("|%-5s | %-25s \t| %-20s", "번호", "이벤트 기간 ", "이벤트 제목");
			System.out.println();
			System.out.println("--------------------------------------------------------------");
			for (int i = 0; i < bookClient.jsonArray.length(); i++) {
				JSONObject jo = bookClient.jsonArray.getJSONObject(i);
				System.out.print(jo.getInt("eventId") + "\t" + "|");
				System.out.print(jo.getString("eventStartDate") + " ~ " + jo.getString("eventEndDate") + "\t" + "|");
				System.out.println(jo.getString("eventName"));
				pager.setRowsPerPage(jo.getInt("rowsPerPage"));
				pager.setPagesPerGroup(jo.getInt("pagesPerGroup"));
				pager.setTotalRows(jo.getInt("totalRows"));
				pager.setPageNo(jo.getInt("pageNo"));
			}
			System.out.println("--------------------------------------------------------------");
			pager = new Pager(pager.getRowsPerPage(), pager.getPagesPerGroup(), pager.getTotalRows(),
					pager.getPageNo());

			if (pager.getGroupNo() != 1) {
				System.out.print("<first> ");
				System.out.print("<back> ");
			}
			int[] end = new int[pager.getPagesPerGroup()];
			for (int i = 0; i < pager.getPagesPerGroup(); i++) {
				System.out.print(pager.getStartPageNo() + i + " ");
				end[i] = pager.getStartPageNo() + i;
				if ((pager.getStartPageNo() + i) == pager.getTotalPageNo()) {
					break;
				}
			}
			for (int i = 0; i < pager.getPagesPerGroup(); i++) {
				if (pager.getTotalPageNo() == end[i]) {
					pflag=true;
					break;
				} else {
					pflag=false;
				}
			}
			if(pflag==false) {
				System.out.print("<next> ");
				System.out.print("<end>");
			}
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("CategoryView error");
		}
	}

	public static void seeBoard(BookClient bookClient) {
		Pager pager = new Pager();
		boolean pflag=false;
		String pageNo = "1";
		try {
			JSONObject updata = new JSONObject();
			updata.put("pageNo", pageNo);
			JSONObject jsonObject = new JSONObject();
			jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "3");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
			
			System.out.println("------------------------[ 이벤트 게시판 ]------------------------");
			System.out.printf("|%-5s | %-25s \t| %-20s", "번호", "이벤트 기간 ", "이벤트 제목");
			System.out.println();
			System.out.println("--------------------------------------------------------------");
			for (int i = 0; i < bookClient.jsonArray.length(); i++) {
				JSONObject jo = bookClient.jsonArray.getJSONObject(i);
				System.out.print(jo.getInt("eventId") + "\t|");
				System.out.print(jo.getString("eventStartDate") + " ~ " + jo.getString("eventEndDate") + "\t" + "|");
				System.out.println(jo.getString("eventName"));
				pager.setRowsPerPage(jo.getInt("rowsPerPage"));
				pager.setPagesPerGroup(jo.getInt("pagesPerGroup"));
				pager.setTotalRows(jo.getInt("totalRows"));
				pager.setPageNo(jo.getInt("pageNo"));
			}
			System.out.println("--------------------------------------------------------------");
			pager = new Pager(pager.getRowsPerPage(), pager.getPagesPerGroup(), pager.getTotalRows(),
					pager.getPageNo());
			int[] end = new int[pager.getPagesPerGroup()];
			for (int i = 0; i < pager.getPagesPerGroup(); i++) {
				System.out.print(pager.getStartPageNo() + i + " |");
				end[i] = pager.getStartPageNo() + i;
				if ((pager.getStartPageNo() + i) == pager.getTotalPageNo()) {
					break;
				}
			}
			for (int i = 0; i < pager.getPagesPerGroup(); i++) {
				if (pager.getTotalPageNo() == end[i]) {
					pflag=true;
					break;
				} else {
					pflag=false;
				}
			}
			if(pflag==false) {
				System.out.print("<next> ");
				System.out.print("<emd>");
			}
			while (true) {
				System.out.println();
				System.out.println("--------------------");
				System.out.println("1.목록그만보기 | 2.계속보기");
				System.out.print("choice : ");
				String q = sc.nextLine();
				if (q.equals("1")) {
					break;
				}

				paging(bookClient, pager);
			}

			System.out.println("--------------------------------------------------------------");
			System.out.println("1.이벤트 선택 | 2.메인 페이지로 돌아가기");
			System.out.println("--------------------------------------------------------------");
			System.out.print("선택: ");
			String menuNo1 = sc.nextLine();
			switch (menuNo1) {
			case "1":
				eventDetail(bookClient);
				break;
			case "2":
				bookClient.choice = "first";
				break;
			default: {
				System.out.println("다시 선택하세요.");
				seeBoard(bookClient);
				break;
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EventView1 error");
		}

	}

	public static void eventDetail(BookClient bookClient) {
		System.out.print("이벤트 선택: ");
		String eventNo = sc.nextLine();
		if(eventNo.equals("")) {
			System.out.println("다시 선택하세요.");
			eventDetail(bookClient);
		}
		try {
			JSONObject updata = new JSONObject();
			updata.put("eventId", eventNo);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "3");
			jsonObject.put("listNo", "2");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
			if (bookClient.jsonArray.length() == 0) {
				System.out.println("품절되었거나 종료된 이벤트 입니다.");
				eventDetail(bookClient);
			}
			for (int i = 0; i < bookClient.jsonArray.length(); i++) {
				JSONObject jo = bookClient.jsonArray.getJSONObject(i);
				System.out.println("----------------------------[ " + jo.getString("eventName")
						+ " ]----------------------------");
				System.out.printf("%-8s : %-6s \n", "굿 즈 ", jo.getString("goodsName"));
				System.out.printf("%-8s : %-6s \n", "책 이름 ", jo.getString("bookName"));
				System.out
						.println("이벤트 기간  : " + jo.getString("eventStartDate") + " ~ " + jo.getString("eventEndDate"));
				System.out.println("이벤트 내용  : " + jo.getString("eventContent"));
			}
			System.out.println("----------------------------------------------------------------------");
			System.out.println("1. 이벤트 참여 | 2.굿즈 구매 | 3.이벤트 페이지로 돌아가기 | 4.메인 페이지로 돌아가기");
			System.out.println("----------------------------------------------------------------------");
			System.out.print("선택: ");
			String goodsBuy = sc.nextLine();
			switch (goodsBuy) {
			case "1":
				if (bookClient.flag == false) {
					System.out.println("로그인이 필요합니다.");
					UserView.seeBoard(bookClient);
				} else if (eventcheck[Integer.parseInt(eventNo)] == 0) {
					System.out.println("참여되었습니다.");
					eventcheck[Integer.parseInt(eventNo)]++;
					bookClient.choice = "first";
				} else if (eventcheck[Integer.parseInt(eventNo)] != 0) {
					System.out.println("이미 참여되었습니다.");
					bookClient.choice = "first";
				}
				break;
			case "2":
				// 장바구니로
				GoodsDetail(bookClient, eventNo);
				break;
			case "3":
				seeBoard(bookClient);
				break;
			case "4":
				bookClient.choice = "first";
				break;
			default:
				System.out.println("이벤트를 다시 선택하세요.");
				eventDetail(bookClient);
				break;
			
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EventView2 error");
		}
	}

	public static void GoodsDetail(BookClient bookClient, String eventNo) {
		try {
			JSONObject updata = new JSONObject();
			updata.put("eventId", eventNo);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "3");
			jsonObject.put("listNo", "3");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
			for (int i = 0; i < bookClient.jsonArray.length(); i++) {
				JSONObject jo = bookClient.jsonArray.getJSONObject(i);
				System.out.println("----------------------------[ " + jo.getString("goodsName")
						+ " ]----------------------------");
				System.out.printf("%-8s : %-6d \n", "가격 ", jo.getInt("goodsPrice"));
				System.out.println("이벤트 기간 : " + jo.getString("eventStartDate") + " ~ " + jo.getString("eventEndDate"));
				System.out.printf("%-7s : %-1000s \n", "굿즈 설명 ", jo.getString("goodsContent"));

			}
			System.out.println("----------------------------------------------------------------------");
			System.out.println("1. 구매하기 | 2. 메인 페이지로 돌아가기");
			System.out.println("----------------------------------------------------------------------");
			System.out.print("선택: ");
			String menuNo2 = sc.nextLine();
			switch (menuNo2) {
			case "1":
				if (bookClient.flag == false) {
					System.out.println("로그인이 필요합니다.");
					UserView.seeBoard(bookClient);
				} else {
					buyGoods(bookClient, eventNo);

				}
				break;
			case "2":
				bookClient.choice = "first";
				break;
			default:
				System.out.println("다시 입력하세요.");
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("buygoods error");
		}
	}

	public static void buyGoods(BookClient bookClient, String eventNo) {
		try {
			JSONObject updata = new JSONObject();
			updata.put("goodsId", eventNo);
			updata.put("home", bookClient);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "3");
			jsonObject.put("listNo", "4");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
			System.out.println("굿즈를 구매했습니다.");
			bookClient.choice = "first";
		} catch (IOException e) {
			System.out.println("eventview buygoods error");
			e.printStackTrace();
		}
	}
}
