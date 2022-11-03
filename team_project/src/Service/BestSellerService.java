package Service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import DAO.BestSellerDAO;
import DTO.BookDTO;
import project.SocketClient;

public class BestSellerService {

	// 전체 베스트 셀러

	public List<BookDTO> showAllBestSeller(int best) {
		BestSellerDAO bestSellerDAO = new BestSellerDAO();
		List<BookDTO> AllbookList = new ArrayList<>();

		AllbookList = bestSellerDAO.selectAllBestSeller(best);

		return AllbookList;
	}

	// 연령별 베스트 셀러
	public List<BookDTO> showAge1020BestSeller(int best) {
		BestSellerDAO bestSellerDAO = new BestSellerDAO();
		List<BookDTO> age1020 = new ArrayList<>();

		age1020 = bestSellerDAO.selectAge1020BestSeller(best);

		return age1020;

	}
	public List<BookDTO> showAge2040BestSeller(int best) {
		BestSellerDAO bestSellerDAO = new BestSellerDAO();
		List<BookDTO> age2040 = new ArrayList<>();

		age2040 = bestSellerDAO.selectAge2040BestSeller(best);

		return age2040;

	}
	public List<BookDTO> showAge40OverBestSeller(int best) {
		BestSellerDAO bestSellerDAO = new BestSellerDAO();
		List<BookDTO> Over40 = new ArrayList<>();

		Over40 = bestSellerDAO.selectAgeOver40BestSeller(best);

		return Over40;

	}

//	// 성별 베스트 셀러
//	public List<BookDTO> showGenderBestSeller(int bookId) {
//		BestSellerDAO bestSellerDAO = new BestSellerDAO();
//		List<BookDTO> bookList = new ArrayList<>();
//
//		bookList = bestSellerDAO.selectGenderBestSeller(bookId);
//
//		return bookList;
//	}

}
