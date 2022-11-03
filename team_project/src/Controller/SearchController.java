package Controller;

import org.json.JSONObject;

import project.SocketClient;

public class SearchController {
	public void search(SocketClient sender, String listNo) {
		JSONObject root= new JSONObject();
		String json="";
		switch(listNo) {
			case "0":
				root.put("controller", "menuNo");
				root.put("data", "search");
				json = root.toString();
				sender.send(json);
				break;
		}		
	}
}
