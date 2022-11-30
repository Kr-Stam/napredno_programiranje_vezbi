package problems.kalendarnanastani;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.IntStream;

public class EventCalendarTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int year = scanner.nextInt();
        scanner.nextLine();
        EventCalendar eventCalendar = new EventCalendar(year);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            String name = parts[0];
            String location = parts[1];
            Date date = df.parse(parts[2]);
            try {
                eventCalendar.addEvent(name, location, date);
            } catch (WrongDateException e) {
                System.out.println(e.getMessage());
            }
        }
        Date date = df.parse(scanner.nextLine());
        eventCalendar.listEvents(date);
        eventCalendar.listByMonth();
    }
}

// vashiot kod ovde
class EventCalendar{
    int year;
    Map<Date, TreeSet<Event>> events;

    public EventCalendar(int year) {
        this.year = year;
        this.events = new HashMap<>();
    }

    public void addEvent(String name, String location, Date date) throws WrongDateException {
        if((date.getYear() + 1900) != year) {
            String tmp = String.format("Wrong date: %s", date);
            tmp = tmp.replace("GMT", "UTC");
            throw new WrongDateException(tmp);
        }
        Date tmp = new Date(date.getYear(), date.getMonth(), date.getDate());
        if(!events.containsKey(tmp))
            events.put(tmp , new TreeSet<>());
        events.get(tmp).add(new Event(name, location, date));
    }

    public void listEvents(Date date){
        if(events.containsKey(date))
            events.get(date).forEach(System.out::println);
        else
            System.out.println("No events on this day!");
    }

    public void listByMonth(){
        IntStream.range(0, 12).forEach(m ->{
            int tmp = events.keySet()
                    .stream()
                    .filter(x -> x.getMonth() == m)
                    .mapToInt(x -> events.get(x).size())
                    .sum();
            System.out.println(String.format("%d : %d", m + 1, tmp));
        });
    }
}

class Event implements Comparable<Event>{
    private String name;
    private String location;
    private Date time;

    public Event(String name, String location, Date time) {
        this.name = name;
        this.location = location;
        this.time = time;
    }

    @Override
    public int compareTo(Event o) {
        return Comparator
                .comparing(Event::getTime)
                .thenComparing(Event::getName)
                .compare(this, o);
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Date getTime() {
        return time;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyy HH:mm");
        return String.format("%s at %s, %s", sdf.format(time), location, name);
    }
}

class WrongDateException extends Exception{
    public WrongDateException(String msg) {
        super(msg);
    }
}
/*
    public void listByMonth() - ги печати сите месеци (1-12) со бројот на настани во тој месец.


 */