package day05;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;

public class ChatServer {
	
	static Scanner scanner = new Scanner(System.in);
	ServerSocket serverSocket;
	ExecutorService threadPool= Executors.newFixedThreadPool(100); 
	Map<String, SocketClient> chatRoom=Collections.synchronizedMap(new HashMap<>());
	
	//server 소켓 생성
	public void start() throws IOException{
		serverSocket = new ServerSocket(50041);
		System.out.println("[server] start");
		
		Thread thread = new Thread(()->{
			try {
				while(true) {
					Socket socket= serverSocket.accept();
					SocketClient sc= new SocketClient(this,socket);
				}
			}catch(Exception e) {
				
			}
		});
		thread.start();
	}
	
	//클라이언트가 들어온것 확인	
	public void addSocketClient(SocketClient socketClient) {
		String key=socketClient.chatName+"@"+socketClient.clientIp;
		chatRoom.put(key, socketClient);
		System.out.println("입장: "+key);
		System.out.println("현재 사용자 수: "+chatRoom.size()+"\n");
	}
	
	//클라이언트가 나가는걸 확인
	public void removeSocketClient(SocketClient socketClient) {
		String key=socketClient.chatName+"@"+socketClient.clientIp;
		chatRoom.remove(key);
		System.out.println("퇴장: "+key);
		System.out.println("현재 사용자 수: "+chatRoom.size()+"\n");
	}
	
	//server에서 멈주게 될경우
	public void stop() {
		try {
			serverSocket.close();
			threadPool.shutdownNow();
			chatRoom.values().stream().forEach(sc->sc.close());
			System.out.println("[server] close");
		} catch (IOException e) {
			System.out.println("[server] close");
		}
		
	}

	public void income(SocketClient sender) {
//		System.out.println(sender.choice);
		JSONObject root = new JSONObject();
		root.put("check", "complete");
		root.put("message", "환영합니다.");
		root.put("choice",sender.choice);
		String json=root.toString();
		sender.send(json);
	}
	public void list(SocketClient sender, String listNo) {
		String number = listNo;
		JSONObject root; 
		switch(number) {
			case "1":
				root = new JSONObject();
				
				break;
			case "2":
				BestSeller bestSeller=new BestSeller();
				bestSeller.toString();
				break;
			case "3":
				Event event = new Event();
				event.toString();
				break;
			case "4":
				Search search = new Search();
				search.toString();
				break;
			case "5":
				root=new JSONObject();
				root.put("check","complete");
				root.put("data","ww");
			    root.put("choice",sender.choice);
			    String json=root.toString();
				sender.send(json);
			    break;
			case "6":
				QnA qna=new QnA();
				qna.toString();
				break;
			case "7":
				Exit exit=new Exit();
				exit.toString();
				break;
		}
	}
	/*
	public void sendToAll(SocketClient sender) {
		JSONObject root=new JSONObject();
		root.put("clientIp", "homepage");
		root.put("data",sender.chatName);
		String json=root.toString();
		
		sender.send(json);
	}*/

	public static void main(String[] args) {
		try {
			ChatServer chatServer = new ChatServer();
			chatServer.start();
			
			Scanner sc=new Scanner(System.in);
			while(true) { 
				String key=sc.nextLine();
				if(key.equals("q")) {
					break;
				}
			}
			sc.close();			
			//TCP 서버 종료
			chatServer.stop();	
		}catch(IOException e) {
			System.out.println("[server] "+e.getMessage());
		}
	}
}

