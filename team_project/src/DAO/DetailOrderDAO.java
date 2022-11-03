package DAO;

import lombok.Data;

@Data
public class DetailOrderDAO {
	private int detailOrderId;
	private int ordersId;
	private int bookId;
	private int bookAmount;
}
