package problems;

import java.util.*;

public class TripleTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.average());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.average());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.average());
        tDouble.sort();
        System.out.println(tDouble);
    }
}

class Triple<T extends Number>{
    List<T> list;

    public Triple(T a, T b, T c) {
        this.list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);
    }


    public double max() {
        return list.stream().mapToDouble(Number::doubleValue).summaryStatistics().getMax();
    }

    public double average(){
        return list.stream().mapToDouble(Number::doubleValue).summaryStatistics().getAverage();
    }

    public void sort(){
        list.sort(Comparator.comparingDouble(Number::doubleValue));
    }

    @Override
    public String toString() {
        double A = list.get(0).doubleValue();
        double B = list.get(1).doubleValue();
        double C = list.get(2).doubleValue();
        return String.format("%.2f %.2f %.2f", A, B, C);
    }
}
