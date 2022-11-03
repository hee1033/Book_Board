package DTO;


import lombok.Data;

@Data
public class DetailOrderDTO {
	private int detailOrderId;
	private int ordersId;
	private int bookId;
	private int bookAmount;
}
