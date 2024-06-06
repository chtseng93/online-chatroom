package com.example.onlinechatroom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



import com.example.onlinechatroom.config.ContactEntity;
import com.example.onlinechatroom.config.WebSocketSessions;
import com.example.onlinechatroom.entity.ChatMessageEntity;
import com.example.onlinechatroom.model.UserReq;
import com.example.onlinechatroom.service.ChatMessageService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class ChatMessageController {
	
	@Autowired
	private ChatMessageService chatMessageService;
	
	@Autowired
    private WebSocketSessions sessions;
	
		
	@MessageMapping("/message")
	@SendTo("/topic/public")
	public ChatMessageEntity message(ChatMessageEntity sendMessage) throws Exception {
	    System.out.println(sendMessage);
	    sessions.getAllUsers().forEach(name -> {
		    log.info("message: "+name);
		});
	    ChatMessageEntity returnMessageEntity = new ChatMessageEntity(); 
	    returnMessageEntity.setFromName(sendMessage.getFromName());
	    returnMessageEntity.setGender(sendMessage.getGender());
	    returnMessageEntity.setFromId(sendMessage.getFromId());
	    returnMessageEntity.setToName("all");
	    returnMessageEntity.setMessage(sendMessage.getMessage());
	    return returnMessageEntity;
	}
	

	@MessageMapping("/announce")
	@SendTo("/topic/alert")
	public ChatMessageEntity announce(ChatMessageEntity sendMessageEntity) throws Exception {
		log.info("==============>Before.../announce");
		log.info(sendMessageEntity!=null?sendMessageEntity.toString():"");
	    ChatMessageEntity returnMessageEntity = new ChatMessageEntity(); 
	    returnMessageEntity.setFromName(sendMessageEntity.getFromName());
	    returnMessageEntity.setFromId(sendMessageEntity.getFromName());
	    returnMessageEntity.setToName("all");
	    returnMessageEntity.setMessage(sendMessageEntity.getMessage());
	    return returnMessageEntity;
	}

	@MessageMapping("/connect")
	@SendTo("/topic/crcontact")
	public List<ContactEntity> connectWs(ChatMessageEntity messageEntity) throws Exception {
		log.info("==============>/topic/crcontact");
		return chatMessageService.getAllContact();
	}
	
	@MessageMapping("/disconnect")
	@SendTo("/topic/crcontact")
	public List<ContactEntity> disconnectWs(ChatMessageEntity messageEntity) throws Exception {
		log.info("==============>/topic/crcontact");
		return chatMessageService.getAllContact();
	}

	// 這裡 @MessageMapping 可以當成 @RequestMapping,
    // 當有訊息 (sendMsg 方法中的 messageEntity 参数即為客服端发送的訊息object)
    // 發送到 /sendMsg 時會在這進行處理
    @MessageMapping("/sendMsg")
    public ChatMessageEntity sendMsg(ChatMessageEntity sendMessageEntity) {
    	log.info("==========>sendMsg");
    	log.info(sendMessageEntity!=null?sendMessageEntity.toString():"");
    	chatMessageService.sendToUser(sendMessageEntity);
    	ChatMessageEntity returnMessageEntity = new ChatMessageEntity(); 
	    returnMessageEntity.setFromName(sendMessageEntity.getFromName());
	    returnMessageEntity.setFromId(sendMessageEntity.getFromId());
	    returnMessageEntity.setToId(sendMessageEntity.getToId());
	    returnMessageEntity.setToName(sendMessageEntity.getToName());
	    returnMessageEntity.setGender(sendMessageEntity.getGender());
	    returnMessageEntity.setMessage(sendMessageEntity.getMessage());
    	return returnMessageEntity;
    }
    
	@GetMapping("/chat/entry")
	public String websocketEntry(Model model) {
		return "chatentry";
	}
	
	@PostMapping("/chat/index")
	public String websocketIndex(Model model,@ModelAttribute("user") UserReq userReq) {
		log.info(userReq!=null?userReq.toString():"");
		model.addAttribute("userGender", userReq.getGender()!=null?userReq.getGender():"male");
		model.addAttribute("userName", userReq.getUserName());
		return "chatroom";
	}
    


}
