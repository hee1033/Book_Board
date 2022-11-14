package project.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import project.SocketClient;
import project.DAO.CartDAO;
import project.DAO.OrderDAO;
import project.DAO.UserDAO;
import project.DTO.CartDTO;
import project.DTO.DetailOrderDTO;
import project.DTO.OrderDTO;
import project.DTO.UserDTO;

public class MyPageService {
	
	Scanner sc = new Scanner(System.in);
		// 마이페이지 개인정보 수정
		public boolean showUserUpdate(SocketClient sender, Map<String,String> map) {
			
			UserDAO daoUser = new UserDAO();
			String password = map.get("password");
			String name=map.get("name");
			String email=map.get("email");
			String address=map.get("address");
			String tel=map.get("tel");
			int age=Integer.parseInt(map.get("age"));
			UserDAO userDao = new UserDAO();
			
			UserDTO userDto = new UserDTO();
			userDto = userDao.selectUserData(sender);
			if (password.equals("n")) {
				userDto.setUserPassword(userDto.getUserPassword());
			} else {
				userDto.setUserPassword(password);
			}
			if (name.equals("n")) {
				userDto.setUserName(userDto.getUserName());
			} else {
				userDto.setUserName(name);
			}
			if (email.equals("n")) {
				userDto.setUserEmail(userDto.getUserEmail());
			} else {
				userDto.setUserEmail(email);
			}
			if (address.equals("n")) {
				userDto.setUserAddress(userDto.getUserAddress());
			} else {
				userDto.setUserAddress(address);
			}
			if (tel.equals("n")) {
				userDto.setUserTel(userDto.getUserTel());
			} else {
				userDto.setUserTel(tel);
			}
			if (age == 0) {
				userDto.setUserAge(userDto.getUserAge());
			} else {
				userDto.setUserAge(age);
			}

			boolean result =daoUser.updateUserUpdate(userDto);
			return result;
		}
		
		//주문목록 
		public List<OrderDTO>showOrderList(SocketClient sender) {
			List<OrderDTO> list = new ArrayList<>();
			OrderDAO orderDao = new OrderDAO();
			list=orderDao.selectOrderList(sender);
			return list;
		}
		
		//주문목록 자세히
		public List<DetailOrderDTO> showDetailOrderList(SocketClient sender,int orderNum) {
			List<DetailOrderDTO> list = new ArrayList<>();
			OrderDAO orderDao = new OrderDAO();
			list=orderDao.selectDetailOrderList(sender,orderNum);
			return list;
		}
		
		//장바구니 목록
		public List<CartDTO> showCart(SocketClient sender) {
			List<CartDTO> list = new ArrayList<>();
			CartDAO cartDao = new CartDAO();
			list=cartDao.selectCart(sender);
			return list;
		}
		

}
