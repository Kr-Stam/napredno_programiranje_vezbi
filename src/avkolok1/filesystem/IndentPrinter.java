package avkolok1.filesystem;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IndentPrinter {

    public static String printIndents(int level){
        return IntStream.range(0, level).mapToObj(i -> "\t").collect(Collectors.joining());
    }
}
