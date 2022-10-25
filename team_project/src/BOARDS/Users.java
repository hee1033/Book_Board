package BOARDS;

import java.util.Date;

import lombok.Data;

@Data
public class Users {
	private String users_id;
	private String users_password;
	private String users_name;
	private String users_email;
	private String users_address;
	private String users_tel;
	private Date users_indate;
	private int users_age;
	private String users_sex;
}
