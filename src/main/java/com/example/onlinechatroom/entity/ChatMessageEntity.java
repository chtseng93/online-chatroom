package com.example.onlinechatroom.entity;

import java.util.Date;

import lombok.Data;


@Data
public class ChatMessageEntity {
	
	private String toId;
	private String toName;
	private String fromId;
	private String fromName;
	private String message;
	private String status;
	private String to;
	private String from;
	private String gender;
//	private Date time;
	
	
	


}
