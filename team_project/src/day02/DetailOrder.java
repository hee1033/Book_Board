package day02;

import lombok.Data;

@Data
public class DetailOrder {
	private int detailOrderId;
	private int ordersId;
	private int bookId;
	private int bookAmount;
}
