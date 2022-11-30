package av4.calculator;

public class Multiplication implements Strategy{
    @Override
    public double execute(double a, double b) {
        return a * b;
    }
}
