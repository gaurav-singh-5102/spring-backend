package com.nagarro.Notificationservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.RequestUpgradeStrategy;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@CrossOrigin
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	RequestUpgradeStrategy upgradeStrategy = new TomcatRequestUpgradeStrategy();
	@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/notification/websocket").setHandshakeHandler(new DefaultHandshakeHandler(upgradeStrategy)).setAllowedOrigins("http://localhost:4200", "http://localhost:8086", "http://localhost:8084");
        registry.addEndpoint("/notification/websocket").setHandshakeHandler(new DefaultHandshakeHandler(upgradeStrategy)).setAllowedOrigins("http://localhost:4200", "http://localhost:8086", "http://localhost:8084").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/user");
    }

}
