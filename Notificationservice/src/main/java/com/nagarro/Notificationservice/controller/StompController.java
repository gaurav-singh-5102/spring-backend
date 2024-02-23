package com.nagarro.Notificationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.nagarro.Notificationservice.model.Notification;

@Controller
public class StompController {

	@Autowired
    private SimpMessageSendingOperations messagingTemplate;
	
	@MessageMapping("/notification.send")
    public void notifyUser(@Payload Notification notification){
        messagingTemplate.convertAndSend(getDestination(notification.getReceiver()), notification);
    }
	

	private String getDestination(String user) {
        return String.format("/user/%s/private", user);
    }
	
	
}
