package problems;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class TimesTest {

    public static void main(String[] args) {
        TimeTable timeTable = new TimeTable();
        try {
            timeTable.readTimes(System.in);
        } catch (UnsupportedFormatException e) {
            System.out.println("UnsupportedFormatException: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("InvalidTimeException: " + e.getMessage());
        }
        System.out.println("24 HOUR FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
        System.out.println("AM/PM FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
    }

}

enum TimeFormat {
    FORMAT_24, FORMAT_AMPM
}

class TimeTable{
    List<MyTime> timeList;

    public TimeTable() {
        this.timeList = new ArrayList<>();
    }

    public void readTimes(InputStream in) throws UnsupportedFormatException, InvalidTimeException {
        Scanner scanner = new Scanner(in);
        while(scanner.hasNextLine()){
            String[] parts = scanner.nextLine().split(" ");
            for(String part: parts)
                timeList.add(new MyTime(part));
        }
    }

    public void writeTimes(PrintStream out, TimeFormat format) {
        timeList.stream().sorted(Comparator.naturalOrder()).forEach(x -> out.println(x.getFormatted(format)));
        out.flush();
    }
}

class MyTime implements Comparable<MyTime>{
    private int hours;
    private int minutes;

    public MyTime(String time) throws UnsupportedFormatException, InvalidTimeException {
        String [] parts;
        if(time.contains("."))
            parts = time.split("\\.");
        else if(time.contains(":"))
            parts = time.split(":");
        else
            throw new UnsupportedFormatException(time);

        int h = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);

        if(h > 23 || h < 0 || m > 59 || m < 0)
            throw new InvalidTimeException();

        this.hours = h;
        this.minutes = m;
    }

    public String getFormatted(TimeFormat format){
        switch(format){
            case FORMAT_24:
                return String.format("%2s:%02d", hours, minutes);
            case FORMAT_AMPM:
                String tmp = "AM";
                int h = hours;
                if(h > 12){
                    h -= 12;
                    if(h != 24) {
                        tmp = "PM";
                    }
                }else if(h == 12)
                    tmp = "PM";
                else if(h == 0)
                    h = 12;
                return String.format("%2s:%02d %s", h, minutes, tmp);
            default:
                return null;
        }
    }

    @Override
    public int compareTo(MyTime o) {
        return Integer.compare(this.hours * 60 + this.minutes, o.hours * 60 + o.minutes);
    }
}

class UnsupportedFormatException extends Exception{
    public UnsupportedFormatException(String time) {
        super(time);
    }
}


class InvalidTimeException extends Exception{}