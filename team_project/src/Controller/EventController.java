package Controller;

import org.json.JSONObject;

import project.SocketClient;

public class EventController {
	public void event(SocketClient sender, String listNo) {
		JSONObject root= new JSONObject();
		String json="";
		switch(listNo) {
			case "0":
				root.put("controller", "menuNo");
				root.put("data", "event");
				json = root.toString();
				sender.send(json);
				break;
		}
	}
}
