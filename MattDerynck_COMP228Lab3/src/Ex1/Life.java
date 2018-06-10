package Ex1;

public class Life extends Insurance {
    public Life() {
        this.insuranceType = "Life";
    }

    public Life(double monthlyCost){
        this();
        this.monthlyCost = monthlyCost;
    }

    @Override
    public void setInsuranceCost(double cost) {
        monthlyCost = cost;
    }

    @Override
    public String displayInfo() {
        return String.format("%s Insurance Monthly Cost: $%s%n",getInsuranceType(),getMonthlyCost());
    }
}
