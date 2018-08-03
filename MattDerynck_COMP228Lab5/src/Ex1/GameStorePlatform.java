package Ex1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameStorePlatform extends Application {

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

        ComboBox<Player> existingPlayers = new ComboBox<Player>();
        //Adding a dummy value to allow for adding of a new player instead of updating an existing one.
        existingPlayers.getItems().add(new Player(0, "-- New Player --", "", "", "", "", ""));
        existingPlayers.getSelectionModel().select(0);
        existingPlayers.getItems().addAll(playerDAO.getAll());
        existingPlayers.setOnAction(event -> {
            if (existingPlayers.getSelectionModel().getSelectedIndex() == 0) {
                createOrUpdate.setText("Create");
            } else {
                createOrUpdate.setText("Update");
            }
        });

        playerInfoGrid.add(existingPlayers, 0, 0);
        playerInfoGrid.add(new Label("First Name: "), 0, 1);
        playerInfoGrid.add(new Label("Last Name: "), 0, 2);
        playerInfoGrid.add(new Label("Address: "), 0, 3);
        playerInfoGrid.add(new Label("Postal Code: "), 0, 4);
        playerInfoGrid.add(new Label("Province: "), 0, 5);
        playerInfoGrid.add(new Label("Phone Number: "), 0, 6);

        TextField firstName = new TextField();
        TextField lastName = new TextField();
        TextField address = new TextField();
        TextField postalCode = new TextField();
        TextField province = new TextField();
        TextField phoneNumber = new TextField();

        playerInfoGrid.add(firstName, 1, 1);
        playerInfoGrid.add(lastName, 1, 2);
        playerInfoGrid.add(address, 1, 3);
        playerInfoGrid.add(postalCode, 1, 4);
        playerInfoGrid.add(province, 1, 5);
        playerInfoGrid.add(phoneNumber, 1, 6);


        playerPane.setLeft(playerInfoGrid);

        //Bottom of pane - Player list
        TableView<Player> table = new TableView<Player>();
        table.setEditable(false);
        table.setItems(FXCollections.observableArrayList(playerDAO.getAll()));
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Player, String>("firstName"));
        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
        table.getColumns().addAll(firstNameCol, lastNameCol);

        playerPane.setBottom(table);

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

}
