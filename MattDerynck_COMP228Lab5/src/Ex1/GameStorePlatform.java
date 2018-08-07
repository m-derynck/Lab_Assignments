package Ex1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class GameStorePlatform extends Application {

    //Application initializations
    private static DAO playerDAO;
    private static DAO gameDAO;
    private static DAO resultsDAO;

    public GameStorePlatform() {

    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        //DAO initialization
        playerDAO = new PlayerDAO();
        gameDAO = new GameDAO();
        resultsDAO = new ResultsDAO();

        // For mock data creation.
        // mockData(playerDAO,gameDAO,resultsDAO);

        //App configuration
        Group rootGroup = new Group();
        Scene scene = new Scene(rootGroup, 500, 400, Color.WHITE);
        primaryStage.setTitle("Player & Game Stats");

        //Initialize the tab pane and its properties.
        TabPane tabPane = new TabPane();
        double tabSize = (scene.widthProperty().doubleValue()) / 3;
        double offset = 15;
        tabPane.setTabMinWidth(tabSize - offset);
        tabPane.setTabMaxWidth(tabSize - offset);

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

        ComboBox<Player> existingPlayers = createPlayerCombo();
        existingPlayers.getItems().addAll(playerDAO.getAll());
        existingPlayers.setOnAction(event -> {
            if (existingPlayers.getSelectionModel().getSelectedIndex() == 0) {
                //Resets values in each field to the default.
                createOrUpdate.setText("Create");
                firstName.setText("");
                lastName.setText("");
                address.setText("");
                postalCode.setText("");
                province.setText("");
                phoneNumber.setText("");
            } else {
                //Sets the input fields to the current values of the selected Player.
                createOrUpdate.setText("Update");
                firstName.setText(existingPlayers.getSelectionModel().getSelectedItem().getFirstName());
                lastName.setText(existingPlayers.getSelectionModel().getSelectedItem().getLastName());
                address.setText(existingPlayers.getSelectionModel().getSelectedItem().getAddress());
                postalCode.setText(existingPlayers.getSelectionModel().getSelectedItem().getPostalCode());
                province.setText(existingPlayers.getSelectionModel().getSelectedItem().getProvince());
                phoneNumber.setText(existingPlayers.getSelectionModel().getSelectedItem().getPhoneNumber());
            }
        });
        playerInfoGrid.add(existingPlayers, 0, 0);

        //Add button listener event
        createOrUpdate.setOnAction(event -> {
            Player player = new Player();
            if (existingPlayers.getSelectionModel().getSelectedIndex() == 0) {//CREATE NEW PLAYER
                player.setFirstName(firstName.getText());
                player.setLastName(lastName.getText());
                player.setAddress(address.getText());
                player.setPostalCode(postalCode.getText());
                player.setProvince(province.getText());
                player.setPhoneNumber(phoneNumber.getText());
                playerDAO.save(player);
                displayMessage("New Player Saved.",Alert.AlertType.INFORMATION);
            } else {//UPDATE EXISTING PLAYER
                player = existingPlayers.getSelectionModel().getSelectedItem();
                String[] params = {firstName.getText(), lastName.getText(), address.getText(), postalCode.getText(), province.getText(), phoneNumber.getText()};
                playerDAO.update(player, params);
                displayMessage("Player Updated.",Alert.AlertType.INFORMATION);
            }
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
        ScrollPane playerScroll = new ScrollPane();
        playerScroll.setFitToWidth(true);
        playerScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        TableView<Player> table = new TableView<Player>();
        table.setEditable(false);
        table.setItems(FXCollections.observableArrayList(playerDAO.getAll()));
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Player, String>("firstName"));
        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
        table.getColumns().addAll(firstNameCol, lastNameCol);
        playerScroll.setContent(table);
        playerPane.setBottom(playerScroll);

        //Add content to the tab.
        playerTab.setContent(playerPane);
        tabPane.getTabs().add(playerTab);

        Tab gameTab = new Tab("Games");
        gameTab.setClosable(false);
        HBox hGameBox = new HBox();
        hGameBox.getChildren().add(new Label("Game"));
        gameTab.setContent(hGameBox);
        tabPane.getTabs().add(gameTab);

        Tab resultTab = new Tab("Results");
        resultTab.setClosable(false);
        HBox hResultBox = new HBox();
        hResultBox.getChildren().add(new Label("Results"));
        resultTab.setContent(hResultBox);
        tabPane.getTabs().add(resultTab);

        rootGroup.getChildren().add(tabPane);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    //Example code to populate some data into the database.
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

    private ComboBox<Player> createPlayerCombo() {
        ComboBox<Player> comboBox = new ComboBox<Player>();
        //Adding a dummy value to allow for adding of a new player instead of updating an existing one.
        comboBox.getItems().add(new Player(0, "-- New Player --", "", "", "", "", ""));
        comboBox.getSelectionModel().select(0);
        return comboBox;
    }

    //Create alert to inform user.
    public void displayMessage(String message, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setTitle(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
