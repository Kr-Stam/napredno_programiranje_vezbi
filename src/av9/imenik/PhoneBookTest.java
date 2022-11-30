package av9.imenik;

import java.util.*;
import java.util.stream.Collectors;

public class PhoneBookTest {

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            try {
                phoneBook.addContact(parts[0], parts[1]);
            } catch (DuplicateNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            String[] parts = line.split(":");
            if (parts[0].equals("NUM")) {
                phoneBook.contactsByNumber(parts[1]);
            } else {
                phoneBook.contactsByName(parts[1]);
            }
        }
    }

}

// Вашиот код овде

class Contact implements Comparable<Contact>{
    String name;
    String number;

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public String toString() {
        return name + " " + number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public int compareTo(Contact o) {
        return Comparator.comparing(Contact::getName).thenComparing(Contact::getNumber).compare(this, o);
    }
}

class PhoneBookMySolution {
    Map<String, Contact> contacts;

    public PhoneBookMySolution() {
        this.contacts = new HashMap<>();
    }

    public void addContact(String name, String number) throws DuplicateNumberException {
        int tmp = contacts.size();
        contacts.put(number, new Contact(name ,number));
        if (tmp == contacts.size())
            throw new DuplicateNumberException(String.format("Duplicate number: %s", number));
        contacts.put(name, new Contact(number, name));
    }

    public void contactsByNumber(String number) {
        List<String> tmp = contacts.keySet().stream().filter(x -> x.contains(number)).collect(Collectors.toList());
        if (tmp.isEmpty())
            System.out.println("NOT FOUND");
        else {
            tmp.stream().map(x -> contacts.get(x)).sorted(Comparator.comparing(Contact::getName).thenComparing(Contact::getNumber)).forEach(System.out::println);
        }
    }

    public void contactsByName(String name) {
        List<Contact> tmp = new ArrayList<>();
        contacts.forEach((k, v) ->{
            if(v.getName().equals(name))
                tmp.add(v);
        });
        if (tmp.isEmpty())
            System.out.println("NOT FOUND");
        else {
            tmp.sort(Comparator.comparing(Contact::getNumber));
            tmp.forEach(System.out::println);
        }
    }
}

class PhoneBook{
    Map<String, Set<Contact>> contactsBySubNum;
    Map<String, Set<Contact>> contactsByName;
    Set<String> numbers;

    public PhoneBook() {
        this.contactsByName = new HashMap<>();
        this.contactsBySubNum = new HashMap<>();
        this.numbers = new HashSet<>();
    }

    private List<String> subNums(String num){
        List<String> result = new ArrayList<>();
        for(int i = 3; i <= num.length(); i++){
            for(int j = 0; j <= num.length() - i; j++){
                result.add(num.substring(j, j + i));
            }
        }
        return result;
    }

    public void addContact(String name, String number) throws DuplicateNumberException {
        if (numbers.contains(number))
            throw new DuplicateNumberException(String.format("Duplicate number: %s", number));

        if(!contactsByName.containsKey(name))
            contactsByName.put(name, new TreeSet<>());

        contactsByName.get(name).add(new Contact(name, number));

        subNums(number).forEach(x -> {
            if(!contactsBySubNum.containsKey(x))
                contactsBySubNum.put(x, new TreeSet<>());

            contactsBySubNum.get(x).add(new Contact(name, number));
        });

        numbers.add(number);
    }

    public void contactsByNumber(String number) {
        if(contactsBySubNum.containsKey(number))
            contactsBySubNum.get(number).forEach(System.out::println);
        else
            System.out.println("NOT FOUND");
    }

    public void contactsByName(String name) {
        if(contactsByName.containsKey(name))
            contactsByName.get(name).forEach(System.out::println);
        else
            System.out.println("NOT FOUND");
    }

}


class DuplicateNumberException extends Exception {

    public DuplicateNumberException(String message) {
        super(message);
    }
}