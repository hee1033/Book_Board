package day02;

import lombok.Data;

@Data
public class BestSeller {
	private int bestSellerNo;
	private int bookId;
	private int bookAmount;
	private int age1020;
	private int age2040;
	private int ageOver40;
	private String sexMan;
	private String sexWoman;
}
