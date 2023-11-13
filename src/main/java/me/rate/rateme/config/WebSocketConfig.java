package me.rate.rateme.config;

import lombok.RequiredArgsConstructor;
import me.rate.rateme.config.property.WebSocketProperty;
import me.rate.rateme.websocket.handler.CodeStreamingHandler;
import me.rate.rateme.websocket.interceptor.PathVariableExtractor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketProperty property;
    private final CodeStreamingHandler codeStreamingHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(codeStreamingHandler, property.getRoomPath())
                .setHandshakeHandler(handshakeHandler())
                .addInterceptors(new PathVariableExtractor(property.getRoomPath()))
                .setAllowedOrigins("*");
    }

    private DefaultHandshakeHandler handshakeHandler() {
        return new DefaultHandshakeHandler(new TomcatRequestUpgradeStrategy());
    }
}
