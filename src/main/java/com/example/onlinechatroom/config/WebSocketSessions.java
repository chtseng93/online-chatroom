package com.example.onlinechatroom.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
@Component
public class WebSocketSessions {
	
	private ConcurrentHashMap<String, String> sessionUsers = new ConcurrentHashMap<>();
	private List<ContactEntity> uidUsers = new ArrayList<>();
    @Override
    public String toString() {
        return "[WebSocketSessions] sessionUsers: " + sessionUsers.size();
    }
    
   
    public List<ContactEntity> getUidUsers() {
    	System.out.println("===============>getUidUsers:");
    	uidUsers.stream().forEach(entity->{
    		System.out.println(entity);
    	});
		return uidUsers;
	}

	public synchronized void registerSessionId(String name, String sessionId, String uid, String gender) {
        Assert.notNull(name, "user must not be null");
        Assert.notNull(uid, "sessionId must not be null");
        Assert.notNull(sessionId, "sessionId must not be null");
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setUid(uid);
        contactEntity.setName(name);
        contactEntity.setGender(gender);
        contactEntity.setSessionId(sessionId);
        sessionUsers.put(sessionId, uid);
        uidUsers.add(contactEntity);
    }

    public synchronized void removeSessionId(String sessionId) {
        Assert.notNull(sessionId, "sessionId must not be null");

        if (sessionUsers.containsKey(sessionId)) {
            sessionUsers.remove(sessionId);
        }
        
        uidUsers.removeIf(e-> sessionId.equals(e.getSessionId()));
        System.out.println("uidUsers.removeIf");
    }

    public ArrayList<String> getAllUsers() {
        return new ArrayList<>(sessionUsers.values());
    }

    public ArrayList<String> getAllSessionIds() {
        return new ArrayList<>(sessionUsers.keySet());
    }

    /**
     	* 取得相同使用者的所有sessionIds
     */
    public ArrayList<String> getSessionIds(String user) {
        ArrayList<String> sessionIds = new ArrayList<>();
        for (Map.Entry<String, String> entry : sessionUsers.entrySet()) {
            String userInMap = entry.getValue();
            if (userInMap.equals(user)) {
                sessionIds.add(entry.getKey());
            }
        }
        return sessionIds;
    }
    
    
//	public List<ContactEntity> getAllContact() {
//
////		List<ContactEntity> list = uidUsers.entrySet().stream().map(e -> new ContactEntity(e.getKey(), e.getValue()))
//		List<ContactEntity> list = uidUsers.entrySet().collect(Collectors.toList());
//
//		return list;
//
//	}
	
	


}
