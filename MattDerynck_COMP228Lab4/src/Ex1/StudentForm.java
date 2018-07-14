package Ex1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StudentForm extends JFrame {
    //Frame properties
    private final BorderLayout layout;
    private final JPanel westPanel, eastPanel, southPanel, centerPanel;

    //Center Panel properties
    private final List<JCheckBox> extraCurrs;

    //East Panel properties
    private final JLabel lblName, lblAddress, lblProvince, lblCity, lblPostalCode, lblPhoneNum, lblEmail;
    private final JTextField name, address, province, city, postalCode, phoneNum, email;

    //South panel properties
    private final JButton displayBtn;
    private final JTextArea displayArea;
    private final JScrollPane displayScroll;

    //West Panel properties
    private final JComboBox courses;
    private final ButtonGroup majorGroup;
    private final JRadioButton majorSCI;
    private final JRadioButton majorBUS;
    private final JList courseList;
    private List<String> selectedCourses = new ArrayList<>();
    private final JScrollPane scroll;


    //String constants for course lists.
    private final String[] compSciList = {"Java", "Python", "HTML-LUL", "Scratch", "Networking"};
    private final String[] busList = {"Management", "Business Law", "Marketing", "Finance", "Foundations"};

    public StudentForm() {
        super("Student Form");
        //Initialize and set the layout
        layout = new BorderLayout(5, 5);
        setLayout(layout);

        //Panel initializations
        westPanel = new JPanel(new GridLayout(0, 2));
        eastPanel = new JPanel(new GridBagLayout());
        southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        centerPanel = new JPanel(new GridLayout(0, 1));

        //Display Button and text area initialization
        displayBtn = new JButton("Display");
        displayBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        displayBtn.addActionListener(e -> displayInformation());
        displayArea = new JTextArea(4, 0);
        displayArea.setEditable(false);
        //Add the scrollbar effect to the text area.
        displayScroll = new JScrollPane(displayArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        //Course list initialization
        courseList = new JList();
        courseList.setVisibleRowCount(5);
        courseList.setLayoutOrientation(JList.VERTICAL);
        scroll = new JScrollPane(courseList);

        //Course combo box initialization, default to the comp sci list.
        courses = new JComboBox(compSciList);
        //Using a lambda expression to configure the action listener for the course list.
        courses.addActionListener(e -> {
            String selectedItem = ((JComboBox) e.getSource()).getSelectedItem().toString();
            if (!selectedCourses.contains(selectedItem)) {
                selectedCourses.add(selectedItem);
                courseList.setListData(selectedCourses.toArray());
                courseList.validate();
            }
        });

        //Radio button & group initialization
        majorSCI = new JRadioButton("Computer Science");
        //Default to computer science selected.
        majorSCI.setSelected(true);
        //Add an action listener to the radio button.
        majorSCI.addActionListener(e -> {
            selectedCourses.clear();
            courseList.setListData(selectedCourses.toArray());
            courses.setModel(new DefaultComboBoxModel(compSciList));
        });

        majorBUS = new JRadioButton("Business");
        majorBUS.addActionListener(e -> {
            selectedCourses.clear();
            courseList.setListData(selectedCourses.toArray());
            courses.setModel(new DefaultComboBoxModel(busList));
        });
        //Add the radio buttons to a group.
        majorGroup = new ButtonGroup();
        majorGroup.add(majorSCI);
        majorGroup.add(majorBUS);

        //Extra curricular check boxes initialization.
        extraCurrs = new ArrayList<>();
        extraCurrs.add(new JCheckBox("Student Council"));
        extraCurrs.add(new JCheckBox("Volunteer Work"));
        extraCurrs.add(new JCheckBox("Sports team"));
        extraCurrs.add(new JCheckBox("Internship"));

        //Text field initializations
        name = new JTextField(15);
        address = new JTextField(15);
        province = new JTextField(15);
        city = new JTextField(15);
        postalCode = new JTextField(15);
        phoneNum = new JTextField(15);
        email = new JTextField(15);

        //Label initializations
        lblName = new JLabel("Name: ");
        lblAddress = new JLabel("Address: ");
        lblProvince = new JLabel("Province: ");
        lblCity = new JLabel("City: ");
        lblPostalCode = new JLabel("Postal Code: ");
        lblPhoneNum = new JLabel("Phone Number: ");
        lblEmail = new JLabel("Email: ");

        //Organize each panels layout before adding the the frame.
        createWestPanelLayout();
        createEastPanelLayout();
        createSouthPanelLayout();
        createCenterPanelLayout();

        //Add the panels to the frame.
        add(centerPanel, BorderLayout.CENTER);
        add(westPanel, BorderLayout.WEST);
        add(eastPanel, BorderLayout.EAST);
        add(southPanel, BorderLayout.SOUTH);

    }

    private void createEastPanelLayout() {
        //Create the grid back constants to customize the layout.
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        eastPanel.add(majorSCI, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        eastPanel.add(majorBUS, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        eastPanel.add(courses, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 2;
        eastPanel.add(scroll, gbc);
    }

    private void createSouthPanelLayout() {
        //Add the display button and the display area to the south panel.
        southPanel.add(displayBtn);
        southPanel.add(displayScroll);
    }

    private void createWestPanelLayout() {
        //Add the labels and text fields in order.
        westPanel.add(lblName);
        westPanel.add(name);
        westPanel.add(lblAddress);
        westPanel.add(address);
        westPanel.add(lblProvince);
        westPanel.add(province);
        westPanel.add(lblCity);
        westPanel.add(city);
        westPanel.add(lblPostalCode);
        westPanel.add(postalCode);
        westPanel.add(lblPhoneNum);
        westPanel.add(phoneNum);
        westPanel.add(lblEmail);
        westPanel.add(email);
    }

    private void createCenterPanelLayout() {
        //Add the checkbox list to the center panel.
        for (JCheckBox checkBox : extraCurrs) {
            centerPanel.add(checkBox);
        }
    }

    //Creates and sets the students information in a nicely formatted string to the displayArea
    private void displayInformation() {
        String information = String.format("-Student Info-%n%s, %s, %s, %s, %s, %s, %s%nMajor:%n%s%nCourses:%n", name.getText(), address.getText(), province.getText(), city.getText(), postalCode.getText(), phoneNum.getText(), email.getText(), majorSCI.isSelected() ? majorSCI.getText() : majorBUS.getText());
        for (String course : selectedCourses) {
            information += String.format(course + "%n");
        }
        information += "Extra Curricular:";
        for (JCheckBox checkBox : extraCurrs) {
            if (checkBox.isSelected()) {
                information += String.format("%n" + checkBox.getText());
            }
        }
        displayArea.setText(information);
    }
}
