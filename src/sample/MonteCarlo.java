package sample;

public class MonteCarlo {
    private double res;
    private double integral;

    public MonteCarlo(double res) {
        this.res = res;
    }

    public double calculateIntegral(){
        double area = 16*16;
        integral = area*res;
        return integral;
    }
}
