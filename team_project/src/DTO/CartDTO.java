package project.DTO;

import lombok.Data;

@Data
public class CartDTO {
	private String bookName;
	private int bookAmount;
	private int sumPrice;
}
