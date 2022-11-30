//package avkolok2.labs;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class LabExercises {
//    List<Student> students;
//
//    public LabExercises() {
//        this.students = new ArrayList<>();
//    }
//
//    public void addStudent(Student student) {
//        students.add(student);
//    }
//
//    public void printByAveragePoints(boolean asc, int n) {
//        List<Student> tmp;
//        tmp = students.stream().sorted(Comparator.comparing(Student::getAvgPoints).thenComparing(Student::getIndex)).collect(Collectors.toList());
//
//        if(asc){
//            for(int i = 0; i < n && i < tmp.size(); i++){
//                System.out.println(tmp.get(i));
//            }
//        }else{
//            for(int i = tmp.size() - 1; i < n && i >= 0; i--){
//                System.out.println(tmp.get(i));
//            }
//        }
//    }
//
//    public Iterable<Object> failedStudents() {
//        return students.stream().filter(s -> !s.isPassed()).collect(Collectors.toList());
//    }
//
//    public Map<Integer, Double> getStatisticsByYear() {
//        return students.stream().filter(Student::isPassed).collect(Collectors.groupingBy(
//                Student::getYear,
//                Collectors.averagingDouble(Student::getAvgPoints)
//        ));
//    }
//}
