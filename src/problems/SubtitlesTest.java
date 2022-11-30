package problems;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class SubtitlesTest {
    public static void main(String[] args) {
        Subtitles subtitles = new Subtitles();
        int n = subtitles.loadSubtitles(System.in);
        System.out.println("+++++ ORIGINIAL SUBTITLES +++++");
        subtitles.print();
        int shift = n * 37;
        shift = (shift % 2 == 1) ? -shift : shift;
        System.out.println(String.format("SHIFT FOR %d ms", shift));
        subtitles.shift(shift);
        System.out.println("+++++ SHIFTED SUBTITLES +++++");
        subtitles.print();
    }
}

class Subtitles{
    List<Subtitle> list;

    public Subtitles() {
        this.list = new ArrayList<>();
    }

    public int loadSubtitles(InputStream in) {
        Scanner scanner = new Scanner(in);
        String line;
        List<String> args = new ArrayList<>();
        while(scanner.hasNextLine()){
            while(scanner.hasNextLine()){
                line = scanner.nextLine();
                if(line.equals(""))
                    break;
                args.add(line);
            }
            list.add(new Subtitle(args));
            args.clear();
        }

        return list.size();
    }

    public void print() {
        list.forEach(System.out::println);
    }

    public void shift(int shift) {
        list.forEach(x -> x.shift(shift));
    }
}

class Subtitle{
    private int id;
    private List<String> text;
    private long start;
    private long end;

    public Subtitle(String id, List<String> text, String time) {
        this.id = Integer.parseInt(id);
        this.text = text;
        String[] parts = time.split(" --> ");
        this.start = parseTimeStamp(parts[0]);
        this.end = parseTimeStamp(parts[1]);
    }

    public Subtitle(List<String> args) {
        this.id = Integer.parseInt(args.get(0));
        String[] parts = args.get(1).split(" --> ");
        this.start = parseTimeStamp(parts[0]);
        this.end = parseTimeStamp(parts[1]);

        this.text = new ArrayList<>();
        for(int i = 2; i < args.size(); i++)
            text.add(args.get(i));

        //Ne raboti ova vo code
//        this.text = List.copyOf(args.subList(2, args.size()));
    }

    private long parseTimeStamp(String timestamp){
        long result = 0L;
        String[] parts = timestamp.split(":");
        result += 3600L * 1000L * Long.parseLong(parts[0]);
        result += 60L * 1000L * Long.parseLong(parts[1]);
        parts = parts[2].split(",");
        result += 1000L * Long.parseLong(parts[0]);
        result += Long.parseLong(parts[1]);
        return result;
    }

    private String createTimestamp(long time){
        long mils = time % 1000L;
        time /= 1000L;
        long s = time % 60;
        time /= 60;
        long m = time % 60;
        time /= 60;
        long h = time;

        return String.format("%02d:%02d:%02d,%03d", h, m, s, mils);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append("\n");
        sb.append(createTimestamp(start)).append(" --> ").append(createTimestamp(end)).append("\n");
        text.forEach(x -> sb.append(x).append("\n"));
        return sb.toString();
    }

    public void shift(int a){
        start += a;
        end += a;
    }
}