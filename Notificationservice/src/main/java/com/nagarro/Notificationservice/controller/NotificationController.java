package com.nagarro.Notificationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.Notificationservice.exceptions.UserNotFoundException;
import com.nagarro.Notificationservice.model.Notification;

@RestController
public class NotificationController {

	@Autowired
    private StompController stompController;

    @PostMapping("/sendNotification")
    public ResponseEntity<String> sendNotification(@RequestBody @Validated Notification notification) throws UserNotFoundException{
//    	String token = authHeader.substring(7);
    	stompController.notifyUser(notification);
        return ResponseEntity.ok("Notification sent successfully");
    }
}
