package Controller;

import org.json.JSONObject;

import project.SocketClient;

public class ExitController {
	public void exit(SocketClient sender, String listNo) {
		JSONObject root= new JSONObject();
		String json="";
		switch(listNo) {
			case "0":
				root.put("controller", "menuNo");
				root.put("data", "exit");
				json = root.toString();
				sender.send(json);
				break;
		}
		
	}
}
