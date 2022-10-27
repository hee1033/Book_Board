package day04;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;

public class HomepageServer {
	
	ServerSocket serverSocket;
	Map<String,SocketClient> login = Collections.synchronizedMap(new HashMap<>());

	//서버 시작
	public void start() throws IOException{
		serverSocket = new ServerSocket(50001);
		System.out.println("[서버] 시작");
		
		try {
			while(true) {
				Socket socket = serverSocket.accept();
				SocketClient sc = new SocketClient(this,socket);
			}
		}catch(IOException e) {
			
		}
	}
	
	public void addSocketClient(SocketClient socketClient) {
		String key = socketClient.userName;
		login.put(key,socketClient);
		System.out.println("현재 접속자 수 : "+login.size());
	}
	
	public void removeSocketClient(SocketClient socketClient) {
		String key = socketClient.userName;
		login.remove(key);
		System.out.println("현재 접속자 수 : "+ login.size());
	}
	
	public void sendTOAll(SocketClient sender, String message) {
		JSONObject root = new JSONObject();
		root.put("userName", sender.userName);
		root.put("message", message);
		String json = root.toString();
		
		Collection<SocketClient> socketClients = login.values();
		for(SocketClient sc : socketClients) {
			if(sc == sender) continue;
			sc.send(json);
		}
	}
	
	public void stop() {
		try {
			serverSocket.close();
			login.values().stream().forEach(sc -> sc.close());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			HomepageServer homepageServer = new HomepageServer();
			homepageServer.start();
			
			Scanner scan = new Scanner(System.in);
			while(true) {
				String key = scan.nextLine();
				if(key.equals("q")) break;
			}
			scan.close();
			homepageServer.stop();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
