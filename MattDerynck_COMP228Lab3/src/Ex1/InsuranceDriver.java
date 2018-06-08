package Ex1;

public class InsuranceDriver {
    public static void main(String[] args) {


        Health healthIns = new Health(100);
        healthIns.displayInfo();
        Life lifeIns = new Life(100);
        lifeIns.setInsuranceCost(200);
        lifeIns.displayInfo();
    }
}
