package fr.jugorleans.poker.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.*;

/**
 * Created by francoispenaud on 27/04/15.
 */
@Slf4j
public class MyWebSocketHandler implements WebSocketHandler {

    private TournamentController tournamentController;

    public MyWebSocketHandler(TournamentController tournamentController) {
        //tournamentController.setSocketHandler(this);
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Connection established");
//        session.sendMessage(new TextMessage("test"));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.info("handle");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
