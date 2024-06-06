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
	@Override
	public String toString() {
		return "ChatMessageEntity [toId=" + toId + ", toName=" + toName + ", fromId=" + fromId + ", fromName="
				+ fromName + ", message=" + message + ", status=" + status + ", to=" + to + ", from=" + from
				+ ", gender=" + gender + "]";
	}
	
	
	

	
	
	


}
