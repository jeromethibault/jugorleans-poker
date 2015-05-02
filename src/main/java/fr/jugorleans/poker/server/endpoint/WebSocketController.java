package fr.jugorleans.poker.server.endpoint;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Test
 */
@Controller
public class WebSocketController {

    @MessageMapping(value = "/pokerjug")
    @SendTo(value = "/websocket/test")
    public String test(String message) {
        System.out.println("Réception d'un message via la websocket");
        System.out.println(message);
        return "message via web socket";
    }
}
