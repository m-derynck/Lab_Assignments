package Ex3;

public abstract class Mortgage implements MortgageConstants {
/*
an abstract class that implements the MortgageConstants interface.
A Mortgage includes a mortgage number, customer name, amount of mortgage,
interest rate, and term.
Don’t allow mortgage amounts over $300,000. Force any
mortgage term that is not defined in the MortgageConstants
interface to a short-term, one year loan. Create a getMortgageInfo
method to display all the mortgage data.
*/


    private int mortgageNumber;
    private String customerName;
    private double mortgageAmount;
    private double interestRate;
    private int term;

    public Mortgage(int mortgageNumber, String customerName, double mortgageAmount, double interestRate, int term){
        this.mortgageNumber = mortgageNumber;
        this.customerName = customerName;
        setMortgageAmount(mortgageAmount);
        this.interestRate = interestRate;
        setTerm(term);
    }

    public String getMortgageInfo() {

        double amountOwing = ((getMortgageAmount() * (getInterestRate()/100)) * term) + getMortgageAmount();

        return String.format("Mortgage Provided by: %s%nMortgage Number: %d%nCustomer Name: %s%nMortgage Amount: $%.2f%nInterest Rate: %.2f%nMortgage Term: %d Year(s)%nAmount Owing: $%.2f%n%n",
                bankName,getMortgageNumber(), getCustomerName(), getMortgageAmount(), getInterestRate(), getTerm(), amountOwing);
    }


    //Getter & Setters
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getMortgageAmount() {
        return mortgageAmount;
    }

    public void setMortgageAmount(double mortgageAmount) {
        if (mortgageAmount > maxMortgage) {//Check if the amount being set is greater than 300,000
            this.mortgageAmount = maxMortgage;
        } else {
            this.mortgageAmount = mortgageAmount;
        }
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        if (term == shortTerm || term == mediumTerm || term == longTerm) {
            this.term = term;
        } else {
            this.term = shortTerm;
        }
    }

    public int getMortgageNumber() {
        return mortgageNumber;
    }

}