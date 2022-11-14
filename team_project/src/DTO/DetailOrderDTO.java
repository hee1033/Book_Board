package project.DTO;

import java.sql.Date;

import lombok.Data;

@Data
public class DetailOrderDTO {
	private String bookName;
	private int orderPrice;
	private int orderAmount;
	private Date orderDate;
	private String orderAddress;
	private String person;
	private String userId;
}