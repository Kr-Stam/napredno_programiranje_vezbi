package labs.telefonskiimenik;

import java.util.*;

public class PhonebookTester {

    public static void main(String[] args) throws Exception {
        Scanner jin = new Scanner(System.in);
        String line = jin.nextLine();
        switch (line) {
            case "test_contact":
                testContact(jin);
                break;
            case "test_phonebook_exceptions":
                testPhonebookExceptions(jin);
                break;
            case "test_usage":
                testUsage(jin);
                break;
        }
    }

    private static void testFile(Scanner jin) throws Exception {
        PhoneBook phonebook = new PhoneBook();
        while (jin.hasNextLine())
            phonebook.addContact(new Contact(jin.nextLine(), jin.nextLine().split("\\s++")));
        String text_file = "phonebook.txt";
        PhoneBook.saveAsTextFile(phonebook, text_file);
        PhoneBook pb = PhoneBook.loadFromTextFile(text_file);
        if (!pb.equals(phonebook)) System.out.println("Your file saving and loading doesn't seem to work right");
        else System.out.println("Your file saving and loading works great. Good job!");
    }

    private static void testUsage(Scanner jin) throws Exception {
        PhoneBook phonebook = new PhoneBook();
        while (jin.hasNextLine()) {
            String command = jin.nextLine();
            switch (command) {
                case "add":
                    phonebook.addContact(new Contact(jin.nextLine(), jin.nextLine().split("\\s++")));
                    break;
                case "remove":
                    phonebook.removeContact(jin.nextLine());
                    break;
                case "print":
                    System.out.println(phonebook.numberOfContacts());
                    System.out.println(Arrays.toString(phonebook.getContacts()));
                    System.out.println(phonebook.toString());
                    break;
                case "get_name":
                    System.out.println(phonebook.getContactForName(jin.nextLine()));
                    break;
                case "get_number":
                    System.out.println(Arrays.toString(phonebook.getContactsForNumber(jin.nextLine())));
                    break;
            }
        }
    }

    private static void testPhonebookExceptions(Scanner jin) {
        PhoneBook phonebook = new PhoneBook();
        boolean exception_thrown = false;
        try {
            while (jin.hasNextLine()) {
                phonebook.addContact(new Contact(jin.nextLine()));
            }
        } catch (InvalidNameException e) {
            System.out.println(e.name);
            exception_thrown = true;
        } catch (Exception e) {
        }
        if (!exception_thrown) System.out.println("Your addContact method doesn't throw InvalidNameException");
        /*
		exception_thrown = false;
		try {
		phonebook.addContact(new Contact(jin.nextLine()));
		} catch ( MaximumSizeExceddedException e ) {
			exception_thrown = true;
		}
		catch ( Exception e ) {}
		if ( ! exception_thrown ) System.out.println("Your addContact method doesn't throw MaximumSizeExcededException");
        */
    }

    private static void testContact(Scanner jin) throws Exception {
        boolean exception_thrown = true;
        String names_to_test[] = {"And\nrej", "asd", "AAAAAAAAAAAAAAAAAAAAAA", "Ð�Ð½Ð´Ñ€ÐµÑ˜A123213", "Andrej#", "Andrej<3"};
        for (String name : names_to_test) {
            try {
                new Contact(name);
                exception_thrown = false;
            } catch (InvalidNameException e) {
                exception_thrown = true;
            }
            if (!exception_thrown) System.out.println("Your Contact constructor doesn't throw an InvalidNameException");
        }
        String numbers_to_test[] = {"+071718028", "number", "078asdasdasd", "070asdqwe", "070a56798", "07045678a", "123456789", "074456798", "073456798", "079456798"};
        for (String number : numbers_to_test) {
            try {
                new Contact("Andrej", number);
                exception_thrown = false;
            } catch (InvalidNumberException e) {
                exception_thrown = true;
            }
            if (!exception_thrown)
                System.out.println("Your Contact constructor doesn't throw an InvalidNumberException");
        }
        String nums[] = new String[10];
        for (int i = 0; i < nums.length; ++i) nums[i] = getRandomLegitNumber();
        try {
            new Contact("Andrej", nums);
            exception_thrown = false;
        } catch (MaximumSizeExceddedException e) {
            exception_thrown = true;
        }
        if (!exception_thrown)
            System.out.println("Your Contact constructor doesn't throw a MaximumSizeExceddedException");
        Random rnd = new Random(5);
        Contact contact = new Contact("Andrej", getRandomLegitNumber(rnd), getRandomLegitNumber(rnd), getRandomLegitNumber(rnd));
        System.out.println(contact.getName());
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact.toString());
        contact.addNumber(getRandomLegitNumber(rnd));
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact.toString());
        contact.addNumber(getRandomLegitNumber(rnd));
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact.toString());
    }

    static String[] legit_prefixes = {"070", "071", "072", "075", "076", "077", "078"};
    static Random rnd = new Random();

    private static String getRandomLegitNumber() {
        return getRandomLegitNumber(rnd);
    }

    private static String getRandomLegitNumber(Random rnd) {
        StringBuilder sb = new StringBuilder(legit_prefixes[rnd.nextInt(legit_prefixes.length)]);
        for (int i = 3; i < 9; ++i)
            sb.append(rnd.nextInt(10));
        return sb.toString();
    }


}


class InvalidNumberException extends Exception {
    InvalidNumberException() {
    }
}

class InvalidNameException extends Exception {
    String name;

    InvalidNameException(String name) {
        this.name = name;
    }
}

class MaximumSizeExceddedException extends Exception {
    MaximumSizeExceddedException() {
    }
}

class Contact {
    String name;
    ArrayList<String> phoneNumbers;

    Contact(String name, String... phoneNumbersArr) throws Exception {
        if (nameIsValid(name))
            throw new InvalidNameException(name);
        this.name = name;
        phoneNumbers = new ArrayList<String>();
        for (int i = 0; i < phoneNumbersArr.length; i++)
            addNumber(phoneNumbersArr[i]);
    }

    static boolean nameIsValid(String name) {
        return !name.matches("[A-Za-z0-9]{5,10}");
    }

    static boolean numberIsValid(String phone) {
        return phone.matches("^07[0125678][0-9]{6}$");
    }

    String getName() {
        return name;
    }

    String[] getNumbers() {
        return phoneNumbers.stream().sorted().toArray(String[]::new);
    }

    void addNumber(String number) throws Exception {
        if (phoneNumbers.size() == 5)
            throw new MaximumSizeExceddedException();
        if (!numberIsValid(number))
            throw new InvalidNumberException();
        phoneNumbers.add(number);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        sb.append("\n");
        sb.append(this.phoneNumbers.size());
        sb.append("\n");
        phoneNumbers.stream().sorted().forEach(num -> sb.append(num).append("\n"));
        return sb.toString();
    }


}

class PhoneBook {
    List<Contact> contacts;

    PhoneBook() {
        contacts = new ArrayList<>();
    }

    void addContact(Contact contact) throws Exception {
        if (contacts.size() == 250)
            throw new MaximumSizeExceddedException();
        if (contacts.stream().anyMatch(c -> c.getName().equals(contact.getName()))) throw new InvalidNameException(contact.getName());

        contacts.add(contact);
    }

    Contact getContactForName(String name) throws Exception {
        Optional<Contact> tmp = contacts.stream().filter(c -> c.getName().equals(name)).findAny();
        if(!tmp.isPresent())
            return null;
        return tmp.get();
    }

    int numberOfContacts() {
        return contacts.size();
    }

    Contact[] getContacts() {
        return contacts.stream().sorted(Comparator.comparing(Contact::getName)).toArray(Contact[]::new);
    }

    boolean removeContact(String name) {
        return contacts.removeIf(c -> c.getName().equals(name));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        contacts.stream().sorted(Comparator.comparing(Contact::getName)).forEach(c -> sb.append(c).append("\n"));
        return sb.toString();
    }

    static boolean saveAsTextFile(PhoneBook phonebook, String path) {
        return true;
    }

    static PhoneBook loadFromTextFile(String path) {
        return new PhoneBook();
    }

    Contact[] getContactsForNumber(String number_prefix) {
        return contacts.stream().filter(c -> Arrays.stream(c.getNumbers()).anyMatch(num -> num.startsWith(number_prefix))).toArray(Contact[]::new);
    }


}
/*


Во оваа задача треба да имплементирате класа која ќе претставува телефонски именик.
Именикот се состои од повеќе контакти при што, за секој контакт се чуваат неговото име и максимум до 5 телефонски броја за тој контакт.
За потребите на класата Contact треба да ги имплементирате следниве методи.

    Contact(String name, String... phonenumber) - конструктор со параметри - името треба да е подолго од 4 караткери,
    но максимум до 10 карактери и не смее да содржи други знаци освен латинични букви и бројки во спротивно се фрла исклучок од тип InvalidNameException -
     телефонските броеви мора да се состојат од точно 9 цифри при што првите три цифри се
     "070", "071", "072", "075","076","077" или "078" во спротивно се фрла исклучок од тип InvalidNumberException -
     контактот може да содржи максимум 5 броја во спротивно се фрла исклучок MaximumSizeExceddedException
    getName():String - get метод за името
    getNumbers():String[] - get метод за броевите кои треба да се лексикографски подредени (нека враќа копија од оригиналната низа)
    addNumber(String phonenumber) - метод кој додава нов број во контактот - за овој метод важат истите ограничувања за форматот на броевите како и во конструкторот
    toString():String - враќа текстуален опис во следниот формат Во прв ред името на контактот, во втор ред бројот на телефонски броеви и понатаму во одделни редови секој број поединечно повторно сортирани лексикографски
    valueOf(String s):Contact - статички метод кој за дадена тексутална репрезентација на контактот ќе врати соодветен објект - доколку настане било каков проблем при претварањето од тексутална репрезентација во објект Contact треба да се фрли исклучок од тип InvalidFormatException

Користејќи ја класата Contact која ја напишавте сега треба да се развие и класа за телефонски именик PhoneBook. Оваа класа содржи низа од не повеќе од 250 контакти и ги нуди следниве методи

    PhoneBook() - празен конструктор
    addContact(Contact contact):void - додава нов контакт во именикот, притоа доколку се надмине максималниот капацитет од 250 се фрла исклучок MaximumSizeExceddedException - дополнително ограничување е што сите имиња на контакти мора да бидат единствени, доколку контактот што сакате да го додадете има исто име со некој од веќе постоечките контакти треба да фрлите исклучок од типот InvalidNameException
    getContactForName(String name):Contact - го враќа контактот со соодветното име доколку таков постои во спротивно враќа null
    numberOfContacts():int - го враќа бројот на контакти во именикот
    getContacts():Contact[] - враќа низа од сите контакти сортирани според нивното име (нека враќа копија од низата)
    removeContact(String name):boolean - го брише соодветниот контакт од именикот и раќа true доколку постои, во спротивно враќа false
    toString():String - враќа текстуален опис на именикот каде се наредени сите контакти подредени според нивното име, одделени со по еден празен ред
    saveAsTextFile(PhoneBook phonebook,String path):boolean - статички метод кој го запишува именикот во текстуална датотека која се наоѓа на локација path,доколку не постои датотеката треба да се креира- методот враќа false само доколку има некаков проблем при запишување на податоците во датотеката
    loadFromTextFile(String path):Phonebook - статички метод кој вчитува именик претходно запишан со методот saveAsTextFile - доколку датотеката не постои или неможе да се отвори за читање се пропагира оригиналниот IOException, а доколку настане проблем при парсирањето на текстот од датотеката треба да се фрли исклучок InvalidFormatException
    getContactsForNumber(String number_prefix):Contact[] - за даден префикс од број (првите неколку цифри) ги враќа сите контакти кои имаат барем еден број со тој префикс - низата не треба да содржи дупликат контакти или null елементи подредена според имињата на контактите

*Сите исклучоци освен IOException треба сами да ги напишете - секаде каде што е можно додате дополнително објаснување или податочни членови во врска со причината за исклучокот

 */