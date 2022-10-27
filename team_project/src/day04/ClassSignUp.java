package day04;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import DTO.Users;

public class ClassSignUp {
	static Scanner scan = new Scanner(System.in);
	public static void signUp() {
		Users user = new Users();
		System.out.print("(필수)아이디를 입력하세요\nID :");
		user.setUsers_id(scan.nextLine());
		System.out.print("(필수)비밀번호를 입력하세요\nPASSWORD : ");
		user.setUsers_password(scan.nextLine());
		System.out.print("(필수)이름을 입력하세요 : ");
		user.setUsers_name(scan.nextLine());
		System.out.print("(필수)이메일을 입력하세요 : ");
		user.setUsers_email(scan.nextLine());
		System.out.print("(필수)주소를 입력하세요 : ");
		user.setUsers_address(scan.nextLine());
		System.out.print("(필수)전화번호를 입력하세요 : ");
		user.setUsers_tel(scan.nextLine());
		System.out.print("나이를 입력하세요 : ");
		user.setUsers_age(Integer.parseInt(scan.nextLine()));
		System.out.print("성별을 입력하세요(man or woman) : ");
		user.setUsers_sex(scan.nextLine());

		String sql = ""
				+ "INSERT INTO users (users_id, users_password, users_name, users_email, users_address, users_tel, users_indate, users_age, users_sex) "
				+ "VALUES (?, ?, ?, ?, ?, ?, SYSDATE, ?, ?)";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsers_id());
			pstmt.setString(2, user.getUsers_password());
			pstmt.setString(3, user.getUsers_name());
			pstmt.setString(4, user.getUsers_email());
			pstmt.setString(5, user.getUsers_address());
			pstmt.setString(6, user.getUsers_tel());
			pstmt.setInt(7, user.getUsers_age());
			pstmt.setString(8, user.getUsers_sex());
			pstmt.executeUpdate();
			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
