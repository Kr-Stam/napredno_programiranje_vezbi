package av7.arrangewords;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ArrangeWords {
    private static String arrange(String sentence){
        return Arrays.stream(sentence.split("\\s+"))
                .map(word -> arrangeWord(word))
                .sorted()
                .collect(Collectors.joining(" "));
    }

    private static String arrangeWord(String word) {
        return word.chars().mapToObj(i -> String.valueOf((char) i)).sorted().collect(Collectors.joining());
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(arrange(br.readLine()));
    }
}
