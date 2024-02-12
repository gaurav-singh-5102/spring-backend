package com.nagarro.postservice.services;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.nagarro.postservice.dto.NotificationRequestDto;

@Service
public class NotificationClient {

	private final WebSocketStompClient stompClient;
	
	public NotificationClient(WebSocketStompClient stompClient) {
        this.stompClient = stompClient;
    }
	 public void sendNotification(String receiver, String message) {
	        StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {
	        	@Override
	            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
	                session.send("/app/notification.send", new NotificationRequestDto(receiver, message));
	            }
	        };

	        try {
	            stompClient.connect("http://localhost:8084/websocket", sessionHandler).get();
	        } catch (InterruptedException | ExecutionException e) {
	            e.printStackTrace();
	        }
	    }
}
