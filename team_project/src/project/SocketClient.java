package project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.json.JSONObject;

import Controller.BestSellerController;
import Controller.CategoryController;
import Controller.EventController;
import Controller.ExitController;
import Controller.ManagerController;
import Controller.MyPageController;
import Controller.QnAController;
import Controller.SearchController;
import Controller.UserController;
import DTO.BestSellerDTO;
import DTO.BookDTO;
import DTO.CategoryDTO;
import DTO.ManagerDTO;
import DTO.UserDTO;


public class SocketClient {

	//필드
	ChatServer chatServer;
	Socket socket;
	String chatName;
	String clientIp;
	DataInputStream dis; //byte
	DataOutputStream dos; //기본타입->byte
	public ManagerDTO managerDTO;
	public UserDTO userDTO;
	public CategoryDTO categoryDTO;
	public BestSellerDTO bestSellerDTO;
	public BookDTO bookDTO;
	
	public String userID;
	public String choice;
	public boolean flag;//유저
	public boolean mflag;//관리자
	public ManagerDTO managerD=new ManagerDTO();
//	List<Integer> bookList;
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
					String controller = jsonObject.getString("controller");
					
					switch(controller) {
						case "incoming":
							this.chatName=jsonObject.getString("Name");
							this.choice=jsonObject.getString("choice");
							chatServer.income(this);
							chatServer.addSocketClient(this);
							break;
						case "mainBoard":
							this.choice=jsonObject.getString("choice");
							this.flag=jsonObject.getBoolean("flag");
							JSONObject root=new JSONObject();
							root.put("controller", "complete");
							root.put("choice",this.choice);
							root.put("flag", this.flag);
							root.put("message", "\n**MainBoard**");
							String json=root.toString();
							send(json);
							break;
							
						case "menuNo":
							String menuNo=jsonObject.getString("menuNo");
							String listNo=jsonObject.getString("listNo");
							JSONObject jsonUpdata=new JSONObject();
							jsonUpdata=jsonObject.getJSONObject("updata");
							switch(menuNo) {
								case "1":
									CategoryController categoryController = new CategoryController();
									categoryController.category(this,listNo,jsonUpdata);		
									break;
								case "2":
									BestSellerController bestSellerController= new BestSellerController();
									bestSellerController.bestSeller(this,listNo,jsonUpdata);
									break;
								case "3":
									EventController eventController= new EventController(); 
									eventController.event(this,listNo);
								case "4":
									SearchController searchController= new SearchController(); 
									searchController.search(this,listNo);
									break;
								case "5":
									MyPageController myPageController = new MyPageController();
									myPageController.mypage(this, listNo, jsonUpdata);
									break;
								case "6":
									QnAController qnaController= new QnAController(); 
									qnaController.qna(this,listNo,jsonUpdata);
									break;
								case "7":
									UserController userController=new UserController();
									userController.user(this,listNo,jsonUpdata);
									break;
								case "8":
									ExitController exitController =new ExitController(); 
									exitController.exit(this,listNo);
									break;
								case "9":
									ManagerController managerController= new ManagerController(); 
									managerController.manager(this,listNo,jsonUpdata);
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

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	
}