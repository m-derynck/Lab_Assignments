package Ex1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InsuranceDriver {

    //Insurance array reference.
    private static ArrayList<Insurance> insuranceList = new ArrayList<>();

    public static void main(String[] args) {
        displayMenu();
    }

    //Display the insurance choice panel to the user
    public static void displayInsurance() {
        //Panel and layout initialization
        JPanel panel = new JPanel(new GridLayout(0, 2));
        JPanel comboBoxPane = new JPanel(); //use FlowLayout
        String[] comboBoxItems = {"Health Insurance", "Life Insurance"};
        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setEditable(false);
        comboBoxPane.add(cb);
        JLabel insuranceLbl = new JLabel("Choose insurance type:");
        panel.add(insuranceLbl);
        panel.add(comboBoxPane);
        JLabel monthlyCostLbl = new JLabel("Enter monthly cost:");
        JTextField monthlyCost = new JTextField();
        panel.add(monthlyCostLbl);
        panel.add(monthlyCost);
        int selection = JOptionPane.showConfirmDialog(null, panel, "Choose Insurance Type", JOptionPane.DEFAULT_OPTION);
        if(selection == -1){
            displayMenu();
        }
        double monthlyCostAmnt = inputToDouble(monthlyCost.getText());
        Insurance userInsurance;
        //Check if our monthly cost is a valid input
        if (monthlyCostAmnt != -1.0) {//Valid insurance
            if (cb.getSelectedIndex() == 0) {//Create new health insurance object
                userInsurance = new Health(monthlyCostAmnt);
            } else {//Create new life insurance object
                userInsurance = new Life(monthlyCostAmnt);
            }
            //Add this to the insurance list.
            insuranceList.add(userInsurance);
            //Display the newly created object to the user.
            displayInformation(userInsurance);
            displayMenu();
        } else {//Input was invalid, display the insurance panel again.
            displayInsurance();
        }
    }

    //Displays the newly created insurance object's information.
    public static void displayInformation(Insurance insurance) {
        JPanel panel = new JPanel();
        JLabel info = new JLabel(insurance.displayInfo());
        panel.add(info);
        JOptionPane.showConfirmDialog(null, panel, "Insurance Information", JOptionPane.DEFAULT_OPTION);
    }

    //Helper method to check the user's input.
    private static double inputToDouble(String input) {
        try {
            return Double.parseDouble(input);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input type.", "Error", JOptionPane.ERROR_MESSAGE);
            return -1.0;
        }
    }

    //Display's a menu to the user with a few options. Updates the menu if the user has already added an insurance policy.
    private static void displayMenu() {
        String[] options;
        int selection;
        boolean multiplePolicies = (insuranceList.size() > 0);
        if (multiplePolicies) {
            options = new String[]{"New Policy", "List policies", "Exit"};
            selection = JOptionPane.showOptionDialog(null, "Select an option", "Insurance Menu", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, 0);
        } else {
            options = new String[]{"New Policy", "Exit"};
            selection = JOptionPane.showOptionDialog(null, "Select an option", "Insurance Menu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, 0);
        }

        if (selection == 0){//Create a new insurance policy
            displayInsurance();
        }
        else if(selection == 1){
            if(multiplePolicies){//Display all the insurance policies.
                polymorphicSetDisplay();
                displayMenu();
            }
            else{//Exit the system.
                System.exit(0);
            }
        }
        else{//Exit the system.
            System.exit(0);
        }
    }

    //This display lists all current insurance policies and add's $100 to the monthly cost.
    private static void polymorphicSetDisplay() {
        for (Insurance ins : insuranceList) {
            //Gets the current cost and just adds 100 to it.
            ins.setInsuranceCost(ins.getMonthlyCost() + 100);
            //Display this change to the user in a message box.
            JOptionPane.showMessageDialog(null, ins.displayInfo(), "Polymorphic Set & Display", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
