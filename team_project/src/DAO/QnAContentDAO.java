package DAO;

import java.util.Date;

import lombok.Data;

@Data
public class QnAContentDAO {
   private int contentId ;
   private String contentManagerId ;
   private String managerAnswer ;
   private String userReplier ;
   private String userSecq;
   private Date contentDate;
   private int qnaId;
}