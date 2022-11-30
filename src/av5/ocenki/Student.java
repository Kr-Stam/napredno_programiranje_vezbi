package av5.ocenki;

public class Student implements Comparable<Student>{
    private String lastName;
    private String firstName;
    private int exam1;
    private int exam2;
    private int exam3;

    private double grade;
    private char mark;

    public Student(String lastName, String firstName, int exam1, int exam2, int exam3) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.exam1 = exam1;
        this.exam2 = exam2;
        this.exam3 = exam3;
        this.grade = exam1 * 0.25 + exam2 * 0.3 + exam3 * 0.45;
        this.mark = calcMark();
    }

    public Student(String line) {
        String[] tmp = line.split(":");
        this.lastName = tmp[0];
        this.firstName = tmp[1];
        this.exam1 = Integer.parseInt(tmp[2]);
        this.exam2 = Integer.parseInt(tmp[3]);
        this.exam3 = Integer.parseInt(tmp[4]);
        this.grade = exam1 * 0.25 + exam2 * 0.3 + exam3 * 0.45;
        this.mark = calcMark();
    }

    private char calcMark(){
        char result = 'f';
        if(grade >= 0 && grade < 60)
            result = 'F';
        else if(grade >= 60 && grade < 70)
            result = 'D';
        else if(grade >= 70 && grade < 80)
            result = 'C';
        else if(grade >= 80 && grade < 90)
            result = 'B';
        else if(grade >= 90)
            result = 'A';
        return result;
    }

    public char getMark() {
        return mark;
    }

    @Override
    public String toString() {
        return String.format("%s %s %c", lastName, firstName, mark);
    }

    @Override
    public int compareTo(Student o) {
        return Double.compare(this.grade, o.grade);
    }

    public String print(){
        return String.format("%s %s %d %d %d %c", lastName, firstName, exam1, exam2, exam3, mark);
    }
}
