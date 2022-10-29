package day05;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.json.JSONObject;

public class SocketClient {

	//필드
	ChatServer chatServer;
	Socket socket;
	String chatName;
	String clientIp;
	DataInputStream dis; //byte
	DataOutputStream dos; //기본타입->byte
	String choice;
	
	//생성자
	public SocketClient(ChatServer chatServer,Socket socket) {
		try {
			this.chatServer=chatServer;
			this.socket=socket;
			this.dis=new DataInputStream(socket.getInputStream());
			this.dos=new DataOutputStream(socket.getOutputStream());
			InetSocketAddress isa=(InetSocketAddress)socket.getRemoteSocketAddress();
			this.clientIp=isa.getHostName();
			receive();
			
			
		} catch (IOException e) {
		}
	}
	
	public void receive() {
		chatServer.threadPool.execute(()->{
			try {
				while(true) {
					String receiveJson=dis.readUTF();
					JSONObject jsonObject = new JSONObject(receiveJson);
					String command = jsonObject.getString("command");
					
					switch(command) {
						case "incoming":
							//"data"는 cathClient에서 쓴 chatName 값이다.
							this.chatName=jsonObject.getString("Name");
							this.choice=jsonObject.getString("choice");
							chatServer.income(this);
							chatServer.addSocketClient(this);
							break;
						case "mainBoard":
							this.choice=jsonObject.getString("choice");
							chatServer.income(this);
							break;
						case "listNo":
							String listNo=jsonObject.getString("data");
							chatServer.list(this,listNo);
					}
					
				}
			}catch(Exception e) {
//				chatServer.sendToAll(this);
				chatServer.removeSocketClient(this);
			}
		});
	}

	public void send(String json) {
		try {
			dos.writeUTF(json);
			dos.flush();
		} catch (IOException e) {
			System.out.println("213123");
		}
	}
	
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
		}
	}
	
	
}
