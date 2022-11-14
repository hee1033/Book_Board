package project.View;

import java.io.IOException;
import java.util.Scanner;

import org.json.JSONObject;

import project.BookClient;
import project.DTO.Pager;


public class CategoryView {
	static Scanner scan = new Scanner(System.in);
	static String bookId;
	// 카테고리 목록
	public static void category(BookClient bookClient) {
		System.out.println("------------------------[카테고리]------------------------");
		System.out.println("1.소설 | 2.시/에세이 | 3. 인문 | 4. 컴퓨터/IT | 5.가정/육아\n6.요리 | 7.건강 | 8.취미/실용/스포츠 | 9.경제/경영 | 10.자기계발");
		System.out.println("------------------------------------------------------");
		System.out.print("listNo = ");
		String listNo = scan.nextLine();
		switch(listNo) {
			case "1":
				seeCategory(bookClient, listNo);
				break;
			case "2":
				seeCategory(bookClient, listNo);
				break;
			case "3":
				seeCategory(bookClient, listNo);
				break;
			case "4":
				seeCategory(bookClient, listNo);
				break;
			case "5":
				seeCategory(bookClient, listNo);
				break;
			case "6":
				seeCategory(bookClient, listNo);
				break;
			case "7":
				seeCategory(bookClient, listNo);
				break;
			case "8":
				seeCategory(bookClient, listNo);
				break;
			case "9":
				seeCategory(bookClient, listNo);
				break;
			case "10":
				seeCategory(bookClient, listNo);
				break;
			default:
				System.out.println("없는 번호입니다. 다시 선택해주세요.");
				category(bookClient);
				break;
		}
		
	}
	
	//페이징
	public static void paging(BookClient bookClient, String listNo, Pager pager) {
		boolean pflag=false;
		System.out.print("pageNo : ");
		String pageNo = scan.nextLine();
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
		System.out.println("[책 목록]");
		
		try {
			JSONObject updata = new JSONObject();
			updata.put("categoryId", listNo);
			updata.put("pageNo", pageNo);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "1");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
			
			System.out.println("카테고리\t 책 번호\t 책 이름");
			System.out.println("-------------------------------------------------");
			for (int i = 0; i < bookClient.jsonArray.length(); i++) {
				JSONObject jo = bookClient.jsonArray.getJSONObject(i);
				System.out.print(jo.getString("categoryName") + "\t");
				System.out.print(jo.getInt("bookId") + "\t");
				System.out.println(jo.getString("bookName"));
				//Controller에서 지정한 값을 가져와 Pager 생성
				pager.setRowsPerPage(jo.getInt("rowsPerPage"));
				pager.setPagesPerGroup(jo.getInt("pagesPerGroup"));
				pager.setTotalRows(jo.getInt("totalRows"));
				pager.setPageNo(jo.getInt("pageNo"));
			}
			System.out.println("-------------------------------------------------");
			pager=new Pager(pager.getRowsPerPage(),pager.getPagesPerGroup(),pager.getTotalRows(),pager.getPageNo());
			//첫번째 그룹이 아니라면 맨앞과 이전을 보여준다.
			if(pager.getGroupNo()!=1) {
				System.out.print("<first> ");
				System.out.print("<back> ");
			}
			//마지막 그룹의 값을 저장하기위해 end배열 생성
			int[] end=new int[pager.getPagesPerGroup()];
			//print문으로 현재 페이지의 첫번째 값과 마지막값 print
			for(int i=0;i<pager.getPagesPerGroup();i++) {
				System.out.print(pager.getStartPageNo()+i+" ");
				end[i]=pager.getStartPageNo()+i;
				//if문은 마지막 그룹에 값을 지정해주기 위해 사용
				if((pager.getStartPageNo()+i)==pager.getTotalPageNo()) {
					break;
				}
			}
			//pflag를 이용하여 위에서 저장한 end배열의 존재 유무 확인
			for (int i = 0; i < pager.getPagesPerGroup(); i++) {
				if (pager.getTotalPageNo() == end[i]) {
					pflag=true;
					break;
				} else {
					pflag=false;
				}
			}
			//현재 그룹에 마지막페이지 그룹이 없다면 print
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
	
	//페이징
	public static void seeCategory(BookClient bookClient, String listNo) {
		Pager pager = new Pager();
		boolean pflag=false;
		String pageNo = "1";
		System.out.println("[책 목록]");
		try {
			JSONObject updata = new JSONObject();
			updata.put("categoryId", listNo);
			updata.put("pageNo", pageNo);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "1");
			jsonObject.put("listNo", "1");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
			System.out.println("카테고리\t 책 번호\t 책 이름");
			System.out.println("-------------------------------------------------");
			for (int i = 0; i < bookClient.jsonArray.length(); i++) {
				JSONObject jo = bookClient.jsonArray.getJSONObject(i);
				System.out.print(jo.getString("categoryName") + "\t");
				System.out.print(jo.getInt("bookId") + "\t");
				System.out.println(jo.getString("bookName"));
				pager.setRowsPerPage(jo.getInt("rowsPerPage"));
				pager.setPagesPerGroup(jo.getInt("pagesPerGroup"));
				pager.setTotalRows(jo.getInt("totalRows"));
				pager.setPageNo(jo.getInt("pageNo"));
			}
			System.out.println("-------------------------------------------------");
			pager=new Pager(pager.getRowsPerPage(),pager.getPagesPerGroup(),pager.getTotalRows(),pager.getPageNo());
			int[] end=new int[pager.getPagesPerGroup()];
			for(int i=0;i<pager.getPagesPerGroup();i++) {
				System.out.print(pager.getStartPageNo()+i+" ");
				end[i]=pager.getStartPageNo()+i;
				if((pager.getStartPageNo()+i)==pager.getTotalPageNo()) {
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
			while(true) {
				System.out.println();
				System.out.println("-------------------------------------------------");
				System.out.println("1. 목록그만보기 | 2. 계속보기");
				System.out.print("choice : ");
				String q=scan.nextLine();
				if(q.equals("1")) {
					break;
				}
				paging(bookClient, listNo, pager);
			}
			
			bookBoard(bookClient);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("CategoryView error");
		}

	}
	
	public static void bookBoard(BookClient bookClient) {
		//돌아가기
		System.out.println("-------------------------------------------------");
		System.out.println("1.책 선택 | 2.메인 페이지로 돌아가기");
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
			System.out.println("-------------------------------------------------");
			bookBoard(bookClient);
		}

	}


	// 북 상세정보
	public static void bookDetail(BookClient bookClient) {
		
		System.out.println("\n[책 상세 정보]");
		System.out.println("-------------------------------------------------");

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
		System.out.println("-------------------------------------------------");
		System.out.println("1.책 구매 | 2.장바구니 | 3. 메인페이지로 돌아가기");
		System.out.println("-------------------------------------------------");

		System.out.print("선택 : ");
		String select = scan.nextLine();
		
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
			System.out.println("-------------------------------------------------");
			seeCategory(bookClient,select);
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
