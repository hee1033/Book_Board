package Controller;

import org.json.JSONObject;

import Service.QnAService;
import project.SocketClient;

public class QnAController {
	public void qna(SocketClient sender, String listNo, JSONObject jsonUpdata) {
	      QnAService qna = new QnAService();
	      JSONObject root= new JSONObject();
	      String json="";
	      
	      switch(listNo) {
	         case "0":
	            root.put("controller", "menuNo");
	            root.put("data", "qna");
	            root.put("message", "QnA"); //이거 뭘까?
	            json = root.toString();
	            sender.send(json);
	            break;
	            
	         case "1":
	            String detailNum = jsonUpdata.getString("updata");
	            
	            if (detailNum.equals("0")) {
	               qna.showBookQuestions(sender);
	            }else {
	               qna.showQuestionDetail(sender,detailNum);
	            }
	            break;
	            
	         case "2":
	            detailNum = jsonUpdata.getString("updata");
	            if (detailNum.equals("0")) {
	               qna.showEventQuestions(sender);
	            } else {
	               qna.showQuestionDetail(sender,detailNum);
	            }
	            break;
	         case "3":
	            break;
	      }
	   }
}
