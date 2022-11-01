package day06;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.json.JSONObject;

import DTO.DTOBook;
import DTO.DTOCategory;
import DTO.DTOUser;
import detailView.BookDetailView;
import service.CategoryService;
import view.CategoryView;

public class SocketClient {

	public DTOBook bookD = new DTOBook();
	public DTOCategory categoryD = new DTOCategory();

	//필드
	ChatServer chatServer;
	Socket socket;
	String chatName;
	String clientIp;
	DataInputStream dis; //byte
	DataOutputStream dos; //기본타입->byte
	String choice;
	boolean flag;
	DTOUser userD = new DTOUser();
	
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
					String check = jsonObject.getString("check");
					
					switch(check) {
						case "incoming":
							//"data"는 cathClient에서 쓴 chatName 값이다.
							this.chatName=jsonObject.getString("Name");
							this.choice=jsonObject.getString("choice");
							chatServer.income(this);
							chatServer.addSocketClient(this);
							break;
						case "mainBoard":
							this.choice=jsonObject.getString("choice");
							this.flag=jsonObject.getBoolean("flag");
							JSONObject root=new JSONObject();
							root.put("check", "complete");
							root.put("choice",this.choice);
							root.put("flag", this.flag);
							root.put("message", "**MainBoard**");
							String json=root.toString();
							send(json);
							break;
							
						case "menuNo":
							String menuNo = jsonObject.getString("menuNo");
							String listNo = jsonObject.getString("listNo");
							JSONObject jsonUpdata=new JSONObject();
							jsonUpdata=jsonObject.getJSONObject("updata");
							switch(menuNo) {
								case "1":
									seeCategory(this,listNo,jsonUpdata);
									break;
								case "2":
									bestSeller(this,listNo);
									break;
								case "3":
									event(this,listNo);
								case "4":
									search(this,listNo);
									break;
								case "5":
									mypage(this,listNo,jsonUpdata);
									break;
								case "6":
									qna(this,listNo);
									break;
								case "7":
									user(this,listNo,jsonUpdata);
									break;
								case "8":
									exit(this,listNo);
									break;
							}
							break;
					}
					
				}
			}catch(Exception e) {
				chatServer.removeSocketClient(this);
			}
		});
	}
	public void seeCategory(SocketClient sender, String listNo, JSONObject jsonUpdata) {
		CategoryView category = new CategoryView();
		CategoryService categoryService = new CategoryService();
		BookDetailView bookDetailView = new BookDetailView();
		JSONObject root= new JSONObject();
		String json="";
		switch(listNo) {
			case "0":
				root.put("check", "menuNo");
				root.put("data", "category");
				json=root.toString();
				sender.send(json);
				break;
			case "1":
				categoryService.showCategory(sender,jsonUpdata);
				break;
			case "2" :
				bookDetailView.bookDetail(sender, jsonUpdata);
		}
	}
	
	public void bestSeller(SocketClient sender, String listNo) {
//		BestSeller bestSeller = new BestSeller();
		JSONObject root= new JSONObject();
		String json="";
		switch(listNo) {
			case "0":
				root.put("check", "menuNo");
				root.put("data", "best");
				json = root.toString();
				sender.send(json);
				break;
		}
	}

	public void event(SocketClient sender, String listNo) {
//		Event event = new Event();
		JSONObject root= new JSONObject();
		String json="";
		switch(listNo) {
			case "0":
				root.put("check", "menuNo");
				root.put("data", "event");
				json = root.toString();
				break;
		}
	}

	public void search(SocketClient sender, String listNo) {
//		Search search = new Search();
		JSONObject root= new JSONObject();
		String json="";
		switch(listNo) {
			case "0":
				root.put("check", "menuNo");
				root.put("data", "search");
				json = root.toString();
				sender.send(json);
				break;
		}		
	}

	public void mypage(SocketClient sender, String listNo, JSONObject jsonUpdata) {
		MyPage myPage = new MyPage();
		User user = new User();
		JSONObject root= new JSONObject();
		String json="";
		switch(listNo){
			case "0":
				root.put("check", "menuNo");
				root.put("data", "mypage");
				json = root.toString();
				sender.send(json);
				break;
			case "1":
				myPage.userUpdate(sender, jsonUpdata);
				break;
			case "2":
				myPage.orderList();
				break;
			case "3":
				myPage.cart();
				break;
			case "5":
				user.out();
				break;
				
		}
	}

	public void qna(SocketClient sender, String listNo) {
//		QnA qna = new QnA();
		JSONObject root= new JSONObject();
		String json="";
		switch(listNo) {
			case "0":
				root.put("check", "menuNo");
				root.put("data", "qna");
				root.put("message", "QnA");
				json = root.toString();
				sender.send(json);
				break;
		}
	}

	public void user(SocketClient sender, String listNo, JSONObject jsonUpdata) {
		User user  = new User();
		JSONObject root= new JSONObject();
		String json="";
		switch(listNo) {
			case "0":
				root.put("check", "menuNo");
				root.put("data", "user");
				json = root.toString();
				sender.send(json);
				break;
			case "1":
				user.login(sender, jsonUpdata);
				break;
			case "2":
				user.logout(sender);
				break;
			case "3":
				user.in(sender,jsonUpdata);
				break;
		}
		
	}

	public void exit(SocketClient sender, String listNo) {
//		Exit exit = new Exit();
		JSONObject root= new JSONObject();
		String json="";
		switch(listNo) {
			case "0":
				root.put("check", "menuNo");
				root.put("data", "exit");
				json = root.toString();
				sender.send(json);
				break;
		}
		
	}

	public void send(String json) {
		try {
			dos.writeUTF(json);
			dos.flush();
		} catch (IOException e) {
			System.out.println("socket send error");
		}
	}
	
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
		}
	}

	public DTOBook getBookD() {
		return bookD;
	}

	public void setBookD(DTOBook bookD) {
		this.bookD = bookD;
	}
	
	
}
