package com.nagarro.websockets.interceptor;

import java.util.List;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.messaging.util.matcher.MessageMatcher;
import org.springframework.security.messaging.util.matcher.SimpMessageTypeMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;

@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {


	private final MessageMatcher<Object> matcher = new SimpMessageTypeMatcher(SimpMessageType.CONNECT);

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		
		System.out.println("Print...");
		System.out.println(message.getHeaders().toString());
		if (!this.matcher.matches(message)) {
			System.out.println(message.getHeaders().toString());
			return message;
		}
			
		List<String> nativeHeaders = (List<String>)((LinkedMultiValueMap<String, ?>) message.getHeaders().get("nativeHeaders")).get("Authorization");
		System.out.println(nativeHeaders.get(0).substring(7));
		
		//		System.out.println(((List<String>)nativeHeaders.get("Authorization")));
//		Map<String, Object> sessionAttributes = SimpMessageHeaderAccessor.getSessionAttributes(message.getHeaders());
//		CsrfToken expectedToken = (sessionAttributes != null)
//				? (CsrfToken) sessionAttributes.get(CsrfToken.class.getName()) : null;
//		if (expectedToken == null) {
//			throw new MissingCsrfTokenException(null);
//		}
//		System.out.println(expectedToken);
//		String actualTokenValue = SimpMessageHeaderAccessor.wrap(message)
//			.getFirstNativeHeader(expectedToken.getHeaderName());
//		boolean csrfCheckPassed = expectedToken.getToken().equals(actualTokenValue);
//		if (!csrfCheckPassed) {
//			throw new InvalidCsrfTokenException(expectedToken, actualTokenValue);
//		}
		return message;
	}
	
}
