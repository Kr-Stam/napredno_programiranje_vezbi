package av9.audition;

import java.util.*;

public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticpant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}

class Participant implements Comparable<Participant>{
    String name;
    String code;
    String city;
    int age;

    public Participant(String city, String code, String name, int age) {
        this.name = name;
        this.code = code;
        this.city = city;
        this.age = age;
    }


    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return code.equals(that.code) && city.equals(that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, city);
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getCity() {
        return city;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d", code, name, age);
    }


    @Override
    public int compareTo(Participant o) {
        int result = this.name.compareTo(o.name);
        if(result == 0)
            result = Integer.compare(this.age, o.age);
        if(result == 0)
            result = this.code.compareTo(o.code);
        return result;
    }
}

class Audition {
    HashSet<Participant> hashSet;

    public Audition() {
        hashSet = new HashSet<>();
    }

    public void addParticpant(String name, String code, String city, int age) {
        Participant tmp = new Participant(name, code, city, age);

        hashSet.add(tmp);
    }

    public void listByCity(String city) {
        hashSet.stream().filter(x -> x.getCity().equals(city)).sorted(comparator).forEach(System.out::println);
    }

    private static final Comparator<Participant> comparator = Comparator.comparing(Participant::getName).thenComparing(Participant::getAge).thenComparing(Participant::getCode);
}