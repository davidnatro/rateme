package me.rate.rateme.websocket.storage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Getter
@Component
public class WebSocketSessionStorage {

    private final Map<String, List<WebSocketSession>> sessions = new ConcurrentHashMap<>();
}
