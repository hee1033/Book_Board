package Controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import DTO.BookDTO;
import Service.BestSellerService;
import project.SocketClient;

public class BestSellerController {
	public void bestSeller(SocketClient sender, String listNo, JSONObject jsonUpdata) {
		BestSellerService bestSellerService = new BestSellerService();
		JSONObject root= new JSONObject();
		String json="";
		switch(listNo) {
			case "0":
				root.put("controller", "menuNo");
				root.put("data", "best");
				json = root.toString();
				sender.send(json);
				break;
			case "1" :
				showAllbestSeller(sender, jsonUpdata);
				break;
			case "2" :
				showAge1020BestSeller(sender, jsonUpdata);
				break;
			case "3" :
				showAge2040BestSeller(sender, jsonUpdata);
				break;
			case "4" :
				showAge40OverBestSeller(sender, jsonUpdata);
				break;
		}
	}
	//전체 베스트셀러
	public void showAllbestSeller(SocketClient sender, JSONObject jsonUpdata) {
		BestSellerService bestSellerService = new BestSellerService();
		List<BookDTO> AllbookList = new ArrayList<>();
	
		int bookId = Integer.parseInt(jsonUpdata.getString("best"));
		
		AllbookList = bestSellerService.showAllBestSeller(bookId);
		
		JSONArray jsonArray = new JSONArray();
		for(BookDTO db : AllbookList) {
			JSONObject jo = new JSONObject();
			jo.put("bookId", db.getBookId());
			jo.put("bookName", db.getBookName());
			jsonArray.put(jo);
		}
		JSONObject root = new JSONObject();
		root.put("controller", "show");
		root.put("data",jsonArray);
		
		String json = root.toString();
		sender.send(json);
	}
	
	// 연령별 베스트셀러
	// 10~20대
	public void showAge1020BestSeller(SocketClient sender, JSONObject jsonUpdata) {
		BestSellerService bestSellerService = new BestSellerService();
		List<BookDTO> age1020 = new ArrayList<>();
	
		int bookId = Integer.parseInt(jsonUpdata.getString("best"));

		age1020 = bestSellerService.showAge1020BestSeller(bookId);
		
		JSONArray jsonArray = new JSONArray();
		for(BookDTO db : age1020) {
			JSONObject jo = new JSONObject();
			jo.put("bookId", db.getBookId());
			jo.put("bookName", db.getBookName());
			jsonArray.put(jo);
		}
		JSONObject root = new JSONObject();
		root.put("controller", "show");
		root.put("data",jsonArray);
		
		String json = root.toString();
		sender.send(json);
	}
	
	// 20~40대
	public void showAge2040BestSeller(SocketClient sender, JSONObject jsonUpdata) {
		BestSellerService bestSellerService = new BestSellerService();
		List<BookDTO> age2040 = new ArrayList<>();
	
		int bookId = Integer.parseInt(jsonUpdata.getString("best"));
	
		age2040 = bestSellerService.showAge2040BestSeller(bookId);
		
		JSONArray jsonArray = new JSONArray();
		for(BookDTO db : age2040) {
			JSONObject jo = new JSONObject();
			jo.put("bookId", db.getBookId());
			jo.put("bookName", db.getBookName());
			jsonArray.put(jo);
		}
		JSONObject root = new JSONObject();
		root.put("controller", "show");
		root.put("data",jsonArray);
		
		String json = root.toString();
		sender.send(json);
	}
	
	//40대 이상
	public void showAge40OverBestSeller(SocketClient sender, JSONObject jsonUpdata) {
		BestSellerService bestSellerService = new BestSellerService();
		List<BookDTO> Over40 = new ArrayList<>();
	
		int bookId = Integer.parseInt(jsonUpdata.getString("best"));

		Over40 = bestSellerService.showAge40OverBestSeller(bookId);
		
		JSONArray jsonArray = new JSONArray();
		for(BookDTO db : Over40) {
			JSONObject jo = new JSONObject();
			jo.put("bookId", db.getBookId());
			jo.put("bookName", db.getBookName());
			jsonArray.put(jo);
		}
		JSONObject root = new JSONObject();
		root.put("controller", "show");
		root.put("data",jsonArray);
		
		String json = root.toString();
		sender.send(json);
	}
//	// 성별 베스트셀러
//		public void showGenderbestSeller(SocketClient sender, JSONObject jsonUpdata) {
//			BestSellerService bestSellerService = new BestSellerService();
//			List<BookDTO> bookList = new ArrayList<>();
//		
//			int bookId = Integer.parseInt(jsonUpdata.getString("best"));
//			
//			bookList = bestSellerService.showGenderbestSeller(bookId);
//			
//			JSONArray jsonArray = new JSONArray();
//			for(BookDTO db : bookList) {
//				JSONObject jo = new JSONObject();
//				jo.put("bookId", db.getBookId());
//				jo.put("bookName", db.getBookName());
//				jsonArray.put(jo);
//			}
//			JSONObject root = new JSONObject();
//			root.put("controller", "show");
//			root.put("data",jsonArray);
//			
//			String json = root.toString();
//			sender.send(json);
//		}
	
	
	
}
