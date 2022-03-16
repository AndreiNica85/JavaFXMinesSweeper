package com.project.javafxminessweeper;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private Button exitGameButton, returnToMainMenuButton, startGameButton;

    @FXML
    private ChoiceBox<String> choiceBox = new ChoiceBox<String>();

    @FXML
    private ImageView inGameExitButton;

    private Stage stage;

    @FXML
    private AbstractSquare node;

    @FXML
    private FlowPane inGameFlowPane = new FlowPane();

    /** Methods for ingame.fxml */

    protected void choiceBoxClick(){
        choiceBox.getSelectionModel().select(1);
    }



    @FXML
    protected void setEasyLevel(){
        for(int i = 0; i < 26*22; i++){
            node = new NumberSquare();
            node.setMinSize(20,20);
            node.setMaxSize(28,28);
            node.setPrefSize(24,25);
            node.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    node.setText(node.getText());
                    node.setContentDisplay(ContentDisplay.TEXT_ONLY);
//                    node.setVisible(false);
                }
            });
            inGameFlowPane.getChildren().add(node);
        }
    }

    @FXML
    protected void setHardLevel(){
        for(int i = 0; i < 28*24; i++){
            node = new NumberSquare();
            node.setMinSize(25,25);
            node.setMaxSize(25,25);
            node.setPrefSize(25,25);
            node.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    node.setText(node.getText());
                    node.setContentDisplay(ContentDisplay.TEXT_ONLY);
//                    node.setVisible(false);
                }
            });
            inGameFlowPane.getChildren().add(node);
        }
    }

    @FXML
    protected void setNormalLevel(){
        for(int i = 0; i < 26*22; i++){
            node = new NumberSquare();
            node.setMinSize(20,20);
            node.setMaxSize(28,28);
            node.setPrefSize(24,25);
            node.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    node.setText(node.getText());
                    node.setContentDisplay(ContentDisplay.TEXT_ONLY);
//                    node.setVisible(false);
                }
            });
            inGameFlowPane.getChildren().add(node);
        }
    }

    @FXML
    protected EventHandler<? super MouseEvent> setVarza(){
//        testButton.setContentDisplay(ContentDisplay.TEXT_ONLY);
//        testButton.setBlendMode(BlendMode.SOFT_LIGHT);

        return null;
    }
    @FXML
    protected void exitGameClickActionInGameButton(){
        stage = (Stage) inGameExitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void returnToMainMenuClickActionButton() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("minessweeper-view.fxml"));
        stage = (Stage) returnToMainMenuButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    /** Methods for minessweeper-view.fxml */

    @FXML
    protected void startGameClickActionButton() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ingame.fxml"));
        stage = (Stage) startGameButton.getScene().getWindow();
        stage.setScene(new Scene(root));

    }

    @FXML
    protected void exitGameClickActionButton(){
        stage = (Stage) exitGameButton.getScene().getWindow();
        stage.close();
    }
}
