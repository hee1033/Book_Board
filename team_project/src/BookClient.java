package project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import project.View.BestSellerView;
import project.View.CategoryView;
import project.View.EventView;
import project.View.MainBoardView;
import project.View.ManagerView;
import project.View.MyPageView;
import project.View.QnAView;
import project.View.SearchView;
import project.View.UserView;

public class BookClient {
	
	static Scanner sc= new Scanner(System.in);
	public JSONArray jsonArray;
//	public JSONObject jsonO;
	//필드
	public Socket socket;
	public DataInputStream dis;
	public DataOutputStream dos;
	public String bookName;//socket name
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
			BookClient bookClient = new BookClient();
			bookClient.connect();
			System.out.print("입장 id : ");
			bookClient.bookName=sc.nextLine();
			//chatName을 비회원 DB생성 후 중복은 안되게 해서 들어올수 있게 만들어야 한다....
			JSONObject jsonObject= new JSONObject();
			jsonObject.put("controller", "incoming");
			jsonObject.put("Name", bookClient.bookName);
			jsonObject.put("choice", "first");
			String json=jsonObject.toString();
			//SocketClient .send로 json변수를 보낸다.
			bookClient.send(json);
			bookClient.receive();
			
			while(true) {
				//가장 마지막의 choice를 이용해 다시 main으로 돌아오기 - "first" 주기
				if(bookClient.choice.equals("first")) {
					MainBoardView.seeMainBoard(bookClient);
					jsonObject= new JSONObject();
					jsonObject.put("controller", "mainBoard");
					jsonObject.put("choice", "x");
					jsonObject.put("flag", bookClient.flag);
					json=jsonObject.toString();
					bookClient.send(json);
					bookClient.receive();
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
						json=jsonObject.toString();
						bookClient.send(json);
						bookClient.receive();
						switch(bookClient.data) {
						case "category":
							CategoryView.category(bookClient);
							break;
						case "best":
							BestSellerView.seeBestSeller(bookClient);
							break;
						case "event":
							EventView.seeBoard(bookClient);
							break;
						case "search":
							SearchView.seeSearch(bookClient);
							break;
						case "mypage":
							if(bookClient.flag==false) {
								System.out.println("로그인이 필요합니다.");
								UserView.seeBoard(bookClient);
							}else {
								MyPageView.seeBoard(bookClient);
							}
							break;
						case "qna":
							QnAView.seeBoard(bookClient);
							break;
						case "user":
							if(bookClient.flag==false) {
								UserView.seeBoard(bookClient);	
							}else {
								UserView.writeLogout(bookClient);
							}
							break;
						case "manager":
							if(bookClient.mflag==false) {
								ManagerView.writeManagerLogin(bookClient);
							}else {
								ManagerView.seeBoard(bookClient);
							}
							break;
						default :
							System.out.println("다른 번호를 선택해주세요");
							break;
						}
					}
				}
			}
			sc.close();
			bookClient.exit();
		}catch(Exception e) {
			System.out.println("[client] 서버 연결 안됨");
			e.printStackTrace();
		}
		System.out.println("[client] 종료.");
	}
	
}
