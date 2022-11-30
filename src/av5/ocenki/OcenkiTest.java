package av5.ocenki;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OcenkiTest {

    public static void main(String[] args) {
        try{
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            ArrayList<Student> students = parse(br);
            students.sort(Comparator.reverseOrder());
            students.forEach(System.out::println);
            writeToFile(students);
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Student> parse(BufferedReader br){
        return new ArrayList<>(br.lines().map(Student::new).toList());
    }

    public static void writeToFile(List<Student> students) throws IOException {
        FileWriter fw = new FileWriter("result.txt");
        System.setOut(new PrintStream("C:\\Users\\krist\\IdeaProjects\\napredno\\src\\av5\\ocenki\\result.txt"));
        int[] counts = new int[5];
        for(Student student: students) {
            System.out.println(student.print());
            switch (student.getMark()){
                case 'A': counts[0]++; break;
                case 'B': counts[1]++; break;
                case 'C': counts[2]++; break;
                case 'D': counts[3]++; break;
                case 'F': counts[4]++; break;
            }
        }
        System.out.println("A " + counts[0]);
        System.out.println("B " + counts[1]);
        System.out.println("C " + counts[2]);
        System.out.println("D " + counts[3]);
        System.out.println("F " + counts[4]);

        System.setOut(System.out);
    }
}
