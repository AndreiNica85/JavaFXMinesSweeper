package com.project.javafxminessweeper;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;

/** MVC Controller Class for EndGameDialogPane */

public class EndGameDialogPaneController {

    @FXML
    protected Pane endGamePane;

    @FXML
    protected Button backToMenuButton;

    @FXML
    protected Button gameOverAndCongratButton;

    @FXML
    protected Button minesSweptButton;

    @FXML
    protected Button mouseClicksButton;

    @FXML
    protected Button timeButton;

    @FXML
    protected ProgressIndicator endGameProgressIndicator;

}
