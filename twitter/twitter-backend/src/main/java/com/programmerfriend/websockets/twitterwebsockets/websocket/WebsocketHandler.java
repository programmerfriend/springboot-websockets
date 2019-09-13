package com.programmerfriend.websockets.twitterwebsockets.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.programmerfriend.websockets.twitterwebsockets.TwitterStatusReceived;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Slf4j
public class WebsocketHandler extends TextWebSocketHandler implements ApplicationListener<TwitterStatusReceived> {

    private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private ObjectWriter objectWriter;

    public WebsocketHandler(ObjectMapper objectMapper) {
        this.objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
        log.error("error occured at sender " + session, throwable);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(String.format("Session %s closed because of %s", session.getId(), status.getReason()));
        sessions.remove(session.getId());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Connected ... " + session.getId());
        sessions.put(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Handling message: {}", message);
    }

    private void sendMessageToAll(String message) {
        TextMessage textMessage = new TextMessage(message);
        sessions.forEach((key, value) -> {
            try {
                value.sendMessage(textMessage);
                log.info("Send message {} to socketId: {}", message, key);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onApplicationEvent(TwitterStatusReceived twitterStatusReceived) {
        try {
            String msg = objectWriter.writeValueAsString(twitterStatusReceived);
            sendMessageToAll(msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
