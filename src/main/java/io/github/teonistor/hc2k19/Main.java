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
        primaryStage.show();
    }


    public static void main(String[] arg) {

        new Game(3).play();

//        new ProbabilisticPlayer();

//        launch(arg);
    }
}
