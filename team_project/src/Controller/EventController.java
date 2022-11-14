package project.Controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import project.SocketClient;
import project.DTO.EventDTO;
import project.DTO.GoodsDTO;
import project.DTO.Pager;
import project.Service.EventService;
import project.Service.GoodsService;

public class EventController {
	public void event(SocketClient sender, String listNo, JSONObject jsonUpdata) {
		JSONObject root = new JSONObject();
		String json = "";
		switch (listNo) {
		case "0":
			root.put("controller", "menuNo");
			root.put("data", "event");
			json = root.toString();
			sender.send(json);
			break;
		case "1":
			showEvent(sender, jsonUpdata);
			break;
		case "2":
			showGoods(sender, jsonUpdata);
			break;
		case "3":
			showGoodsDetail(sender, jsonUpdata);
			break;
		case "4":
			buyGoods(sender,jsonUpdata);
			break;
		}
	}

	public void showEvent(SocketClient sender, JSONObject jsonUpdata) {
		EventService eventService = new EventService();
		List<EventDTO> eventList = new ArrayList<>();
		int pageNo=jsonUpdata.getInt("pageNo");
		Pager pager = new Pager(3,5,eventService.getTotalRows(),pageNo);
		eventList = eventService.ServiceEvent(pager);
		
		JSONArray jsonArray = new JSONArray();
		for(EventDTO db : eventList) {
			JSONObject jo = new JSONObject();
			jo.put("eventId",db.getEventId());
			jo.put("eventName",db.getEventName());
			jo.put("eventStartDate",db.getEventStartDate());
			jo.put("eventEndDate",db.getEventEndDate());
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
	}

	public void showGoods(SocketClient sender, JSONObject jsonUpdata) {
		GoodsService goodsService = new GoodsService();
		List<GoodsDTO> goodsList = new ArrayList<>();
		
		int eventId = jsonUpdata.getInt("eventId");
		goodsList = goodsService.showGoodsUpdata(eventId);
		
		JSONArray jsonArray = new JSONArray();
		for(GoodsDTO db : goodsList) {
			JSONObject jo = new JSONObject();
			jo.put("goodsName",db.getGoodsName());
			jo.put("eventName",db.getEventName());
			jo.put("bookName",db.getBookName());
			jo.put("eventStartDate",db.getEventStartDate());
			jo.put("eventEndDate",db.getEventEndDate());
			jo.put("eventContent",db.getEventContent());
			jsonArray.put(jo);
		}
		JSONObject root = new JSONObject();
		root.put("controller", "show");
		root.put("data", jsonArray);
		String json = root.toString();
		sender.send(json);
	}
	public void showGoodsDetail(SocketClient sender, JSONObject jsonUpdata) {
		GoodsService goodsService = new GoodsService();
		List<GoodsDTO> goodsList = new ArrayList<>();
		
		int eventId = jsonUpdata.getInt("eventId");
		goodsList = goodsService.showGoodsUpdata(eventId);
		
		JSONArray jsonArray = new JSONArray();
		for(GoodsDTO db : goodsList) {
			JSONObject jo = new JSONObject();
			jo.put("goodsName",db.getGoodsName());
			jo.put("goodsPrice",db.getGoodsPrice());
			jo.put("eventStartDate",db.getEventStartDate());
			jo.put("eventEndDate",db.getEventEndDate());
			jo.put("goodsContent",db.getGoodsContent());
			jsonArray.put(jo);
		}
		JSONObject root = new JSONObject();
		root.put("controller", "show");
		root.put("data", jsonArray);
		String json = root.toString();
		sender.send(json);
	}
	public static void buyGoods(SocketClient sender, JSONObject jsonUpdata) {
		GoodsService goodsService = new GoodsService();
		
		int goodsId = jsonUpdata.getInt("goodsId");
		boolean result=goodsService.buyGoods(sender, goodsId);
		
		sender.flag = result;
		
		JSONObject root = new JSONObject();
		root.put("controller","mainBoard");
		root.put("choice","first");
		String json = root.toString();
		if(sender.flag=true) {
			sender.send(json);
		} else {
			System.out.println("구매 실패");
		}
		
	}
}
