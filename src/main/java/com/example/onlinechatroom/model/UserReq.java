package com.example.onlinechatroom.model;


import lombok.Data;

@Data
public class UserReq {
	   private String gender;
	   private String userName;
	   private String passWord;
	@Override
	public String toString() {
		return "UserReq [gender=" + gender + ", userName=" + userName + ", passWord=" + passWord + "]";
	}
	   
	   
	  
}
