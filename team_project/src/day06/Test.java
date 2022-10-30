package day06;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.JSONObject;

public class Test {
	
	static void test(ChatClient chatClient){
		String json="";
		Connection conn = ConnectionProvider.getConnection();
		try {
			String sql = "select users_name from users where users_id='king'";
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(sql);
			ResultSet rs=pstmt.executeQuery();
			String books="";
			if(rs.next()) {
				books=rs.getString("users_name");
			}
			JSONObject jsonObject= new JSONObject();
			jsonObject= new JSONObject();
			jsonObject.put("command", "mainBoard");
			jsonObject.put("choice",books);
			json=jsonObject.toString();
			rs.close();
			pstmt.close();
			chatClient.send(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		return json;
	}
	
}
