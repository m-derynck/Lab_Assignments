package Ex3;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ProcessMortgage {
    static ArrayList<Mortgage> mortgages = new ArrayList<>();

    public static void main(String[] args) {
        displayMenu();
    }

    public static void displayMenu() {
        String[] options = {"Display Mortgages", "Add Mortgage", "Exit"};
        int selection = JOptionPane.showOptionDialog(null, "Welcome to CityToronto Mortgage App.",
                "CityToronto", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, 0);

        if (selection == 0) {
            displayMortages();
        } else if (selection == 1) {
            addMortgage();
        } else {
            System.exit(0);
        }
    }

    public static void addMortgage() {
        //Get the current prime rate from the user.
        double currentRate;
        do {
            currentRate = getInputDouble(JOptionPane.showInputDialog(null, "Enter current interest rate"));
        }
        while (currentRate == -1.0);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        //Panel input variables.
        JComboBox mortgageTypeInput = new JComboBox<>(new String[]{"Business", "Personal"});
        JTextField nameInput = new JTextField();
        JTextField amountInput = new JTextField();
        JTextField termInput = new JTextField();

        //Setting the grid bag constraints and row/column details.
        //Column 0
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Mortgage Type:"), gbc);
        gbc.gridy = 1;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridy = 2;
        panel.add(new JLabel("Mortgage Amount:"), gbc);
        gbc.gridy = 3;
        panel.add(new JLabel("Term:"), gbc);
        gbc.gridy = 4;
        gbc.gridwidth = 2;//Set the column span to two for the term info.
        JLabel termInfo = new JLabel("*Term can be 1, 3, or 5 year. Invalid term reverts to 1 year.");
        termInfo.setFont(new Font("Serif", Font.BOLD, 9));
        panel.add(termInfo, gbc);
        //Column 1
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(mortgageTypeInput, gbc);
        gbc.gridy = 1;
        panel.add(nameInput, gbc);
        gbc.gridy = 2;
        panel.add(amountInput, gbc);
        gbc.gridy = 3;
        panel.add(termInput, gbc);

        JOptionPane.showMessageDialog(null, panel, "Choose Mortgage", JOptionPane.INFORMATION_MESSAGE);
        Mortgage userMortgage;
        try {
            if (mortgageTypeInput.getSelectedIndex() == 0) {
                //Create new Business Mortgage.
                userMortgage = new BusinessMortgage(mortgages.size()+1, nameInput.getText(),
                        Double.parseDouble(amountInput.getText()), currentRate, Integer.parseInt(termInput.getText()));
            } else {
                //Create new Personal Mortgage.
                userMortgage = new PersonalMortgage(mortgages.size()+1, nameInput.getText(),
                        Double.parseDouble(amountInput.getText()), currentRate, Integer.parseInt(termInput.getText()));
            }
            //Add the new mortgage to the polymorphic array list.
            mortgages.add(userMortgage);
            displayMortages();
        } catch (Exception e) {
            //Catch any exceptions from the mortgage creation process.
            JOptionPane.showMessageDialog(null, "Invalid input. Message: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            addMortgage();
        }


    }

    //Displays the current list of mortgages if there is any available.
    public static void displayMortages() {
        String displayAll = "";
        if (mortgages.size() == 0) {
            displayAll = "No mortgages created.";
        } else {
            for (Mortgage mortgage : mortgages) {
                displayAll += mortgage.getMortgageInfo();
            }
        }
        JOptionPane.showMessageDialog(null, displayAll, "Mortgage information", JOptionPane.INFORMATION_MESSAGE);
        displayMenu();
    }


    //Helper method to parse a string into a double.
    public static double getInputDouble(String input) {
        try {
            if (input == null) {//User hit cancel or the X
                displayMenu();
            }
            return Double.parseDouble(input);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
            return -1.0;
        }
    }
}
