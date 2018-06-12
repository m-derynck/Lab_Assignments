package Ex2;

import java.util.Scanner;

public class StudentDriver {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Student student = null;

        System.out.println("Student creation application");
        System.out.println("Please enter your name:");
        String name = input.nextLine();
        System.out.printf("Please choose a Student type:%n1) Full-Time Student%n2) Part-Time Student%n");

        int studentType;
        boolean validInput;
        do {
            try {
                studentType = Integer.parseInt(input.nextLine());
                if (studentType == 1) {
                    student = new FullTimeStudent(name);
                    validInput = true;
                } else if (studentType == 2) {
                    student = new PartTimeStudent(name);
                    validInput = true;
                } else {
                    validInput = false;
                    System.out.println("Enter 1 or 2.");
                }
            } catch (Exception e) {
                System.out.println("Bad input.");
                validInput = false;
            }
        }
        while (!validInput);

        System.out.println("Number of credit hours:");
        double creditHours = 0;
        boolean validHoursInput;
        do {
            try {
                creditHours = Double.parseDouble(input.nextLine());
                validHoursInput = true;

            } catch (Exception e) {
                System.out.println("Bad input.");
                validHoursInput = false;
            }
        }
        while (!validHoursInput);

        System.out.println("Student creation successful. Student details:");
        System.out.printf("Name: %s%nStatus: %s%nCredit Hours: %.2f%nTuition Fees: $%.2f%n",student.getName(),student.isFullTime() ? "Full-Time" : "Part-Time",creditHours,student.tuitionFee(creditHours));

    }
}
