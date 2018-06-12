package Ex2;

import java.util.Optional;

public class PartTimeStudent extends Student {

    public PartTimeStudent(String name) {
        super(name, false);
    }

    @Override
    public double tuitionFee(double creditHours) {
        return 100 * creditHours;
    }
}