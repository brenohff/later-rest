package br.com.brenohff.later.websocket;

import br.com.brenohff.later.enums.MessageType;
import br.com.brenohff.later.model.LTChat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        LTChat chatMessage = (LTChat) headerAccessor.getSessionAttributes().get("chatMessage");

        if (chatMessage != null && chatMessage.getUsers().getName() != null) {
            logger.info("User Disconnected : " + chatMessage.getUsers().getName());

            LTChat ltChat = new LTChat();
            ltChat.setType(MessageType.LEAVE);
            ltChat.setUsers(chatMessage.getUsers());

            messagingTemplate.convertAndSend("/topic/event/" + chatMessage.getEventId(), ltChat);
        }
    }
}