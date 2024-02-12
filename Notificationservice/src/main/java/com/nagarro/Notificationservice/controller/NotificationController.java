package com.nagarro.Notificationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.nagarro.Notificationservice.model.Notification;

@Controller
public class NotificationController {
	
	@Autowired
    private SimpMessageSendingOperations messagingTemplate;
	
	@MessageMapping("/application")
    @SendTo("/all/messages")
    public Notification send(final Notification notification) throws Exception {
        return notification;
    }

	@MessageMapping("/notification.register")
    public void register(@Payload Notification notification, SimpMessageHeaderAccessor headerAccessor) {
        // TODO : Change username to user id when integrating with main app.

        headerAccessor.getSessionAttributes().put("username", notification.getSender());

        // Send the registration message to the private queue of the receiving user
        messagingTemplate.convertAndSend(getDestination(notification.getReceiver()), notification);

    }
	
	@MessageMapping("/notification.send")
    public void notifyUser(@Payload Notification notification){
        // For one-to-one messages, send to the private queue of the receiving user
		System.out.println(notification.getReceiver());
		System.out.println(notification);
        messagingTemplate.convertAndSend(getDestination(notification.getReceiver()), notification);
    }
	private String getDestination(String user) {
		System.out.println(user);
        return String.format("/user/%s/private", user);
    }
	
}
