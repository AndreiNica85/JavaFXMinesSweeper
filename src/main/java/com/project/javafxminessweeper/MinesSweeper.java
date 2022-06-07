package com.project.javafxminessweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** Welcome to MinesSweeper. A game we all love. */

public class MinesSweeper extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("MinesSweeper v1.0");
        stage.setScene(scene);
        stage.show();
    }

    public static void minesSweeperLaunch(){
        launch();
    }
}
