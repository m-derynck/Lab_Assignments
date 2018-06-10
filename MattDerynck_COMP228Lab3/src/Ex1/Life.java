package Ex1;

public class Life extends Insurance {
    private static final String type = "Life";

    public Life() {
        super(type);
    }

    public Life(double monthlyCost) {
        super(type, monthlyCost);
    }

    @Override
    public void setInsuranceCost(double monthlyCost) {
        this.monthlyCost = monthlyCost;
    }

    @Override
    public String displayInfo() {
        return String.format("%s Insurance Monthly Cost: $%.2f%n", getInsuranceType(), getMonthlyCost());
    }
}
