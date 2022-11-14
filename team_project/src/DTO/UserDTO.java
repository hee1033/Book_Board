package project.DTO;

import java.util.Date;

import lombok.Data;

@Data
public class UserDTO {
   private String userId;
   private String userPassword;
   private String userName;
   private String userEmail;
   private String userAddress;
   private String userTel;
   private Date userIndate;
   private int userAge;
   private String userSex;
}