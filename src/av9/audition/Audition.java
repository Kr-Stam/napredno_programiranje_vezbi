//package av9.audition;
//
//import java.util.HashSet;
//import java.util.List;
//
//public class Audition {
//    HashSet<Participant> hashSet;
//
//    public Audition() {
//        hashSet = new HashSet<>();
//    }
//
//    public void addParticpant(String name, String code, String city, int age) {
//        Participant tmp = new Participant(name, code, city, age);
//
//        hashSet.add(tmp);
//    }
//
//    public void listByCity(String city) {
//        hashSet.stream().filter(x -> x.getCity().equals(city)).forEach(System.out::println);
//    }
//}
