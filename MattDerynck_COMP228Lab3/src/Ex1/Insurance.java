package Ex1;

public abstract class Insurance {
    protected String insuranceType;
    protected double monthlyCost;

    protected Insurance(String insuranceType){
        this.insuranceType = insuranceType;
    }

    protected Insurance(String insuranceType,double monthlyCost){
        this.insuranceType = insuranceType;
        this.monthlyCost = monthlyCost;
    }

    public abstract void setInsuranceCost(double cost);//Sets the insurance cost
    public abstract String displayInfo();//Used to return a formatted string of the insurance object's properties.


    public String getInsuranceType() {
        return insuranceType;
    }

    public double getMonthlyCost() {
        return monthlyCost;
    }

}
