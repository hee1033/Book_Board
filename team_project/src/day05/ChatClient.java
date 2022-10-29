package day05;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import org.json.JSONObject;

public class ChatClient {

	static Scanner scanner = new Scanner(System.in);
	// 필드
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	String chatName;
	String choice;

	public void connect() throws IOException {
		socket = new Socket("localhost", 50041);
		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());
		System.out.println("[client] complete server connect");
	}

	public void receive() {

		try {
			String json = dis.readUTF();
			JSONObject root = new JSONObject(json);
			String check = root.getString("check");
			switch (check) {
			case "complete":
				String message = root.getString("message");
				System.out.println(message);
				this.choice = root.getString("choice");
				break;
			}
		} catch (IOException e) {
			System.out.println("[client] server disconnected.");
			System.exit(0);
		}
	}

	public void send(String json) throws IOException {
		dos.writeUTF(json);
		dos.flush();
	}

	public void exit() throws IOException {
		System.out.println("BYE~");
		socket.close();
	}

	public static void main(String[] args) {
		try {
			ChatClient chatClient = new ChatClient();
			chatClient.connect();
			System.out.print("입장 id : ");
			chatClient.chatName = scanner.nextLine();
			// chatName을 비회원 DB생성 후 중복은 안되게 해서 들어올수 있게 만들어야 한다....
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("command", "incoming");
			jsonObject.put("Name", chatClient.chatName);
			jsonObject.put("choice", "first");
			String json = jsonObject.toString();
			// SocketClient .send로 json변수를 보낸다.
			chatClient.send(json);
			chatClient.receive();

			// DB연결
			// chatClient의 객체를 매개변수를 주면서 connect가 되어야 클래스와 연결된다.
			Test.test(chatClient);

			while (true) {
				Thread.sleep(500);
				// 가장 마지막의 listNo에 "first" 주기
				if (chatClient.choice.equals("first")) {
					MainBoard.print();
					jsonObject = new JSONObject();
					jsonObject.put("command", "mainBoard");
					jsonObject.put("choice", "x");
					json = jsonObject.toString();
					chatClient.send(json);
					chatClient.choice = "";
				} else {
					// 목록의 중간과정
					System.out.print("listNo = ");
					String listNo = scanner.nextLine();
					if (listNo.equals("q")) {
						break;
					} else {
						jsonObject = new JSONObject();
						jsonObject.put("command", "listNo");
						jsonObject.put("data", listNo);
//						jsonObject.put("choice", chatClient.choice);
						json = jsonObject.toString();
						chatClient.send(json);
					}
				}

			}
			scanner.close();
			chatClient.exit();
		} catch (Exception e) {
			System.out.println("[client] 서버 연결 안됨");
		}
		System.out.println("[client] 종료.");
	}

}
