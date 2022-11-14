package project;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;

public class BookServer {
	
	static Scanner scanner = new Scanner(System.in);
	ServerSocket serverSocket;
	ExecutorService threadPool = Executors.newFixedThreadPool(100); 
	Map<String, SocketClient> bookRoom = Collections.synchronizedMap(new HashMap<>());
	
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
		String key=socketClient.bookName+"@"+socketClient.clientIp;
		bookRoom.put(key, socketClient);
		System.out.println("입장: "+key);
		System.out.println("현재 사용자 수: "+bookRoom.size()+"\n");
	}
	
	//클라이언트가 나가는걸 확인
	public void removeSocketClient(SocketClient socketClient) {
		String key=socketClient.bookName+"@"+socketClient.clientIp;
		bookRoom.remove(key);
		System.out.println("퇴장: "+key);
		System.out.println("현재 사용자 수: "+bookRoom.size()+"\n");
	}
	
	//server에서 멈주게 될경우
	public void stop() {
		try {
			serverSocket.close();
			threadPool.shutdownNow();
			bookRoom.values().stream().forEach(sc->sc.close());
			System.out.println("[server] close");
		} catch (IOException e) {
			System.out.println("[server] close");
		}
	}
	
	//들어오고 나오고
	public void income(SocketClient sender) {
		JSONObject root=new JSONObject();
		root.put("controller", "complete");
		root.put("choice",sender.choice);
		root.put("flag", sender.flag);
		root.put("message", "환영합니다.");
		String json=root.toString();
		sender.send(json);
	}

	public static void main(String[] args) {
		try {
			BookServer bookServer = new BookServer();
			bookServer.start();
			System.out.println("---------------------------------------------");
			System.out.println("서버를 종료하려면 q or Q를 입력하고 Enter키를 입력하세요.");
			System.out.println("---------------------------------------------");
			Scanner sc=new Scanner(System.in);
			while(true) { 
				String key=sc.nextLine();
				if(key.equals("q")) {
					break;
				}
			}
			sc.close();			
			//TCP 서버 종료
			bookServer.stop();	
		}catch(IOException e) {
			System.out.println("[server] "+e.getMessage());
		}
	}
}

