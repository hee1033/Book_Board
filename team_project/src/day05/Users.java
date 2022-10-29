package day05;


import java.util.Date;

import lombok.Data;

@Data
public class Users {
   private String usersId;
   private String usersPassword;
   private String usersName;
   private String usersEmail;
   private String usersAddress;
   private String usersTel;
   private Date usersIndate;
   private int usersAge;
   private String usersSex;
}