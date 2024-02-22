package com.nagarro.websockets.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nagarro.websockets.dto.NotificationRequestDto;
import com.nagarro.websockets.exception.ChatMessageValidationException;
import com.nagarro.websockets.exception.InvalidNotificationRequestException;
import com.nagarro.websockets.model.ChatMessage;

import jakarta.annotation.PreDestroy;

@Service
public class MessageService {
	
	private final WebClient webClient;
	 
    private String notificationServiceBaseUrl;
	private Validator validator;
	
    private HashMap<String, HashMap<String, List<ChatMessage>>> messages = new HashMap<>();
    
    public MessageService(Validator validator, WebClient.Builder webClientBuilder) {
    	this.validator = validator;
    	this.webClient = webClientBuilder.baseUrl("http://localhost:8084").build();
    }

    public void saveMessage(ChatMessage chatMessage) throws ChatMessageValidationException, InvalidNotificationRequestException {
        validateChatMessage(chatMessage);
        messages.computeIfAbsent(chatMessage.getSenderName(), k -> new HashMap<>())
                .computeIfAbsent(chatMessage.getReceiverName(), k -> new ArrayList<>())
                .add(chatMessage);
        messages.computeIfAbsent(chatMessage.getReceiverName(), k -> new HashMap<>())
                .computeIfAbsent(chatMessage.getSenderName(), k -> new ArrayList<>())
                .add(chatMessage);
        System.out.println(chatMessage);
        sendNotification(chatMessage);
    }

    private void validateChatMessage(ChatMessage chatMessage) throws ChatMessageValidationException {

        if (chatMessage.getContent() == null || chatMessage.getSenderName() == null || chatMessage.getReceiverName() == null
                || chatMessage.getType() == null || chatMessage.getTimestamp() == null) {
            throw new ChatMessageValidationException();
        }

    }

    public HashMap<String, List<ChatMessage>> getMessages(String username) {
        if (!messages.containsKey(username)) {
            loadMessagesFromFile(username);
        }
        return messages.getOrDefault(username, new HashMap<>());
    }

    // Save messages to a JSON file for a specific user
    public void saveMessagesToFile(String user) {
        try {
            System.out.println("Creating backup for " + user);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            File backupFolder = new File("backups");
            if (!backupFolder.exists()) {
                backupFolder.mkdir();
            }

            File userBackupFile = new File(backupFolder, user + "_backup.json");
            if (!userBackupFile.exists()) {
                userBackupFile.createNewFile();
            }
            objectMapper.writeValue(userBackupFile, getMessages(user));
            messages.remove(user);
        } catch (IOException e) {
            System.err.println("Could not create backup for " + user + "!");
        }
    }

    // Load messages from a JSON file for a specific user
    public void loadMessagesFromFile(String user) {
        try {
            System.out.println("Loading backup for " + user);
            ObjectMapper objectMapper = new ObjectMapper();
            File userBackupFile = new File("backups/" + user + "_backup.json");

            if (userBackupFile.exists()) {
                messages.put(user,
                        objectMapper.readValue(userBackupFile, new TypeReference<HashMap<String, List<ChatMessage>>>() {
                        }));
            }
        } catch (IOException e) {
            System.err.println("Could not load backup for " + user + "!");
        }
    }
    
    private void sendNotification(ChatMessage chatMessage) throws InvalidNotificationRequestException  {
        // Construct the Notification object
        NotificationRequestDto notification = new NotificationRequestDto();
        notification.setContent(chatMessage.getContent());
        notification.setSender(chatMessage.getSenderId());
        notification.setReceiver(chatMessage.getReceiverId());
        notification.setGroupNotification(false);
        notification.setTimestamp(LocalDateTime.now());
        System.out.println(notification);
        validateNotification(notification);
        
        webClient.post()
	        .uri("/sendNotification")
	        .bodyValue(notification)
	        .retrieve()  
	        .toEntity(String.class)  
	        .block();  
    }
    private void validateNotification(NotificationRequestDto notification) throws InvalidNotificationRequestException {
        Errors errors = new BeanPropertyBindingResult(notification, "entity");
        validator.validate(notification, errors);
        if(errors.hasErrors()) {
        	throw new InvalidNotificationRequestException(errors.getAllErrors());
        }
    }

    @PreDestroy
    private void saveBackupsOnShutdown() {
        for (String key : messages.keySet()) {
            saveMessagesToFile(key);
        }
    }
}
