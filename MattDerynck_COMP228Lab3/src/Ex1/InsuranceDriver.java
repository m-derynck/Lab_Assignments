package Ex1;

/*


Write a driver class to test this hierarchy. This application should ask the user to enter
the type of insurance and its monthly fee. Then, will create the appropriate object
(Life or Health) and display the insurance information.

As you create each insurance object, place an Insurance reference to each new Insurance
object into an array. Each class has its own setInsuranceCost method. Write a polymorphic
screen manager that walks through the array sending setInsuranceCost messages to each
object in the array and displaying this information on the screen.

 */


import javax.swing.*;

public class InsuranceDriver {
    public static void main(String[] args) {
        Insurance[] insuranceList = new Insurance[2];
        Health healthIns = new Health(100);
        healthIns.displayInfo();
        Life lifeIns = new Life(100);
        lifeIns.setInsuranceCost(200);
        lifeIns.displayInfo();

        //JOptionPane.showMessageDialog(null,"Welcome to pour your money away App.");
        JPanel panel = new JPanel();
        JPanel comboBoxPane = new JPanel(); //use FlowLayout
        String[] comboBoxItems = { "Health Insurance", "Life Insurance" };
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
        JOptionPane.showConfirmDialog(null,panel,"Choose Insurance Type",JOptionPane.DEFAULT_OPTION);



    }
}
