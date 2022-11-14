package project.Service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import project.ConnectionProvider;
import project.DAO.EventDAO;
import project.DTO.EventDTO;
import project.DTO.Pager;

public class EventService {
	Connection conn = ConnectionProvider.getConnection();

	public List<EventDTO> ServiceEvent(Pager pager) {
		EventDAO eventDAO = new EventDAO();
		List<EventDTO> eventList = new ArrayList<>();
		eventList = eventDAO.SelectAllEvent(pager);
		return eventList;
	}
	
	public int getTotalRows(){
		EventDAO eventDAO = new EventDAO();
		int totalRows = eventDAO.selectTotalRows();
		System.out.println(totalRows);
		return totalRows;
	}
}