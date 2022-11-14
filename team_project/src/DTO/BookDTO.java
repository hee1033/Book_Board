package project.DTO;

import java.util.Date;

import lombok.Data;

@Data
public class BookDTO {
   private int bookId;
   private String bookName;
   private int bookPrice;
   private String bookWriter;
   private int bookStock;
   private String bookContent;
   private Date bookDate;
   private String companyName;
   private int categoryId;
   private int bookSellingPrice;
   private String categoryName;
   private int companyId;
   
   
}