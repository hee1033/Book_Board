package project.View;

import java.io.IOException;
import java.sql.Connection;
import java.util.Scanner;

import org.json.JSONObject;

import project.BookClient;
import project.ConnectionProvider;
import project.DTO.Pager;

public class SearchView {
	static Scanner sc = new Scanner(System.in);
	static Connection conn = ConnectionProvider.getConnection();
	static String bookId;
	
	public static void paging(BookClient bookClient, String search, Pager pager) {
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
			updata.put("bookName", search);
			updata.put("pageNo", pageNo);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "4");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
			System.out.println("-----------------------------------------------------------------------");
			System.out.println("카테고리\t|" + "책 번호\t|" + "책 이름\t");
			System.out.println("-----------------------------------------------------------------------");
			for (int i = 0; i < bookClient.jsonArray.length(); i++) {
				JSONObject jo = bookClient.jsonArray.getJSONObject(i);
				System.out.print(jo.getString("categoryName") + "\t|");
				System.out.print(jo.getInt("bookId") + "\t|");
				System.out.println(jo.getString("bookName"));
				pager.setRowsPerPage(jo.getInt("rowsPerPage"));
				pager.setPagesPerGroup(jo.getInt("pagesPerGroup"));
				pager.setTotalRows(jo.getInt("totalRows"));
				pager.setPageNo(jo.getInt("pageNo"));
			}
			System.out.println("--------------------------------------------------------------");
			pager = new Pager(pager.getRowsPerPage(), pager.getPagesPerGroup(), pager.getTotalRows(),
					pager.getPageNo());
			if(pager.getGroupNo()!=1) {
				System.out.print("<first> ");
				System.out.print("<back> ");
			}
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
				System.out.print("<end>");
			}
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("CategoryView error");
		}
	}

	public static void seeSearch(BookClient bookClient) {
		boolean pflag=false;
		Pager pager = new Pager();
		String pageNo = "1";
		System.out.println("-------------");
		System.out.print("통합 검색: ");
		String search = sc.nextLine();
		if (search.equals("")) {
			System.out.println("-------------");
			System.out.println("다시 선택하세요.");
			System.out.println("-------------");
			seeSearch(bookClient);
		}
		System.out.println("----------------------------------------------------------------------");
		System.out.println("카테고리\t|" + "책 번호\t|" + "책 이름\t");
		System.out.println("----------------------------------------------------------------------");
		try {
			JSONObject updata = new JSONObject();
			updata.put("bookName", search);
			updata.put("pageNo", pageNo);
			JSONObject jsonObject = new JSONObject();
			jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "4");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
			if (bookClient.jsonArray.length() == 0) {
				System.out.println("검색 결과가 없습니다.");
				bookClient.choice = "first";
			} else {
				for (int i = 0; i < bookClient.jsonArray.length(); i++) {
					JSONObject jo = bookClient.jsonArray.getJSONObject(i);
					System.out.print(jo.getString("categoryName") + "\t|");
					System.out.print(jo.getInt("bookId") + "\t|");
					System.out.println(jo.getString("bookName"));
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
					System.out.print("<end>");
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
					paging(bookClient, search, pager);
				}

				searchCheck(bookClient);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SearchView error");
		}

	}

	public static void searchCheck(BookClient bookClient) {
		System.out.println("---------------------------------------------");
		System.out.println("1.책 선택 | 2.다른 책 검색 | 3.메인 페이지로 돌아가기");
		System.out.println("---------------------------------------------");
		System.out.print("선택: ");
		String menuNo = sc.nextLine();
		switch (menuNo) {
		case "1":
			bookDetail(bookClient);
			break;
		case "2":
			seeSearch(bookClient);
			break;
		case "3":
			bookClient.choice = "first";
			break;
		default:
			System.out.println("다시 선택하세요.");
			searchCheck(bookClient);
			break;
		}
	}
	
	public static void bookDetail(BookClient bookClient) {
		System.out.print("book ID : ");
		bookId = sc.nextLine();
		System.out.println("\n---------------[책 상세 정보]---------------");
		System.out.println("-------------------------------------------");

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
				System.out.print("책이름 : " + jo.getString("bookName") + "\t");
				System.out.print("재고수량 :" + jo.getInt("bookStock") + "\n");
				System.out.print("원가 : " + jo.getInt("bookPrice") + "\t");
				System.out.print("-> 할인가 : " + jo.getInt("bookSellingPrice") + "\n");
				System.out.print("작가 : " + jo.getString("bookWriter") + "\t");
				System.out.print("출판사 : " + jo.getString("companyName") + "\n");
				System.out.print("줄거리 : " + jo.getString("bookContent"));
			}

			System.out.println("-------------------------------------------");
			buyBoard(bookClient);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserView error");
		}
	}
	
	public static void buyBoard(BookClient bookClient) {
		System.out.println("---------------------------------------");
		System.out.println("1.책 구매 | 2.장바구니 | 3. 메인페이지로 돌아가기");
		System.out.print("선택 : ");
		String select = sc.nextLine();
		
		switch (select) {
		case "1":
			if(bookClient.flag == false) {
				System.out.println("로그인이 필요합니다.");
				UserView.seeBoard(bookClient);
			}else {
				buyBook(bookClient);
			};
			break;
		case "2" :
			if(bookClient.flag == false) {
				System.out.println("로그인이 필요합니다.");
				UserView.seeBoard(bookClient);
			}else {
				insertCart(bookClient);
			}
			break;
		case "3":
			bookClient.choice= "first";
			break;
		default : 
			System.out.println();
			System.out.println("다시 입력해주세요");	
			System.out.println("--------------------------------");
			seeSearch(bookClient);
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
