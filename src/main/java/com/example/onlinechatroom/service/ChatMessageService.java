package com.example.onlinechatroom.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.onlinechatroom.config.ContactEntity;
import com.example.onlinechatroom.config.WebSocketSessions;
import com.example.onlinechatroom.entity.ChatMessageEntity;


@Service
public class ChatMessageService {
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	@Autowired
    private WebSocketSessions sessions;
	
	public void sendToUser(ChatMessageEntity messageEntity) {
		
		sessions.getAllSessionIds().forEach(name -> {
		    System.out.println(name);
		});
		System.out.println("=========>"+"/user/subscribe/" + messageEntity.getToId());
		System.out.println("=========>"+"/user/subscribe/" + messageEntity.getFromId());
        simpMessagingTemplate.convertAndSend("/user/subscribe/" + messageEntity.getToId(), messageEntity);
        simpMessagingTemplate.convertAndSend("/user/subscribe/" + messageEntity.getFromId(), messageEntity);
    }
	
	public void sendToAllContectList(List<ContactEntity>users) {
		simpMessagingTemplate.convertAndSend("/topic/crcontact",users);
	}
	
	public List<ContactEntity> getAllContact() {
		return sessions.getUidUsers();
	}
	
	

}
