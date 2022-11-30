package problems.stopcorona;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

interface ILocation {
    double getLongitude();

    double getLatitude();

    LocalDateTime getTimestamp();
}

public class StopCoronaTest {

    public static double timeBetweenInSeconds(ILocation location1, ILocation location2) {
        return Math.abs(Duration.between(location1.getTimestamp(), location2.getTimestamp()).getSeconds());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        StopCoronaApp stopCoronaApp = new StopCoronaApp();

        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");

            switch (parts[0]) {
                case "REG": //register
                    String name = parts[1];
                    String id = parts[2];
                    try {
                        stopCoronaApp.addUser(name, id);
                    } catch (UserAlreadyExistException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "LOC": //add locations
                    id = parts[1];
                    List<ILocation> locations = new ArrayList<>();
                    for (int i = 2; i < parts.length; i += 3) {
                        locations.add(createLocationObject(parts[i], parts[i + 1], parts[i + 2]));
                    }
                    stopCoronaApp.addLocations(id, locations);

                    break;
                case "DET": //detect new cases
                    id = parts[1];
                    LocalDateTime timestamp = LocalDateTime.parse(parts[2]);
                    stopCoronaApp.detectNewCase(id, timestamp);

                    break;
                case "REP": //print report
                    stopCoronaApp.createReport();
                    break;
                default:
                    break;
            }
        }
    }

    private static ILocation createLocationObject(String lon, String lat, String timestamp) {
        return new ILocation() {
            @Override
            public double getLongitude() {
                return Double.parseDouble(lon);
            }

            @Override
            public double getLatitude() {
                return Double.parseDouble(lat);
            }

            @Override
            public LocalDateTime getTimestamp() {
                return LocalDateTime.parse(timestamp);
            }
        };
    }
}

class StopCoronaApp {
    private Map<String, User> users;
    private List<User> infected;

    public StopCoronaApp() {
        this.users = new HashMap<>();
        this.infected = new ArrayList<>();
    }

    public void addUser(String name, String id) throws UserAlreadyExistException {
        if (users.containsKey(id))
            throw new UserAlreadyExistException(id);
        users.put(id, new User(name, id));
    }

    public void addLocations(String id, List<ILocation> iLocations) {
        users.get(id).addLocations(iLocations);
    }

    public void detectNewCase(String id, LocalDateTime timestamp) {
        User user = users.get(id);
        user.setInfected(true);
        user.setInfectedAt(timestamp);
        infected.add(user);
    }

    Map<User, Integer> getDirectContacts(User user) {
        Map<User, Integer> result = new HashMap<>();
        users.values().stream()
                .filter(user2 -> !user2.equals(user))
                .forEach(user2 -> {
                    user.getLocations().forEach(location -> {
                        List<ILocation> tmp = user2.getLocations()
                                .stream().filter(location2 -> {
//                                    if(user2.getId().equals("ce776a00-561f-492d-a147-29d822bfdda6")) {
//                                        System.out.println(Duration.between(location.getTimestamp(), location2.getTimestamp()).toSeconds());
//                                        int a = Math.abs(location.getTimestamp().getMinute() - location2.getTimestamp().getMinute());
//                                        double b = Math.sqrt(Math.pow(location.getLatitude() - location2.getLatitude(), 2) + Math.pow(location.getLongitude() - location2.getLongitude(), 2));
//                                        System.out.println(String.format("%d %f %b", a, b, checkDistance(location, location2)));
//                                    }
                                    return checkDistance(location, location2);
                                })
                                .collect(Collectors.toList());
                        if (!tmp.isEmpty()) {
                            if(!result.containsKey(user2))
                                result.put(user2, tmp.size());
                            else
                                result.put(user2, result.get(user2) + tmp.size());
                        }
                    });
                });
        return result;
    }


    public static boolean checkDistance(ILocation location1, ILocation location2) {
        long duration = Duration.between(location1.getTimestamp(), location2.getTimestamp()).toMillis();
        if(Math.abs(duration) > 300000)
            return false;
        return Math.sqrt(Math.pow(location1.getLatitude() - location2.getLatitude(), 2) + Math.pow(location1.getLongitude() - location2.getLongitude(), 2)) <= 2;
    }

    public Collection<User> getIndirectContacts(User u) {
        List<User> indirectContacts = new ArrayList<>();
        Map<User, Integer> directContacts = getDirectContacts(u);

        HashSet<User> directContacts2 = new HashSet<>();
        directContacts.keySet().forEach(contact -> {
            directContacts2.addAll(getDirectContacts(contact).keySet());
        });

        return users.values().stream()
                .filter(user -> !directContacts.containsKey(user) && !user.equals(u))
                .filter(directContacts2::contains)
                .collect(Collectors.toList());
    }

    public void createReport(){
        List<Integer> indirectContactsStats = new ArrayList<>();
        List<Integer> directContactsStats = new ArrayList<>();
        infected.forEach(user -> {
            System.out.println(String.format("%s %s %s", user.getName(), user.getId(), user.getInfectedAt()));
            System.out.println("Direct contacts:");
            Map<User, Integer> directContacts = getDirectContacts(user);

            directContacts.entrySet().stream().sorted((left, right) -> {
                int result = 0;
                result = right.getValue().compareTo(left.getValue());
//                if(result != 0)
//                    return result;
//                result = left.getKey().getName().compareTo(right.getKey().getName());
//                if(result != 0)
//                    return result;
//                result = left.getKey().getId().compareTo(right.getKey().getId());
                return result;
            }).forEach(entry -> {
                System.out.println(String.format("%s %s %d", entry.getKey().getName(), entry.getKey().getShortId(), entry.getValue() ));
            });
            System.out.println(String.format("Count of direct contacts: %d", directContacts.values().stream().mapToInt(x -> x).sum()));
            System.out.println("Indirect contacts:");
            Collection<User> indirectContacts = getIndirectContacts(user);
            indirectContacts.stream().sorted(Comparator.comparing(User::getName).thenComparing(User::getId)).forEach(contact -> {
                System.out.println(String.format("%s %s", contact.getName(), contact.getShortId()));
            });
            System.out.println(String.format("Count of indirect contacts: %d", indirectContacts.size()));

            directContactsStats.add(directContacts.values().stream().mapToInt(x -> x).sum());
            indirectContactsStats.add(indirectContacts.size());
        });
        System.out.println(String.format("Average direct contacts: %.4f", directContactsStats.stream().mapToDouble(x -> x).summaryStatistics().getAverage()));
        System.out.println(String.format("Average indirect contacts: %.4f", indirectContactsStats.stream().mapToDouble(x -> x).summaryStatistics().getAverage()));
    }
}

class User {
    private String name;
    private String id;
    private List<ILocation> locations;
    private Boolean infected;
    private LocalDateTime infectedAt;

    public User(String name, String id) {
        this.name = name;
        this.id = id;
        this.locations = new ArrayList<>();
    }

    public void addLocations(List<ILocation> locations) {
        this.locations.addAll(locations);
    }

    public void setInfected(boolean infected) {
        this.infected = infected;
    }

    public String getName() {
        return name;
    }

    public List<ILocation> getLocations() {
        return locations;
    }

    public Boolean getInfected() {
        return infected;
    }

    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return ((User) obj).getId().equals(id);
        }
        return false;
    }

    public void setInfectedAt(LocalDateTime infectedAt) {
        this.infectedAt = infectedAt;
    }

    public LocalDateTime getInfectedAt() {
        return infectedAt;
    }

    public String getShortId(){
        return id.substring(0, 4).concat("***");
    }
}

class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String id) {
        super(String.format("User with id %s already exists", id));
    }
}
/*

    метод Collection<User> getIndirectContacts (User u) - што ќе враќа колекција од индиректните контакти на корисникот u.
    За индиректни контакти се сметаат блиските контакти на директните контакти на u,
    при што еден корисник не може да биде и директен и индиректен контакт на некој друг корисник.
    метод void createReport ()- што ќе креира и испечати извештај за МЗ во којшто за сите корисници-носители на вирусот ќе испечати информации во следниот формат.

    [user_name] [user_id] [timestamp_detected]
    Direct contacts:
    [contact1_name] [contact1_first_five_letters_of_id] [number_of_detected_contacts1]
    [contact2_name] [contact2_first_five_letters_of_id] [number_of_detected_contacts1]
    ...
    [contactN_name] [contactN_first_five_letters_of_id] [number_of_detected_contactsN]
    Count of direct contacts: [sum]
    Indirect contacts:
    [contact1_name] [contact1_first_five_letters_of_id]
    [contact2_name] [contact2_first_five_letters_of_id]
    ...
    [contactN_name] [contactN_first_five_letters_of_id]
    Count of indirect contacts: [count]

    Дополнително на крајот на извештајот да се испечати просечниот број на директни и индиректни контакти на корисниците што се носители на Корона вирусот.

Напомена:

    Близок контакт се смета контактот на двајца корисници кога евклидовото растојание помеѓу некоја од нивните локациите е <=2,
    а временското растојание на соодветно измерените локации е помало од 5 минути.
    Носителите на вирусот да се сортирани според времето кога се детектирани дека се носители.
    Директните контакти на носителите да бидат сортирани според бројот на остварени блиски контакти во опаѓачки редослед.
    Индиректните контакти да се сортирани лексикографски според нивното име, а доколку е исто според ИД на корисникот.


 */