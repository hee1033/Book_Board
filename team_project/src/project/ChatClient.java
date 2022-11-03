package project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import View.BestSellerView;
import View.CategoryView;
import View.MainBoardView;
import View.ManagerView;
import View.MyPageView;
import View.UserView;



public class ChatClient {
	
	static Scanner sc= new Scanner(System.in);
	public JSONArray jsonArray;
	public JSONObject jsonO;
	//필드
	public Socket socket;
	public DataInputStream dis;
	public DataOutputStream dos;
	public String chatName;//socket name
	public String choice;//mainboard 
	public String data;//메인 결과
	public boolean flag;//로그인
	public boolean mflag;//관리자 로그인
	
	
	public void connect() throws IOException{
		socket=new Socket("localhost",50041);
		dis=new DataInputStream(socket.getInputStream());
		dos=new DataOutputStream(socket.getOutputStream());
		System.out.println("[client] complete server connect");
	}
	
	public void receive() {
		try {
			String message="";
			String json = dis.readUTF();
			JSONObject root = new JSONObject(json);
			String controller = root.getString("controller");
			switch (controller) {
			case "complete":
				this.choice = root.getString("choice");
				flag=root.getBoolean("flag");
				message = root.getString("message");
				System.out.println(message);
				break;
			case "manager":
				this.choice = root.getString("choice");
				mflag=root.getBoolean("mflag");
				message = root.getString("message");
				System.out.println(message);
				break;
			case "menuNo":
				data = root.getString("data");
				break;
			case "show":
				jsonArray = root.getJSONArray("data");
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
	
	public void exit() throws IOException{
		System.out.println("BYE~");
		socket.close();
	}
	
	public static void main(String[] args) {
		try {
			ChatClient chatClient = new ChatClient();
			chatClient.connect();
			System.out.print("입장 id : ");
			chatClient.chatName=sc.nextLine();
			//chatName을 비회원 DB생성 후 중복은 안되게 해서 들어올수 있게 만들어야 한다....
			JSONObject jsonObject= new JSONObject();
			jsonObject.put("controller", "incoming");
			jsonObject.put("Name", chatClient.chatName);
			jsonObject.put("choice", "first");
			String json=jsonObject.toString();
			//SocketClient .send로 json변수를 보낸다.
			chatClient.send(json);
			chatClient.receive();
			
			while(true) {
				//가장 마지막의 choice를 이용해 다시 main으로 돌아오기 - "first" 주기
				if(chatClient.choice.equals("first")) {
					MainBoardView.seeMainBoard();
					jsonObject= new JSONObject();
					jsonObject.put("controller", "mainBoard");
					jsonObject.put("choice", "x");
					jsonObject.put("flag", chatClient.flag);
					json=jsonObject.toString();
					chatClient.send(json);
					chatClient.receive();
				}
				else {
					//menuNo에 값이 없을 경우
					System.out.print("MenuNo = ");
					String menuNo=sc.nextLine();
					if(menuNo.equals("8")) {
						break;
					}else {
						JSONObject updata = new JSONObject();
						jsonObject= new JSONObject();
						jsonObject.put("controller", "menuNo");
						jsonObject.put("menuNo", menuNo);
						jsonObject.put("listNo", "0");
						jsonObject.put("updata", updata);
						json = jsonObject.toString();
						chatClient.send(json);
						chatClient.receive();
						switch(chatClient.data) {
						case "category":
							CategoryView.category(chatClient);
							break;
						case "best":
							BestSellerView.seeBestSeller1(chatClient);
							break;
						case "event":
							break;
						case "search":
							break;
						case "mypage":
							if(chatClient.flag==false) {
								System.out.println("로그인이 필요합니다.");
								UserView.seeBoard(chatClient);
							}else {
								MyPageView.seeBoard(chatClient);
							}
							break;
						case "qna":
							break;
						case "user":
							if(chatClient.flag==false) {
								UserView.seeBoard(chatClient);	
							}else {
								System.out.println("\n**로그인이 되어있습니다**\n다시 MenuNo를 선택해주세요.\n");
							}
							break;
						case "manager":
							if(chatClient.mflag==false) {
								ManagerView.writeManagerLogin(chatClient);
							}else {
								ManagerView.seeBoard(chatClient);
							}
							break;
						}
					}
				}
			}
			sc.close();
			chatClient.exit();
		}catch(Exception e) {
			System.out.println("[client] 서버 연결 안됨");
			e.printStackTrace();
		}
		System.out.println("[client] 종료.");
	}



	


	
}
