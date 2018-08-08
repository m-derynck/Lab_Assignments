package Ex1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

public class GameStorePlatform extends Application {

    //DAO Initializations
    private static final DAO playerDAO = new PlayerDAO();
    private static final DAO gameDAO = new GameDAO();
    private static final DAO resultsDAO = new ResultsDAO();

    //General Properties
    private static final int WIDTH = 500;
    private static final int HEIGHT = 475;

    //Observable lists used for the various tables and combo boxes.
    private static final ObservableList<Player> playerList = FXCollections.observableArrayList(playerDAO.getAll());
    private static final ObservableList<Game> gameList = FXCollections.observableArrayList(gameDAO.getAll());
    private static final ObservableList<Result> resultList = FXCollections.observableArrayList(resultsDAO.getAll());

    //Player tab properties.
    private static final ComboBox<Player> playerListCB = new ComboBox<>();
    private static final TableView<Player> playerTable = new TableView<>();

    //Game tab properties
    private static final TableView<Game> gameTable = new TableView<>();

    //Results tab properties
    private static final TableView<Result> resultTable = new TableView<>();

    public GameStorePlatform() {

    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

        // For mock data creation.
        // mockData(playerDAO,gameDAO,resultsDAO);

        //App configuration
        Group rootGroup = new Group();
        Scene scene = new Scene(rootGroup, WIDTH, HEIGHT, Color.WHITE);
        primaryStage.setTitle("Player & Game Stats");

        //Initialize the tab pane and its properties.
        TabPane tabPane = new TabPane();
        double tabSize = (scene.widthProperty().doubleValue()) / 3;
        double offset = 20;
        tabPane.setTabMinWidth(tabSize - offset);
        tabPane.setTabMaxWidth(tabSize - offset);

        //Create and add the various tabs.
        tabPane.getTabs().add(createPlayerTab());
        tabPane.getTabs().add(createGameTab());
        tabPane.getTabs().add(createResultTab());

        //Add the TabPane to the root group
        rootGroup.getChildren().add(tabPane);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    //Example code to populate some data into the database
/*
    public void mockData(DAO playerDAO, DAO gameDAO, DAO resultsDAO) {
        Random random = new Random();
        List<Player> playerList = new ArrayList<Player>();
        List<Game> gameList = new ArrayList<Game>();
        List<Result> resultList = new ArrayList<Result>();

        //Generate 10 players and save them.
        for (int i = 0; i < 10; i++) {
            Player newPlayer = new Player(i, "FName" + i, "LName" + i, "123 Main st", "L2B 1X2", "Ontario", "416-441-3122");
            playerDAO.save(newPlayer);

        }
        playerList = playerDAO.getAll();
        //Generate 5 games and save them
        String[] gameTitles = {"Godzilla", "Mario Kart 64", "QuipLash", "GuildWars 2", "CS:GO"};
        for (int i = 0; i < gameTitles.length; i++) {
            Game game = new Game(i, gameTitles[i]);
            gameDAO.save(game);
        }
        gameList = gameDAO.getAll();

        for (Player player : playerList) {
            Result newResult = new Result(gameList.get(random.nextInt(gameList.size())), player, Date.from(Instant.now()), random.nextInt(100));
            resultsDAO.save(newResult);
        }

        //Test updating player.
        String[] params = {"Matt", "Derynck", "150 Hollywood Blvd", "H0L W0D", "CALIFORNIA", "131-944-9111"};
        playerDAO.update(playerList.get(1), params);

    }
*/

    //Creates the player tab and sets up the hooks and functionality for the various fields and buttons required.
    private Tab createPlayerTab() {
        //Configure the player tab.
        Tab playerTab = new Tab("Players");
        playerTab.setClosable(false);
        BorderPane playerPane = new BorderPane();

        //Top of pane.
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(new Label("--Player Menu--"));
        playerPane.setTop(hBox);

        //Left of pane.
        GridPane playerInfoGrid = new GridPane();
        playerInfoGrid.setVgap(3);

        //Add buttons
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);

        Button createOrUpdate = new Button("Create");
        Button clear = new Button("Clear");
        buttonBox.getChildren().addAll(clear, createOrUpdate);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        playerInfoGrid.add(buttonBox, 1, 7);

        TextField firstName = new TextField();
        TextField lastName = new TextField();
        TextField address = new TextField();
        TextField postalCode = new TextField();
        TextField province = new TextField();
        TextField phoneNumber = new TextField();

        //Initially refresh the data to populate the ComboBox.
        refreshPlayerData();
        playerListCB.setOnAction(event -> {
            if (playerListCB.getSelectionModel().getSelectedIndex() == 0) {
                //Resets values in each field to the default.
                createOrUpdate.setText("Create");
                firstName.setText("");
                lastName.setText("");
                address.setText("");
                postalCode.setText("");
                province.setText("");
                phoneNumber.setText("");
            } else if (playerListCB.getSelectionModel().getSelectedIndex() != -1) {
                //Sets the input fields to the current values of the selected Player.
                createOrUpdate.setText("Update");
                firstName.setText(playerListCB.getSelectionModel().getSelectedItem().getFirstName());
                lastName.setText(playerListCB.getSelectionModel().getSelectedItem().getLastName());
                address.setText(playerListCB.getSelectionModel().getSelectedItem().getAddress());
                postalCode.setText(playerListCB.getSelectionModel().getSelectedItem().getPostalCode());
                province.setText(playerListCB.getSelectionModel().getSelectedItem().getProvince());
                phoneNumber.setText(playerListCB.getSelectionModel().getSelectedItem().getPhoneNumber());
            }
        });
        playerInfoGrid.add(playerListCB, 0, 0);

        //Add button listener event
        createOrUpdate.setOnAction(event -> {
            Player player = new Player();
            if (playerListCB.getSelectionModel().getSelectedIndex() == 0) {//CREATE NEW PLAYER
                player.setFirstName(firstName.getText());
                player.setLastName(lastName.getText());
                player.setAddress(address.getText());
                player.setPostalCode(postalCode.getText());
                player.setProvince(province.getText());
                player.setPhoneNumber(phoneNumber.getText());
                playerDAO.save(player);
                displayMessage("New Player Saved.", Alert.AlertType.INFORMATION);
            } else {//UPDATE EXISTING PLAYER
                player = playerListCB.getSelectionModel().getSelectedItem();
                String[] params = {firstName.getText(), lastName.getText(), address.getText(), postalCode.getText(), province.getText(), phoneNumber.getText()};
                playerDAO.update(player, params);
                displayMessage("Player Updated.", Alert.AlertType.INFORMATION);
            }
            refreshPlayerData();
        });

        //Clear button listener. Clears all values in input fields
        clear.setOnAction(event -> {
            //Resets values in each field to the default.
            firstName.setText("");
            lastName.setText("");
            address.setText("");
            postalCode.setText("");
            province.setText("");
            phoneNumber.setText("");
        });

        //Add the labels and text fields to the player form grid.
        playerInfoGrid.add(new Label("First Name: "), 0, 1);
        playerInfoGrid.add(new Label("Last Name: "), 0, 2);
        playerInfoGrid.add(new Label("Address: "), 0, 3);
        playerInfoGrid.add(new Label("Postal Code: "), 0, 4);
        playerInfoGrid.add(new Label("Province: "), 0, 5);
        playerInfoGrid.add(new Label("Phone Number: "), 0, 6);

        playerInfoGrid.add(firstName, 1, 1);
        playerInfoGrid.add(lastName, 1, 2);
        playerInfoGrid.add(address, 1, 3);
        playerInfoGrid.add(postalCode, 1, 4);
        playerInfoGrid.add(province, 1, 5);
        playerInfoGrid.add(phoneNumber, 1, 6);

        //Add the info grid to the main pane.
        playerPane.setLeft(playerInfoGrid);

        //Bottom of pane - Player list
        //Configure the bottom pane as a scroll pane.

        //Various tableview properties being set
        playerTable.setFixedCellSize(25);
        playerTable.setPrefHeight(200);
        playerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        playerTable.setEditable(false);
        playerTable.setItems(playerList);

        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Player, String>("firstName"));
        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
        playerTable.getColumns().addAll(firstNameCol, lastNameCol);
        playerPane.setBottom(playerTable);

        //Add content to the tab.
        playerTab.setContent(playerPane);
        return playerTab;
    }

    private void refreshPlayerData() {
        refreshData();
        //In order to refresh the ComboBox we have to clear the selection before getting the items to avoid an issue with the event listener.
        playerListCB.getSelectionModel().clearSelection();
        playerListCB.getItems().clear();

        //Adding a dummy value to allow for adding of a new player instead of updating an existing one.
        playerListCB.getItems().add(new Player(0, "-- New Player --", "", "", "", "", ""));
        playerListCB.getSelectionModel().selectFirst();
        playerListCB.getItems().addAll(playerList);

        //Refresh the table data.
        playerTable.setItems(playerList);
    }

    private void refreshData() {
        playerList.setAll(playerDAO.getAll());
        gameList.setAll(gameDAO.getAll());
        resultList.setAll(resultsDAO.getAll());
    }

    //Creates the game tab and sets up the hooks and functionality for the various fields and buttons required.
    private Tab createGameTab() {
        //Configure the game tab.
        Tab gameTab = new Tab("Games");
        gameTab.setClosable(false);
        BorderPane gamePane = new BorderPane();

        //Top of pane.
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(new Label("--Game Menu--"));
        gamePane.setTop(hBox);


        //Add the elements to the flow pane for adding a new game title.
        FlowPane addGamePane = new FlowPane();
        addGamePane.setHgap(15);
        addGamePane.setVgap(15);
        Label gameNameLbl = new Label("Game Name: ");
        TextField gameName = new TextField();
        Button addGameBtn = new Button("Add");
        addGameBtn.setOnAction(event -> {
            Game newGame = new Game();
            newGame.setGameTitle(gameName.getText());
            gameDAO.save(newGame);
            refreshData();
            gameTable.setItems(gameList);
            displayMessage("Game added.", Alert.AlertType.INFORMATION);
        });
        addGamePane.getChildren().addAll(gameNameLbl, gameName, addGameBtn);
        gamePane.setLeft(addGamePane);

        //Configure the bottom pane which holds the current list of games.
        //Table configuration
        gameTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        gameTable.setEditable(false);
        TableColumn titleCol = new TableColumn("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<Game, String>("gameTitle"));
        gameTable.getColumns().add(titleCol);
        gameTable.setItems(gameList);
        gameTable.setFixedCellSize(25);
        gameTable.setPrefHeight(375);

        //Add a title to show above the table.
        HBox gameListLblBox = new HBox();
        Label gameListLbl = new Label("--List of games--");
        gameListLblBox.getChildren().add(gameListLbl);
        gameListLblBox.setAlignment(Pos.CENTER);
        VBox bottomBox = new VBox();
        bottomBox.setSpacing(10);
        bottomBox.getChildren().addAll(gameListLblBox, gameTable);
        gamePane.setBottom(bottomBox);

        gameTab.setContent(gamePane);
        return gameTab;
    }

    //Creates the results tab and sets up the hooks and functionality for the various fields and buttons required.
    private Tab createResultTab() {
        Tab resultTab = new Tab("Results");
        resultTab.setClosable(false);
        BorderPane resultPane = new BorderPane();

        //Top pane configuration
        HBox topBox = new HBox();
        topBox.setAlignment(Pos.CENTER);
        Label resultLbl = new Label("--Results Menu--");
        topBox.getChildren().add(resultLbl);
        resultPane.setTop(topBox);

        //Left pane for result adding.
        //Various fields and label initializations.
        GridPane newResultGrid = new GridPane();
        newResultGrid.setVgap(3);
        Label newResultLbl = new Label("--New Result--");
        Label selectPlayerLbl = new Label("Player: ");
        ComboBox<Player> playerListCB = new ComboBox<>();
        playerListCB.setMaxWidth(150);
        playerListCB.setMinWidth(150);
        playerListCB.setItems(playerList);
        Label selectGameLbl = new Label("Game: ");
        ComboBox<Game> gameListCB = new ComboBox<>();
        gameListCB.setMaxWidth(150);
        gameListCB.setMinWidth(150);
        gameListCB.setItems(gameList);
        Label scoreLbl = new Label("Score: ");
        TextField score = new TextField();
        score.setMaxWidth(150);
        score.setMinWidth(150);
        Label dateLbl = new Label("Date: ");
        DatePicker datePicker = new DatePicker();
        Button addResultBtn = new Button("Add");
        addResultBtn.setOnAction(event -> {
            //Adds a new result based on the input fields and updates the results table.
            Result newResult = new Result();
            newResult.setPlayer(playerListCB.getSelectionModel().getSelectedItem());
            newResult.setGame(gameListCB.getSelectionModel().getSelectedItem());
            //Some exception catching for bad values in the score and date inputs.
            try {
                newResult.setScore(Integer.parseInt(score.getText()));
                newResult.setDatePlayed(Date.from(Instant.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()))));
                resultsDAO.save(newResult);
                refreshData();
                resultTable.setItems(resultList);
                displayMessage("Result added.", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                displayMessage("Invalid Input. Error message: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        });

        //Add the components to the grid.
        newResultGrid.add(newResultLbl, 0, 0, 2, 1);
        newResultGrid.add(selectPlayerLbl, 0, 1);
        newResultGrid.add(playerListCB, 1, 1);
        newResultGrid.add(selectGameLbl, 0, 2);
        newResultGrid.add(gameListCB, 1, 2);
        newResultGrid.add(scoreLbl, 0, 3);
        newResultGrid.add(score, 1, 3);
        newResultGrid.add(dateLbl, 0, 4);
        newResultGrid.add(datePicker, 1, 4);
        newResultGrid.add(addResultBtn, 1, 5);

        resultPane.setLeft(newResultGrid);

        //Bottom pane for results table.
        resultTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        resultTable.setEditable(false);

        //Setup columns for the table
        TableColumn playerCol = new TableColumn("Player");
        playerCol.setCellValueFactory(new PropertyValueFactory<Result, String>("Player"));
        TableColumn gameCol = new TableColumn("Game");
        gameCol.setCellValueFactory(new PropertyValueFactory<Result, String>("Game"));
        TableColumn scoreCol = new TableColumn("Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<Result, String>("score"));
        TableColumn dateCol = new TableColumn("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<Result, String>("datePlayed"));

        resultTable.getColumns().addAll(playerCol, gameCol, scoreCol, dateCol);
        resultTable.setItems(resultList);
        resultTable.setFixedCellSize(25);
        resultTable.setPrefHeight(250);
        resultPane.setBottom(resultTable);

        resultTab.setContent(resultPane);
        return resultTab;
    }


    //Create alert to inform user.
    private void displayMessage(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setTitle(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
