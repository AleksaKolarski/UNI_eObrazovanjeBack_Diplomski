package com.projekat.eObrazovanje.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.projekat.eObrazovanje.socket.Gazepoint;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	@Autowired
	Gazepoint client;
	

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		
		// ako koristimo SockJS
		//registry.addEndpoint("/websocket-endpoint").withSockJS().setClientLibraryUrl("/lib/sockjs.min.js").setInterceptors(getInterceptor()).setSessionCookieNeeded(false);
		
		registry.addEndpoint("/websocket-endpoint").setAllowedOrigins("http://localhost:4200").addInterceptors(getInterceptor());
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic");
		registry.setApplicationDestinationPrefixes("/app");
	}
	
	private HandshakeInterceptor getInterceptor() {
        return new HandshakeInterceptor(){

            @Override
            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {            	
            	System.out.println("client trying to connect");
            	try {
            		client.startConnection("127.0.0.1", 4242);
            		return true;
            	}
            	catch (Exception e) {
            		System.out.println("Could not connect to gazepoint client.");
            		response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            		return false;
				}
            }

            @Override
            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {}

        };
    }
	
	public class FilterChannelInterceptor implements ChannelInterceptor {
	    @Override
	    public Message<?> preSend(Message<?> message, MessageChannel channel) {
	        StompHeaderAccessor headerAccessor= StompHeaderAccessor.wrap(message);
	        if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())) {
	        	// ako ovo iskoristimo za konektovanje ka gazepoint serveru 
	        	// onda u slucaju odbijanja websocket konekcije konekcija ka 
	        	// gazepointu ostane ukljucena ???
	        }
	        return message;
	    }
	}
	
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(new FilterChannelInterceptor());
	}
	
	@Component
	public class StompEventListener {

	    @EventListener
	    private void handleSessionDisconnect(SessionDisconnectEvent event) {
	    	System.out.println("client disconected");
	    	client.stopConnection();
	    }

	    @EventListener
	    private void handleSessionUnsubscribeEvent(SessionUnsubscribeEvent event) {
        	System.out.println("client unsubscribed");
        	client.stopConnection();
	    }
	}
}
