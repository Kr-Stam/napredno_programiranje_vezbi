package av6.standarddeviation;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;

public class MyMathClass {

    public MyMathClass() {
    }

    public static double standardDeviation(ArrayList<? extends Number> list){
//        double sum = 0;
//        for(Number n: list)
//            sum += n.doubleValue();
//        double avg = sum / list.size();
//        sum = 0;
//        for(Number n: list)
//            sum += (avg - n.doubleValue()) * (avg - n.doubleValue());
//        return Math.sqrt(sum/ list.size());


        DoubleSummaryStatistics stats = list.stream().mapToDouble(Number::doubleValue).summaryStatistics();

        double result = 0;

        for(Number n: list){
            result += (stats.getAverage() - n.doubleValue()) * (stats.getAverage() - n.doubleValue());
        }

        return Math.sqrt(result / stats.getCount());
    }

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        System.out.println(standardDeviation(list));
    }
}
