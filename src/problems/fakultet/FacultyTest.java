package problems.fakultet;

//package mk.ukim.finki.vtor_kolokvium;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class FCourse{
    String name;
    List<Integer> grades;

    public FCourse(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }

    public void addGrade(int grade){
        grades.add(grade);
    }

    public double getAvg(){
        return grades.stream().mapToDouble(Integer::doubleValue).summaryStatistics().getAverage();
    }

    public int getNum(){
        return grades.size();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s %d %.2f", name, getNum(), getAvg());
    }
}

class Faculty {
    Map<String, Student> students;
    List<FCourse> fCourses;
    List<String> logs;

    public Faculty() {
        this.students = new HashMap<>();
        this.fCourses = new ArrayList<>();
        logs = new ArrayList<>();
    }

    void addStudent(String id, int yearsOfStudies) {
        students.put(id, new Student(id, yearsOfStudies));
        if(yearsOfStudies > 4 || yearsOfStudies < 3)
            System.out.println("ERROR INVALID YEARSOFSTUDY");
    }

    void addGradeToStudent(String studentId, int term, String courseName, int grade) throws OperationNotAllowedException {
        Student student = students.get(studentId);
        if(students.containsKey(studentId)) {
            student.addGrade(term, courseName, grade);

            if(fCourses.stream().noneMatch(x -> x.getName().equals(courseName)))
                fCourses.add(new FCourse(courseName));

            FCourse course = fCourses.stream().filter(x -> x.getName().equals(courseName)).findFirst().get();
            course.addGrade(grade);
        }

        if (student.getCoursesPassed() == student.getYearsOfStudies() * 2 * 3) {
            logs.add(String.format("Student with ID %s graduated with average grade %.2f in %d years.", studentId, student.getAverageGrade(), student.getYearsOfStudies()));
            students.remove(studentId);
        }
    }

    String getFacultyLogs() {
        StringBuilder sb = new StringBuilder();
        logs.forEach(x -> sb.append(x).append("\n"));
        if(sb.length() > 0)
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    String getDetailedReportForStudent(String id) {
        return students.get(id).getDetailedReport();
    }

    void printFirstNStudents(int n) {
        List<Student> tmp = students.values().stream()
                .sorted(Comparator
                        .comparing(Student::getCoursesPassed)
                        .thenComparing(Student::getAverageGrade)
                        .thenComparing(Student::getId)
                        .reversed())
                .collect(Collectors.toList());
        if(tmp.size() > n)
            tmp.subList(0, n).forEach(x -> System.out.println(x.getShortReport()));
        else if(!tmp.isEmpty())
            tmp.forEach(x -> System.out.println(x.getShortReport()));
    }

    void printCourses() {
        fCourses.stream()
                .sorted(Comparator
                        .comparing(FCourse::getNum)
                        .thenComparing(FCourse::getAvg)
                        .thenComparing(FCourse::getName))
                .forEach(System.out::println);
    }
}

class Student{
    private String id;
    private int yearsOfStudies;
    List<Term> terms;

    public Student(String id, int yearsOfStudies) {
        this.id = id;
        this.yearsOfStudies = yearsOfStudies;
        this.terms = new ArrayList<>();
        IntStream.range(1, yearsOfStudies * 2 + 1).forEach(i -> {
            terms.add(new Term(i));
        });
    }

    public String getId() {
        return id;
    }

    public int getYearsOfStudies() {
        return yearsOfStudies;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void addGrade(int termNum, String courseName, int grade) throws OperationNotAllowedException {
        if(!termIsValid(termNum))
            throw new OperationNotAllowedException(String.format("Term %d is not possible for student with ID %s", termNum, id));
        Optional<Term> tmp = terms.stream().filter(x -> x.getNum() == termNum).findAny();

        Term term;
        term = tmp.get();
        if(!term.addGrade(courseName, grade))
            throw new OperationNotAllowedException(String.format("Student %s already has 3 grades in term %d", id, termNum));
    }

    private boolean termIsValid(int term){
        return term <= yearsOfStudies * 2;
    }

    public String getDetailedReport(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Student: %s", id)).append("\n");
        terms.forEach(x -> sb.append(x.getDetailedReport()));
        sb.append(String.format("Average grade: %.2f", getAverageGrade())).append("\n");
        sb.append(String.format("Courses attended: %s", getCoursesString()));
        return sb.toString();
    }

    public List<Course> getCourses(){
        return terms.stream().map(Term::getCourses).flatMap(List::stream).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public String getCoursesString(){
        return getCourses().stream().map(Course::getCourseName).sorted(Comparator.naturalOrder()).collect(Collectors.joining(","));
    }

    public double getAverageGrade(){
        if(getCourses().isEmpty())
            return 5;
        return getCourses().stream().mapToDouble(Course::getGrade).summaryStatistics().getAverage();
    }

    public int getCoursesPassed(){
        if(getCourses().isEmpty())
            return 0;
//        return (int) getCourses().stream().filter(x -> x.getGrade() >= 6).count();
        return getCourses().size();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public String getShortReport() {
        return String.format("Student: %s Courses passed: %d Average grade: %.2f", id, getCoursesPassed(), getAverageGrade());
    }
}

class Term{
    private int num;
    private List<Course> courses;

    public Term(int num) {
        this.num = num;
        this.courses = new ArrayList<>();
    }

    public int getNum() {
        return num;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public boolean addGrade(String courseName, int grade) {
        if(courses.size() == 3 || courses.stream().anyMatch(x -> x.getCourseName().equals(courseName)))
            return false;
        courses.add(new Course(courseName, grade));
        return true;
    }

    public String getDetailedReport(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Term %d\n", num));
        sb.append(String.format("Courses: %d", courses.size())).append("\n");
        sb.append(String.format("Average grade for term: %.2f", getAvg())).append("\n");
        return sb.toString();
    }

    public double getAvg(){
        if(courses.size() == 0)
            return 5;
        return courses.stream().mapToDouble(Course::getGrade).summaryStatistics().getAverage();
    }
}

class Course{
    private String courseName;
    private int grade;

    public Course(String courseName, int grade) {
        this.courseName = courseName;
        this.grade = grade;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getGrade() {
        return grade;
    }
}

public class FacultyTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();

        if (testCase == 1) {
            System.out.println("TESTING addStudent AND printFirstNStudents");
            Faculty faculty = new Faculty();
            for (int i = 0; i < 10; i++) {
                faculty.addStudent("student" + i, (i % 2 == 0) ? 3 : 4);
            }
            faculty.printFirstNStudents(10);

        } else if (testCase == 2) {
            System.out.println("TESTING addGrade and exception");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            try {
                faculty.addGradeToStudent("123", 7, "NP", 10);
            } catch (OperationNotAllowedException e) {
                System.out.println(e.getMessage());
            }
            try {
                faculty.addGradeToStudent("1234", 9, "NP", 8);
            } catch (OperationNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        } else if (testCase == 3) {
            System.out.println("TESTING addGrade and exception");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            for (int i = 0; i < 4; i++) {
                try {
                    faculty.addGradeToStudent("123", 1, "course" + i, 10);
                } catch (OperationNotAllowedException e) {
                    System.out.println(e.getMessage());
                }
            }
            for (int i = 0; i < 4; i++) {
                try {
                    faculty.addGradeToStudent("1234", 1, "course" + i, 10);
                } catch (OperationNotAllowedException e) {
                    System.out.println(e.getMessage());
                }
            }
        } else if (testCase == 4) {
            System.out.println("Testing addGrade for graduation");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            int counter = 1;
            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 3; j++) {
                    try {
                        faculty.addGradeToStudent("123", i, "course" + counter, (i % 2 == 0) ? 7 : 8);
                    } catch (OperationNotAllowedException e) {
                        System.out.println(e.getMessage());
                    }
                    ++counter;
                }
            }
            counter = 1;
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 3; j++) {
                    try {
                        faculty.addGradeToStudent("1234", i, "course" + counter, (j % 2 == 0) ? 7 : 10);
                    } catch (OperationNotAllowedException e) {
                        System.out.println(e.getMessage());
                    }
                    ++counter;
                }
            }
            System.out.println("LOGS");
            System.out.println(faculty.getFacultyLogs());
            System.out.println("PRINT STUDENTS (there shouldn't be anything after this line!");
            faculty.printFirstNStudents(2);
        } else if (testCase == 5 || testCase == 6 || testCase == 7) {
            System.out.println("Testing addGrade and printFirstNStudents (not graduated student)");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), i % 5 + 6);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            if (testCase == 5)
                faculty.printFirstNStudents(10);
            else if (testCase == 6)
                faculty.printFirstNStudents(3);
            else
                faculty.printFirstNStudents(20);
        } else if (testCase == 8 || testCase == 9) {
            System.out.println("TESTING DETAILED REPORT");
            Faculty faculty = new Faculty();
            faculty.addStudent("student1", ((testCase == 8) ? 3 : 4));
            int grade = 6;
            int counterCounter = 1;
            for (int i = 1; i < ((testCase == 8) ? 6 : 8); i++) {
                for (int j = 1; j < 3; j++) {
                    try {
                        faculty.addGradeToStudent("student1", i, "course" + counterCounter, grade);
                    } catch (OperationNotAllowedException e) {
                        e.printStackTrace();
                    }
                    grade++;
                    if (grade == 10)
                        grade = 5;
                    ++counterCounter;
                }
            }
            System.out.println(faculty.getDetailedReportForStudent("student1"));
        } else if (testCase==10) {
            System.out.println("TESTING PRINT COURSES");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            faculty.printCourses();
        } else if (testCase==11) {
            System.out.println("INTEGRATION TEST");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 2 : 3); k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }

            }

            for (int i=11;i<15;i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= 3; k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            System.out.println("LOGS");
            System.out.println(faculty.getFacultyLogs());
            System.out.println("DETAILED REPORT FOR STUDENT");
            System.out.println(faculty.getDetailedReportForStudent("student2"));
            try {
                System.out.println(faculty.getDetailedReportForStudent("student11"));
                System.out.println("The graduated students should be deleted!!!");
            } catch (NullPointerException e) {
                System.out.println("The graduated students are really deleted");
            }
            System.out.println("FIRST N STUDENTS");
            faculty.printFirstNStudents(10);
            System.out.println("COURSES");
            faculty.printCourses();
        }
    }
}

class OperationNotAllowedException extends Exception{
    public OperationNotAllowedException(String message) {
        super(message);
    }
}

