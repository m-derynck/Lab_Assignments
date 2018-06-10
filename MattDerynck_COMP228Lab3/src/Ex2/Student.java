package Ex2;

public abstract class Student {

    private String name;
    private boolean fullTime;

    public Student(){

    }

    public abstract double tuitionFee();

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
