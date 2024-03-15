package com.nagarro.websockets.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.nagarro.websockets.exception.ChatMessageValidationException;
import com.nagarro.websockets.exception.InvalidNotificationRequestException;
import com.nagarro.websockets.model.ChatMessage;
import com.nagarro.websockets.model.ConnectMessage;
import com.nagarro.websockets.model.User;
import com.nagarro.websockets.service.MessageService;

@Controller
public class ChatController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    @Autowired
    private MessageService messageService;
    private List<User> users = new ArrayList<>();
    
    @MessageMapping("/chat.join")
    @SendTo("/topic/join")
    public ConnectMessage join(@Payload ConnectMessage connectMessage, SimpMessageHeaderAccessor headerAccessor) {        
    	User userInfo = new User(); 
        userInfo.setId(connectMessage.getSenderId());
        userInfo.setName(connectMessage.getSenderName());
        boolean userExists = users.stream().anyMatch(user -> user.getId().equals(userInfo.getId()));
        // If the user doesn't exist or is not active, add/update the user and set status to "Active"
        if (!userExists || !"Active".equals(userInfo.getStatus())) {
            userInfo.setStatus("Active");
            // If the user exists, update their status
            if (userExists) {
                users.stream()
                     .filter(user -> user.getId().equals(userInfo.getId()))
                     .findFirst()
                     .ifPresent(user -> user.setStatus("Active"));
            } else {
                users.add(userInfo);
            }
        }
        connectMessage.setHistory(messageService.getMessages(connectMessage.getSenderId()));
        connectMessage.setUsers(users);
        return connectMessage;
    }

    @MessageMapping("/chat.register")
    public void register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
    	try {
    		
    		headerAccessor.getSessionAttributes().put("username", chatMessage.getSenderName());
    		headerAccessor.getSessionAttributes().put("userid", chatMessage.getSenderId());		
    		messagingTemplate.convertAndSend(getDestination(chatMessage.getReceiverId()), chatMessage);
    	}
    	catch(Exception ex) {
    		System.out.println(ex);
    	}

    }

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessage chatMessage)
            throws ChatMessageValidationException, InvalidNotificationRequestException {
        try {
            messageService.saveMessage(chatMessage);
    		messagingTemplate.convertAndSend(getDestination(chatMessage.getReceiverId()), chatMessage);
    		
    	}
    	catch(Exception ex) {
            ex.printStackTrace();
    	}
        
    }

    @MessageMapping("/chat.unregister")
    @SendTo("/topic/join")
    public ConnectMessage leave(@Payload ConnectMessage connectMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().remove("username");
        messageService.saveMessagesToFile(connectMessage.getSenderId());
        User userInfo = new User();
        userInfo.setId(connectMessage.getSenderId());
        userInfo.setName(connectMessage.getSenderName());
        userInfo.setStatus("Inactive");
       
        users.removeIf(user -> user.getId().equals(userInfo.getId()));
        users.add(userInfo);
        connectMessage.setUsers(users);
        return connectMessage;
    }

    private String getDestination(String user) {
        return String.format("/user/%s/private", user);
    }
}
