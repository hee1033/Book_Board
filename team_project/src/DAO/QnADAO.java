package project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import project.ConnectionProvider;
import project.DTO.Pager;
import project.DTO.QnADTO;

public class QnADAO {
	Connection conn = ConnectionProvider.getConnection();

	public List<QnADTO> showKindsOfQuestion(int qnaType, Pager pager) {
		Connection conn = ConnectionProvider.getConnection();
		List<QnADTO> list = new ArrayList<>();
		try {
			String sql = "SELECT QNA_ID, QNA_TITLE, QNA_CONTENT "
					+ "FROM (SELECT ROWNUM AS RNUM, QNA_ID, QNA_TITLE, QNA_CONTENT "
					+ "FROM (SELECT QNA_ID, QNA_TITLE, QNA_CONTENT FROM QNA "
					+ "WHERE QNA_TYPE = ? ORDER BY QNA_ID) WHERE ROWNUM <= ?) " + "WHERE RNUM >= ?";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnaType);
			pstmt.setInt(2, pager.getEndRowNo());
			pstmt.setInt(3, pager.getStartRowNo());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				QnADTO qnadto = new QnADTO();
				qnadto.setQnaId(rs.getInt("QNA_ID"));
				qnadto.setQnaTitle(rs.getString("QNA_TITLE"));
				qnadto.setQnaContent(rs.getString("QNA_CONTENT"));
				list.add(qnadto);
			}
			rs.close();
			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return list;
	}

	public boolean registerQuestion(QnADTO qnadto) {
		Connection conn = ConnectionProvider.getConnection();
		boolean result = false;
		try {
			String sql = "INSERT INTO QNA (QNA_ID, QNA_TITLE, QNA_CONTENT, QNA_DATE, BOOK_ID, USERS_ID, QNA_TYPE) "
					+ "VALUES(SEQ_QNA_ID.NEXTVAL, ?, ?, SYSDATE, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, qnadto.getQnaTitle());
			pstmt.setString(2, qnadto.getQnaContent());
			pstmt.setInt(3, qnadto.getBookId());
			pstmt.setString(4, qnadto.getUserId());
			pstmt.setInt(5, qnadto.getQnaType());

			int rows = pstmt.executeUpdate();
			System.out.println("저장된 행 수: " + rows);
			System.out.println("[서버] DB에 qna글 전달 완료");

			pstmt.close();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return result;
	}

	public int getTotalRows(int qnaType) {
		Connection conn = ConnectionProvider.getConnection();
		int totalRows = 0;
		try {
			String sql = "select count(*) from qna where qna_type=? ";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnaType);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				totalRows = rs.getInt("count(*)");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return totalRows;

	}

	public boolean modifyQuestion(QnADTO qnaDTO) {
		Connection conn = ConnectionProvider.getConnection();
		boolean result = false;

		try {
			System.out.println("dao왔니?");
			String sql = "UPDATE QNA SET QNA_TITLE= ? , QNA_CONTENT = ?, QNA_DATE = TO_DATE(SYSDATE, 'yyyy-mm-dd hh24:mi:ss')"
					+ "WHERE QNA_ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qnaDTO.getQnaTitle());
			pstmt.setString(2, qnaDTO.getQnaContent());
			pstmt.setInt(3, qnaDTO.getQnaId());

			pstmt.executeUpdate();

			pstmt.close();
			
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return result;
	}

	public QnADTO selectQuesion(int qna_id) {
		Connection conn = ConnectionProvider.getConnection();
		QnADTO qnaDTO = null;
		try {
			String sql = "SELECT QNA_ID, QNA_TITLE, QNA_CONTENT, QNA_DATE, BOOK_ID, USERS_ID, QNA_TYPE FROM QNA WHERE QNA_ID =?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qna_id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				qnaDTO = new QnADTO();
				qnaDTO.setQnaId(rs.getInt("QNA_ID"));
				qnaDTO.setQnaTitle(rs.getString("QNA_TITLE"));
				qnaDTO.setQnaContent(rs.getString("QNA_CONTENT"));
				qnaDTO.setQnaDate(rs.getDate("QNA_DATE"));
				qnaDTO.setBookId(rs.getInt("BOOK_ID"));
				qnaDTO.setUserId(rs.getString("USERS_ID"));
				qnaDTO.setQnaType(rs.getInt("QNA_TYPE"));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return qnaDTO;
	}

	public boolean deleteQuestion(int qna_id) {
		Connection conn = ConnectionProvider.getConnection();
		boolean result = false;
		try {
			String sql = "DELETE FROM QNA WHERE QNA_ID=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qna_id);
			pstmt.executeUpdate();
			pstmt.close();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return result;
	}

	// 미답변인 항목 조회
	public List<QnADTO> showNotAnswer(int qna_type) {
		Connection conn = ConnectionProvider.getConnection();
		List<QnADTO> list = new ArrayList<>();
		try {
			String sql = "SELECT Q.QNA_ID, QNA_TITLE, QNA_CONTENT, QNA_DATE, BOOK_ID, USERS_ID "
					+ "FROM QNA Q, CONTENT C "
					+ "WHERE Q.QNA_ID = C.QNA_ID(+) AND  MANAGER_ANSWER IS NULL and qna_type = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qna_type);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				QnADTO qnadto = new QnADTO();
				qnadto.setQnaId(rs.getInt("QNA_ID"));
				qnadto.setQnaTitle(rs.getString("QNA_TITLE"));
				qnadto.setQnaContent(rs.getString("QNA_CONTENT"));
				System.out.println(qnadto);
				list.add(qnadto);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return list;
	}

	public List<QnADTO> showNotAnswerDetail(int detailNum) {
		Connection conn = ConnectionProvider.getConnection();
		List<QnADTO> list = new ArrayList<>();
		try {
			String sql = "SELECT Q.QNA_ID, QNA_TITLE, QNA_CONTENT, QNA_DATE, BOOK_ID, USERS_ID "
					+ "FROM QNA Q, CONTENT C "
					+ "WHERE Q.QNA_ID = C.QNA_ID(+) AND  MANAGER_ANSWER IS NULL and Q.QNA_ID = ?";
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, detailNum);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				QnADTO qnadto = new QnADTO();
				qnadto.setQnaId(rs.getInt("QNA_ID"));
				qnadto.setQnaTitle(rs.getString("QNA_TITLE"));
				qnadto.setQnaContent(rs.getString("QNA_CONTENT"));
				System.out.println(qnadto);
				list.add(qnadto);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return list;
	}
}