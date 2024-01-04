package com.example.onlinechatroom.config;

import lombok.Data;

@Data
public class ContactEntity {
	private String status;
    private String name;
    private String uid;
    private String gender;
    private String sessionId;
    
    public ContactEntity() {
    	
    };
	public ContactEntity(String uid, String name,String gender) {
		this.name = name;
		this.uid = uid;
		this.gender=gender;
	}
    
   

}
