package io.github.teonistor.hc2k19;

import io.github.teonistor.hc2k19.game.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("poker.fxml"));
        primaryStage.setTitle("Poker - Texas Hold'em!");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }


    public static void main(String[] arg) throws InterruptedException {
        new Thread(() -> {launch(arg); System.exit(0);}).start(); // KEK LOL System.exit

        Thread.sleep(1000); // KEK

        new Game().play();

//        new ProbabilisticPlayer();

    }
}
