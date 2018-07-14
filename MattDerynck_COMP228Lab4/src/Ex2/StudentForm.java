package Ex2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class StudentForm extends Application {

    //Declaration of the panes to be used.
    private EastPane eastPane;
    private CenterPane centerPane;
    private WestPane westPane;
    private SouthPane southPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Initialize the panes.
        eastPane = new EastPane();
        centerPane = new CenterPane();
        westPane = new WestPane();
        southPane = new SouthPane();

        //Create BorderPane and add the custom panes.
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setLeft(eastPane);
        pane.setCenter(centerPane);
        pane.setRight(westPane);
        pane.setBottom(southPane);

        //App configuration
        Scene scene = new Scene(pane);
        primaryStage.setTitle("Student Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    class EastPane extends GridPane {

        protected final TextField name = new TextField();
        protected final TextField address = new TextField();
        protected final TextField province = new TextField();
        protected final TextField city = new TextField();
        protected final TextField postalCode = new TextField();
        protected final TextField phoneNumber = new TextField();
        protected final TextField email = new TextField();

        public EastPane() {
            //Some pane configuration settings.
            setHgap(2.5);
            setVgap(2.5);
            setPadding(new Insets(0, 12.5, 0, 0));

            //Add the student form fields, labels and text fields.
            add(new Label("Name: "), 0, 0);
            add(name, 1, 0);
            add(new Label("Address: "), 0, 1);
            add(address, 1, 1);
            add(new Label("Province: "), 0, 2);
            add(province, 1, 2);
            add(new Label("City: "), 0, 3);
            add(city, 1, 3);
            add(new Label("Postal Code: "), 0, 4);
            add(postalCode, 1, 4);
            add(new Label("Phone Number: "), 0, 5);
            add(phoneNumber, 1, 5);
            add(new Label("Email: "), 0, 6);
            add(email, 1, 6);

        }
    }

    class CenterPane extends GridPane {
        protected List<CheckBox> extraCurrs;

        public CenterPane() {
            //Grid configuration
            setVgap(15);
            setPadding(new Insets(0, 12.5, 0, 0));

            //Initialize the extra curricular list.
            extraCurrs = new ArrayList<>();
            extraCurrs.add(new CheckBox("Student Council"));
            extraCurrs.add(new CheckBox("Volunteer Work"));
            extraCurrs.add(new CheckBox("Sports team"));
            extraCurrs.add(new CheckBox("Internship"));

            //Go through the list of extra curricular's and add them to the pane.
            for (int i = 0; i < extraCurrs.size(); i++) {
                add(extraCurrs.get(i), 0, i);
            }

        }
    }

    class WestPane extends GridPane {
        //String constants for course lists.
        private final ObservableList<String> compSciList = FXCollections.observableArrayList("Java", "Python", "HTML-LUL", "Scratch", "Networking");
        private final ObservableList<String> busList = FXCollections.observableArrayList("Management", "Business Law", "Marketing", "Finance", "Foundations");

        //Layout properties
        protected final ToggleGroup group = new ToggleGroup();
        protected final RadioButton compSciBtn;
        protected final RadioButton busBtn;
        protected final ObservableList<String> selectedCourses = FXCollections.observableArrayList();
        protected final ComboBox cbCourses;

        public WestPane() {
            //Grid properties
            setPadding(new Insets(0, 0, 0, 0));
            setHgap(10);
            setVgap(10);

            //ComboBox with course list.
            cbCourses = new ComboBox(compSciList);
            cbCourses.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            cbCourses.setOnAction(event -> {
                if (cbCourses.getSelectionModel().getSelectedItem() != null) {
                    String course = cbCourses.getSelectionModel().getSelectedItem().toString();
                    if (!selectedCourses.contains(course)) {
                        selectedCourses.add(cbCourses.getValue().toString());
                    }
                }
            });

            //Setup the radio buttons and add them to a common group to toggle.
            compSciBtn = new RadioButton("Computer Science");
            compSciBtn.setSelected(true);
            compSciBtn.setToggleGroup(group);
            compSciBtn.setOnAction(event -> {
                selectedCourses.clear();
                cbCourses.setItems(compSciList);
            });
            busBtn = new RadioButton("Business");
            busBtn.setToggleGroup(group);
            busBtn.setOnAction(event -> {
                selectedCourses.clear();
                cbCourses.setItems(busList);
            });

            //Display list showing the selected courses.
            ListView<String> list = new ListView<>();
            list.setItems(selectedCourses);
            list.setPrefHeight(125);

            //Add each of the pane elements.
            add(compSciBtn, 0, 0);
            add(busBtn, 1, 0);
            add(cbCourses, 0, 2, 2, 1);
            add(list, 0, 3, 2, 1);
        }

    }

    class SouthPane extends GridPane {

        private Button displayBtn;
        private TextArea displayArea;

        public SouthPane() {
            //Grid configuration
            setVgap(15);
            setPadding(new Insets(10, 0, 0, 0));

            //Display button configuration, button to display all submitted student information to the displayArea.
            displayBtn = new Button("Display");
            displayBtn.setOnAction(event -> {
                String displayString = String.format("-Student Info-%n%s, %s, %s, %s, %s, %s, %s%n%nMajor:%n%s%n%nCourses:%n",
                        eastPane.name.getText(),
                        eastPane.address.getText(),
                        eastPane.province.getText(),
                        eastPane.city.getText(),
                        eastPane.postalCode.getText(),
                        eastPane.phoneNumber.getText(),
                        eastPane.email.getText(),
                        westPane.compSciBtn.isSelected() ? westPane.compSciBtn.getText() : westPane.busBtn.getText());
                //Go through the current selected courses list and add them to the display string.
                for (String course : westPane.selectedCourses) {
                    displayString += String.format(course + "%n");
                }
                displayString += String.format("%nExtra Curricular:");
                for (CheckBox cb : centerPane.extraCurrs) {
                    if (cb.isSelected()) {
                        displayString += String.format("%n" + cb.getText());
                    }
                }

                displayArea.setText(displayString);
            });

            //Display Area configuration, displays current student information.
            displayArea = new TextArea();
            displayArea.setPrefRowCount(5);
            displayArea.setPrefColumnCount(60);
            displayArea.setEditable(false);


            add(displayBtn, 0, 0);
            add(displayArea, 0, 1);
            GridPane.setHalignment(displayBtn, HPos.CENTER);

        }
    }
}
