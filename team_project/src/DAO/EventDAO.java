package project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import project.ConnectionProvider;
import project.DTO.EventDTO;
import project.DTO.Pager;

public class EventDAO {
	Scanner sc = new Scanner(System.in);
	Connection conn = ConnectionProvider.getConnection();

	public List<EventDTO> SelectAllEvent(Pager pager) {
		List<EventDTO> eventlist = new ArrayList<>();
		try {
			String sql = "SELECT RNUM, EVENT_ID, EVENT_NAME, EVENT_START_DATE , EVENT_END_DATE "
					+ "FROM( SELECT ROWNUM AS RNUM, EVENT_ID, EVENT_NAME, EVENT_START_DATE , EVENT_END_DATE "
					+ "FROM ( SELECT E.EVENT_ID, EVENT_NAME, EVENT_START_DATE , EVENT_END_DATE "
					+ "FROM EVENT E, GOODS G "
					+ "WHERE E.EVENT_ID=G.EVENT_ID AND EVENT_START_DATE<=SYSDATE AND EVENT_END_DATE>=SYSDATE AND GOODS_NUM>0 ORDER BY E.EVENT_ID) WHERE ROWNUM<=?) "
					+ "WHERE RNUM>=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pager.getEndRowNo());
			pstmt.setInt(2, pager.getStartRowNo());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				EventDTO eventDTO = new EventDTO();
				eventDTO.setEventId(rs.getInt("EVENT_ID"));
				eventDTO.setEventName(rs.getString("EVENT_NAME"));
				eventDTO.setEventStartDate(rs.getDate("EVENT_START_DATE"));
				eventDTO.setEventEndDate(rs.getDate("EVENT_END_DATE"));
				eventlist.add(eventDTO);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EventList error");
		}
		return eventlist;
	}
	
	//이벤트 업데이트
	public boolean insertEvent(EventDTO eventDto) {
		boolean result=false;
		System.out.println(eventDto.toString());
		try {
			String sql= "insert into event values(?,?,to_date(?,'yyyy/mm/dd'),to_date(?,'yyyy/mm/dd'),?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, eventDto.getEventId());
			pstmt.setString(2, eventDto.getEventName());
			pstmt.setString(3,eventDto.getEventStart());
			pstmt.setString(4, eventDto.getEventEnd());
			pstmt.setInt(5, eventDto.getBookId());
			pstmt.setString(6, eventDto.getEventContent());
			pstmt.executeUpdate();
			pstmt.close();
			result = true;
		}catch(Exception e) {
			e.printStackTrace();
			result=false;
		}
		
		return result;
	}
	
	public boolean deleteEvent(int eventId ) {
		   boolean result=false;
			try {
				String sql="delete from event where event_id = ?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, eventId);
				pstmt.executeUpdate();
				pstmt.close();
				result=true;
			}catch(Exception e) {
				result= false;
				e.printStackTrace();
			}
			return result;
	   }
	
	public int selectTotalRows() {
		int totalRows = 0;
		try {
			String sql = "select count(*) from event E, GOODS G WHERE E.EVENT_ID=G.EVENT_ID AND EVENT_START_DATE<=SYSDATE AND EVENT_END_DATE>=SYSDATE AND GOODS_NUM>0";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				totalRows = rs.getInt("count(*)");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return totalRows;
	}

}
