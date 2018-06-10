package Ex1;

public class Health extends Insurance {
    private static final String type = "Health";

    public Health() {
        super(type);
    }

    public Health(double monthlyCost) {
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
