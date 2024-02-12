package com.nagarro.Notificationservice.service;

import java.util.concurrent.ExecutionException;

import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.nagarro.Notificationservice.model.Notification;

@Service
public class NotificationClient {

	private final WebSocketStompClient stompClient;

    public NotificationClient(WebSocketStompClient stompClient) {
        this.stompClient = stompClient;
    }
    
    public void sendNotification(Notification notification) {
        StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            	System.out.println("after connected");
                session.send("/app/notification.send", notification);
            }
        };

        try {
        	System.out.println("Sending..."+notification);
            stompClient.connect("ws://localhost:8084/websocket", sessionHandler).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    
    @Scheduled(fixedDelay = 1000) // Scheduled to run every minute (60,000 milliseconds)
    public void sendNotificationsPeriodically() {
        // Set your notifications here
    	Notification notification = new Notification("kanika27mahajan@gmail.com", "Your periodic notification message");

        System.out.println(notification);
        sendNotification(notification);
    }
	
}
