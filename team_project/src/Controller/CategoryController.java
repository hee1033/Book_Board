package project.Controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import project.SocketClient;
import project.DTO.BookDTO;
import project.DTO.Pager;
import project.Service.CategoryService;

public class CategoryController {
	public void category(SocketClient sender, String listNo, JSONObject jsonUpdata) {
		JSONObject root = new JSONObject();
		String json = "";
		switch (listNo) {
		case "0":
			root.put("controller", "menuNo");
			root.put("data", "category");
			json = root.toString();
			sender.send(json);
			break;
		case "1":
			showCategory(sender, jsonUpdata);
			break;
		case "2":
			showBookDetail(sender, jsonUpdata);
			break;
		case "3":
			buyBook(sender, jsonUpdata);
			break;
		case "4" :
			insertCart(sender,jsonUpdata);
			break;
		}
	}
	
	public void showCategory(SocketClient sender, JSONObject jsonUpdata) {
		CategoryService categoryService = new CategoryService();
		List<BookDTO> bookList = new ArrayList<>();
		//**수정
		int categoryId = Integer.parseInt(jsonUpdata.getString("categoryId"));
		int pageNo=jsonUpdata.getInt("pageNo");
		
		Pager pager = new Pager(2,3,categoryService.getTotalRows(categoryId),pageNo);
		bookList = categoryService.showCategory(categoryId,pager);
		
		//북 리스트
		JSONArray jsonArray = new JSONArray();
		for(BookDTO db : bookList) {
			JSONObject jo = new JSONObject();
			jo.put("categoryName", db.getCategoryName());
			jo.put("bookId", db.getBookId());
			jo.put("bookName", db.getBookName());
			jo.put("rowsPerPage", pager.getRowsPerPage());
			jo.put("pagesPerGroup", pager.getPagesPerGroup());
			jo.put("totalRows", pager.getTotalRows());
			jo.put("pageNo",pager.getPageNo());
			jsonArray.put(jo);
		}
		JSONObject root = new JSONObject();
		root.put("controller","show");
		root.put("data",jsonArray);
		String json = root.toString();
		sender.send(json);
		

	}
	
	public void showBookDetail(SocketClient sender,JSONObject jsonUpdata) {
		CategoryService categoryService = new CategoryService();
		List<BookDTO> bookList = new ArrayList<>();
		int bookId = jsonUpdata.getInt("bookId");
		bookList = categoryService.showBookDetail(bookId);
		
		JSONArray jsonArray = new JSONArray();
		
		for(BookDTO db : bookList) {
			JSONObject jo = new JSONObject();
			jo.put("bookName", db.getBookName());
			jo.put("bookStock", db.getBookStock());
			jo.put("bookPrice", db.getBookPrice());
			jo.put("bookSellingPrice", db.getBookSellingPrice());
			jo.put("bookWriter", db.getBookWriter());
			jo.put("companyName", db.getCompanyName());
			jo.put("bookContent", db.getBookContent());
			jsonArray.put(jo);
		}
		JSONObject root = new JSONObject();
		root.put("controller","show");
		root.put("data",jsonArray);
		String json = root.toString();
		sender.send(json);
		
		
	}
	
	public void insertCart(SocketClient sender, JSONObject jsonUpdata) {
		CategoryService categoryService = new CategoryService();
		int bookId = jsonUpdata.getInt("bookId");
		boolean result=categoryService.insertCartList(sender, bookId);
		if(result==true) {
			JSONObject root = new JSONObject();
			root.put("controller", "complete");
			root.put("choice", "first");
			root.put("flag", sender.flag);
			root.put("message", "장바구니에 담겼습니다.");
			String json = root.toString();
			sender.send(json);
		}
	}
	
	public void buyBook(SocketClient sender, JSONObject jsonUpdata) {
		CategoryService categoryService = new CategoryService();
		int bookId = jsonUpdata.getInt("bookId");
		boolean result=categoryService.buyBook(sender,bookId);
		if(result==true) {
			JSONObject root = new JSONObject();
			root.put("controller", "complete");
			root.put("choice", "first");
			root.put("flag", sender.flag);
			root.put("message", "책 구매가 완료되었습니다.");
			String json = root.toString();
			sender.send(json);
		}
	}


}
