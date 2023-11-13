package me.rate.rateme.websocket.handler;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.rate.rateme.websocket.storage.WebSocketSessionStorage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Component
@RequiredArgsConstructor
public class CodeStreamingHandler implements WebSocketHandler {

    private final WebSocketSessionStorage sessionStorage;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String roomId = (String) session.getAttributes().get("roomId");
        log.info("Establishing connection for room -> '{}'", roomId);
        if (!sessionStorage.getSessions().containsKey(roomId)) {
            sessionStorage.getSessions().put(roomId, new ArrayList<>());
        }

        sessionStorage.getSessions().get(roomId).add(session);
        log.info("Established connection for room -> '{}'", roomId);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
            throws Exception {
        String acceptedMessage = (String) message.getPayload();
        log.info("Accepted -> message: '{}'", acceptedMessage);
        String roomId = (String) session.getAttributes().get("roomId");

        for (final WebSocketSession webSocketSession : sessionStorage.getSessions().get(roomId)) {
            if (webSocketSession != session) {
                webSocketSession.sendMessage(message);
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("Websocket error occured -> '{}'", exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
            throws Exception {
        String roomId = (String) session.getAttributes().get("roomId");
        log.info("Closing connection for room -> '{}'", roomId);
        sessionStorage.getSessions().remove(roomId);
        log.info("Closed connection for room -> '{}'", roomId);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
