package DTO;


import java.util.Date;

import lombok.Data;

@Data
public class BookDTO {
   private int bookId;
   private String bookName;
   private int bookPrice;
   private String bookWriter;
   private int bookSellingPrice;
   private int bookStock;
   private String bookContent;
   private Date bookDate;
   private int companyID;
   private String companyName;
   private int categoryId;
   private String categoryName;
   
}