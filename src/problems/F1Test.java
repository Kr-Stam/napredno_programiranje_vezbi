package problems;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class F1Test {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}

class F1Race {
    List<Driver> drivers;

    public void readResults(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        drivers = br.lines().map(Driver::new).collect(Collectors.toList());
    }

    public void printSorted(PrintStream out) {
        List<Driver> tmp = drivers.stream().sorted().collect(Collectors.toList());
        for(int i = 0; i < tmp.size(); i++){
            System.out.println(String.format("%d. %s", i + 1, tmp.get(i)));
        }
    }
}

class Driver implements Comparable<Driver>{
    private String name;
    private long[] laps;

    public Driver(String line) {
        String[] parts1 = line.split(" ");
        this.name = parts1[0];
        this.laps = new long[3];
        int[] parts = Arrays.stream(parts1[1].split(":")).mapToInt(Integer::parseInt).toArray();
        this.laps[0] = parts[0] * 60 * 1000L + parts[1] * 1000L + parts[2];
        parts = Arrays.stream(parts1[2].split(":")).mapToInt(Integer::parseInt).toArray();
        this.laps[1] = parts[0] * 60 * 1000L + parts[1] * 1000L + parts[2];
        parts = Arrays.stream(parts1[3].split(":")).mapToInt(Integer::parseInt).toArray();
        this.laps[2] = parts[0] * 60 * 1000L + parts[1] * 1000L + parts[2];
    }

    long getBestTime(){
        return Arrays.stream(laps).min().getAsLong();
    }

    @Override
    public int compareTo(Driver o) {
        return Long.compare(this.getBestTime(), o.getBestTime());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        long tmp = getBestTime();
        sb.append(tmp / 60 / 1000);
        sb.append(":");
        sb.append(String.format("%02d",tmp / 1000 % 60));
        sb.append(":");
        sb.append(String.format("%03d",tmp % 1000));

        return String.format("%-9s %10s", name, sb.toString());
    }

    public String getName() {
        return name;
    }
}
