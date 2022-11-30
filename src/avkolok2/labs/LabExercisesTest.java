package avkolok2.labs;

import java.util.*;
import java.util.stream.Collectors;

public class LabExercisesTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LabExercises labExercises = new LabExercises();
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            String[] parts = input.split("\\s+");
            String index = parts[0];
            List<Integer> points = Arrays.stream(parts).skip(1)
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toList());

            labExercises.addStudent(new Student(index, points));
        }

        System.out.println("===printByAveragePoints (ascending)===");
        labExercises.printByAveragePoints(true, 100);
        System.out.println("===printByAveragePoints (descending)===");
        labExercises.printByAveragePoints(false, 100);
        System.out.println("===failed students===");
        labExercises.failedStudents().forEach(System.out::println);
        System.out.println("===statistics by year");
        labExercises.getStatisticsByYear().entrySet().stream()
                .map(entry -> String.format("%d : %.2f", entry.getKey(), entry.getValue()))
                .forEach(System.out::println);

    }
}

class LabExercises {
    List<Student> students;

    public LabExercises() {
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void printByAveragePoints(boolean asc, int n) {
        List<Student> tmp;
        tmp = students.stream().sorted(Comparator.comparing(Student::getAvgPoints).thenComparing(Student::getIndex)).collect(Collectors.toList());

        if (asc) {
            for (int i = 0; i < n && i < tmp.size(); i++) {
                System.out.println(tmp.get(i));
            }
        } else {
            for (int i = tmp.size() - 1; i >= tmp.size() - n && i >= 0; i--) {
                System.out.println(tmp.get(i));
            }
        }
    }

    public Iterable<Object> failedStudents() {
        return students.stream().filter(s -> !s.isPassed()).sorted(Comparator.comparing(Student::getIndex).thenComparing(Student::getAvgPoints)).collect(Collectors.toList());
    }

    public Map<Integer, Double> getStatisticsByYear() {
        return students.stream().filter(Student::isPassed).collect(Collectors.groupingBy(
                Student::getYear,
                Collectors.averagingDouble(Student::getAvgPoints)
        ));
    }
}

class Student {
    private String index;
    private List<Integer> points;
    private double avgPoints;
    private boolean passed;

    public Student(String index, List<Integer> points) {
        this.index = index;
        this.points = points;
        this.avgPoints = points.stream().mapToDouble(x -> (double) x).sum() / 10;
        this.passed = points.size() >= 8;
    }

    public String getIndex() {
        return index;
    }

    public List<Integer> getPoints() {
        return points;
    }

    public double getAvgPoints() {
        return avgPoints;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(index);
        sb.append(" ");
        if (passed)
            sb.append("YES");
        else
            sb.append("NO");

        sb.append(String.format(" %.2f", avgPoints));

        return sb.toString();
    }

    public boolean isPassed() {
        return passed;
    }

    public int getYear(){
        return 20 - Integer.parseInt(index) / 10000;
    }
}