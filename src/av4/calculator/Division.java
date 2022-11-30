package av4.calculator;

public class Division implements Strategy{
    @Override
    public double execute(double a, double b) {
        return a / b;
    }
}
