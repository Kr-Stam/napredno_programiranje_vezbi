package problems;

import java.util.Scanner;

public class GenericFractionTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double n1 = scanner.nextDouble();
        double d1 = scanner.nextDouble();
        float n2 = scanner.nextFloat();
        float d2 = scanner.nextFloat();
        int n3 = scanner.nextInt();
        int d3 = scanner.nextInt();
        try {
            GenericFraction<Double, Double> gfDouble = new GenericFraction<Double, Double>(n1, d1);
            GenericFraction<Float, Float> gfFloat = new GenericFraction<Float, Float>(n2, d2);
            GenericFraction<Integer, Integer> gfInt = new GenericFraction<Integer, Integer>(n3, d3);
            System.out.printf("%.2f\n", gfDouble.toDouble());
            System.out.println(gfDouble.add(gfFloat));
            System.out.println(gfInt.add(gfFloat));
            System.out.println(gfDouble.add(gfInt));
            gfInt = new GenericFraction<Integer, Integer>(n3, 0);
        } catch(ZeroDenominatorException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }

}

class GenericFraction<T extends Number, U extends Number>{
    T n;
    U d;

    public GenericFraction(T n, U d) throws ZeroDenominatorException {
        this.n = n;
        if(d.doubleValue() == 0)
            throw new ZeroDenominatorException("Denominator cannot be zero");
        this.d = d;
    }

    public double toDouble() {
        return n.doubleValue() / d.doubleValue();
    }

    public GenericFraction<Double, Double> add(GenericFraction<?, ?> fraction) throws ZeroDenominatorException {
        double den = this.d.doubleValue() * fraction.d.doubleValue();
        double num = this.d.doubleValue() * fraction.n.doubleValue() + this.n.doubleValue() * fraction.d.doubleValue();
        return new GenericFraction<Double, Double>(num, den);
    }

    public static GenericFraction<Double, Double> reduce(GenericFraction<?, ?> fraction) throws ZeroDenominatorException {
        double num, den;
        num = fraction.n.doubleValue();
        den = fraction.d.doubleValue();
        for(int i = 2; i < den && i < num;){
            if(den % i == 0 && num % i == 0){
                den /= i;
                num /= i;
            }else {
                i++;
            }
        }
        return new GenericFraction<Double, Double>(num, den);
    }

    @Override
    public String toString() {
        try {
            GenericFraction<Double, Double> tmp = reduce(this);
            return String.format("%.2f / %.2f", tmp.n.doubleValue(), tmp.d.doubleValue());
        } catch (ZeroDenominatorException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class ZeroDenominatorException extends Exception{
    public ZeroDenominatorException(String message) {
        super(message);
    }
}