package day04;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.json.JSONObject;

public class SocketClient {

	HomepageServer homepageServer;
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	String clientIp;
	String userName;
	
	public SocketClient(HomepageServer homepageServer, Socket socket) {
		try {
			this.homepageServer = homepageServer;
			this.socket = socket;
			this.dis = new DataInputStream(socket.getInputStream());
			this.dos = new DataOutputStream(socket.getOutputStream());
			InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
			this.clientIp = isa.getHostName();
			receive();
		}catch(IOException e) {
			
		}
	}
	
	public void receive() {
		try {
			while(true) {
				String receiveJson = dis.readUTF();
				
				JSONObject jsonObject = new JSONObject(receiveJson);
				String command = jsonObject.getString("command");
				
				switch(command) {
				case "incoming" :
					this.userName = jsonObject.getString("data");
					homepageServer.sendTOAll(this, "접속함");
					homepageServer.addSocketClient(this);
					break;
				case "message" :
					String message = jsonObject.getString("data");
					homepageServer.sendTOAll(this, message);
					break;
				}
			}
		}catch(IOException e) {}
	}
	
	public void send(String json) {
		try {
			dos.writeUTF(json);
			dos.flush();
		}catch(IOException e) {}
	}
	
	public void close() {
		try {
			socket.close();
			
		}catch(Exception e) {}
	}
}
