package av5.oldestperson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OldestPerson {

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            System.out.println(oldestPerson(br));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Person oldestPerson(BufferedReader br){
        List<Person> people = br.lines().map(Person::new).toList();
        if(people.stream().max(Comparator.naturalOrder()).isPresent())
            return people.stream().max(Comparator.naturalOrder()).get();
        return null;
    }
}
