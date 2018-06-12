package Ex3;

import javafx.scene.control.DatePicker;

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
        double currentRate;
        do {
            currentRate = getInputDouble(JOptionPane.showInputDialog(null, "Enter current interest rate"));
        }
        while (currentRate == -1.0);
        JPanel panel = new JPanel(new GridLayout(0, 2));
        //Panel input variables.
        JComboBox mortgageTypeInput = new JComboBox<>(new String[]{"Business", "Personal"});
        JTextField nameInput = new JTextField();
        JTextField amountInput = new JTextField();
        JTextField termInput = new JTextField();

        //Panel layout
        panel.add(new JLabel("Mortgage Type:"));
        panel.add(mortgageTypeInput);
        panel.add(new JLabel("Name:"));
        panel.add(nameInput);
        panel.add(new JLabel("Mortgage Amount:"));
        panel.add(amountInput);
        panel.add(new JLabel("Term:"));
        panel.add(termInput);
        JOptionPane.showMessageDialog(null, panel, "Choose Mortgage", JOptionPane.INFORMATION_MESSAGE);
        Mortgage userMortgage;

        if (mortgageTypeInput.getSelectedIndex() == 0) {
            //Create new Business Mortgage.
            userMortgage = new BusinessMortgage(1, nameInput.getText(),
                    Double.parseDouble(amountInput.getText()), currentRate, Integer.parseInt(termInput.getText()));
        } else {
            //Create new Personal Mortgage.
            userMortgage = new PersonalMortgage(1, nameInput.getText(),
                    Double.parseDouble(amountInput.getText()), currentRate, Integer.parseInt(termInput.getText()));
        }
        mortgages.add(userMortgage);
        displayMortages();
    }

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


    public static double getInputDouble(String input) {
        try {
            double currentRate = Double.parseDouble(input);
            return currentRate;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
            return -1.0;
        }
    }
}
