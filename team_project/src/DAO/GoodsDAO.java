package DAO;

import java.util.Date;

import lombok.Data;

@Data
public class GoodsDAO {
	private String eventName;
	private int goodsId;
	private String goodsName;
	private String bookName;
	private Date eventStartDate;
	private Date eventEndDate;
	private String eventContent;
	private int goodsPrice;
	private String goodsContent;
}
