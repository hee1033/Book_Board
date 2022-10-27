package day04;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import org.json.JSONObject;

public class Client {

	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	String userName;
	
	public void connect() throws IOException {
		
		socket = new Socket("localhost", 50001);
		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());
		System.out.println("클라이언트 연결됨");
	}
	
	public void receive() {
		Thread thread = new Thread(()-> {
			try {
				while(true) {
					String json = dis.readUTF();
					JSONObject root = new JSONObject(json);
					String clientIp = root.getString("clientIp");
					String userName = root.getString("userName");
					String message = root.getString("message");
					System.out.println("<"+userName+">");
				}
			}catch(Exception e) {
				System.exit(0);
			}
		});
		thread.start();
	}
	
	public void send(String json) throws IOException{
		dos.writeUTF(json);
		dos.flush();
	}
	
	public void unconnect() throws IOException{
		socket.close();
	}
	
	public static void main(String[] args) {
		try {
			Client client = new Client();
			client.connect();
			
			Scanner scan = new Scanner(System.in);
			System.out.print("USER NAME : ");
			client.userName = scan.nextLine();
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("command", "incoming");
			jsonObject.put("data", client.userName);
			String json = jsonObject.toString();
			client.send(json);
			
			client.receive();
			
			while(true) {
				String message = scan.nextLine();
				if(message.toLowerCase().equals("q")) {
					break;
				}else {
					jsonObject = new JSONObject();
					jsonObject.put("command", "message");
					jsonObject.put("data",message);
					json = jsonObject.toString();
					client.send(json);
				}
			}
			scan.close();
			client.unconnect();
			
		}catch(Exception e) {
			System.out.println("클라이언트 서버 연결 안됨");
		}
	}
	
}
