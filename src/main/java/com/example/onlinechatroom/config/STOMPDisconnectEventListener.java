package com.example.onlinechatroom.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.example.onlinechatroom.service.ChatMessageService;


@Component
public class STOMPDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent>{

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ChatMessageService chatMessageService;

	@Autowired
	private WebSocketSessions sessions;

	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
		String sessionId = accessor.getSessionId();
		sessions.removeSessionId(sessionId);
		chatMessageService.sendToAllContectList(sessions.getUidUsers());
		System.out.println("removeSessionId sessionId:{"+sessionId+"}");
	}

}
