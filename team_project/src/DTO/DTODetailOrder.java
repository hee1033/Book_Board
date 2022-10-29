package DTO;

import lombok.Data;

@Data
public class DTODetailOrder {
	private int detailOrderId;
	private int ordersId;
	private int bookId;
	private int bookAmount;
}
