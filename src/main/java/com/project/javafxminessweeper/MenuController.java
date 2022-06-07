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

/** MVC Controller Class for game start Menu */

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

    /* Tooltip fields */
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

    /* Label fields */
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

    /* Top 10 List fields */
    private List<Player> best10PlayersEasyList;
    private List<Player> best10PlayersNormalList;
    private List<Player> best10PlayersHardList;

    /* Constructor - Called before initialize() method */
    public MenuController() throws Exception {
        /* Creating Tooltips */
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

        /* Tooltips labels */
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

        /* Set Tooltips to corresponding labels */
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

        /* Default settings for Rated Game start */
        this.difficulty = NORMAL;
        this.controllerDisplay = new Display(16,18,40,40);

        this.infoTextLabel = new Label();
        this.choiceBox = new ChoiceBox<>();

        this.best10PlayersEasyList = new ArrayList<>();
        this.best10PlayersNormalList = new ArrayList<>();
        this.best10PlayersHardList = new ArrayList<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* Setting up Tooltips whose Node objects are null before this initialize, so they need to be set here */
        this.playerName.setTooltip(this.nameTooltip);
        this.textFieldSetCustomHeight.setTooltip(this.customGameRowsNumberTool);
        this.textFieldSetCustomWidth.setTooltip(this.customGameColumnsNumberTool);
        this.textFieldSetNumberOfMines.setTooltip(this.customGameNumberOfMinesTool);

        /* Set Tooltips graphic to every table columns */
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

        /* Expand Top 10 titledPane on game start */
        expandTitledPaneAtLoadMenu(this.titledPaneNormal);

        /* Animation for Accordion Title Panes */
        this.titledPaneNormal.setAnimated(true);
        this.titledPaneHard.setAnimated(true);
        this.titledPaneEasy.setAnimated(true);

        /* Set Choice box choices and default value */
        this.choiceBox.getItems().addAll("Difficulty: Hard", "Difficulty: Normal", "Difficulty: Easy");
        this.choiceBox.setValue("Difficulty: Normal");

        this.mainLabelText = this.mainLabel.getText();
        InnerShadow innerShadowChoiceBox = (InnerShadow) choiceBox.getEffect();
        this.infoTextLabel.setText(
                "   Choose number of mines, number of columns (width between 2-28)\n" +
                        "      and number of rows (height between 2-24) for in game Display.\n" +
                        "Custom games don't count for the Top 10 players tables above. Have Fun!");

        /* Change colors, game difficulty and expanded property when choice box option chosen on Action */
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

        /* Top 10 Tables Settings */
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

        /* Populate Top 10 Tables reading from game directory files */
        readFromFileAndPopulateTopTenPlayers();
    }

    /* Read from game directory files for Top 10 Players */
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

        /* Use player comparator to sort the lists that contain players and store only first 10 of them along with their posNumbers */
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

    /* Expand to see Top at load Menu */
    public void expandTitledPaneAtLoadMenu(TitledPane titledPane){
        titledPane.setExpanded(true);
        this.menuAccordion.setExpandedPane(titledPane);
    }

    /* Set GridPane Display Nodes on Start Game */
    public GridPane setNodesOnInGameGridPaneDisplay(InGameController inGameController, GridPane gridPane, int rows, int cols){
        /* It is 25 multiplication because a square is 25 * 25 area long (Check AbstractSquare constructor) */
        gridPane.setMaxHeight(25 * rows);
        gridPane.setMaxWidth(25 * cols);
        /* Populating Pane and display2DArray with Initial squares */
        for (int r = 0; r < rows; r++) {   //height
            for (int c = 0; c < cols; c++) {  //width
                ToggleButton tog = new InitialSquare(r,c);
                int finalR = r;
                int finalC = c;

                /* Set each InitialSquare toggleButton with initializeDisplayOnFirstClick method event */
                tog.setOnMouseClicked(e -> {
                    InGameController.countMouseClicks++;
                    inGameController.getMouseClicksButton().setText("" + InGameController.countMouseClicks);
                    inGameController.initializeDisplayOnFirstClick(gridPane,finalR, finalC);
                });

                /* Focus traversable false doesn't show the focus mouse selecting square border for this Node */
                tog.setFocusTraversable(false);
                this.controllerDisplay.getDisplay2DArray()[r][c] = tog;
                gridPane.add(tog, c, r);
            }
        }
        return gridPane;
    }

    /* Action for exitGameButton to exit game */
    @FXML
    protected void exitGameClickActionButton(){
        stage = (Stage) exitGameButton.getScene().getWindow();
        stage.close();
    }

    /* Set Display by Difficulty - when Difficulty is chosen from choiceBox */
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

    /* Start Rated Game functionality */
    @FXML
    protected void startGameClickActionButton() throws Exception{
        /* Load .fxml file */
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ingame-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        /* Get InGameController object to be able to configure some object before setting this new InGameController object Scene to Stage */
        InGameController inGameController = loader.getController();

        /* Get Player name before Start Game */
        if(this.playerName.getText().isEmpty()){
            this.mainLabel.setText("Player Name must not be empty!");
            this.mainLabel.setTextFill(Color.RED);
            return;
        }else{
            inGameController.setPlayer(new Player(this.playerName.getText()));
        }

        /* Set InGame display */
        this.controllerDisplay = startGameSetDisplay();
        int rows = this.controllerDisplay.getDisplay2DArray().length;
        int cols = this.controllerDisplay.getDisplay2DArray()[0].length;
        inGameController.setInGameDisplay(this.controllerDisplay);

        inGameController.setInGameGridPane(setNodesOnInGameGridPaneDisplay(inGameController, inGameController.getInGameGridPane(), rows, cols));
        inGameController.minesButton.setText("" + inGameController.getInGameDisplay().getMines());
        inGameController.setInGameDifficulty(this.difficulty);

        /* Get Top 10 Player List to be updated by difficulty */
        if(this.difficulty == EASY){
            inGameController.setInGameList(this.best10PlayersEasyList);
        }else if(this.difficulty == NORMAL){
            inGameController.setInGameList(this.best10PlayersNormalList);
        }else if(this.difficulty == HARD){
            inGameController.setInGameList(this.best10PlayersHardList);
        }

        /* Start Rated Game */
        inGameController.setStatus(Status.STARTED_RATED_GAME);
        stage = (Stage) startGameButton.getScene().getWindow();
        stage.setScene(scene);
    }

    /* Start Custom Game functionality */
    @FXML
    protected void startCustomGameClickActionButton() throws IOException{

        /* Load .fxml file. After that load Parent root to be able to create new Scene */
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ingame-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        /* Load InGame Controller Object to be able to manage some Objects before showing the new Scene */
        InGameController inGameController = loader.getController();

        /* Set up the new Scene */
        this.difficulty = CUSTOM;
        inGameController.setInGameDifficulty(this.difficulty);

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
        inGameController.setInGameGridPane(setNodesOnInGameGridPaneDisplay(inGameController, inGameController.getInGameGridPane(), this.controllerDisplay.getDisplay2DArray().length, this.controllerDisplay.getDisplay2DArray()[0].length));
        inGameController.minesButton.setText("" + inGameController.getInGameDisplay().getMines());
        inGameController.setStatus(Status.STARTED_CUSTOM_GAME);

        /* Set the new Scene to Stage and start Custom Game */
        stage = (Stage) startCustomGameButton.getScene().getWindow();
        stage.setScene(scene);
    }

    /* Change styles and colors - On Action methods - on Main Menu */
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
