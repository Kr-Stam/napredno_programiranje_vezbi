//package avkolok2.labs;
//
//import java.util.List;
//
//public class Student {
//    private String index;
//    private List<Integer> points;
//    private double avgPoints;
//    private boolean passed;
//
//    public Student(String index, List<Integer> points) {
//        this.index = index;
//        this.points = points;
//        this.avgPoints = points.stream().mapToDouble(x -> (double) x).sum() / 10;
//        this.passed = points.size() >= 8;
//    }
//
//    public String getIndex() {
//        return index;
//    }
//
//    public List<Integer> getPoints() {
//        return points;
//    }
//
//    public double getAvgPoints() {
//        return avgPoints;
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(index);
//        sb.append(" ");
//        if (passed)
//            sb.append("YES");
//        else
//            sb.append("NO");
//
//        sb.append(String.format(" %.2f", avgPoints));
//
//        return sb.toString();
//    }
//
//    public boolean isPassed() {
//        return passed;
//    }
//
//    public int getYear(){
//        return 20 - Integer.parseInt(index) / 10000;
//    }
//}
