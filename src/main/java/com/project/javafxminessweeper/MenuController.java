package com.project.javafxminessweeper;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

import static com.project.javafxminessweeper.Difficulty.*;

public class MenuController {

    @FXML
    private Button startCustomGameButton;
    @FXML
    private Button exitGameButton;
    @FXML
    private Button startGameButton;

    @FXML
    private TextField textFieldSetNumberOfMines;
    @FXML
    private TextField textFieldSetCustomWidth;
    @FXML
    private TextField textFieldSetCustomHeight;

    @FXML
    private Label infoTextLabel;
    @FXML
    private Label onlyDigitsLabel;
    @FXML
    private Label minesBoundaryLabel;
    @FXML
    private Label rowsAndColsBoundaryLabel;

    @FXML
    private ChoiceBox<String> choiceBox;

    private Display controllerDisplay;
    private Stage stage;
    private Difficulty difficulty;

    /** Constructor - Called before initialize() method */
    public MenuController() throws Exception {
        this.difficulty = Difficulty.Normal;
        this.infoTextLabel = new Label();
        this.controllerDisplay = new Display(16,18,40,40);
        this.choiceBox = new ChoiceBox<>();
    }

    /** FXML Initializer */
    @FXML
    void initialize(){
        this.choiceBox.getItems().addAll(Easy.name(),Difficulty.Normal.name(), Hard.name());
        this.choiceBox.setValue("Difficulty: Normal");
        this.infoTextLabel.setText("""
                  Choose number of mines, number of columns (width between 1-28)\s
                    and number of rows (height between 1-24) for in game Display.\s
                Custom games don't count for the Top 5 players tables above. Have Fun!""");
    }

    /* Set GridPane Display Nodes on Start Game */
    public GridPane setNodesOnInGameGridPaneDisplay(GridPane gridPane, int rows, int cols){
        gridPane.setMaxHeight(25 * rows);
        gridPane.setMaxWidth(25 * cols);
        for (int r = 0; r < rows; r++) {   //height
            for (int c = 0; c < cols; c++) {  //width
                ToggleButton tog = new InitialSquare(r,c);
                int finalR = r;
                int finalC = c;
                tog.setOnMouseClicked(e -> this.controllerDisplay.initializeDisplayOnFirstClick(gridPane,finalR, finalC));
                this.controllerDisplay.display[r][c] = tog;
                gridPane.add(tog, c, r);
            }
        }
        return gridPane;
    }

    /** StartGame functionality */

    @FXML
    protected void startGameClickActionButton() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ingame-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        InGameController inGameController = loader.getController();
        this.controllerDisplay = startGameSetDisplay();
        int rows = this.controllerDisplay.display.length;
        int cols = this.controllerDisplay.display[0].length;
        inGameController.inGameGridPane = setNodesOnInGameGridPaneDisplay(inGameController.inGameGridPane, rows, cols);
        stage = (Stage) startGameButton.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    protected void exitGameClickActionButton(){
        stage = (Stage) exitGameButton.getScene().getWindow();
        stage.close();
    }

    public Difficulty setAndGetDifficultyFromChoiceBox(){
        String diff = choiceBox.getValue();
        switch (diff) {
            case "Easy" -> this.difficulty = Easy;
            case "Normal" -> this.difficulty = Normal;
            case "Hard" -> this.difficulty = Hard;
        }
        return this.difficulty;
    }

    protected Display startGameSetDisplay() throws BoundaryException{
        this.difficulty = setAndGetDifficultyFromChoiceBox();
        if(difficulty == Easy) {
            this.controllerDisplay = new Display(8, 10, 10,10);
        }else if(difficulty == Normal){
            this.controllerDisplay = new Display(16,18,40,40);
        }else{
            this.controllerDisplay = new Display(24,28,104,104);
        }
        return this.controllerDisplay;
    }

    /** Start Custom Game functionality */

    @FXML
    protected void startCustomGameClickActionButton() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ingame-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        InGameController inGameController = loader.getController();
        int mines, cols, rows, minesMaxLimit = 1;
        try{
            mines = Integer.parseInt(textFieldSetNumberOfMines.getText());
            cols = Integer.parseInt(textFieldSetCustomWidth.getText());
            rows = Integer.parseInt(textFieldSetCustomHeight.getText());
            minesMaxLimit = (int) Math.ceil(cols * rows * 0.15);
            this.controllerDisplay = new Display(rows,cols,mines,minesMaxLimit);
        }catch (IllegalArgumentException e){
            this.infoTextLabel.setVisible(false);
            this.onlyDigitsLabel.setVisible(true);
            return;
        }catch (BoundaryException e){
            this.infoTextLabel.setVisible(false);
            if(e instanceof NumberOfMinesBoundaryException){
                this.minesBoundaryLabel.setText("Number of Mines must be between 1 and " + minesMaxLimit);
                this.minesBoundaryLabel.setVisible(true);
            }else if(e instanceof WidthAndHeightBoundaryException){
                this.rowsAndColsBoundaryLabel.setVisible(true);
            }
            return;
        }
        inGameController.inGameDisplay = this.controllerDisplay;
        inGameController.inGameGridPane = setNodesOnInGameGridPaneDisplay(inGameController.inGameGridPane,this.controllerDisplay.display.length,this.controllerDisplay.display[0].length);
        stage = (Stage) startCustomGameButton.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    protected void showInfoLabelText(){
        this.minesBoundaryLabel.setVisible(false);
        this.rowsAndColsBoundaryLabel.setVisible(false);
        this.onlyDigitsLabel.setVisible(false);
        this.infoTextLabel.setVisible(true);
    }
}
