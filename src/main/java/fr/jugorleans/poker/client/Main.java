package fr.jugorleans.poker.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("PokerClient.fxml"));
        primaryStage.setTitle("JUG'Orléans Poker App");

        Scene scene = new Scene(root, 1024, 1024);
        scene.getStylesheets().add("PokerClient.css");

        primaryStage.setScene(scene);
        primaryStage.show();

//        List<Transport> transports = new ArrayList<>(2);
//        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
//        transports.add(new RestTemplateXhrTransport());
//
//        SockJsClient sockJsClient = new SockJsClient(transports);
//        sockJsClient.doHandshake(new MyWebSocketHandler(), "ws://localhost:8080/game");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
