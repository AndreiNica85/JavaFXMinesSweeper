package com.project.javafxminessweeper;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;

/** MVC Controller Class for In Game Display elements */

public class InGameController implements Initializable {

    @FXML
    private ProgressIndicator progressIndicator22;
    @FXML
    private ProgressIndicator progressIndicator11;

    @FXML
    private ImageView inGameMainMenuCloseButton;

    @FXML
    private GridPane inGameGridPane;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private VBox lastClickVBox;
    @FXML
    private VBox lastClickCoordsVBox;

    @FXML
    private Button secondsInGameButton;
    @FXML
    private Button mouseClickedButton;
    @FXML
    private Button columnNumberButton;
    @FXML
    protected Button rowNumberButton;
    @FXML
    protected Button minesButton;
    @FXML
    private Button mouseClicksButton;
    @FXML
    Button timerButton;

    private Display inGameDisplay;
    private Timeline timeLine;
    private Player player;
    private List<Player> inGameList;
    private Difficulty inGameDifficulty;
    private Status status;
    private DropShadow displayDropShadowEffect;
    private List<MineSquare> mineSquaresList;
    private final String guessedSquareStyleString = "-fx-background-color: transparent; -fx-text-fill: WHITE; -fx-border-color:GRAY; -fx-border-width: 0.7 0.7 0.7 0.7;";
    private int flaggedMines = 0;
    private int[] seconds;
    protected static int countMouseClicks = 0;

    /* Breadth First Search coordinates around a square cell. They are always the same */
    private static final int[] rowCoordinatesAroundSquareDigitToAdd = {-1, 0, 1, 1, 1, 0,-1,-1};
    private static final int[] colCoordinatesAroundSquareDigitToAdd = {-1,-1,-1, 0, 1, 1, 1, 0};

    public InGameController()  {
        this.status = Status.INITIAL_STATUS;

        /* Reset seconds array to 0 for every new game */
        this.seconds = new int[]{0};

        /* Used a List to store mines square objects so that we can discover all of them at once on finish game */
        this.mineSquaresList = new ArrayList<>();

        /* TimeLine object - every second Tick put in String format from seconds to minute and hours */
        this.timeLine = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            seconds[0]++;
            timerButton.setText(String.format("%02d:%02d", seconds[0] / 3600, (seconds[0] % 3600) / 60));
            secondsInGameButton.setText(String.format("%02d",seconds[0] % 60));
        }));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /* Set open progress to 100% */
        this.progressIndicator11.setProgress(1.0);

        /* Other way to assign ToolTip - install method */
        Tooltip progressBarTool = new Tooltip("Map Revealed Percentage");
        Tooltip timerTool = new Tooltip("Timer");
        Tooltip mouseClicksTool = new Tooltip("Mouse Clicks");
        Tooltip minesToDiscoverTool = new Tooltip("Remaining Mines");
        Tooltip lastMouseClickSquareTool = new Tooltip("Last Mouse Click Square");
        Tooltip lastMouseClickCoordinatesTool = new Tooltip("Last Mouse Click Square Coordinates");
        Tooltip.install(this.minesButton, minesToDiscoverTool);
        Tooltip.install(this.secondsInGameButton, timerTool);
        Tooltip.install(this.progressBar, progressBarTool);
        Tooltip.install(this.progressIndicator11, progressBarTool);
        Tooltip.install(this.progressIndicator22, progressBarTool);
        Tooltip.install(this.timerButton, timerTool);
        Tooltip.install(this.mouseClicksButton, mouseClicksTool);
        Tooltip.install(this.lastClickVBox, lastMouseClickSquareTool);
        Tooltip.install(this.lastClickCoordsVBox, lastMouseClickCoordinatesTool);
        Tooltip.install(this.mouseClickedButton, lastMouseClickSquareTool);
    }

    /* First Game Mouse Click method on random AbstractSquare - Populate Display on first Click and change the AbstractSquare from InitialSquare to one of the other 3 states */
    /* 1. Generate Mines and Add them to mineSquaresList, so we can reveal them at once at finish game */
    /* 2. Loop through the 2DArray, create and add EmptySquares, generate and add NumberSquares around MineSquares. Add all to Pane */
    /* 3. Start in game Timer and open onClickMethod for first click */
    public void initializeDisplayOnFirstClick(GridPane pane, int rowFirstClick, int colFirstClick){

        /* First Clear all the InitialSquares from pane */
        pane.getChildren().clear();

        /* Get Pane Effect to be able to modify its color during mouse clicks events */
        this.displayDropShadowEffect = (DropShadow) pane.getEffect();

        /* 1.1 First generate all mines randomly and store them only in 2DArray */
        Random mineRow = new Random();
        Random mineCol = new Random();
        while(this.inGameDisplay.getMines() > this.mineSquaresList.size()) {

            /* 1.2 Generate random row and column for a mine square */
            int rowM = mineRow.nextInt(this.inGameDisplay.getDisplay2DArray().length);
            int colM = mineCol.nextInt(this.inGameDisplay.getDisplay2DArray()[0].length);

            /* 1.3 If this random point's coordinates are different from the first click's row and column and is not already a mine element in the 2D array then add the new mineSquare object to list and 2D array */
            if (!(rowM == rowFirstClick && colM == colFirstClick) && !(this.inGameDisplay.getDisplay2DArray()[rowM][colM] instanceof MineSquare)) {
                MineSquare mineSquare = new MineSquare(rowM, colM);
                this.mineSquaresList.add(mineSquare);
                this.inGameDisplay.getDisplay2DArray()[rowM][colM] = mineSquare;
            }
        }

        /* 2.1 First loop through 2D array with double for loop */
        for(int i = 0; i < this.inGameDisplay.getDisplay2DArray().length; i++){
            for(int j = 0; j < this.inGameDisplay.getDisplay2DArray()[0].length; j++){

                /* First omit the elements in 2D array that already contain MineSquare object because we don't want to overwrite them */
                if(!(this.inGameDisplay.getDisplay2DArray()[i][j] instanceof MineSquare)){

                    /* Populate NumberSquares and EmptySquares together with their mouse click action buttons on First click */
                    int digit = getDigitForNumberSquare(i,j);

                    /* If digit = 0 that means we don't have mines around this square, so we instantiate it as EmptySquare and store it */
                    if(digit == 0){
                        EmptySquare emptySquare = new EmptySquare(i,j);
                        this.inGameDisplay.getDisplay2DArray()[i][j] = emptySquare;

                    /* Else it is a NumberSquare, so we instantiate it and store it together with their mouse click Events */
                    }else{
                        NumberSquare numberSquare = new NumberSquare(i,j,digit);
                        this.inGameDisplay.getDisplay2DArray()[i][j] = numberSquare;

                        /* Set On Mouse Pressed and Released for double-click option on NumberSquare for in Game */
                        numberSquare.setOnMousePressed(f -> {

                            /* If ITS equals to '?' DO NOTHING. Else if IT IS NOT equals to '?' DO the following: */
                            /* If NumberSquare is revealed, and you clicked on it  */
                            /* This is available only for NumberSquare */
                            if(!numberSquare.getText().equals("?")){
                                if(f.getButton() == MouseButton.PRIMARY && numberSquare.isRevealedSquare()){

                                    /* Mouse Double-click */
                                    /* If Guessed Mines around this NumberSquare are equal to the NumberSquare.getNumber then discover other Square around that Number */
                                    /* Attention - Be sure your Guessed Mines are Really mines when double-click or else you might lose the game and that's bad :) */
                                    int guessedMines = countGuessedMinesAroundNumberSquare(numberSquare);
                                    if(f.getClickCount() == 2 && guessedMines == numberSquare.getNumber()){
                                        discoverOnDoubleClickWhenGuessedMinesEqualsNumberMethod(numberSquare);

                                        /* Update mouseClicks Button */
                                        countMouseClicks++;
                                        this.mouseClicksButton.setText("" + InGameController.countMouseClicks);

                                    /* Mouse Single-Click */
                                    /* Else if single click - it shows possible mines around this NumberSquare */
                                    }else if(f.getClickCount() == 1){
                                        selectSquaresAroundNumberSquare(numberSquare);
                                        numberSquare.setSelected(true);
                                    }
                                }
                            }
                        });
                        numberSquare.setOnMouseReleased(f -> {

                            /* If ITS equals to '?' DO NOTHING. Else if IT IS NOT equals to '?' DO the following: */
                            if(!numberSquare.getText().equals("?")){

                                /* If NumberSquare is revealed and you clicked on it */
                                if(f.getButton() == MouseButton.PRIMARY && numberSquare.isRevealedSquare()){

                                    /* On this Release event just DeSelect the Squares you selected on Press event */
                                    deSelectSquaresAroundNumberSquare(numberSquare);
                                    numberSquare.setSelected(true); // This is a must because ToggleButton toggles between mouse clicks
                                }
                            }
                        });
                    }
                }

                /* Set mouse Click Event for EVERY AbstractSquare element in the 2D array */
                int finalI = i;
                int finalJ = j;

                /* Store the initial Node style to return to it on toggle when needed */
                String initStyle = this.inGameDisplay.getDisplay2DArray()[finalI][finalJ].getStyle();

                this.inGameDisplay.getDisplay2DArray()[i][j].setOnMouseClicked(e -> {

                        /* If square - is not yet revealed - only EVENT*/
                        if(e.getButton() == MouseButton.PRIMARY && !((AbstractSquare) this.inGameDisplay.getDisplay2DArray()[finalI][finalJ]).isRevealedSquare()){

                            if(!this.inGameDisplay.getDisplay2DArray()[finalI][finalJ].getText().equals("?")){

                                /* Check and reveal the Square */
                                onClickMethod(finalI, finalJ,true);

                                /* Update Mouse Clicks Button */
                                countMouseClicks++;
                                this.mouseClicksButton.setText("" + InGameController.countMouseClicks);
                            }
                        }else if(e.getButton() == MouseButton.SECONDARY){

                            /* On second mouse button click if it is marked with '?' then go back to initial State */
                            if(this.inGameDisplay.getDisplay2DArray()[finalI][finalJ].getText().equals("?")){

                                /* Back to initial Style and properties */
                                this.inGameDisplay.getDisplay2DArray()[finalI][finalJ].setText(" ");
                                this.inGameDisplay.getDisplay2DArray()[finalI][finalJ].setStyle(initStyle);

                                /* Set Reveal false so the square can be still clicked */
                                ((AbstractSquare) this.inGameDisplay.getDisplay2DArray()[finalI][finalJ]).setRevealedSquare(false);

                                /* Set back to initial numberOfRevealSquares, mines, DropEffect, flaggedMines, progressBar styles and progress */
                                AbstractSquare.numberOfRevealedSquares--;
                                if(this.inGameDisplay.getDisplay2DArray()[finalI][finalJ] instanceof MineSquare){
                                    this.flaggedMines--;
                                }
                                this.progressBar.setProgress(countProgressBarPercentage());
                                this.progressIndicator11.setProgress(1.0 - this.progressBar.getProgress());
                                this.progressIndicator22.setProgress(this.progressBar.getProgress());
                                this.displayDropShadowEffect.setColor(this.getDisplayEffectColor());
                                this.progressBar.setStyle(progressBarStringColor());
                                int mines = Integer.parseInt(this.minesButton.getText());
                                mines++;
                                this.minesButton.setText("" + mines);

                            /* On second mouse button click you guess if it is a mine and is marked with '?' and set it revealed for other methods to see */
                            }else if(!((AbstractSquare) this.inGameDisplay.getDisplay2DArray()[finalI][finalJ]).isRevealedSquare()){

                                /* Update text for square, numberOfRevealSquares, mines, DropEffect, flaggedMines, progressBar styles and progress */
                                this.inGameDisplay.getDisplay2DArray()[finalI][finalJ].setText("?");
                                this.inGameDisplay.getDisplay2DArray()[finalI][finalJ].setStyle(guessedSquareStyleString);

                                /* Set Reveal true so the square cannot be clicked anymore because is checked '?' now */
                                ((AbstractSquare) this.inGameDisplay.getDisplay2DArray()[finalI][finalJ]).setRevealedSquare(true);

                                AbstractSquare.numberOfRevealedSquares++;
                                if(this.inGameDisplay.getDisplay2DArray()[finalI][finalJ] instanceof MineSquare){
                                    this.flaggedMines++;
                                }
                                this.progressBar.setProgress(countProgressBarPercentage());
                                this.progressIndicator11.setProgress(1.0 - this.progressBar.getProgress());
                                this.progressIndicator22.setProgress(this.progressBar.getProgress());
                                this.displayDropShadowEffect.setColor(this.getDisplayEffectColor());
                                this.progressBar.setStyle(progressBarStringColor());
                                int mines = Integer.parseInt(this.minesButton.getText());
                                mines--;
                                this.minesButton.setText("" + mines);
                                countMouseClicks++;
                                this.mouseClicksButton.setText("" + InGameController.countMouseClicks);

                                /* Also check for a last mouse second or primary click IF THE GAME IS FINISHED OR NOT. Finished means 100 % revealed */
                                if(isGameFinished()){
                                    this.status = endGameStatusMethod();
                                    this.status.startStopTimer_EndGameDialog(this);
                                }
                            }
                        }
                });

                /* Switch off selected border for square */
                this.inGameDisplay.getDisplay2DArray()[i][j].setFocusTraversable(false);

                /* 2.2 Add square (Node) to Pane */
                pane.add(this.inGameDisplay.getDisplay2DArray()[i][j],j,i);
            }
        }

        /* Start Timer and process first click on onClickMethod */
        this.status.startStopTimer_EndGameDialog(this);

        /* Click on it at the end of initializeDisplayFirstClick :) */
        onClickMethod(rowFirstClick, colFirstClick,true);
    }

    /* Click Method for every Square */
    protected void onClickMethod(int row, int col, boolean mouseClick){
        AbstractSquare abstractSquare = (AbstractSquare) this.inGameDisplay.getDisplay2DArray()[row][col];

        if(abstractSquare instanceof NumberSquare){

            /* Reveal it */
            abstractSquare.setRevealedSquare(true);
            abstractSquare.revealSelectedSquareOnClick();

            /* Update color of Display's Effect */
            this.displayDropShadowEffect.setColor(getDisplayEffectColor());

            /* Change progressBar Color */
            this.progressBar.setStyle(progressBarStringColor());

        }else if(abstractSquare instanceof EmptySquare){

            /* Discover territory around it */
            discoverTerritory(row,col);

            /* Update color of Display's Effect */
            this.displayDropShadowEffect.setColor(getDisplayEffectColor());

            /* Change progressBar's Color */
            this.progressBar.setStyle(progressBarStringColor());

        }else if(abstractSquare instanceof MineSquare){

            /* Discover all mines first */
            discoverAllMines();

            /* Highlight the one that is clicked with new char */
            abstractSquare.setText("" + (char)(9760));
            abstractSquare.setStyle(guessedSquareStyleString);

            /* Update display's effect color */
            this.displayDropShadowEffect.setColor(Color.BLACK);

            /* Update Mouse Clicked Button effect and colors and coordinates BEFORE end game */
            this.mouseClickedButton.setText(abstractSquare.getText());
            this.mouseClickedButton.setTextFill(Color.RED);
            this.mouseClickedButton.setEffect(this.displayDropShadowEffect);
            this.columnNumberButton.setText("" + (abstractSquare.getColDigit() + 1));
            this.rowNumberButton.setText("" + (abstractSquare.getRowDigit() + 1));

            /* End The Game */
            this.status = endGameStatusMethod();
            this.status.startStopTimer_EndGameDialog(this);
        }

        /* Update progress bar and indicators */
        this.progressBar.setProgress(countProgressBarPercentage());
        this.progressIndicator11.setProgress(1.0 - this.progressBar.getProgress());
        this.progressIndicator22.setProgress(this.progressBar.getProgress());

        /* Update mouseClicked Button along with colors and Effect - Only if it is a mouse click */
        if(mouseClick){
            Color absColor = (Color) abstractSquare.getTextFill();
            DropShadow DSEffect = (DropShadow) this.mouseClickedButton.getEffect();
            this.mouseClickedButton.setText(abstractSquare.getText());
            this.mouseClickedButton.setTextFill(abstractSquare.getTextFill());
            DSEffect.setColor(absColor);
            this.columnNumberButton.setText("" + (abstractSquare.getColDigit() + 1));
            this.rowNumberButton.setText("" + (abstractSquare.getRowDigit() + 1));
        }

        /* For every click check if Map is Revealed and if mines guessed are set to 0 then Congratulations finished game */
        if(isGameFinished()){
            this.status = endGameStatusMethod();
            this.status.startStopTimer_EndGameDialog(this);
        }
    }



    /* Count squares percent for progress bar and indicators */
    public double countProgressBarPercentage(){
        int totalSquares = this.inGameDisplay.getDisplay2DArray().length * this.inGameDisplay.getDisplay2DArray()[0].length;
        double result;
        try{
            /* Transform the result into a number range from 0.0 to 1.0 for progressBar and Indicators */
            result = (AbstractSquare.getNumberOfRevealedSquares() * 1.0 / totalSquares);
        }catch (ArithmeticException e){
            result = 0.0;
        }
        return result;
    }

    /* Get the String to change Style and Color for progressBar during play according to its percentage */
    private String progressBarStringColor(){

        /* Between 0 and 24 percent is Red */
        if(this.countProgressBarPercentage() < 0.24){
            return "-fx-accent: RED";

            /* Between 24 and 76 percent is YellowGreen */
        }else if(this.countProgressBarPercentage() < 0.76){
            return "-fx-accent: YELLOWGREEN";

            /* Between 76 and 100 percent is LightGreen */
        }else{
            return "-fx-accent: LIGHTGREEN";
        }
    }

    /* Check if Game is Finished - meaning that all squares where revealed and mines guessed */
    boolean isGameFinished(){
        return countProgressBarPercentage() == 1.0 && this.minesButton.getText().equals("0") && this.flaggedMines == this.getInGameDisplay().getMines();
    }

    /* Discover all Mines function */
    protected void discoverAllMines(){
        for (MineSquare mineSquare : this.mineSquaresList) {
            mineSquare.setRevealedSquare(true);
            mineSquare.revealSelectedSquareOnClick();
        }
    }

    /* Get end Game Status */
    protected Status endGameStatusMethod(){
        if(!isGameFinished()){
            return Status.GAME_OVER;
        }
        if(this.status == Status.STARTED_CUSTOM_GAME){
            this.status = Status.FINISHED_CUSTOM_GAME;
        }else if(this.status == Status.STARTED_RATED_GAME){
            this.status = Status.FINISHED_RATED_GAME;
        }
        return this.status;
    }



    /* Get Color updating the color parameter values according to progress value to switch Display color from Red to Green during reveal map in game play */
    private Color getDisplayEffectColor(){
        double colorProgressValue = Double.parseDouble(String.format("%.2f",countProgressBarPercentage()));
        return new Color(1.0 - colorProgressValue, colorProgressValue,0.0,1.0);
    }



    /* Discover territory function using Breadth First Search algorithm with ArrayDeque implementation */
    protected void discoverTerritory(int row, int col){
        ArrayDeque<EmptySquare> squaresAroundCell = new ArrayDeque<>();  // BFS around Empty square cell

        /* Store the EmptySquare that was clicked first from which to start from */
        squaresAroundCell.add((EmptySquare) this.inGameDisplay.getDisplay2DArray()[row][col]);

        /* Add EmptySquares around that first one from above and level by level (BFS) until we hit the edge of display or a NumberSquare */
        while(!(squaresAroundCell.isEmpty())){

            /* Poll emptySquare from Deque */
            EmptySquare emptySquare = squaresAroundCell.poll();

            /* Reveal the square, so we don't stumble upon it again later and end up in an infinite loop */
            if(!emptySquare.isRevealedSquare()){
                emptySquare.setRevealedSquare(true);
                emptySquare.revealSelectedSquareOnClick();
            }

            /* Store those squares that surround the EmptySquare so this way we go level by level for every Square (BFS) */
            for(int i = 0; i < colCoordinatesAroundSquareDigitToAdd.length; i++){
                int rowSquare = rowCoordinatesAroundSquareDigitToAdd[i] + emptySquare.getRowDigit();
                int colSquare = colCoordinatesAroundSquareDigitToAdd[i] + emptySquare.getColDigit();
                if(isValidSquareCell(rowSquare,colSquare) && (this.inGameDisplay.getDisplay2DArray()[rowSquare][colSquare] instanceof EmptySquare) && !((AbstractSquare) this.inGameDisplay.getDisplay2DArray()[rowSquare][colSquare]).isRevealedSquare()){
                    squaresAroundCell.add((EmptySquare) this.inGameDisplay.getDisplay2DArray()[rowSquare][colSquare]);

                    /* If it is a NumberSquare... just reveal it */
                }else if(isValidSquareCell(rowSquare,colSquare) && (this.inGameDisplay.getDisplay2DArray()[rowSquare][colSquare] instanceof NumberSquare) && !((AbstractSquare) this.inGameDisplay.getDisplay2DArray()[rowSquare][colSquare]).isRevealedSquare()){
                    ((AbstractSquare) this.inGameDisplay.getDisplay2DArray()[rowSquare][colSquare]).setRevealedSquare(true);
                    ((AbstractSquare) this.inGameDisplay.getDisplay2DArray()[rowSquare][colSquare]).revealSelectedSquareOnClick();
                }
            }
        }
    }

    /* Getting squareNumber digit from mines around the square cell. Max could be 8 and if no mines around this square then return 0 */
    private int getDigitForNumberSquare(int row, int col){
        int numberSquareResult = 0;
        for(int i = 0; i < rowCoordinatesAroundSquareDigitToAdd.length; i++){
            int rowMineCheck = rowCoordinatesAroundSquareDigitToAdd[i] + row;
            int colMineCheck = colCoordinatesAroundSquareDigitToAdd[i] + col;
            if(isValidSquareCell(rowMineCheck,colMineCheck) && (this.inGameDisplay.getDisplay2DArray()[rowMineCheck][colMineCheck] instanceof MineSquare)){
                numberSquareResult++;
            }
        }
        return numberSquareResult;
    }

    /* Counts the number of marked squares (marked with '?' - alias Guessed Mines) */
    public int countGuessedMinesAroundNumberSquare(NumberSquare numberSquare){
        int guessedMines = 0;
        for(int i = 0; i < rowCoordinatesAroundSquareDigitToAdd.length; i++){
            int rowAdd = rowCoordinatesAroundSquareDigitToAdd[i] + numberSquare.getRowDigit();
            int colAdd = colCoordinatesAroundSquareDigitToAdd[i] + numberSquare.getColDigit();
            if(isValidSquareCell(rowAdd,colAdd) && this.inGameDisplay.getDisplay2DArray()[rowAdd][colAdd].getText().equals("?")){
                guessedMines++;
            }
        }
        return guessedMines;
    }

    /* Single-mouse-click NumberSquare - mouse Release method - DeSelects the number of marked squares (marked with '?' - alias Guessed Mines) */
    public void deSelectSquaresAroundNumberSquare(NumberSquare numberSquare) {
        for (int i = 0; i < rowCoordinatesAroundSquareDigitToAdd.length; i++) {
            int rowAdd = rowCoordinatesAroundSquareDigitToAdd[i] + numberSquare.getRowDigit();
            int colAdd = colCoordinatesAroundSquareDigitToAdd[i] + numberSquare.getColDigit();
            if (isValidSquareCell(rowAdd, colAdd) && !((AbstractSquare) this.inGameDisplay.getDisplay2DArray()[rowAdd][colAdd]).isRevealedSquare()) {
                this.inGameDisplay.getDisplay2DArray()[rowAdd][colAdd].setSelected(false);
                this.inGameDisplay.getDisplay2DArray()[rowAdd][colAdd].setText(" ");
            }
        }
    }

    /* Single-mouse-click NumberSquare - mouse Pressed method - Selects the number of marked squares (marked with '?' - alias Guessed Mines) */
    public void selectSquaresAroundNumberSquare(NumberSquare numberSquare) {
        for (int i = 0; i < rowCoordinatesAroundSquareDigitToAdd.length; i++) {
            int rowAdd = rowCoordinatesAroundSquareDigitToAdd[i] + numberSquare.getRowDigit();
            int colAdd = colCoordinatesAroundSquareDigitToAdd[i] + numberSquare.getColDigit();
            if (isValidSquareCell(rowAdd, colAdd) && !((AbstractSquare) this.inGameDisplay.getDisplay2DArray()[rowAdd][colAdd]).isRevealedSquare()) {
                this.inGameDisplay.getDisplay2DArray()[rowAdd][colAdd].setSelected(true);
                this.inGameDisplay.getDisplay2DArray()[rowAdd][colAdd].setText("" + (char)(9760));
            }
        }
    }

    /* Available method ONLY for NumberSquares - They are only squares that are still able to click on after reveal */
    /* Double-Click a NumberSquare - if the number is equal to the Guessed Mines (marked with '?') then discover the rest of the squares around NumberSquare because is safe */
    /* Attention - Be sure your Guessed Mines are Really mines when double-click or else you might lose the game and that's bad :) */
    public void discoverOnDoubleClickWhenGuessedMinesEqualsNumberMethod(NumberSquare numberSquare){
        for(int i = 0; i < rowCoordinatesAroundSquareDigitToAdd.length; i++){
            int rowAdd = rowCoordinatesAroundSquareDigitToAdd[i] + numberSquare.getRowDigit();
            int colAdd = colCoordinatesAroundSquareDigitToAdd[i] + numberSquare.getColDigit();
            if(isValidSquareCell(rowAdd,colAdd) && !this.inGameDisplay.getDisplay2DArray()[rowAdd][colAdd].getText().equals("?") && !((AbstractSquare) this.inGameDisplay.getDisplay2DArray()[rowAdd][colAdd]).isRevealedSquare()){
                AbstractSquare abstractSquare = (AbstractSquare) this.inGameDisplay.getDisplay2DArray()[rowAdd][colAdd];
                onClickMethod(abstractSquare.getRowDigit(), abstractSquare.getColDigit(),false);

                /* If you stumble upon a MineSquare you loose so here return because we have above the onClickMethod which handles end Game */
                if(abstractSquare instanceof MineSquare){
                    return;
                }
            }
        }
    }

    /* Check margins coordinates for square cell in display2DArray */
    public boolean isValidSquareCell(int row, int col){
        return row >= 0 && row <= this.inGameDisplay.getDisplay2DArray().length - 1 && col >= 0 && col <= this.inGameDisplay.getDisplay2DArray()[0].length - 1;
    }

    /* Action method for Back To Menu Button */
    @FXML
    public void backToMainMenuCloseButtonAction() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menu-view.fxml")));
        Stage mainMenu = (Stage) inGameMainMenuCloseButton.getScene().getWindow();
        mainMenu.setScene(new Scene(root));
    }

    public void showEndGameDialogPane(Status status) throws IOException {

        /* Check if is Rated Game First */
        if(this.inGameDifficulty != Difficulty.CUSTOM){

            /* If Rated game then setup Player and write it to File */
            setupAndBuildPlayer();
            writePlayerToFile();
        }

        /* Load Controller for endGameDialog to create its objects and set them up */
        FXMLLoader loader = new FXMLLoader(getClass().getResource("endgamepane-view.fxml"));
        Pane root = loader.load();
        EndGameDialogPaneController endGameDialogPaneController = loader.getController();

        Stage stage = new Stage();

        /* Wait until an event happens - like close dialog or back to menu - before giving permit to other windows of the application */
        stage.initModality(Modality.APPLICATION_MODAL);

        /* Get rid of close, minimize, maximize or full-screen buttons decorated window from Windows */
        stage.initStyle(StageStyle.UNDECORATED);

        /* Show player Game statistics */
        endGameDialogPaneController.minesSweptButton.setText("" + this.flaggedMines + "/" + getInGameDisplay().getMines());
        endGameDialogPaneController.mouseClicksButton.setText("" + countMouseClicks);
        endGameDialogPaneController.timeButton.setText("" + timerButton.getText() + ":" + String.format("%02d",seconds[0] % 60));
        endGameDialogPaneController.endGameProgressIndicator.setProgress(Double.parseDouble(String.format("%.2f",countProgressBarPercentage())));

        /* If is not Game Over then you finished the game. Congratulations :) */
        if(status != Status.GAME_OVER){
            endGameDialogPaneController.gameOverAndCongratButton.setText("Congratulations!");
            endGameDialogPaneController.gameOverAndCongratButton.setTextFill(Color.BLUE);
        }

        /* Set Mouse Clicked event for Congratulations or Game Over Button if it is Custom Game you can still play but doesn't count */
        endGameDialogPaneController.gameOverAndCongratButton.setOnMouseClicked(e -> {
            if(getInGameDifficulty() == Difficulty.CUSTOM) {
                    stage.close();
            }
        });

        /* Drag EndGameDialogPane Window to be able to see the mines Display */
        root.setOnMousePressed(pressEvent -> {
            root.setOnMouseDragged(dragEvent -> {
                stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });

        /* Mouse click event for Back To Main Menu Button */
        endGameDialogPaneController.backToMenuButton.setOnMouseClicked(e -> {
            try {
                backToMainMenuCloseButtonAction();
                stage.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        Scene scene = new Scene(root);
        stage.setScene(scene);

        /* Show and wait until you click on a button */
        stage.showAndWait();
    }

    /** Player */
    void setupAndBuildPlayer(){

        /* If exists already get it from list or else add new Player to list */
        int listIndexOfPlayer = inGameList.indexOf(this.player);
        if(listIndexOfPlayer != -1){
            this.player = inGameList.get(listIndexOfPlayer);
        }else{
            inGameList.add(this.player);
        }

        /* Update player statistics - OLD versus NEW */
        this.player.setTotalGamesPlayed(this.player.getTotalGamesPlayed() + 1);
        if(this.status == Status.FINISHED_RATED_GAME){
            this.player.setGamesFinished(this.player.getGamesFinished() + 1);
        }
        this.player.setPercentageFinishedGames(player.calculatePercentageFinishedGames(player.getTotalGamesPlayed(),player.getGamesFinished()));
        this.player.setBestNumberOfSweptMines(Math.max(this.flaggedMines, this.player.getBestNumberOfSweptMines()));
        if(this.status == Status.FINISHED_RATED_GAME) {
            if (this.player.getBestNumberOfMouseClicks() > 0) {
                this.player.setBestNumberOfMouseClicks(Math.min(Integer.parseInt(this.mouseClicksButton.getText()), this.player.getBestNumberOfMouseClicks()));
            } else {
                this.player.setBestNumberOfMouseClicks(Integer.parseInt(this.mouseClicksButton.getText()));
            }
            if (this.player.getBestTimeInSeconds() > 0) {
                this.player.setBestTimeInSeconds(Math.min(this.seconds[0], this.player.getBestTimeInSeconds()));
            } else {
                this.player.setBestTimeInSeconds(this.seconds[0]);
            }
        }
        this.player.setBestPercentageRevealedMap(Math.max(this.progressBar.getProgress() * 100.0, this.player.getBestPercentageRevealedMap()));
    }

    /* Write Player to file on End RATED Game */
    public void writePlayerToFile(){
        Path path = Main.topPath;

        Path fileTopEasy = Path.of(path.toString(),"bestPlayersEasy.dat");
        Path fileTopNormal = Path.of(path.toString(),"bestPlayersNormal.dat");
        Path fileTopHard = Path.of(path.toString(),"bestPlayersHard.dat");

        /* Check if RATED Game first then update the specific file list be it easy, normal or hard */
        if(inGameDifficulty != Difficulty.CUSTOM){
            if(inGameDifficulty == Difficulty.EASY){
                try(ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileTopEasy.toString())))){
                    try {
                        objectOutputStream1.writeObject(inGameList);
                        objectOutputStream1.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }catch (IOException f){
                    f.printStackTrace();
                }
            }else if(inGameDifficulty == Difficulty.NORMAL){
                try(ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileTopNormal.toString())))){
                    try {
                        objectOutputStream2.writeObject(inGameList);
                        objectOutputStream2.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }catch (IOException f){
                    f.printStackTrace();
                }
            }else if(inGameDifficulty == Difficulty.HARD){
                try(ObjectOutputStream objectOutputStream3 = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileTopHard.toString())))){
                    try {
                        objectOutputStream3.writeObject(inGameList);
                        objectOutputStream3.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }catch (IOException f){
                    f.printStackTrace();
                }
            }
        }
    }

    /** Getters and Setters */
    public Display getInGameDisplay() {
        return this.inGameDisplay;
    }

    public void setInGameDisplay(Display inGameDisplay) {
        this.inGameDisplay = inGameDisplay;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timeline getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(Timeline timeLine) {
        this.timeLine = timeLine;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GridPane getInGameGridPane() {
        return inGameGridPane;
    }

    public void setInGameGridPane(GridPane inGameGridPane) {
        this.inGameGridPane = inGameGridPane;
    }

    public Button getMouseClicksButton() {
        return mouseClicksButton;
    }

    public void setMouseClicksButton(Button mouseClicksButton) {
        this.mouseClicksButton = mouseClicksButton;
    }

    public Button getTimerButton() {
        return timerButton;
    }

    public void setTimerButton(Button timerButton) {
        this.timerButton = timerButton;
    }

    public List<Player> getInGameList() {
        return inGameList;
    }

    public void setInGameList(List<Player> inGameList) {
        this.inGameList = inGameList;
    }

    public Difficulty getInGameDifficulty() {
        return inGameDifficulty;
    }

    public void setInGameDifficulty(Difficulty inGameDifficulty) {
        this.inGameDifficulty = inGameDifficulty;
    }

    public DropShadow getDisplayDropShadowEffect() {
        return displayDropShadowEffect;
    }

    public void setDisplayDropShadowEffect(DropShadow displayDropShadowEffect) {
        this.displayDropShadowEffect = displayDropShadowEffect;
    }

    public List<MineSquare> getMineSquaresList() {
        return mineSquaresList;
    }

    public void setMineSquaresList(List<MineSquare> mineSquaresList) {
        this.mineSquaresList = mineSquaresList;
    }

    public String getGuessedSquareStyleString() {
        return guessedSquareStyleString;
    }

    public static int getCountMouseClicks() {
        return countMouseClicks;
    }

    public static void setCountMouseClicks(int countMouseClicks) {
        InGameController.countMouseClicks = countMouseClicks;
    }

    public int getFlaggedMines() {
        return flaggedMines;
    }

    public void setFlaggedMines(int flaggedMines) {
        this.flaggedMines = flaggedMines;
    }

    public int[] getSeconds() {
        return seconds;
    }

    public void setSeconds(int[] seconds) {
        this.seconds = seconds;
    }
}
