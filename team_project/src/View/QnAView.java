package project.View;


import java.util.Scanner;

import org.json.JSONObject;

import project.BookClient;
import project.DTO.Pager;

public class QnAView {
	static Scanner scanner = new Scanner(System.in);
	static int[] bookIdStore;

	public static void seeBoard(BookClient bookClient) {
		System.out.println();
		System.out.println("-----------------------------[QnA]-----------------------------");
		System.out.println("1. 도서 문의 | 2. 이벤트 문의 | 3. 기타 문의 | 4. 질문 올리기 | 5. 돌아가기");
		System.out.println("---------------------------------------------------------------");
		System.out.print("listNo = ");
		String listNo = scanner.nextLine();
		switch (listNo) {
		case "1":
			seeBookQuestions(bookClient, listNo);
			break;
		case "2":
			seeBookQuestions(bookClient, listNo);
			break;
		case "3":
			seeBookQuestions(bookClient, listNo);
			break;
		case "4":
			writeQuestions(bookClient);
			break;
		case "5":
			bookClient.choice = "first";
			break;
		}
		if (!listNo.equals("1") && !listNo.equals("2") && !listNo.equals("3") && !listNo.equals("4")
				&& !listNo.equals("5")) {
			System.out.println("다시 입력해 주세요.");
			seeBoard(bookClient);
		}
	}

	// 페이징
	public static void paging(BookClient bookClient, String listNo, Pager pager) {
		boolean pflag=false;
		System.out.print("pageNo : ");
		String pageNo = scanner.nextLine();
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
		System.out.println("[QnA 목록]");
		try {
			JSONObject updata = new JSONObject();
			updata.put("qna_type", listNo);
			updata.put("pageNo", pageNo);
			updata.put("updata", 0);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "6");
			jsonObject.put("listNo", "showQuestions");
			jsonObject.put("updata", updata);
			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();

			System.out.println("---------------------------------------------------------------");
			// book_id 배열에 저장
			bookIdStore = new int[bookClient.jsonArray.length()];
			for (int i = 0; i < bookClient.jsonArray.length(); i++) {
				JSONObject jo = bookClient.jsonArray.getJSONObject(i);
				int qnaid = jo.getInt("qnaId");
				String qnaTitle = jo.getString("qnaTitle");
				bookIdStore[i] = qnaid;
				System.out.println("No. " + qnaid + ": " + qnaTitle);
				pager.setRowsPerPage(jo.getInt("rowsPerPage"));
				pager.setPagesPerGroup(jo.getInt("pagesPerGroup"));
				pager.setTotalRows(jo.getInt("totalRows"));
				pager.setPageNo(jo.getInt("pageNo"));
			}
			System.out.println("---------------------------------------------------------------");
			pager = new Pager(pager.getRowsPerPage(), pager.getPagesPerGroup(), pager.getTotalRows(),pager.getPageNo());
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

	public static void seeBookQuestions(BookClient bookClient, String listNo) {
		Pager pager = new Pager();
		boolean pflag=false;//pager 때문에 사용
		String pageNo = "1";
		System.out.println("--------------------------------------");
		if (listNo.equals("1")) {
			System.out.println("\t[도서 문의 조회]");
		} else if (listNo.equals("2")) {
			System.out.println("\t[이벤트 문의 조회]");
		} else {
			System.out.println("\t[기타 문의 조회]");
		}

		try {
			JSONObject updata = new JSONObject();
			updata.put("qna_type", listNo);
			updata.put("pageNo", pageNo);
			updata.put("updata", 0);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "6");
			jsonObject.put("listNo", "showQuestions");
			jsonObject.put("updata", updata);

			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();
			bookIdStore = new int[bookClient.jsonArray.length()];
			for (int i = 0; i < bookClient.jsonArray.length(); i++) {
				JSONObject jo = bookClient.jsonArray.getJSONObject(i);
				int qnaid = jo.getInt("qnaId");
				String qnaTitle = jo.getString("qnaTitle");
				bookIdStore[i] = qnaid;
				System.out.println("No. " + qnaid + ": " + qnaTitle);
				pager.setRowsPerPage(jo.getInt("rowsPerPage"));
				pager.setPagesPerGroup(jo.getInt("pagesPerGroup"));
				pager.setTotalRows(jo.getInt("totalRows"));
				pager.setPageNo(jo.getInt("pageNo"));
			}
			System.out.println("---------------------------------------------------------------");
			pager = new Pager(pager.getRowsPerPage(), pager.getPagesPerGroup(), pager.getTotalRows(),pager.getPageNo());
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
			while (true) {
				System.out.println();
				System.out.println("-----------------------------------------------------------");
				System.out.println("1.목록그만보기 | 2.계속보기 | 3. 자세히 보기 | 4. 수정하기 | 5. 삭제하기 ");
				System.out.println("------------------------------------------------------------");
				System.out.print("choice : ");
				String q = scanner.nextLine();
				int detailNum = 0;
				boolean result = false;
				if (q.equals("1")) {
					seeBoard(bookClient);
					break;
				}
				if (q.equals("2")) {
					paging(bookClient, listNo, pager);
				}
				if (q.equals("3")) {
					System.out.println("게시글 번호 입력(뒤로가기:9) : ");
					detailNum = Integer.parseInt(scanner.nextLine()); // detailNum 은 qna_id 와 같다.
					result = false;
					if (detailNum == 9) {
						seeBoard(bookClient);
					}
					for (int i = 0; i < bookIdStore.length; i++) {
						if (bookIdStore[i] == detailNum) {
							result = true;
							break;
						} else
							result = false;
					}
					if (result == false) {
						System.out.println("없는번호입니다. 다시 선택해주세요");
						continue;
					}
					seeDetail(bookClient, listNo, detailNum);
					break;
				}
				if (q.equals("4")) {
					System.out.println("수정할 번호 입력(뒤로가기:9) : ");
					detailNum = Integer.parseInt(scanner.nextLine()); // detailNum 은 qna_id 와 같다.
					result = false;
					if (detailNum == 9) {
						seeBoard(bookClient);
					}
					for (int i = 0; i < bookIdStore.length; i++) {
						if (bookIdStore[i] == detailNum) {
							result = true;
							break;
						} else
							result = false;
					}
					if (result == false) {
						System.out.println("없는번호입니다. 다시 선택해주세요");
						continue;
					}
					modifyQnA(bookClient, detailNum);
					break;
				}
				if (q.equals("5")) {
					System.out.println("삭제할 번호 입력(뒤로가기:9) : ");
					detailNum = Integer.parseInt(scanner.nextLine()); // detailNum 은 qna_id 와 같다.
					result=false;
					if (detailNum==9) {
						seeBoard(bookClient);
					}
					for(int i=0;i<bookIdStore.length;i++) {
						if(bookIdStore[i]==detailNum) {
							result=true;
							break;
						}else
							result=false;
					}
					if(result==false) {
						System.out.println("없는번호입니다. 다시 선택해주세요");
						continue;
					}
					deleteQnA(bookClient, detailNum);
					break;
				}
			}
		} catch (Exception e) {
			seeBoard(bookClient);
		}
	}

	public static void seeDetail(BookClient bookClient, String qna_type, int detailNum) {
		System.out.println("----------☆--문의 답변--★----------");

		try {
			JSONObject jsonObject = new JSONObject();
			JSONObject updata = new JSONObject();
			updata.put("updata", detailNum);
			updata.put("qna_type", qna_type);
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "6");
			jsonObject.put("listNo", "showDetailQuestion"); // seeDetail 메소드에서 llistNo는 불필요
			jsonObject.put("updata", updata);

			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();

			for (int i = 0; i < bookClient.jsonArray.length(); i++) {
				JSONObject jo = bookClient.jsonArray.getJSONObject(i);
				int qnaId = jo.getInt("qnaId");
				String managerAnswer = jo.getString("managerAnswer");
				System.out.println("No. " + qnaId + "에 대한 답글: " + managerAnswer);
			}
			seeBoard(bookClient);
		} catch (Exception e) {
			seeBoard(bookClient);
		}
	}

	public static void writeQuestions(BookClient bookClient) {
		System.out.println("------------------------------------------------------------");
		System.out.println("\t\t1:1 문의 작성하기");
		System.out.println("------------------------------------------------------------");
		System.out.println("문의 카테고리: 1. 도서 문의 | 2. 이벤트 문의 | 3. 기타 문의 | 4. 뒤로가기");
		System.out.print("선택: ");
		String button = scanner.nextLine();
		String bookId = "0";
		if (button.equals("1")) {
			System.out.print("책 번호: ");
			bookId = scanner.nextLine();
		}
		if (button.equals("4")) {
			seeBoard(bookClient);
		}
		// 문의 카테고리 선택시, 다른거 입력했을 경우
		if (!button.equals("1") && !button.equals("2") && !button.equals("3") && !button.equals("4")) {
			System.out.println("카테고리에 맞는 번호를 입력해주세요.");
			writeQuestions(bookClient);
		}
		System.out.print("제목: ");
		String QuestionTitle = scanner.nextLine();
		System.out.println("내용: ");
		String QuestionContent = scanner.nextLine();

		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "6");
			jsonObject.put("listNo", "registerQuestion");

			JSONObject updata = new JSONObject();
			updata.put("QNA_TITLE", QuestionTitle);
			updata.put("QNA_CONTENT", QuestionContent);
			updata.put("QNA_TYPE", button);
			updata.put("BOOK_ID", bookId);

			jsonObject.put("updata", updata);

			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();

			bookClient.choice = "first";

		} catch (Exception e) {
			seeBoard(bookClient);
		}
	}

	public static void modifyQnA(BookClient bookClient, int detailNum) {
		try {
			System.out.println("----------☆--수정하기--★----------");

			System.out.print("수정할 QnA제목: ");
			String modifiedTitle = scanner.nextLine();
			System.out.print("수정할 QnA내용:");
			String modifiedContent = scanner.nextLine();

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "6");
			jsonObject.put("listNo", "modifyQuestion");

			JSONObject jsonUpdata = new JSONObject();
			jsonUpdata.put("modifiedTitle", modifiedTitle);
			jsonUpdata.put("modifiedContent", modifiedContent);
			jsonUpdata.put("qnaId", detailNum);
			jsonObject.put("updata", jsonUpdata);

			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();

			seeBoard(bookClient);
		} catch (Exception e) {
			seeBoard(bookClient);
		}
	}

	public static void deleteQnA(BookClient bookClient, int detailNum) {

		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("controller", "menuNo");
			jsonObject.put("menuNo", "6");
			jsonObject.put("listNo", "deleteQuestion");

			JSONObject jsonUpdata = new JSONObject();
			jsonUpdata.put("qnaId", detailNum);
			jsonObject.put("updata", jsonUpdata);

			String json = jsonObject.toString();
			bookClient.send(json);
			bookClient.receive();

			seeBoard(bookClient);

		} catch (Exception e) {
			seeBoard(bookClient);
		}
	}

}
