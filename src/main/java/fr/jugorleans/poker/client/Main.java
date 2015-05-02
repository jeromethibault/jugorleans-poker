package fr.jugorleans.poker.client;

import fr.jugorleans.poker.client.stomp.StompMessageHandler;
import fr.jugorleans.poker.client.stomp.StompSession;
import fr.jugorleans.poker.client.stomp.WebSocketStompClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.http.HttpMethod;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.config.StompBrokerRelayRegistration;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private final static WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PokerClient.fxml"));
        primaryStage.setTitle("JUG'Orléans Poker App");

        Scene scene = new Scene(loader.load(), 1024, 1024);
        scene.getStylesheets().add("PokerClient.css");

        primaryStage.setScene(scene);
        primaryStage.show();


        //TournamentController tournamentController = loader.getController();

        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        RestTemplateXhrTransport xhrTransport = new RestTemplateXhrTransport(new RestTemplate());
        xhrTransport.setRequestHeaders(headers);
        transports.add(xhrTransport);

        SockJsClient sockJsClient = new SockJsClient(transports);
        URI uri = new URI("ws://localhost:8080/pokerjug");
        WebSocketStompClient stompClient = new WebSocketStompClient(uri, new WebSocketHttpHeaders(), sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        stompClient.connect(new StompMessageHandler() {

            private StompSession stompSession;

            @Override
            public void afterConnected(StompSession session, StompHeaderAccessor headers) {
                System.out.println("Connexion à la websocket établie");
                session.subscribe("/websocket/test", null);
                this.stompSession = session;
                session.send("/pokerserver/pokerjug","toto");
                //session.disconnect();
            }

            @Override
            public void handleMessage(Message<byte[]> message) {
                System.out.println("message");
                System.out.println(new String((byte[]) message.getPayload()));
            }

            @Override
            public void handleReceipt(String receiptId) {
                System.out.println("receipt");
            }

            @Override
            public void handleError(Message<byte[]> message) {
                System.out.println("error");
            }

            @Override
            public void afterDisconnected() {
                System.out.println("disconnected");
            }
        });


    }


    public static void main(String[] args) {
        launch(args);
    }
}
