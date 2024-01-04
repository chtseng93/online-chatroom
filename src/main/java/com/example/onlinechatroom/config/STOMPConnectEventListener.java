package com.example.onlinechatroom.config;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

/**
 * 連線事件監聽
 */
@Component
public class STOMPConnectEventListener implements ApplicationListener<SessionConnectEvent>{
	
	private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WebSocketSessions sessions;

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String user =accessor!=null && accessor.getFirstNativeHeader("user")!=null?accessor.getFirstNativeHeader("user"):"";
        String uid =accessor!=null && accessor.getFirstNativeHeader("uid")!=null?accessor.getFirstNativeHeader("uid"):"";
        String gender =accessor!=null && accessor.getFirstNativeHeader("gender")!=null?accessor.getFirstNativeHeader("gender"):"";
        String sessionId = accessor.getSessionId();
        sessions.registerSessionId(user, sessionId, uid, gender);
        System.out.println("user login, user:{"+user+"}, sessionId:{"+sessionId+"}, uid:{"+uid+"}, gender:{"+gender+"}");
   
    }
    
   

}
