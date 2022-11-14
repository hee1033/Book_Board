package project.DTO;
import lombok.Data;
@Data

public class OrderDTO {
	private int orderNum;
	private String bookName;
	private int ordersBookAmount;
	private int bookPrice;
}
