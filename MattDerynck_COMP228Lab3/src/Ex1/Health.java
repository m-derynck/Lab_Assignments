package Ex1;

public class Health extends Insurance {

    public Health() {
    }

    public Health(double monthlyCost) {
        this.monthlyCost = monthlyCost;
    }

    @Override
    public void setInsuranceCost(double cost) {
        monthlyCost = cost;
    }

    @Override
    public void displayInfo() {
        System.out.printf("Health Insurance Monthly Cost: %s",getMonthlyCost());
    }
}
