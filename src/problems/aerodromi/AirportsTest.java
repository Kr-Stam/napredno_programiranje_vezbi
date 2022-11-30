package problems.aerodromi;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AirportsTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Airports airports = new Airports();
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] codes = new String[n];
        for (int i = 0; i < n; ++i) {
            String al = scanner.nextLine();
            String[] parts = al.split(";");
            airports.addAirport(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
            codes[i] = parts[2];
        }
        int nn = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < nn; ++i) {
            String fl = scanner.nextLine();
            String[] parts = fl.split(";");
            airports.addFlights(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        }
        int f = scanner.nextInt();
        int t = scanner.nextInt();
        String from = codes[f];
        String to = codes[t];
        System.out.printf("===== FLIGHTS FROM %S =====\n", from);
        airports.showFlightsFromAirport(from);
        System.out.printf("===== DIRECT FLIGHTS FROM %S TO %S =====\n", from, to);
        airports.showDirectFlightsFromTo(from, to);
        t += 5;
        t = t % n;
        to = codes[t];
        System.out.printf("===== DIRECT FLIGHTS TO %S =====\n", to);
        airports.showDirectFlightsTo(to);
    }
}

// vashiot kod ovde

class Airports {
    HashMap<String, Airport> airports;

    public Airports() {
        this.airports = new HashMap<>();
    }

    public void addAirport(String name, String country, String code, int passengers) {
        airports.put(code, new Airport(name, country, code, passengers));
    }

    public void addFlights(String from, String to, int time, int duration) {
        airports.get(to).addFlightFrom(new Flight(from, time, duration));
        airports.get(from).addFlightTo(new Flight(to, time, duration));
    }

    public void showFlightsFromAirport(String code) {
        airports.get(code).showFlightsFrom();
    }

    public void showDirectFlightsFromTo(String from, String to) {
        airports.get(from).showDirectFlightsFromTo(from, to);
    }

    public void showDirectFlightsTo(String to) {
        airports.get(to).showDirectFlightsTo();
    }
}

class Airport {
    private String name;
    private String country;
    private String code;
    private int passengers;
    private TreeSet<Flight> flightsFrom;
    private TreeSet<Flight> flightsTo;

    public Airport(String name, String country, String code, int passengers) {
        this.name = name;
        this.country = country;
        this.code = code;
        this.passengers = passengers;
        this.flightsTo = new TreeSet<>();
        this.flightsFrom = new TreeSet<>(Comparator.comparing(Flight::getTime).thenComparing(Flight::getTarget));
    }

    public void addFlightTo(Flight flight) {
        flightsTo.add(flight);
    }

    public void addFlightFrom(Flight flight) {
        flightsFrom.add(flight);
    }

    public void showFlightsFrom() {
        System.out.println(String.format("%s (%s)", name, code));
        System.out.println(country);
        System.out.println(passengers);
        AtomicInteger count = new AtomicInteger(1);
        flightsTo.forEach(x -> {
            System.out.println(String.format("%d. %s-%s %s %s", count.getAndIncrement(), code, x.getTarget(), x.getTimeString(), x.getDurationString()));
        });
    }

    public void showDirectFlightsFromTo(String from, String to) {
        List<Flight> tmp = flightsTo.stream().filter(x -> x.getTarget().equals(to)).collect(Collectors.toList());
        if(tmp.isEmpty()){
            System.out.println(String.format("No flights from %s to %s", from, to));
            return;
        }
        tmp.forEach(x -> {
            System.out.println(String.format("%s-%s %s %s", code, x.getTarget(), x.getTimeString(), x.getDurationString()));
        });
    }

    public void showDirectFlightsTo() {
        flightsFrom.forEach(x -> {
            System.out.println(String.format("%s-%s %s %s", x.getTarget(), code, x.getTimeString(), x.getDurationString()));
        });
    }
}

class Flight implements Comparable<Flight> {
    private String target;
    private int time;
    private int duration;

    public Flight(String target, int time, int duration) {
        this.target = target;
        this.time = time;
        this.duration = duration;
    }

    public String getTarget() {
        return target;
    }

    public int getTime() {
        return time;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public int compareTo(Flight o) {
        return Comparator.comparing(Flight::getTarget).thenComparing(Flight::getTime).compare(this, o);
    }

    public String getTimeString() {
        int tmp = time;
        int h = tmp / 60;
        if (h >= 24)
            h -= 24;
        tmp %= 60;
        int m = tmp;
        tmp = time + duration;
        int h1 = tmp / 60;
        tmp %= 60;
        int m1 = tmp;
        if (h1 >= 24)
            h1 -= 24;
        return String.format("%02d:%02d-%02d:%02d", h, m, h1, m1);
    }

    public Object getDurationString() {
        int tmp = duration;
        int h = tmp / 60;
        tmp %= 60;
        int m = tmp;
        StringBuilder sb = new StringBuilder();
        if (duration + time > 1440) {
            sb.append("+1d ");
        }
        sb.append(String.format("%dh%02dm", h, m));
        return sb.toString();
    }
}



/*


    public void showDirectFlightsTo(String to) - метод кои ги прикажува сите директни летови до аеродромот со код to.

Сите летови треба да бидат сортирани според времето на полетување (целосно точна имплементација се смета без повикување на sort методи).

 */