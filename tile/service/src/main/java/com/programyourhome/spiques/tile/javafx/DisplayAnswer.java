package com.programyourhome.spiques.tile.javafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class DisplayAnswer extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreen(true);
        
        Text text = new Text(answer);
        text.setFont(Font.font("Arial", 150));

        StackPane root = new StackPane();
        root.getChildren().add(text);
        root.setBackground(new Background(new BackgroundFill(Color.valueOf(color), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static String answer;
    static String color;

    public static void main(String[] args) {
    	DisplayAnswer.answer = args[0];
    	DisplayAnswer.color = args[1];
    	launch();
    }

}