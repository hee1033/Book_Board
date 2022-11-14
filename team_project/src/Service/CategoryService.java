package project.Service;

import java.util.ArrayList;
import java.util.List;

import project.SocketClient;
import project.DAO.BookDAO;
import project.DAO.CartDAO;
import project.DAO.CategoryDAO;
import project.DAO.OrderDAO;
import project.DTO.BookDTO;
import project.DTO.Pager;

public class CategoryService {

	// 카테고리 목록
	public List<BookDTO> showCategory(int categoryId, Pager pager) {
		CategoryDAO categoryDAO = new CategoryDAO();
		List<BookDTO> bookList = new ArrayList<>();
		bookList = categoryDAO.showCategory(categoryId,pager);
		return bookList;

	}

	// 책 상세 정보
	public List<BookDTO> showBookDetail(int bookId) {
		BookDAO bookDAO = new BookDAO();
		List<BookDTO> bookList = new ArrayList<>();
		bookList = bookDAO.showBookDetail(bookId);
		return bookList;
	}

	public int getTotalRows(int categoryId){
		CategoryDAO categoryDAO = new CategoryDAO();
		int totalRows = categoryDAO.selectTotalRows(categoryId);
		System.out.println(totalRows);
		return totalRows;
	}
	
	public boolean insertCartList(SocketClient sender, int bookId) {
		CartDAO cartDAO = new CartDAO();
		boolean result=cartDAO.insertCart(sender,bookId);
		return result;
	}
	
	//책 구매
		public boolean buyBook(SocketClient sender, int bookId) {
			OrderDAO orderDAO = new OrderDAO();
			String address= orderDAO.selectUSerAddress(sender);
			boolean result=orderDAO.buyBook(sender, bookId, address);
			
			return result;
		}
}
