package Ex2;

import java.util.Optional;

public abstract class Student {

    private String name;
    private boolean fullTime;

    public Student(String name, boolean fullTime){
        this.name = name;
        this.fullTime = fullTime;
    }

    public abstract double tuitionFee(double creditHours);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFullTime() {
        return fullTime;
    }

    public void setFullTime(boolean fullTime) {
        this.fullTime = fullTime;
    }
}
