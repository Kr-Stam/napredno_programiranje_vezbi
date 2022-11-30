//package av9.imenik;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.TreeMap;
//
//public class PhoneBook {
//    Map<String, String> contacts;
//
//    public PhoneBook() {
//        this.contacts = new HashMap<>();
//    }
//
//    public void addContact(String name, String number) throws DuplicateNumberException {
//        int tmp = contacts.size();
//        contacts.put(number, name);
//        if (tmp == contacts.size())
//            throw new DuplicateNumberException(String.format("Duplicate number: %s", number));
//    }
//
//    public void contactsByNumber(String number) {
//        contacts.forEach((k, v) -> {
//            if(k.contains(number))
//                System.out.println(k + " " + v);
//        });
//    }
//
//    public void contactsByName(String name) {
//        contacts.forEach((k, v) -> {
//            if(v.equals(name))
//                System.out.println(k + " " + v);
//        });
//    }
//}
