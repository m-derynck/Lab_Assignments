package Ex1;

public abstract class Insurance {
    protected String insuranceType;
    protected double monthlyCost;

    public abstract void setInsuranceCost(double cost);
    public abstract void displayInfo();


    public String getInsuranceType() {
        return insuranceType;
    }

    public double getMonthlyCost() {
        return monthlyCost;
    }

}
