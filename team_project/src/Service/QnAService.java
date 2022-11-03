package Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import project.ConnectionProvider;
import project.SocketClient;


public class QnAService {

	public void showBookQuestions(SocketClient socketClient) {
		Connection conn = ConnectionProvider.getConnection();
		try {
			String sql = "SELECT QNA_ID, QNA_TITLE, QNA_CONTENT, QNA_DATE, BOOK_ID, USERS_ID, QNA_TYPE FROM QNA where QNA_TYPE =? ";
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1);

			ResultSet rs = pstmt.executeQuery();

			JSONObject root = new JSONObject();
			JSONArray jsonArray = new JSONArray();

			while (rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("qnaId", rs.getInt("QNA_ID"));
				jo.put("qnaTitle", rs.getString("qna_title"));
				jsonArray.put(jo);
			}
			rs.close();
			pstmt.close();

			root.put("controller", "show");
			root.put("data", jsonArray);
			String json = root.toString();
			socketClient.send(json);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void showEventQuestions(SocketClient socketClient) {
		
	}

	public void showQuestionDetail(SocketClient socketClient, String detailNum) {
		try {
			Connection conn = ConnectionProvider.getConnection();
			String sql = "SELECT CONTENT_ID, MANAGER_ID, USER_REPLIER, USER_SECQ, CONTENT_DATE, QNA_ID, MANAGER_ANSWER FROM CONTENT where QNA_ID = ? ";
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(detailNum));
			
			ResultSet rs = pstmt.executeQuery();
			JSONObject root = new JSONObject();
			JSONArray jsonArray = new JSONArray();

			if(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("QNA_ID", rs.getInt("QNA_ID"));
				jo.put("MANAGER_ANSWER", rs.getString("MANAGER_ANSWER"));
				jsonArray.put(jo);

			}
			System.out.println(jsonArray);
			rs.close();
			pstmt.close();
			
			root.put("controller", "show");
			root.put("data", jsonArray);
			String json = root.toString();
			socketClient.send(json);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	


}
