package project.Service;

import java.util.ArrayList;
import java.util.List;

import project.DAO.BestSellerDAO;
import project.DTO.BookDTO;

public class BestSellerService {

	// 전체 베스트 셀러

	public List<BookDTO> showAllBestSeller(int best) {
		BestSellerDAO bestSellerDAO = new BestSellerDAO();
		List<BookDTO> AllbookList = new ArrayList<>();
		AllbookList = bestSellerDAO.selectAllBestSeller(best);
		return AllbookList;
	}

	// 연령별 베스트 셀러
	public List<BookDTO> showAgeBestSeller(int best) {
		BestSellerDAO bestSellerDAO = new BestSellerDAO();
		List<BookDTO> ageList = new ArrayList<>();
		ageList = bestSellerDAO.selectAgeBestSeller(best);
		return ageList;
	}


	// 성별 베스트 셀러

	public List<BookDTO> showGenderBestSeller(int bookId) {
		BestSellerDAO bestSellerDAO = new BestSellerDAO();
		List<BookDTO> genderList = new ArrayList<>();
		genderList = bestSellerDAO.selectGenderBestSeller(bookId);

		return genderList;
	}

}
