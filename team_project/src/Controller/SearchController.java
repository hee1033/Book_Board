package project.Controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import project.SocketClient;
import project.DTO.Pager;
import project.DTO.SearchDTO;
import project.Service.SearchService;

public class SearchController {
	public void search(SocketClient sender, String listNo, JSONObject jsonUpdata) {

		JSONObject root = new JSONObject();
		String json = "";
		switch (listNo) {
		case "0":
			root.put("controller", "menuNo");
			root.put("data", "search");
			json = root.toString();
			sender.send(json);
			break;
		case "1":
			showSearch(sender, jsonUpdata);
			break;
		}
	}

	public void showSearch(SocketClient sender, JSONObject jsonUpdata) {
		SearchService searchService = new SearchService();
		List<SearchDTO> searchList = new ArrayList<>();
		
		String bookName = jsonUpdata.getString("bookName");
		int pageNo=jsonUpdata.getInt("pageNo");		
		
		Pager pager = new Pager(3,5,searchService.getTotalRows(bookName),pageNo);
		searchList = searchService.ServiceSearch(bookName,pager);
		
		try {
			JSONArray jsonArray = new JSONArray();
			for (SearchDTO db : searchList) {
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
			root.put("controller", "show");
			root.put("data", jsonArray);
			String json = root.toString();
			sender.send(json);
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("SearchController error");
		}

	}
}
