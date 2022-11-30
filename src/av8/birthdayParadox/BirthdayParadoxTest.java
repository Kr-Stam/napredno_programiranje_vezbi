package av8.birthdayParadox;

import java.util.*;
import java.util.stream.IntStream;

public class BirthdayParadoxTest {
    private static final Random RANDOM = new Random();

    public static int experiment(int n){
        Set<Integer> birthdays = new TreeSet<>();
        IntStream.range(0, n).forEach( x ->birthdays.add(RANDOM.nextInt(365) + 1));
        return n - birthdays.size();
    }

    public static double test(int n, int roomSize){
        return (double) IntStream.range(0, n).map(x -> experiment(roomSize)).filter(x -> x > 0).count() / n;
    }

    public static boolean testOptimized(int n, int roomSize){
        Set<Integer> set = new HashSet<>();
        for(int i = 0; i < n; i++){
            int tmp = RANDOM.nextInt(365) + 1;
            if(set.contains(tmp))
                return true;
            set.add(tmp);
        }
        return false;
    }

    public static void trials(int start, int end, int n){
        IntStream.range(start, end).forEach(i -> {
            System.out.println(String.format("%d --> %.2f", i, test(n, i)));
        });
    }

    public static void main(String[] args) {
        trials(2, 100, 5000);
    }
}
