package problems.studentskidosiea;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * January 2016 Exam problem 1
 */
public class StudentRecordsTest {
    public static void main(String[] args) {
        System.out.println("=== READING RECORDS ===");
        StudentRecords studentRecords = new StudentRecords();
        int total = studentRecords.readRecords(System.in);
        System.out.printf("Total records: %d\n", total);
        System.out.println("=== WRITING TABLE ===");
        studentRecords.writeTable(System.out);
        System.out.println("=== WRITING DISTRIBUTION ===");
        studentRecords.writeDistribution(System.out);
    }
}

// your code here

class StudentRecords{
    Map<String, TreeSet<Record>> records;

    public StudentRecords() {
        this.records = new TreeMap<>();
    }

    public int readRecords(InputStream in){
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        Stream<String> lines = br.lines();
        AtomicInteger count = new AtomicInteger();
        lines.forEach(line -> {
            String code = line.split(" ")[1];
            if(!records.containsKey(code))
                records.put(code, new TreeSet<>());
            records.get(code).add(new Record(line));
            count.getAndIncrement();
        });
        return count.get();
    }

    public void writeTable(OutputStream outputStream){
        PrintWriter pw = new PrintWriter(outputStream);
        records.entrySet().forEach(group -> {
            pw.println(group.getKey());
            group.getValue().forEach(record -> {
                pw.println(String.format("%s %.2f", record.getCode(), record.getAvg()));
            });
        });
        pw.flush();
    }

    public void writeDistribution(OutputStream outputStream){
        PrintWriter pw = new PrintWriter(outputStream);
        Map<String, int[]> result = new HashMap<>();
        records.forEach((group, records) -> {
            int[] count = new int[5];
            records.forEach(record -> {
                int[] rCount = record.getCount();
                IntStream.range(0, 5).forEach(i -> {
                    count[i] += rCount[i];
                });
            });

            result.put(group, count);
        });
        result.entrySet().stream().sorted((left, right) -> Integer.compare(right.getValue()[4], left.getValue()[4])).forEach(group -> {
            pw.println(group.getKey());
            int[] count = group.getValue();

            StringBuilder sb = new StringBuilder();
            IntStream.range(0, 5).forEach(i -> {
                sb.append(String.format("%2s | ", i + 6));
                int tmp = count[i] / 10;
                IntStream.range(0, count[i]/10 + 1).forEach(x -> sb.append("*"));
                if(count[i] % 10 == 0)
                    sb.deleteCharAt(sb.length() - 1);
                sb.append("(").append(count[i]).append(")\n");
            });
            sb.deleteCharAt(sb.length() - 1);
            pw.println(sb);
        });
        pw.flush();
    }
}

class Record implements Comparable<Record>{
    private String code;
    private String group;
    private List<Integer> grades;
    private int[] count;
    private DoubleSummaryStatistics stats;

    public Record(String line) {
        String[] parts = line.split(" ");
        this.code = parts[0];
        this.group = parts[1];
        this.grades = new ArrayList<>();
        this.count = new int[5];

        IntStream.range(2, parts.length).forEach(i -> {
            int num = Integer.parseInt(parts[i]);
            grades.add(num);
            count[num - 6]++;
        });

        this.stats = grades.stream().mapToDouble(x -> x).summaryStatistics();
    }

    @Override
    public int compareTo(Record o) {
        return Comparator.comparing(Record::getAvg).reversed().thenComparing(Record::getCode).compare(this, o);
    }

    public double getAvg(){
        return stats.getAverage();
    }

    public String getCode() {
        return code;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    public int[] getCount() {
        return count;
    }
}