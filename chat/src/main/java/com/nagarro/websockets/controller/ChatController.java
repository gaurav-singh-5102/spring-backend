package com.nagarro.websockets.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

import com.nagarro.websockets.exception.ChatMessageValidationException;
import com.nagarro.websockets.exception.InvalidNotificationRequestException;
import com.nagarro.websockets.model.ChatMessage;
import com.nagarro.websockets.model.ConnectMessage;
import com.nagarro.websockets.model.User;
import com.nagarro.websockets.service.MessageService;
import com.nagarro.websockets.service.UserService;

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
        
        System.out.println("Users: "+users);
        //Create a list to hold sender name and status
        
        User userInfo = new User();
        
        userInfo.setId(connectMessage.getSenderId());
        userInfo.setName(connectMessage.getSenderName());
        userInfo.setStatus("Active");
        
        if(!users.contains(userInfo)) {	
        	users.add(userInfo);
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
    		// Send the registration message to the private queue of the receiving user
    		
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
    		System.out.println(chatMessage);
    		messageService.saveMessage(chatMessage);
    		System.out.println(chatMessage);
    		System.out.println("Receiver: "+getDestination(chatMessage.getReceiverId()));
    		messagingTemplate.convertAndSend(getDestination(chatMessage.getReceiverId()), chatMessage);
    		
    	}
    	catch(Exception ex) {
    		System.out.println(ex);
    	}
        // For one-to-one messages, send to the private queue of the receiving user
    }

    @MessageMapping("/chat.unregister")
    @SendTo("/topic/join")
    public ConnectMessage leave(@Payload ConnectMessage connectMessage, SimpMessageHeaderAccessor headerAccessor, @RequestHeader("Authorization") String authHeader) {
        headerAccessor.getSessionAttributes().remove("username");
        messageService.saveMessagesToFile(connectMessage.getSenderId());
        String token = authHeader.substring(7);
//        User user = this.userService.getUser(token, connectMessage.getSenderId());
//        users.put(user, "Inactive");
        
        User userInfo = new User();
        userInfo.setId(connectMessage.getSenderId());
        userInfo.setName(connectMessage.getSenderName());
        userInfo.setStatus("Inactive");
       
//        users.put(connectMessage.getSenderId(), "Inactive");
        connectMessage.setUsers(users);
        return connectMessage;
    }

    private String getDestination(String user) {
        return String.format("/user/%s/private", user);
    }
}
