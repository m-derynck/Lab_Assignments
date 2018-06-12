package Ex3;

public class PersonalMortgage extends Mortgage {

    public PersonalMortgage(int mortgageNumber, String customerName, double mortgageAmount, double interestRate, int term) {
        super(mortgageNumber, customerName, mortgageAmount, interestRate + 2.0, term);
    }
}
