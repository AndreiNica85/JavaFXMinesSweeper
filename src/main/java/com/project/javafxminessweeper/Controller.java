package com.project.javafxminessweeper;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Controller {
    @FXML
    private Button startGameButton;
    @FXML
    private Button exitGameButton;
    @FXML
    private Button returnToMainMenuButton;

    private Stage stage;

    @FXML
    private AbstractSquare node;

    @FXML
    private Label welcomeText;
    @FXML
    private FlowPane inGameFlowPane = new FlowPane();

    protected EventHandler<? super MouseEvent> setvarza(){
        welcomeText.setText("NOAPTE BUNA");
        return null;
    }

    @FXML
    protected void squareClick(){

    }

    @FXML
    protected void setDisplay(){
        for(int i = 0; i < 1; i++){
            node = new NumberSquare();
            node.setText(" ");
            node.setMinSize(20,20);
            node.setMaxSize(28,28);
            node.setPrefSize(24,25);
            node.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    node.setText(node.toString());
                }
            });

            inGameFlowPane.getChildren().add(node);

        }

    }

    @FXML
    protected void startGameClickActionButton() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ingame.fxml"));
        stage = (Stage) startGameButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    protected void returnToMainMenuClickActionButton() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("minessweeper-view.fxml"));
        stage = (Stage) returnToMainMenuButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    protected void exitGameClickActionButton() throws Exception{
        stage = (Stage) exitGameButton.getScene().getWindow();
        stage.close();
    }

}
