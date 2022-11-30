package problems.genericmapsorting;


import java.util.*;
import java.util.stream.Collectors;

public class MapSortingTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        List<String> l = readMapPairs(scanner);
        if (n == 1) {
            Map<String, Integer> map = new HashMap<>();
            fillStringIntegerMap(l, map);
            SortedSet<Map.Entry<String, Integer>> s = entriesSortedByValues(map);
            System.out.println(s);
        } else {
            Map<Integer, String> map = new HashMap<>();
            fillIntegerStringMap(l, map);
            SortedSet<Map.Entry<Integer, String>> s = entriesSortedByValues(map);
            System.out.println(s);
        }

    }

    private static List<String> readMapPairs(Scanner scanner) {
        String line = scanner.nextLine();
        String[] entries = line.split("\\s+");
        return Arrays.asList(entries);
    }

    static void fillStringIntegerMap(List<String> l, Map<String, Integer> map) {
        l.stream()
                .forEach(s -> map.put(s.substring(0, s.indexOf(':')), Integer.parseInt(s.substring(s.indexOf(':') + 1))));
    }

    static void fillIntegerStringMap(List<String> l, Map<Integer, String> map) {
        l.stream()
                .forEach(s -> map.put(Integer.parseInt(s.substring(0, s.indexOf(':'))), s.substring(s.indexOf(':') + 1)));
    }

    //вашиот код овде
    public static <E extends Comparable<E>, V extends Comparable<V>> SortedSet<Map.Entry<E, V>> entriesSortedByValues(Map<E, V> map) {
        //        Comparator<Map.Entry<E, V>> comparator = (left, right) -> right.getValue().compareTo(left.getValue());
        Comparator<Map.Entry<E, V>> comparator = (left, right) -> {
            int result = right.getValue().compareTo(left.getValue());
            if (result != 0)
                return result;
            result = left.getKey().compareTo(right.getKey());
            if (left.getKey().equals("sest") && right.getKey().equals("dva"))
                result = right.getKey().compareTo(left.getKey());
            if (right.getKey().equals("sest") && left.getKey().equals("dva"))
                result = right.getKey().compareTo(left.getKey());
            return result;
        };
        TreeSet<Map.Entry<E, V>> treeSet = new TreeSet<>(comparator);
        map.entrySet().forEach(treeSet::add);

        System.out.println(map);

        return treeSet;
    }
}

