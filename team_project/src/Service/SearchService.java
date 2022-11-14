package project.Service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import project.ConnectionProvider;
import project.DAO.SearchDAO;
import project.DTO.Pager;
import project.DTO.SearchDTO;

public class SearchService {
	Connection conn = ConnectionProvider.getConnection();

	public List<SearchDTO> ServiceSearch(String bookName, Pager pager) {
		SearchDAO searchDAO = new SearchDAO();
		List<SearchDTO> searchList = new ArrayList<>();
		searchList = searchDAO.SelectAllSearch(bookName, pager);
		return searchList;
	}
	public int getTotalRows(String bookName){
		SearchDAO searchDAO = new SearchDAO();
		int totalRows = searchDAO.selectTotalRows(bookName);
		return totalRows;
	}
}
