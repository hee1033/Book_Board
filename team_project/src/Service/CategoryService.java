package Service;

import java.util.ArrayList;
import java.util.List;

import DAO.CategoryDAO;
import DTO.BookDTO;
import DTO.Pager;

public class CategoryService {

	// 카테고리 목록
	public List<BookDTO> showCategory(int categoryId, Pager pager) {
		CategoryDAO categoryDAO = new CategoryDAO();
		List<BookDTO> bookList = new ArrayList<>();
		
		bookList = categoryDAO.showCategory(categoryId,pager);
		return bookList;

	}

	// 책 상세 정보
	public List<BookDTO> showbookDetail(int bookId) {
		CategoryDAO categoryDAO = new CategoryDAO();
		List<BookDTO> bookList = new ArrayList<>();

		bookList = categoryDAO.showbookDetail(bookId);
		return bookList;
	}

	public int getTotalRows(){
		CategoryDAO categoryDAO = new CategoryDAO();
		int totalRows = categoryDAO.selectTotalRows();
		System.out.println(totalRows);
		return totalRows;
	}
//	//로그인
//		public void showLogin(SocketClient sender, String id, String password) {
//			UserDAO userDAO= new UserDAO();
//			//DB
//			boolean result= userDAO.compareId(sender,id);
//			if(result==true) {
//				if (sender.userDTO.getUserId().equals(id) && sender.userDTO.getUserPassword().equals(password)) {
//					sender.flag = true;
//					sender.userID= sender.userDTO.getUserId();
//				}else {
//					sender.flag=false;
//				}
//			}else
//				sender.flag=false;
//			if (sender.flag == true) {
//				userDAO.selectUserData(sender);
//			}
//		}

}
