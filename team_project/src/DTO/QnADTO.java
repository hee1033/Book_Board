package DTO;


import java.util.Date;

import lombok.Data;

@Data
public class QnADTO {
   private int qnaNo ;
   private String qnaTitle ;
   private String qnaContent ;
   private Date qnaDate;
   private int bookId ;
   private String userId;
   private int qnaType;
   
}