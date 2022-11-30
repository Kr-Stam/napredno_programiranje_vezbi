package problems;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class WeatherStationTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            Date date = df.parse(line);
            ws.addMeasurement(temp, wind, hum, vis, date);
        }
        String line = scanner.nextLine();
        Date from = df.parse(line);
        line = scanner.nextLine();
        Date to = df.parse(line);
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}

class WeatherStation{
    private int n;
    private List<Measurement> list;
    private int count;

    public WeatherStation(int n) {
        this.n = n;
        this.list = new ArrayList<>();
    }

    public void addMeasurement(float temp, float wind, float hum, float vis, Date date) {
        Measurement tmp = new Measurement(temp, wind, hum, vis, date);
        if(list.isEmpty()) {
            list.add(tmp);
        }else if(Measurement.timeDiff(list.get(list.size() - 1), tmp) && (list.get(list.size() - 1).getDate().getTime() < tmp.getDate().getTime())) {
            list.add(tmp);
            if(((list.get(list.size() - 1).getDate().getTime() - list.get(0).getDate().getTime()) / 3600L / 24L) > (n * 1000L)) {
                this.list = list.stream().filter(x -> ((tmp.getDate().getTime() - x.getDate().getTime()) / 3600L / 24L) < (n * 1000L)).collect(Collectors.toList());
            }
        }
    }

    public int total() {
        return list.size();
    }

    public void status(Date from, Date to) {
        List<Measurement> tmp = list.stream().filter(x -> Measurement.timeFrame(x, from, to)).collect(Collectors.toList());
        if(tmp.isEmpty())
            throw new RuntimeException();
        tmp.forEach(System.out::println);
        System.out.println(String.format("Average temperature: %.2f", tmp.stream().mapToDouble(Measurement::getTemp).summaryStatistics().getAverage()));
    }
}

class Measurement{

    private float temp;
    private float wind;
    private float hum;
    private float vis;
    private Date date;


    public Measurement(float temp, float wind, float hum, float vis, Date date) {
        this.temp = temp;
        this.wind = wind;
        this.hum = hum;
        this.vis = vis;
        this.date = date;
    }

    public float getTemp() {
        return temp;
    }

    public float getWind() {
        return wind;
    }

    public float getHum() {
        return hum;
    }

    public float getVis() {
        return vis;
    }

    public Date getDate() {
        return date;
    }

    public static boolean timeDiff(Measurement a, Measurement b){
        long timeA = a.date.getTime();
        long timeB = b.date.getTime();

        return (timeB - timeA) >= (25L * 6L * 1000L);
    }

    public static boolean timeFrame(Measurement a, Date from, Date to){
        long timeA = a.date.getTime();
        long timeFrom = from.getTime();
        long timeTo = to.getTime();

        return timeA >= timeFrom && timeA <= timeTo;
    }

    @Override
    public String toString() {
        return String.format("%.1f %.1f km/h %.1f%% %.1f km %s", temp, wind, hum, vis, date);
    }
}
