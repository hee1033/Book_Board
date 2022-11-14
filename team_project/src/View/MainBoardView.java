package project.View;

import project.BookClient;

public class MainBoardView {
	
	 public static void seeMainBoard(BookClient bookClient){
		 if(bookClient.flag==false) {
			 System.out.println("==================================================================================================");
			 System.out.println("                                           미니 북 서점                                    ");
			 System.out.println("1.Category | 2.Best Seller | 3.Event | 4.Search | 5.My Page | 6.QnA | 7.login | 8.Exit | 9.Manager");
			 System.out.println("==================================================================================================="); 
		 }
		 
		 else{
			 System.out.println("==================================================================================================");
			 System.out.println("                                           미니 북 서점                                    ");
			 System.out.println("1.Category | 2.Best Seller | 3.Event | 4.Search | 5.My Page | 6.QnA | 7.logout | 8.Exit | 9.Manager");
			 System.out.println("===================================================================================================");
		 }
			

	 }
	 //메인보드에 베스트셀러 보기(처음만 보여주기) 나중에는 안보여주기
	 //페이징기법으로 다음이랑 처음 맨끝 만들기
	
	
}
