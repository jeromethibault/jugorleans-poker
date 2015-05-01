package fr.jugorleans.poker.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PokerClient.fxml"));
        primaryStage.setTitle("JUG'Orléans Poker App");

        Scene scene = new Scene(loader.load(), 1024, 1024);
        scene.getStylesheets().add("PokerClient.css");

        primaryStage.setScene(scene);
        primaryStage.show();


        TournamentController tournamentController = loader.getController();
        WebSocketHandler socketHandler= new MyWebSocketHandler(tournamentController);

        List<Transport> transports = new ArrayList<>(2);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());

        SockJsClient sockJsClient = new SockJsClient(transports);
        sockJsClient.doHandshake(socketHandler, "ws://localhost:8080/game");
        sockJsClient.start();
        System.out.println("sockJs running : " + sockJsClient.isRunning());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
