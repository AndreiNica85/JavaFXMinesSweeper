package com.project.javafxminessweeper;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.project.javafxminessweeper.Difficulty.*;

public class MenuController implements Initializable{

    @FXML
    private TableView<Player> tableViewHard;
    @FXML
    private TableColumn<Player, Integer> positionNumberColumnTableHard;
    @FXML
    private TableColumn<Player, String> nameColumnTableHard;
    @FXML
    private TableColumn<Player, Double> finishedGamesPercentageColumnHard;
    @FXML
    private TableColumn<Player, Integer> finishedGamesColumnHard;
    @FXML
    private TableColumn<Player, Integer> totalGamesPlayedColumnHard;
    @FXML
    private TableColumn<Player, Integer> bestNumberOfMinesSweptColumnHard;
    @FXML
    private TableColumn<Player, Integer> bestNumberOfMouseClicksColumnHard;
    @FXML
    private TableColumn<Player, Integer> bestTimeInSecondsColumnHard;
    @FXML
    private TableColumn<Player, Double> bestPercentageRevealedMapColumnHard;
    @FXML
    private TableView<Player> tableViewNormal;
    @FXML
    private TableColumn<Player, Integer> positionNumberColumnTableNormal;
    @FXML
    private TableColumn<Player, String> nameColumnTableNormal;
    @FXML
    private TableColumn<Player, Double> finishedGamesPercentageColumnNormal;
    @FXML
    private TableColumn<Player, Integer> finishedGamesColumnNormal;
    @FXML
    private TableColumn<Player, Integer> totalGamesPlayedColumnNormal;
    @FXML
    private TableColumn<Player, Integer> bestNumberOfMinesSweptColumnNormal;
    @FXML
    private TableColumn<Player, Integer> bestNumberOfMouseClicksColumnNormal;
    @FXML
    private TableColumn<Player, Integer> bestTimeInSecondsColumnNormal;
    @FXML
    private TableColumn<Player, Double> bestPercentageRevealedMapColumnNormal;
    @FXML
    private TableView<Player> tableViewEasy;
    @FXML
    private TableColumn<Player, Integer> positionNumberColumnTableEasy;
    @FXML
    private TableColumn<Player, String> nameColumnTableEasy;
    @FXML
    private TableColumn<Player, Double> finishedGamesPercentageColumnEasy;
    @FXML
    private TableColumn<Player, Integer> finishedGamesColumnEasy;
    @FXML
    private TableColumn<Player, Integer> totalGamesPlayedColumnEasy;
    @FXML
    private TableColumn<Player, Integer> bestNumberOfMinesSweptColumnEasy;
    @FXML
    private TableColumn<Player, Integer> bestNumberOfMouseClicksColumnEasy;
    @FXML
    private TableColumn<Player, Integer> bestTimeInSecondsColumnEasy;
    @FXML
    private TableColumn<Player, Double> bestPercentageRevealedMapColumnEasy;

    @FXML
    private Button minesSweeperButton;
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
    private TextField playerName;

    @FXML
    private Label infoTextLabel;
    @FXML
    private Label mainLabel;
    @FXML
    private Label MSweeperLabel;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private AnchorPane anchorPaneStartGame;
    @FXML
    private AnchorPane anchorPaneStartCustomGame;

    @FXML
    private TitledPane titledPaneHard;
    @FXML
    private TitledPane titledPaneNormal;
    @FXML
    private TitledPane titledPaneEasy;
    @FXML
    private Accordion menuAccordion;

    private final Tooltip positionNumberTooltip;
    private final Tooltip nameTooltip;
    private final Tooltip totalGamesTooltip;
    private final Tooltip finishedGamesTooltip;
    private final Tooltip finishedGamesPercentageTooltip;
    private final Tooltip bestTimeTooltip;
    private final Tooltip bestRevealedMapPercentageTooltip;
    private final Tooltip bestMinesSweptNumberTooltip;
    private final Tooltip bestMouseClicksNumberTooltip;
    private final Tooltip customGameColumnsNumberTool;
    private final Tooltip customGameRowsNumberTool;
    private final Tooltip customGameNumberOfMinesTool;

    private final Label posLabelToolEasy;
    private final Label posLabelToolNormal;
    private final Label posLabelToolHard;
    private final Label nameLabelToolEasy;
    private final Label nameLabelToolNormal;
    private final Label nameLabelToolHard;
    private final Label totalGamesLabelToolEasy;
    private final Label totalGamesLabelToolNormal;
    private final Label totalGamesLabelToolHard;
    private final Label finishedGamesLabelToolEasy;
    private final Label finishedGamesLabelToolNormal;
    private final Label finishedGamesLabelToolHard;
    private final Label finishedGamesPercentageLabelToolEasy;
    private final Label finishedGamesPercentageLabelToolNormal;
    private final Label finishedGamesPercentageLabelToolHard;
    private final Label bestTimeLabelToolEasy;
    private final Label bestTimeLabelToolNormal;
    private final Label bestTimeLabelToolHard;
    private final Label bestRevealedMPercentageLabelToolEasy;
    private final Label bestRevealedMPercentageLabelToolNormal;
    private final Label bestRevealedMPercentageLabelToolHard;
    private final Label bestNumberMinesSweptLabelToolEasy;
    private final Label bestNumberMinesSweptLabelToolNormal;
    private final Label bestNumberMinesSweptLabelToolHard;
    private final Label bestNumberMouseClicksLabelToolEasy;
    private final Label bestNumberMouseClicksLabelToolNormal;
    private final Label bestNumberMouseClicksLabelToolHard;

    private String mainLabelText;
    private Display controllerDisplay;
    private Stage stage;
    private Difficulty difficulty;
    private List<Player> best10PlayersEasyList;
    private List<Player> best10PlayersNormalList;
    private List<Player> best10PlayersHardList;

    /** Constructor - Called before initialize() method */
    public MenuController() throws Exception {
        this.customGameColumnsNumberTool = new Tooltip("Number of Columns - Must be between 2 and 28");
        this.customGameRowsNumberTool = new Tooltip("Number of Rows - Must be between 2 and 24");
        this.customGameNumberOfMinesTool = new Tooltip("Number of Mines");

        this.positionNumberTooltip = new Tooltip("Position");
        this.nameTooltip = new Tooltip("Player Name");
        this.totalGamesTooltip = new Tooltip("Total Games");
        this.finishedGamesTooltip = new Tooltip("Games Finished");
        this.finishedGamesPercentageTooltip = new Tooltip("Games Finished Percentage %");
        this.bestTimeTooltip = new Tooltip("Best Time");
        this.bestRevealedMapPercentageTooltip = new Tooltip("Best Revealed Map Percentage %");
        this.bestMinesSweptNumberTooltip = new Tooltip("Best Number of Mines Discovered");
        this.bestMouseClicksNumberTooltip = new Tooltip("Best Number of Mouse Clicks");

        this.posLabelToolEasy  = new Label("#");
        this.posLabelToolNormal = new Label("#");
        this.posLabelToolHard = new Label("#");
        this.nameLabelToolEasy = new Label("Name");
        this.nameLabelToolNormal = new Label("Name");
        this.nameLabelToolHard = new Label("Name");
        this.totalGamesLabelToolEasy = new Label("TG");
        this.totalGamesLabelToolNormal = new Label("TG");
        this.totalGamesLabelToolHard = new Label("TG");
        this.finishedGamesLabelToolEasy = new Label("GF");
        this.finishedGamesLabelToolNormal = new Label("GF");
        this.finishedGamesLabelToolHard = new Label("GF");
        this.finishedGamesPercentageLabelToolEasy = new Label("GF %");
        this.finishedGamesPercentageLabelToolNormal = new Label("GF %");
        this.finishedGamesPercentageLabelToolHard = new Label("GF %");
        this.bestTimeLabelToolEasy = new Label("BT");
        this.bestTimeLabelToolNormal = new Label("BT");
        this.bestTimeLabelToolHard = new Label("BT");
        this.bestRevealedMPercentageLabelToolEasy = new Label("BRM %");
        this.bestRevealedMPercentageLabelToolNormal = new Label("BRM %");
        this.bestRevealedMPercentageLabelToolHard = new Label("BRM %");
        this.bestNumberMinesSweptLabelToolEasy = new Label("BMS");
        this.bestNumberMinesSweptLabelToolNormal = new Label("BMS");
        this.bestNumberMinesSweptLabelToolHard = new Label("BMS");
        this.bestNumberMouseClicksLabelToolEasy = new Label("BMC");
        this.bestNumberMouseClicksLabelToolNormal = new Label("BMC");
        this.bestNumberMouseClicksLabelToolHard = new Label("BMC");

        this.posLabelToolEasy.setTooltip(this.positionNumberTooltip);
        this.posLabelToolNormal.setTooltip(this.positionNumberTooltip);
        this.posLabelToolHard.setTooltip(this.positionNumberTooltip);
        this.nameLabelToolEasy.setTooltip(this.nameTooltip);
        this.nameLabelToolNormal.setTooltip(this.nameTooltip);
        this.nameLabelToolHard.setTooltip(this.nameTooltip);
        this.totalGamesLabelToolEasy.setTooltip(this.totalGamesTooltip);
        this.totalGamesLabelToolNormal.setTooltip(this.totalGamesTooltip);
        this.totalGamesLabelToolHard.setTooltip(this.totalGamesTooltip);
        this.finishedGamesLabelToolEasy.setTooltip(this.finishedGamesTooltip);
        this.finishedGamesLabelToolNormal.setTooltip(this.finishedGamesTooltip);
        this.finishedGamesLabelToolHard.setTooltip(this.finishedGamesTooltip);
        this.finishedGamesPercentageLabelToolEasy.setTooltip(this.finishedGamesPercentageTooltip);
        this.finishedGamesPercentageLabelToolNormal.setTooltip(this.finishedGamesPercentageTooltip);
        this.finishedGamesPercentageLabelToolHard.setTooltip(this.finishedGamesPercentageTooltip);
        this.bestTimeLabelToolEasy.setTooltip(this.bestTimeTooltip);
        this.bestTimeLabelToolNormal.setTooltip(this.bestTimeTooltip);
        this.bestTimeLabelToolHard.setTooltip(this.bestTimeTooltip);
        this.bestRevealedMPercentageLabelToolEasy.setTooltip(this.bestRevealedMapPercentageTooltip);
        this.bestRevealedMPercentageLabelToolNormal.setTooltip(this.bestRevealedMapPercentageTooltip);
        this.bestRevealedMPercentageLabelToolHard.setTooltip(this.bestRevealedMapPercentageTooltip);
        this.bestNumberMinesSweptLabelToolEasy.setTooltip(this.bestMinesSweptNumberTooltip);
        this.bestNumberMinesSweptLabelToolNormal.setTooltip(this.bestMinesSweptNumberTooltip);
        this.bestNumberMinesSweptLabelToolHard.setTooltip(this.bestMinesSweptNumberTooltip);
        this.bestNumberMouseClicksLabelToolEasy.setTooltip(this.bestMouseClicksNumberTooltip);
        this.bestNumberMouseClicksLabelToolNormal.setTooltip(this.bestMouseClicksNumberTooltip);
        this.bestNumberMouseClicksLabelToolHard.setTooltip(this.bestMouseClicksNumberTooltip);

        this.infoTextLabel = new Label();
        this.best10PlayersEasyList = new ArrayList<>();
        this.best10PlayersNormalList = new ArrayList<>();
        this.best10PlayersHardList = new ArrayList<>();
        this.difficulty = NORMAL;
        this.controllerDisplay = new Display(16,18,40,40);
        this.choiceBox = new ChoiceBox<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.playerName.setTooltip(this.nameTooltip);
        this.textFieldSetCustomHeight.setTooltip(this.customGameRowsNumberTool);
        this.textFieldSetCustomWidth.setTooltip(this.customGameColumnsNumberTool);
        this.textFieldSetNumberOfMines.setTooltip(this.customGameNumberOfMinesTool);

        this.positionNumberColumnTableEasy.setGraphic(this.posLabelToolEasy);
        this.positionNumberColumnTableNormal.setGraphic(this.posLabelToolNormal);
        this.positionNumberColumnTableHard.setGraphic(this.posLabelToolHard);
        this.nameColumnTableEasy.setGraphic(this.nameLabelToolEasy);
        this.nameColumnTableNormal.setGraphic(this.nameLabelToolNormal);
        this.nameColumnTableHard.setGraphic(this.nameLabelToolHard);
        this.totalGamesPlayedColumnEasy.setGraphic(this.totalGamesLabelToolEasy);
        this.totalGamesPlayedColumnNormal.setGraphic(this.totalGamesLabelToolNormal);
        this.totalGamesPlayedColumnHard.setGraphic(this.totalGamesLabelToolHard);
        this.finishedGamesColumnEasy.setGraphic(this.finishedGamesLabelToolEasy);
        this.finishedGamesColumnNormal.setGraphic(this.finishedGamesLabelToolNormal);
        this.finishedGamesColumnHard.setGraphic(this.finishedGamesLabelToolHard);
        this.finishedGamesPercentageColumnEasy.setGraphic(this.finishedGamesPercentageLabelToolEasy);
        this.finishedGamesPercentageColumnNormal.setGraphic(this.finishedGamesPercentageLabelToolNormal);
        this.finishedGamesPercentageColumnHard.setGraphic(this.finishedGamesPercentageLabelToolHard);
        this.bestTimeInSecondsColumnEasy.setGraphic(this.bestTimeLabelToolEasy);
        this.bestTimeInSecondsColumnNormal.setGraphic(this.bestTimeLabelToolNormal);
        this.bestTimeInSecondsColumnHard.setGraphic(this.bestTimeLabelToolHard);
        this.bestPercentageRevealedMapColumnEasy.setGraphic(this.bestRevealedMPercentageLabelToolEasy);
        this.bestPercentageRevealedMapColumnNormal.setGraphic(this.bestRevealedMPercentageLabelToolNormal);
        this.bestPercentageRevealedMapColumnHard.setGraphic(this.bestRevealedMPercentageLabelToolHard);
        this.bestNumberOfMinesSweptColumnEasy.setGraphic(this.bestNumberMinesSweptLabelToolEasy);
        this.bestNumberOfMinesSweptColumnNormal.setGraphic(this.bestNumberMinesSweptLabelToolNormal);
        this.bestNumberOfMinesSweptColumnHard.setGraphic(this.bestNumberMinesSweptLabelToolHard);
        this.bestNumberOfMouseClicksColumnEasy.setGraphic(this.bestNumberMouseClicksLabelToolEasy);
        this.bestNumberOfMouseClicksColumnNormal.setGraphic(this.bestNumberMouseClicksLabelToolNormal);
        this.bestNumberOfMouseClicksColumnHard.setGraphic(this.bestNumberMouseClicksLabelToolHard);

        this.mainLabelText = this.mainLabel.getText();
        this.menuAccordion.setExpandedPane(this.titledPaneNormal);
        this.titledPaneNormal.setExpanded(true);
        this.titledPaneNormal.setAnimated(true);
        this.titledPaneHard.setAnimated(true);
        this.titledPaneEasy.setAnimated(true);
        this.choiceBox.getItems().addAll("Difficulty: Hard", "Difficulty: Normal", "Difficulty: Easy");
        this.choiceBox.setValue("Difficulty: Normal");
        InnerShadow innerShadowChoiceBox = (InnerShadow) choiceBox.getEffect();
        this.choiceBox.setOnAction(e -> {
            String diff = this.choiceBox.getValue();
            switch (diff) {
                case "Difficulty: Easy" -> {
                    innerShadowChoiceBox.setColor(Color.GREEN);
                    this.startGameButton.setTextFill(Color.GREEN);
                    this.difficulty = EASY;
                    this.titledPaneEasy.setExpanded(true);
                }
                case "Difficulty: Normal" -> {
                    innerShadowChoiceBox.setColor(Color.BLACK);
                    this.startGameButton.setTextFill(Color.BLACK);
                    this.difficulty = NORMAL;
                    this.titledPaneNormal.setExpanded(true);
                }
                case "Difficulty: Hard" -> {
                    innerShadowChoiceBox.setColor(Color.RED);
                    this.startGameButton.setTextFill(Color.RED);
                    this.difficulty = HARD;
                    this.titledPaneHard.setExpanded(true);
                }
            }
        });
        this.infoTextLabel.setText(
                "   Choose number of mines, number of columns (width between 2-28)\n" +
                        "      and number of rows (height between 2-24) for in game Display.\n" +
                        "Custom games don't count for the Top 10 players tables above. Have Fun!");

        /* Top 10 Tabel Settings */
        nameColumnTableEasy.setCellValueFactory(new PropertyValueFactory("name"));
        nameColumnTableNormal.setCellValueFactory(new PropertyValueFactory("name"));
        nameColumnTableHard.setCellValueFactory(new PropertyValueFactory("name"));
        positionNumberColumnTableEasy.setCellValueFactory(new PropertyValueFactory("posNumber"));
        positionNumberColumnTableNormal.setCellValueFactory(new PropertyValueFactory("posNumber"));
        positionNumberColumnTableHard.setCellValueFactory(new PropertyValueFactory("posNumber"));
        finishedGamesPercentageColumnEasy.setCellValueFactory(new PropertyValueFactory("percentageFinishedGames"));
        finishedGamesPercentageColumnNormal.setCellValueFactory(new PropertyValueFactory("percentageFinishedGames"));
        finishedGamesPercentageColumnHard.setCellValueFactory(new PropertyValueFactory("percentageFinishedGames"));
        totalGamesPlayedColumnEasy.setCellValueFactory(new PropertyValueFactory("totalGamesPlayed"));
        totalGamesPlayedColumnNormal.setCellValueFactory(new PropertyValueFactory("totalGamesPlayed"));
        totalGamesPlayedColumnHard.setCellValueFactory(new PropertyValueFactory("totalGamesPlayed"));
        finishedGamesColumnEasy.setCellValueFactory(new PropertyValueFactory("gamesFinished"));
        finishedGamesColumnNormal.setCellValueFactory(new PropertyValueFactory("gamesFinished"));
        finishedGamesColumnHard.setCellValueFactory(new PropertyValueFactory("gamesFinished"));
        bestNumberOfMinesSweptColumnEasy.setCellValueFactory(new PropertyValueFactory("bestNumberOfSweptMines"));
        bestNumberOfMinesSweptColumnNormal.setCellValueFactory(new PropertyValueFactory("bestNumberOfSweptMines"));
        bestNumberOfMinesSweptColumnHard.setCellValueFactory(new PropertyValueFactory("bestNumberOfSweptMines"));
        bestNumberOfMouseClicksColumnEasy.setCellValueFactory(new PropertyValueFactory("bestNumberOfMouseClicks"));
        bestNumberOfMouseClicksColumnNormal.setCellValueFactory(new PropertyValueFactory("bestNumberOfMouseClicks"));
        bestNumberOfMouseClicksColumnHard.setCellValueFactory(new PropertyValueFactory("bestNumberOfMouseClicks"));
        bestTimeInSecondsColumnEasy.setCellValueFactory(new PropertyValueFactory("bestTimeInSeconds"));
        bestTimeInSecondsColumnNormal.setCellValueFactory(new PropertyValueFactory("bestTimeInSeconds"));
        bestTimeInSecondsColumnHard.setCellValueFactory(new PropertyValueFactory("bestTimeInSeconds"));
        bestPercentageRevealedMapColumnEasy.setCellValueFactory(new PropertyValueFactory("bestPercentageRevealedMap"));
        bestPercentageRevealedMapColumnNormal.setCellValueFactory(new PropertyValueFactory("bestPercentageRevealedMap"));
        bestPercentageRevealedMapColumnHard.setCellValueFactory(new PropertyValueFactory("bestPercentageRevealedMap"));

        readFromFileAndPopulateTopTenPlayers();
    }

    private void readFromFileAndPopulateTopTenPlayers(){
        Path path = Main.topPath;

        Path fileTopEasy = Path.of(path.toString(),"bestPlayersEasy.dat");
        Path fileTopNormal = Path.of(path.toString(),"bestPlayersNormal.dat");
        Path fileTopHard = Path.of(path.toString(),"bestPlayersHard.dat");

        try(ObjectInputStream objectInputStreamEasy = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileTopEasy.toString())))){
            this.best10PlayersEasyList = (List<Player>) objectInputStreamEasy.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        try(ObjectInputStream objectInputStreamNormal = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileTopNormal.toString())))){
            this.best10PlayersNormalList = (List<Player>) objectInputStreamNormal.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        try(ObjectInputStream objectInputStreamHard = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileTopHard.toString())))){
            this.best10PlayersHardList = (List<Player>) objectInputStreamHard.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        List<Player> easyList = new ArrayList<>();
        List<Player> normalList = new ArrayList<>();
        List<Player> hardList = new ArrayList<>();

        PlayerComparator comparator = new PlayerComparator();

        if(this.best10PlayersEasyList != null){
            this.best10PlayersEasyList.sort(comparator);
            for(int i = 0; i < this.best10PlayersEasyList.size() && i < 10; i++){
                this.best10PlayersEasyList.get(i).setPosNumber(i+1);
                easyList.add(this.best10PlayersEasyList.get(i));
            }
        }
        if(this.best10PlayersNormalList != null){
            this.best10PlayersNormalList.sort(comparator);
            for(int i = 0; i < this.best10PlayersNormalList.size() && i < 10; i++){
                this.best10PlayersNormalList.get(i).setPosNumber(i+1);
                normalList.add(this.best10PlayersNormalList.get(i));
            }
        }
        if(this.best10PlayersHardList != null){
            this.best10PlayersHardList.sort(comparator);
            for(int i = 0; i < this.best10PlayersHardList.size() && i < 10; i++){
                this.best10PlayersHardList.get(i).setPosNumber(i+1);
                hardList.add(this.best10PlayersHardList.get(i));
            }
        }

        this.tableViewHard.setItems(FXCollections.observableList(hardList));
        this.tableViewNormal.setItems(FXCollections.observableList(normalList));
        this.tableViewEasy.setItems(FXCollections.observableList(easyList));
    }

    /* Set GridPane Display Nodes on Start Game */
    public GridPane setNodesOnInGameGridPaneDisplay(InGameController inGameController, GridPane gridPane, int rows, int cols){
        gridPane.setMaxHeight(25 * rows);
        gridPane.setMaxWidth(25 * cols);
        for (int r = 0; r < rows; r++) {   //height
            for (int c = 0; c < cols; c++) {  //width
                ToggleButton tog = new InitialSquare(r,c);
                int finalR = r;
                int finalC = c;
                tog.setOnMouseClicked(e -> {
                    InGameController.countMouseClicks++;
                    inGameController.mouseClicksButton.setText("" + InGameController.countMouseClicks);
                    inGameController.initializeDisplayOnFirstClick(gridPane,finalR, finalC);
                });
                this.controllerDisplay.toggleButtonsSquares2DArray[r][c] = tog;
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
        if(this.playerName.getText().isEmpty()){
            this.mainLabel.setText("Player Name must not be empty!");
            this.mainLabel.setTextFill(Color.RED);
            return;
        }else{
            inGameController.player = new Player(this.playerName.getText());
        }
        this.controllerDisplay = startGameSetDisplay();
        int rows = this.controllerDisplay.toggleButtonsSquares2DArray.length;
        int cols = this.controllerDisplay.toggleButtonsSquares2DArray[0].length;
        inGameController.setInGameDisplay(this.controllerDisplay);
        inGameController.setStatus(Status.STARTED_RATED_GAME);
        inGameController.inGameGridPane = setNodesOnInGameGridPaneDisplay(inGameController, inGameController.inGameGridPane, rows, cols);
        inGameController.minesButton.setText("" + inGameController.getInGameDisplay().getMines());
        inGameController.inGameDifficulty = this.difficulty;

        if(this.difficulty == EASY){
            inGameController.inGameList = this.best10PlayersEasyList;
        }else if(this.difficulty == NORMAL){
            inGameController.inGameList = this.best10PlayersNormalList;
        }else if(this.difficulty == HARD){
            inGameController.inGameList = this.best10PlayersHardList;
        }
        stage = (Stage) startGameButton.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    protected void exitGameClickActionButton(){
        stage = (Stage) exitGameButton.getScene().getWindow();
        stage.close();
    }

    protected Display startGameSetDisplay() throws BoundaryException{
        if(difficulty == EASY) {
            this.controllerDisplay = new Display(8, 10, 10,10);
        }else if(difficulty == NORMAL){
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
        this.difficulty = CUSTOM;
        inGameController.inGameDifficulty = this.difficulty;
        inGameController.setStatus(Status.STARTED_CUSTOM_GAME);
        int mines, cols, rows, minesMaxLimit = 1;
        try{
            mines = Integer.parseInt(textFieldSetNumberOfMines.getText());
            cols = Integer.parseInt(textFieldSetCustomWidth.getText());
            rows = Integer.parseInt(textFieldSetCustomHeight.getText());
            minesMaxLimit = (int) Math.ceil(cols * rows * 0.15);
            this.controllerDisplay = new Display(rows,cols,mines,minesMaxLimit);
        }catch (IllegalArgumentException e){
            this.mainLabel.setText("Only digits allowed! Type a number in every empty Field in order to start Custom Game.");
            this.mainLabel.setTextFill(Color.RED);
            return;
        }catch (BoundaryException e){
            this.infoTextLabel.setVisible(false);
            if(e instanceof NumberOfMinesBoundaryException){
                this.mainLabel.setText("Number of Mines must be min 1 and max " + minesMaxLimit);
                this.mainLabel.setTextFill(Color.RED);
            }else if(e instanceof WidthAndHeightBoundaryException){
                this.mainLabel.setText("Min number is 2 for Width and Height! Max Width is 28 and Max Height is 24.");
                this.mainLabel.setTextFill(Color.RED);
            }
            return;
        }
        inGameController.setInGameDisplay(this.controllerDisplay);
        inGameController.inGameGridPane = setNodesOnInGameGridPaneDisplay(inGameController, inGameController.inGameGridPane,this.controllerDisplay.toggleButtonsSquares2DArray.length,this.controllerDisplay.toggleButtonsSquares2DArray[0].length);
        inGameController.minesButton.setText("" + inGameController.getInGameDisplay().getMines());
        stage = (Stage) startCustomGameButton.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    protected void colorGrayForCustomGameAnchorPaneMethod(){
        this.anchorPaneStartCustomGame.setStyle("-fx-background-color: LIGHTGRAY;");
        this.startCustomGameButton.setTextFill(Color.BLACK);
        this.MSweeperLabel.setTextFill(Color.BLACK);
    }

    @FXML
    protected void colorGrayForRatedGameAnchorPaneMethod(){
        this.anchorPaneStartGame.setStyle("-fx-background-color: LIGHTGRAY;");
    }

    @FXML
    protected void colorTransparentForRatedGameAnchorPaneMethod(){
        this.anchorPaneStartGame.setStyle("-fx-background-color: null;");
        this.minesSweeperButton.setTextFill(this.startGameButton.getTextFill());
    }

    @FXML
    protected void colorTransparentForCustomGameAnchorPaneMethod(){
        this.anchorPaneStartCustomGame.setStyle("-fx-background-color: null;");
        this.startCustomGameButton.setTextFill(Color.PURPLE);
        this.mainLabel.setText(this.mainLabelText);
        this.mainLabel.setTextFill(Color.BLUE);
        this.MSweeperLabel.setTextFill(Color.PURPLE);
    }
}
