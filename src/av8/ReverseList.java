package av8;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class ReverseList {

    public static void printReverse(Collection<String> collection){
        collection.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);
    }

    public static void main(String[] args) {
        Collection<String> test = new ArrayList<>();
        test.add("line1");
        test.add("line2");
        test.add("line3");

        printReverse(test);
    }
}
