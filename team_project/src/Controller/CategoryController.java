package Controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import DTO.BookDTO;
import DTO.Pager;
import Service.CategoryService;
import project.SocketClient;

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
			showbookDetail(sender, jsonUpdata);
		}
	}
	
	public void showCategory(SocketClient sender, JSONObject jsonUpdata) {
		
		CategoryService categoryService = new CategoryService();
		
		List<BookDTO> bookList = new ArrayList<>();
		
		int categoryId = Integer.parseInt(jsonUpdata.getString("categoryId"));
		int pageNo=jsonUpdata.getInt("pageNo");
		
		Pager pager = new Pager(5,2,categoryService.getTotalRows(),pageNo);
		
		bookList = categoryService.showCategory(categoryId,pager);
		
		//북 리스트
		JSONArray jsonArray = new JSONArray();
		for(BookDTO db : bookList) {
			JSONObject jo = new JSONObject();
			jo.put("categoryName", db.getCategoryName());
			jo.put("bookId", db.getBookId());
			jo.put("bookName", db.getBookName());
			jsonArray.put(jo);
		}
		JSONObject root = new JSONObject();
		root.put("controller","show");
		root.put("data",jsonArray);
		String json = root.toString();
		sender.send(json);
		

	}
	
	public void showbookDetail(SocketClient sender,JSONObject jsonUpdata) {
		CategoryService categoryService = new CategoryService();
		List<BookDTO> bookList = new ArrayList<>();
		int bookId = jsonUpdata.getInt("bookId");
		bookList = categoryService.showbookDetail(bookId);
		
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
	
	


}
