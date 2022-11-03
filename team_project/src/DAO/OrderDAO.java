package DAO;

import java.util.Date;

import lombok.Data;
@Data

public class OrderDAO {
   private int ordersId;
   private String usersId;
   private Date ordersDate;
   private String ordersAddress;
   private String ordersReceiver;
   private String bookName;
   private int bookAmount;
}
