package av8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CountOccurrenceTest {

    private static int occurrenceOf(Collection<Collection<String>> collection, String str){
        return collection.stream().mapToInt(x -> innerOccurrenceOf(x, str)).sum();
    }

    private static int innerOccurrenceOf(Collection<String> collection, String str){
        return (int) collection.stream().filter(x -> x.equals(str)).count();
    }

    public static void main(String[] args) throws IOException {
        Collection<Collection<String>> test = new ArrayList<>();
        Collection<String> innerTest1 = new ArrayList<>();
        innerTest1.add("example");
        innerTest1.add("example");
        innerTest1.add("example");
        innerTest1.add("test");
        Collection<String> innerTest2 = new ArrayList<>();
        innerTest1.add("test");
        innerTest1.add("test");
        innerTest1.add("test");
        Collection<String> innerTest3 = new ArrayList<>();

        test.add(innerTest1);
        test.add(innerTest2);
        test.add(innerTest3);

        System.out.println(occurrenceOf(test, "test"));
    }
}
