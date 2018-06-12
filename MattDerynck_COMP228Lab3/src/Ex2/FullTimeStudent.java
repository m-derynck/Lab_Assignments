package Ex2;

import java.util.Optional;

public class FullTimeStudent extends Student {


    public FullTimeStudent(String name){
        super(name,true);
    }

    @Override
    public double tuitionFee(double creditHours) {
        return 2000;
    }
}
