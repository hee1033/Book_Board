package DAO;

import java.util.Date;

import lombok.Data;

@Data
public class QnADAO {
   private int qnaNo ;
   private String qnaTitle ;
   private String qnaContent ;
   private Date qnaDate;
   private int bookId ;
   private String userId;
   private int qnaType;
   
}