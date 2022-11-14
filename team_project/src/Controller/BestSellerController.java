package project.Controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import project.SocketClient;
import project.DTO.BookDTO;
import project.Service.BestSellerService;

public class BestSellerController {
	public void bestSeller(SocketClient sender, String listNo, JSONObject jsonUpdata) {
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
				showAgeBestSeller(sender, jsonUpdata);
				break;
			case "3" :
				showGenderbestSeller(sender, jsonUpdata);
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
	public void showAgeBestSeller(SocketClient sender, JSONObject jsonUpdata) {
		BestSellerService bestSellerService = new BestSellerService();
		List<BookDTO> ageList = new ArrayList<>();
		
		int bookId = Integer.parseInt(jsonUpdata.getString("best"));
		ageList = bestSellerService.showAgeBestSeller(bookId);
		
		JSONArray jsonArray = new JSONArray();
		for(BookDTO db : ageList) {
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
	
	// 성별 베스트셀러
		public void showGenderbestSeller(SocketClient sender, JSONObject jsonUpdata) {
			BestSellerService bestSellerService = new BestSellerService();
			List<BookDTO> genderList = new ArrayList<>();
		
			int bookId = Integer.parseInt(jsonUpdata.getString("best"));
			genderList = bestSellerService.showGenderBestSeller(bookId);
			
			JSONArray jsonArray = new JSONArray();
			for(BookDTO db : genderList) {
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
	
	
	
}
