package Ex1;

public class Health extends Insurance {

    public Health() {
        this.insuranceType = "Health";
    }

    public Health(double monthlyCost) {
        this();
        this.monthlyCost = monthlyCost;
    }

    @Override
    public void setInsuranceCost(double cost) {
        monthlyCost = cost;
    }

    @Override
    public void displayInfo() {
        System.out.printf("%s Insurance Monthly Cost: $%s%n",getInsuranceType(),getMonthlyCost());
    }
}
