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

public class InGameController implements Initializable {

    @FXML
    ProgressIndicator progressIndicator22;
    @FXML
    ProgressIndicator progressIndicator11;

    @FXML
    private ImageView inGameMainMenuCloseButton;

    @FXML
    GridPane inGameGridPane;

    @FXML
    ProgressBar progressBar;

    @FXML
    VBox lastClickVBox;
    @FXML
    VBox lastClickCoordsVBox;

    @FXML
    Button secondsInGameButton;
    @FXML
    Button mouseClickedButton;
    @FXML
    Button columnNumberButton;
    @FXML
    Button rowNumberButton;
    @FXML
    Button minesButton;
    @FXML
    Button mouseClicksButton;
    @FXML
    Button timerButton;

    Display inGameDisplay;
    Timeline timeLine;
    Player player;
    List<Player> inGameList;
    Difficulty inGameDifficulty;
    Status status;
    DropShadow dropShadowEffect;
    List<MineSquare> mineSquaresList;
    Tooltip minesToDiscoverTool;
    Tooltip progressBarTool;
    Tooltip timerTool;
    Tooltip mouseClicksTool;
    Tooltip lastMouseClickSquareTool;
    Tooltip lastMouseClickCoordsTool;
    String guessedSquareStyleString = "-fx-background-color: transparent; -fx-text-fill: WHITE; -fx-border-color:GRAY; -fx-border-width: 0.7 0.7 0.7 0.7;";
    static int countMouseClicks = 0;
    int flaggedMines = 0;
    int[] seconds;

    /* BFS coordinates around a square cell */
    private final int[] rowCoordsAroundSquareDigitToAdd = {-1, 0, 1, 1, 1, 0,-1,-1};
    private final int[] colCoordsAroundSquareDigitToAdd = {-1,-1,-1, 0, 1, 1, 1, 0};
    private final int[] crossRowCoordsAroundSquare = {-1, 0, 1, 0};
    private final int[] crossColCoordsAroundSquare = { 0, 1, 0,-1};

    public InGameController()  {
        this.status = Status.INITIAL_STATE;
        this.mineSquaresList = new ArrayList<>();
        this.timeLine = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            seconds[0]++;
            timerButton.setText(String.format("%02d:%02d", seconds[0] / 3600, (seconds[0] % 3600) / 60));
            secondsInGameButton.setText(String.format("%02d",seconds[0] % 60));
        }));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.seconds = new int[]{0};
        this.progressBarTool = new Tooltip("Map Revealed Percentage");
        this.timerTool = new Tooltip("Timer");
        this.mouseClicksTool = new Tooltip("Mouse Clicks");
        this.minesToDiscoverTool = new Tooltip("Remaining Mines");
        this.lastMouseClickSquareTool = new Tooltip("Last Mouse Click Square");
        this.lastMouseClickCoordsTool = new Tooltip("Last Mouse Click Square Coordinates");
        Tooltip.install(this.minesButton,this.minesToDiscoverTool);
        Tooltip.install(this.secondsInGameButton,this.timerTool);
        Tooltip.install(this.progressBar,this.progressBarTool);
        Tooltip.install(this.progressIndicator11,this.progressBarTool);
        Tooltip.install(this.progressIndicator22,this.progressBarTool);
        Tooltip.install(this.timerButton,timerTool);
        Tooltip.install(this.mouseClicksButton,this.mouseClicksTool);
        Tooltip.install(this.lastClickVBox,this.lastMouseClickSquareTool);
        Tooltip.install(this.lastClickCoordsVBox,this.lastMouseClickCoordsTool);
        Tooltip.install(this.mouseClickedButton,this.lastMouseClickSquareTool);
    }

    /* Populate Display on first Click */
    public void initializeDisplayOnFirstClick(GridPane pane, int rowFirstClick, int colFirstClick){
        pane.getChildren().clear();
        this.dropShadowEffect = (DropShadow) pane.getEffect();
        /* Add all mines randomly */
        Random mineRow = new Random();
        Random mineCol = new Random();
        while(this.inGameDisplay.getMines() > this.mineSquaresList.size()) {
            int rowM = mineRow.nextInt(this.inGameDisplay.toggleButtonsSquares2DArray.length);
            int colM = mineCol.nextInt(this.inGameDisplay.toggleButtonsSquares2DArray[0].length);
            if (!(rowM == rowFirstClick && colM == colFirstClick) && !(this.inGameDisplay.toggleButtonsSquares2DArray[rowM][colM] instanceof MineSquare)) {
                MineSquare mineSquare = new MineSquare(rowM, colM);
                this.mineSquaresList.add(mineSquare);
                this.inGameDisplay.toggleButtonsSquares2DArray[rowM][colM] = mineSquare;
            }
        }

        /* Populate NumberSquares and EmptySquares together with their mouse click action buttons on First click */
        for(int i = 0; i < this.inGameDisplay.toggleButtonsSquares2DArray.length; i++){
            for(int j = 0; j < this.inGameDisplay.toggleButtonsSquares2DArray[0].length; j++){
                if(!(this.inGameDisplay.toggleButtonsSquares2DArray[i][j] instanceof MineSquare)){
                    int digit = getDigitForNumberSquare(i,j);
                    if(digit == 0){
                        EmptySquare emptySquare = new EmptySquare(i,j);
                        this.inGameDisplay.toggleButtonsSquares2DArray[i][j] = emptySquare;
                    }else{
                        NumberSquare numberSquare = new NumberSquare(i,j,digit);
                        this.inGameDisplay.toggleButtonsSquares2DArray[i][j] = numberSquare;
                        numberSquare.setOnMousePressed(f -> {
                            if(!numberSquare.getText().equals("?")){
                                if(f.getButton() == MouseButton.PRIMARY && numberSquare.isRevealedSquare()){
                                    int guessedMines = countGuessedMinesAroundNumberSquare(numberSquare);
                                    if(f.getClickCount() == 2 && guessedMines == numberSquare.getNumber()){
                                        guessedMinesEqualsNumberMethod(numberSquare);
                                        countMouseClicks++;
                                        this.mouseClicksButton.setText("" + InGameController.countMouseClicks);
                                    }else if(f.getClickCount() == 1){
                                        selectSquaresAroundNumberSquare(numberSquare);
                                        numberSquare.setSelected(true);
                                    }

                                }
                            }
                        });
                        numberSquare.setOnMouseReleased(f -> {
                            if(!numberSquare.getText().equals("?")){
                                if(f.getButton() == MouseButton.PRIMARY && numberSquare.isRevealedSquare()){
                                    deSelectSquaresAroundNumberSquare(numberSquare);
                                    numberSquare.setSelected(true);
                                }
                            }
                        });
                    }
                }
                int finalI = i;
                int finalJ = j;
                String initStyle = this.inGameDisplay.toggleButtonsSquares2DArray[finalI][finalJ].getStyle();
                this.inGameDisplay.toggleButtonsSquares2DArray[i][j].setOnMouseClicked(e -> {
                        if(e.getButton() == MouseButton.PRIMARY && !((AbstractSquare)this.inGameDisplay.toggleButtonsSquares2DArray[finalI][finalJ]).isRevealedSquare()){
                            if(!this.inGameDisplay.toggleButtonsSquares2DArray[finalI][finalJ].getText().equals("?")){
                                onClickMethod(finalI, finalJ,true);
                                countMouseClicks++;
                                this.mouseClicksButton.setText("" + InGameController.countMouseClicks);
                            }
                        }else if(e.getButton() == MouseButton.SECONDARY){
                            if(this.inGameDisplay.toggleButtonsSquares2DArray[finalI][finalJ].getText().equals("?")){
                                this.inGameDisplay.toggleButtonsSquares2DArray[finalI][finalJ].setText(" ");
                                this.inGameDisplay.toggleButtonsSquares2DArray[finalI][finalJ].setStyle(initStyle);
                                ((AbstractSquare)this.inGameDisplay.toggleButtonsSquares2DArray[finalI][finalJ]).setRevealedSquare(false);
                                AbstractSquare.numberOfRevealedSquares--;
                                if(this.inGameDisplay.toggleButtonsSquares2DArray[finalI][finalJ] instanceof MineSquare){
                                    this.flaggedMines--;
                                }
                                this.progressBar.setProgress(countProgressBarPercentage());
                                this.progressIndicator11.setProgress(this.progressBar.getProgress());
                                this.progressIndicator22.setProgress(this.progressBar.getProgress());
                                this.dropShadowEffect.setColor(this.getColor());
                                this.progressBar.setStyle(progressBarStringColor());
                                int mines = Integer.parseInt(this.minesButton.getText());
                                mines++;
                                this.minesButton.setText("" + mines);
                            }else if(!((AbstractSquare)this.inGameDisplay.toggleButtonsSquares2DArray[finalI][finalJ]).isRevealedSquare()){
                                this.inGameDisplay.toggleButtonsSquares2DArray[finalI][finalJ].setText("?");
                                this.inGameDisplay.toggleButtonsSquares2DArray[finalI][finalJ].setStyle(guessedSquareStyleString);
                                ((AbstractSquare)this.inGameDisplay.toggleButtonsSquares2DArray[finalI][finalJ]).setRevealedSquare(true);
                                AbstractSquare.numberOfRevealedSquares++;
                                if(this.inGameDisplay.toggleButtonsSquares2DArray[finalI][finalJ] instanceof MineSquare){
                                    this.flaggedMines++;
                                }
                                this.progressBar.setProgress(countProgressBarPercentage());
                                this.progressIndicator11.setProgress(this.progressBar.getProgress());
                                this.progressIndicator22.setProgress(this.progressBar.getProgress());
                                this.dropShadowEffect.setColor(this.getColor());
                                this.progressBar.setStyle(progressBarStringColor());
                                int mines = Integer.parseInt(this.minesButton.getText());
                                mines--;
                                this.minesButton.setText("" + mines);
                                countMouseClicks++;
                                this.mouseClicksButton.setText("" + InGameController.countMouseClicks);
                                if(isGameFinished()){
                                    this.status = endGameStatusMethod();
                                    this.status.startStopShowStatsAndEndGameDialog(this);
                                }
                            }
                        }
                });
                this.inGameDisplay.toggleButtonsSquares2DArray[i][j].setFocusTraversable(false);
                pane.add(this.inGameDisplay.toggleButtonsSquares2DArray[i][j],j,i);
            }
        }
        this.status.startStopShowStatsAndEndGameDialog(this);
        onClickMethod(rowFirstClick, colFirstClick,true);
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /* Click Method for Square */
    protected void onClickMethod(int row, int col, boolean mouseClick){
        AbstractSquare abstractSquare = (AbstractSquare) this.inGameDisplay.toggleButtonsSquares2DArray[row][col];
        boolean isStillRevealedHereAtThisPoint = abstractSquare.isRevealedSquare();
        if(abstractSquare instanceof NumberSquare){
            abstractSquare.setRevealedSquare(true);
            abstractSquare.revealedAndDiscoveredSquareDisplayOnClick();
            if(this.dropShadowEffect != null){
                this.dropShadowEffect.setColor(getColor());
                this.progressBar.setStyle(progressBarStringColor());
            }
        }else if(abstractSquare instanceof EmptySquare){
            discoverTerritory(row,col);
            if(this.dropShadowEffect != null){
                this.dropShadowEffect.setColor(getColor());
                this.progressBar.setStyle(progressBarStringColor());
            }
        }else if(abstractSquare instanceof MineSquare){
            discoverAllMines();
            abstractSquare.setText("" + (char)(9760));
            abstractSquare.setTextFill(Color.RED);
            if(this.dropShadowEffect != null){
                abstractSquare.setStyle(guessedSquareStyleString);
                this.dropShadowEffect.setColor(Color.BLACK);
                this.mouseClickedButton.setText(abstractSquare.getText());
                this.mouseClickedButton.setTextFill(abstractSquare.getTextFill());
                this.mouseClickedButton.setEffect(this.dropShadowEffect);
                this.columnNumberButton.setText("" + (abstractSquare.getColDigit() + 1));
                this.rowNumberButton.setText("" + (abstractSquare.getRowDigit() + 1));
            }

            this.status = endGameStatusMethod();
            this.status.startStopShowStatsAndEndGameDialog(this);
        }
        if(!isStillRevealedHereAtThisPoint){  // For guessedMinesEqualsNumberMethod() Method
            this.progressBar.setProgress(countProgressBarPercentage());//Update progressBar
            this.progressIndicator11.setProgress(this.progressBar.getProgress());
            this.progressIndicator22.setProgress(this.progressBar.getProgress());
        }
        if(mouseClick){
            Color absColor = (Color) abstractSquare.getTextFill();
            DropShadow DSEffect = (DropShadow) this.mouseClickedButton.getEffect();
            this.mouseClickedButton.setText(abstractSquare.getText());
            this.mouseClickedButton.setTextFill(abstractSquare.getTextFill());
            DSEffect.setColor(absColor);
            this.columnNumberButton.setText("" + (abstractSquare.getColDigit() + 1));
            this.rowNumberButton.setText("" + (abstractSquare.getRowDigit() + 1));
        }
        if(isGameFinished()){
            this.status = endGameStatusMethod();
            this.status.startStopShowStatsAndEndGameDialog(this);
        }
    }

    /** Player */
    void setupAndBuildPlayer(){
        int listIndexOfPlayer = inGameList.indexOf(this.player);
        if(listIndexOfPlayer != -1){
            this.player = inGameList.get(listIndexOfPlayer);
             inGameList.set(listIndexOfPlayer, this.player);
        }else{
            inGameList.add(this.player);
        }
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

    /* Write Player to file on End Game */
    public void writePlayerToFile(){
        Path path = Main.topPath;

        Path fileTopEasy = Path.of(path.toString(),"bestPlayersEasy.dat");
        Path fileTopNormal = Path.of(path.toString(),"bestPlayersNormal.dat");
        Path fileTopHard = Path.of(path.toString(),"bestPlayersHard.dat");

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

    /* Check if Game is Finished */
    boolean isGameFinished(){
        return countProgressBarPercentage() == 1.0 && this.minesButton.getText().equals("0") && this.flaggedMines == this.getInGameDisplay().getMines();
    }

    /* Discover all Mines function */
    protected void discoverAllMines(){
        for (MineSquare mineSquare : this.mineSquaresList) {
            mineSquare.setRevealedSquare(true);
            mineSquare.revealedAndDiscoveredSquareDisplayOnClick();
        }
    }

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

    /* Count squares percent for progress bar */
    public double countProgressBarPercentage(){
        int totalSquares = this.inGameDisplay.toggleButtonsSquares2DArray.length * this.inGameDisplay.toggleButtonsSquares2DArray[0].length;
        double result;
        try{
            result = (AbstractSquare.numberOfRevealedSquares * 1.0 / totalSquares);
        }catch (ArithmeticException e){
            result = 0.0;
        }
        return result;
    }

    private Color getColor(){
        double color = Double.parseDouble(String.format("%.2f",countProgressBarPercentage()));
        return new Color(1.0-color,color,0.0,1.0);
    }

    private String progressBarStringColor(){
        if(this.countProgressBarPercentage() < 0.24){
            return "-fx-accent: RED";
        }else if(this.countProgressBarPercentage() < 0.76){
            return "-fx-accent: YELLOWGREEN";
        }else{
            return "-fx-accent: GREEN";
        }
    }

    /* Discover territory function */
    protected void discoverTerritory(int row, int col){
        ArrayDeque<EmptySquare> squaresAroundCell = new ArrayDeque<>();  // BFS around Empty square cell
        squaresAroundCell.add((EmptySquare) this.inGameDisplay.toggleButtonsSquares2DArray[row][col]);
        while(!(squaresAroundCell.isEmpty())){
            EmptySquare emptySquare = squaresAroundCell.poll();
            if(!emptySquare.isRevealedSquare()){
                emptySquare.setRevealedSquare(true);
                emptySquare.revealedAndDiscoveredSquareDisplayOnClick();
            }
            for(int i = 0; i < this.crossRowCoordsAroundSquare.length; i++){
                int rowSquare = this.crossRowCoordsAroundSquare[i] + emptySquare.getRowDigit();
                int colSquare = this.crossColCoordsAroundSquare[i] + emptySquare.getColDigit();
                if(isValidSquareCell(rowSquare,colSquare) && (this.inGameDisplay.toggleButtonsSquares2DArray[rowSquare][colSquare] instanceof EmptySquare) && !((AbstractSquare) this.inGameDisplay.toggleButtonsSquares2DArray[rowSquare][colSquare]).isRevealedSquare()){
                    squaresAroundCell.add((EmptySquare) this.inGameDisplay.toggleButtonsSquares2DArray[rowSquare][colSquare]);
                }else if(isValidSquareCell(rowSquare,colSquare) && (this.inGameDisplay.toggleButtonsSquares2DArray[rowSquare][colSquare] instanceof NumberSquare) && !((AbstractSquare) this.inGameDisplay.toggleButtonsSquares2DArray[rowSquare][colSquare]).isRevealedSquare()){
                    ((AbstractSquare)this.inGameDisplay.toggleButtonsSquares2DArray[rowSquare][colSquare]).setRevealedSquare(true);
                    ((AbstractSquare)this.inGameDisplay.toggleButtonsSquares2DArray[rowSquare][colSquare]).revealedAndDiscoveredSquareDisplayOnClick();
                }
            }
        }
    }

    /* Getting squareNumber digit using BFS on first level from mines around the square cell */
    private int getDigitForNumberSquare(int row, int col){
        int numberSquareResult = 0;
        for(int i = 0; i < this.rowCoordsAroundSquareDigitToAdd.length; i++){
            int rowMineCheck = this.rowCoordsAroundSquareDigitToAdd[i] + row;
            int colMineCheck = this.colCoordsAroundSquareDigitToAdd[i] + col;
            if(isValidSquareCell(rowMineCheck,colMineCheck) && (this.inGameDisplay.toggleButtonsSquares2DArray[rowMineCheck][colMineCheck] instanceof MineSquare)){
                numberSquareResult++;
            }
        }
        return numberSquareResult;
    }

    /* Click and NumberSquare if the number is equal to the Guessed Mines and discover the rest of the squares around NumberSquare */
    public int countGuessedMinesAroundNumberSquare(NumberSquare numberSquare){
        int guessedMines = 0;
        for(int i = 0; i < rowCoordsAroundSquareDigitToAdd.length; i++){
            int rowAdd = rowCoordsAroundSquareDigitToAdd[i] + numberSquare.getRowDigit();
            int colAdd = colCoordsAroundSquareDigitToAdd[i] + numberSquare.getColDigit();
            if(isValidSquareCell(rowAdd,colAdd) && this.inGameDisplay.toggleButtonsSquares2DArray[rowAdd][colAdd].getText().equals("?")){
                guessedMines++;
            }
        }
        return guessedMines;
    }

    public void deSelectSquaresAroundNumberSquare(NumberSquare numberSquare) {
        for (int i = 0; i < rowCoordsAroundSquareDigitToAdd.length; i++) {
            int rowAdd = rowCoordsAroundSquareDigitToAdd[i] + numberSquare.getRowDigit();
            int colAdd = colCoordsAroundSquareDigitToAdd[i] + numberSquare.getColDigit();
            if (isValidSquareCell(rowAdd, colAdd) && !((AbstractSquare)this.inGameDisplay.toggleButtonsSquares2DArray[rowAdd][colAdd]).isRevealedSquare()) {
                this.inGameDisplay.toggleButtonsSquares2DArray[rowAdd][colAdd].setSelected(false);
                this.inGameDisplay.toggleButtonsSquares2DArray[rowAdd][colAdd].setText(" ");
            }
        }
    }

    public void selectSquaresAroundNumberSquare(NumberSquare numberSquare) {
        for (int i = 0; i < rowCoordsAroundSquareDigitToAdd.length; i++) {
            int rowAdd = rowCoordsAroundSquareDigitToAdd[i] + numberSquare.getRowDigit();
            int colAdd = colCoordsAroundSquareDigitToAdd[i] + numberSquare.getColDigit();
            if (isValidSquareCell(rowAdd, colAdd) && !((AbstractSquare)this.inGameDisplay.toggleButtonsSquares2DArray[rowAdd][colAdd]).isRevealedSquare()) {
                this.inGameDisplay.toggleButtonsSquares2DArray[rowAdd][colAdd].setSelected(true);
                this.inGameDisplay.toggleButtonsSquares2DArray[rowAdd][colAdd].setText("" + (char)(9760));
            }
        }
    }

    public void guessedMinesEqualsNumberMethod(NumberSquare numberSquare){
        for(int i = 0; i < rowCoordsAroundSquareDigitToAdd.length; i++){
            int rowAdd = rowCoordsAroundSquareDigitToAdd[i] + numberSquare.getRowDigit();
            int colAdd = colCoordsAroundSquareDigitToAdd[i] + numberSquare.getColDigit();
            if(isValidSquareCell(rowAdd,colAdd) && !this.inGameDisplay.toggleButtonsSquares2DArray[rowAdd][colAdd].getText().equals("?") && !((AbstractSquare)this.inGameDisplay.toggleButtonsSquares2DArray[rowAdd][colAdd]).isRevealedSquare()){
                AbstractSquare abstractSquare = (AbstractSquare) this.inGameDisplay.toggleButtonsSquares2DArray[rowAdd][colAdd];
                onClickMethod(abstractSquare.getRowDigit(), abstractSquare.getColDigit(),false);
                if(abstractSquare instanceof MineSquare){
                    return;
                }
            }
        }
    }

    /* Check margins coordinates for square cell in toggleButtonsSquares2DArray */
    public boolean isValidSquareCell(int row, int col){
        return row >= 0 && row <= this.inGameDisplay.toggleButtonsSquares2DArray.length - 1 && col >= 0 && col <= this.inGameDisplay.toggleButtonsSquares2DArray[0].length - 1;
    }

    public Display getInGameDisplay() {
        return this.inGameDisplay;
    }

    public void setInGameDisplay(Display inGameDisplay) {
        this.inGameDisplay = inGameDisplay;
    }

    @FXML
    public void backToMainMenuCloseButtonAction() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("minessweeper-view.fxml")));
        Stage mainMenu = (Stage) inGameMainMenuCloseButton.getScene().getWindow();
        mainMenu.setScene(new Scene(root));
    }

    public void showEndGameDialogPane(Status status) throws IOException {
        if(this.inGameDifficulty != Difficulty.CUSTOM){
            setupAndBuildPlayer();
            writePlayerToFile();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("endgamepane-view.fxml"));
        Pane root = loader.load();
        EndGameDialogPaneController endGameDialogPaneController = loader.getController();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);

        endGameDialogPaneController.minesSweptButton.setText("" + this.flaggedMines + "/" + getInGameDisplay().getMines());
        endGameDialogPaneController.mouseClicksButton.setText("" + countMouseClicks);
        endGameDialogPaneController.timeButton.setText("" + timerButton.getText() + ":" + String.format("%02d",seconds[0] % 60));
        endGameDialogPaneController.endGameProgressIndicator.setProgress(Double.parseDouble(String.format("%.2f",countProgressBarPercentage())));

        if(status != Status.GAME_OVER){
            endGameDialogPaneController.gameOverAndCongratButton.setText("Congratulations!");
            endGameDialogPaneController.gameOverAndCongratButton.setTextFill(Color.BLUE);
        }

        endGameDialogPaneController.gameOverAndCongratButton.setOnMouseClicked(e -> {
            if(status == Status.FINISHED_CUSTOM_GAME) {
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

        /* Mouse click for Back To Main Menu Button */
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
        stage.showAndWait();
    }
}
