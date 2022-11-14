package project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import project.ConnectionProvider;
import project.DTO.QnAContentDTO;

public class QnAContentDAO {
   
	Connection conn = ConnectionProvider.getConnection();
	//qna 질문 + 답변 테이블 조회
	public List<QnAContentDTO> showQuestionDetail(int detailNum){
		List<QnAContentDTO> list = new ArrayList<>();
		try {
			String sql = "SELECT CONTENT_ID, MANAGER_ID, MANAGER_ANSWER, USER_REPLIER, USER_SECQ, CONTENT_DATE, QNA_ID "
					+ "FROM CONTENT "
					+ "WHERE QNA_ID =? ";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, detailNum);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				QnAContentDTO aContentDTO = new QnAContentDTO();
				aContentDTO.setContentId(rs.getInt("CONTENT_ID"));
				aContentDTO.setManagerId(rs.getString("MANAGER_ID"));
				aContentDTO.setManagerAnswer(rs.getString("MANAGER_ANSWER"));
				aContentDTO.setUserReplier(rs.getString("USER_REPLIER"));
				aContentDTO.setUserSecq(rs.getString("USER_SECQ"));
				aContentDTO.setContentDate(rs.getDate("CONTENT_DATE"));
				aContentDTO.setQnaId(rs.getInt("QNA_ID"));
				list.add(aContentDTO);
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean replyNotAnswer(QnAContentDTO qnaContentDTO, int detailNum) {
		boolean result = false;
		Connection conn = ConnectionProvider.getConnection();

		try {
			String sql = "INSERT INTO CONTENT (MANAGER_ANSWER, CONTENT_DATE, MANAGER_ID, QNA_ID, CONTENT_ID) "
					+ "VALUES(? , SYSDATE, ?, ?, seq_content_id.nextval) ";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qnaContentDTO.getManagerAnswer());
			pstmt.setString(2, qnaContentDTO.getManagerId());
			pstmt.setInt(3, detailNum);
			pstmt.executeUpdate();
			System.out.println("qna 답변 완료");
			
			pstmt.close();

			result = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
}