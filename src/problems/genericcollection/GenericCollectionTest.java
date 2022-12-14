package problems.genericcollection;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/*
August 2020 exam
*/

interface IHasTimestamp {
    LocalDateTime getTimestamp();
}

class IntegerElement implements Comparable<IntegerElement>, IHasTimestamp {

    int value;
    LocalDateTime timestamp;


    public IntegerElement(int value, LocalDateTime timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(IntegerElement o) {
        return Integer.compare(this.value, o.value);
    }

    @Override
    public String toString() {
        return "IntegerElement{" +
                "value=" + value +
                ", timestamp=" + timestamp +
                '}';
    }
}

class StringElement implements Comparable<StringElement>, IHasTimestamp {

    String value;
    LocalDateTime timestamp;


    public StringElement(String value, LocalDateTime timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(StringElement o) {
        return this.value.compareTo(o.value);
    }

    @Override
    public String toString() {
        return "StringElement{" +
                "value='" + value + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

class TwoIntegersElement implements Comparable<TwoIntegersElement>, IHasTimestamp {

    int value1;
    int value2;
    LocalDateTime timestamp;

    public TwoIntegersElement(int value1, int value2, LocalDateTime timestamp) {
        this.value1 = value1;
        this.value2 = value2;
        this.timestamp = timestamp;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(TwoIntegersElement o) {
        int cmp = Integer.compare(this.value1, o.value1);
        if (cmp != 0)
            return cmp;
        else
            return Integer.compare(this.value2, o.value2);
    }

    @Override
    public String toString() {
        return "TwoIntegersElement{" +
                "value1=" + value1 +
                ", value2=" + value2 +
                ", timestamp=" + timestamp +
                '}';
    }
}

public class GenericCollectionTest {

    public static void main(String[] args) {

        int type1, type2;
        GenericCollection<IntegerElement> integerCollection = new GenericCollection<IntegerElement>();
        GenericCollection<StringElement> stringCollection = new GenericCollection<StringElement>();
        GenericCollection<TwoIntegersElement> twoIntegersCollection = new GenericCollection<TwoIntegersElement>();
        Scanner sc = new Scanner(System.in);

        type1 = sc.nextInt();

        int count = sc.nextInt();

        for (int i=0;i<count;i++) {
            if (type1 == 1) { //integer element
                int value = sc.nextInt();
                LocalDateTime timestamp = LocalDateTime.parse(sc.next());
                String category = sc.next();
                integerCollection.addGenericItem(category, new IntegerElement(value, timestamp));
            } else if (type1 == 2) { //string element
                String value = sc.next();
                LocalDateTime timestamp = LocalDateTime.parse(sc.next());
                String category = sc.next();
                stringCollection.addGenericItem(category, new StringElement(value, timestamp));
            } else { //two integer element
                int value1 = sc.nextInt();
                int value2 = sc.nextInt();
                LocalDateTime timestamp = LocalDateTime.parse(sc.next());
                String category = sc.next();
                twoIntegersCollection.addGenericItem(category, new TwoIntegersElement(value1, value2, timestamp));
            }
        }



        type2 = sc.nextInt();

        if (type2 == 1) { //findAllBetween
            LocalDateTime start = LocalDateTime.of(2008, 1, 1, 0, 0);
            LocalDateTime end = LocalDateTime.of(2020, 1, 30, 23, 59);
            if (type1 == 1)
                printResultsFromFindAllBetween(integerCollection, start, end);
            else if (type1 == 2)
                printResultsFromFindAllBetween(stringCollection, start, end);
            else
                printResultsFromFindAllBetween(twoIntegersCollection, start, end);
        } else if (type2 == 2) { //itemsFromCategories
            List<String> categories = new ArrayList<>();
            int n = sc.nextInt();
            while (n!=0) {
                categories.add(sc.next());
                n--;
            }
            if (type1 == 1)
                printResultsFromItemsFromCategories(integerCollection, categories);
            else if (type1 == 2)
                printResultsFromItemsFromCategories(stringCollection, categories);
            else
                printResultsFromItemsFromCategories(twoIntegersCollection, categories);
        } else if (type2 == 3) { //byMonthAndDay
            if (type1 == 1)
                printResultsFromByMonthAndDay(integerCollection);
            else if (type1 == 2)
                printResultsFromByMonthAndDay(stringCollection);
            else
                printResultsFromByMonthAndDay(twoIntegersCollection);
        } else { //countByYear
            if (type1 == 1)
                printResultsFromCountByYear(integerCollection);
            else if (type1 == 2)
                printResultsFromCountByYear(stringCollection);
            else
                printResultsFromCountByYear(twoIntegersCollection);
        }


    }

    private static void printResultsFromItemsFromCategories(
            GenericCollection<?> collection, List<String> categories) {
        collection.itemsFromCategories(categories).forEach(element -> System.out.println(element.toString()));
    }

    private static void printResultsFromFindAllBetween(
            GenericCollection<?> collection, LocalDateTime start, LocalDateTime end) {
        collection.findAllBetween(start, end).forEach(element -> System.out.println(element.toString()));
    }

    private static void printSetOfElements (Set<?> set) {
        System.out.print("[");
        System.out.print(set.stream().map(Object::toString).collect(Collectors.joining(", ")));
        System.out.println("]");
    }

    private static void printResultsFromByMonthAndDay (GenericCollection<?> collection) {
        collection.byMonthAndDay().forEach((key, value) -> {
            System.out.print(key + " -> ");
            printSetOfElements(value);
        });
    }

    private static void printResultsFromCountByYear (GenericCollection<?> collection) {
        collection.countByYear().forEach((key,value) -> {
            System.out.println(key + " -> " + value);
        });
    }
}

class GenericCollection<T extends Comparable<T> & IHasTimestamp>{
    Map<String, Set<T>> collection;

    public GenericCollection() {
        this.collection = new HashMap<>();
    }

    public void addGenericItem(String category, T obj){
        if (!collection.containsKey(category))
            collection.put(category, new TreeSet<>(Comparator.reverseOrder()));
        collection.get(category).add(obj);
    }

    Collection<T> findAllBetween(LocalDateTime from, LocalDateTime to){
        return collection.values().stream()
                .flatMap(Collection::stream)
                .filter(item -> Duration.between(from, item.getTimestamp()).getSeconds() > 0 && Duration.between(item.getTimestamp(), to).getSeconds() > 0)
                .collect(Collectors.toCollection(() -> new TreeSet<T>(Comparator.reverseOrder())));
    }

    public Map<String, Set<T>> byMonthAndDay(){
        Comparator<String> comparator = (left, right) -> {
            String[] parts1 = left.split("-");
            String[] parts2 = right.split("-");

            int result = Integer.compare(Integer.parseInt(parts1[0]), Integer.parseInt(parts2[0]));

            if(result != 0)
                return result;

            return Integer.compare(Integer.parseInt(parts1[1]), Integer.parseInt(parts2[1]));
        };
        Map<String, Set<T>> result = new TreeMap<>(comparator);
        collection.values().forEach(set -> {
            set.forEach(item -> {
            int m = item.getTimestamp().getMonth().getValue();
            int d = item.getTimestamp().getDayOfMonth();
            String md = String.format("%02d-%02d", m, d);
            if(!result.containsKey(md))
                result.put(md, new TreeSet<>(Comparator.reverseOrder()));
            result.get(md).add(item);
            });
        });
        return result;
    }

    public Map<Integer, Long> countByYear() {
        Map<Integer, Long> result = new TreeMap<>();
        collection.values().stream()
                .forEach(set -> {
                    set.forEach(item -> {
                    int y = item.getTimestamp().getYear();
                    if(!result.containsKey(y))
                        result.put(y, 0L);
                    result.put(y, result.get(y) + 1);
                    });
                });
        return result;
    }

    public Collection<T> itemsFromCategories(List<String> categories) {
        return categories.stream()
                .map(cat -> collection.get(cat)).flatMap(Collection::stream)
                .collect(Collectors.toCollection(() -> new TreeSet<T>(Comparator.reverseOrder())));
    }
}
/*
???? ???? ???????????????? ?????????????? GenericCollection ???? ???????? ?????? ???? ???? ???????????? ???????????????? ???????????? ?????????? ???? ????
?????????????????? ?? ???????????????? ???????????? ?????????? timestamp ???? ????????????????. ?????????????? ???? ???? ???????????????? ???????????????? ????????????:

    void addGenericItem (String category, T element)- ?????????? ???? ???????????????? ???? ?????? ?????????????? ???? ???????????? ????????????????????
    Collection<T> findAllBetween (LocalDateTime from, LocalDateTime to) - ?????????? ???????????? ???? ?????????? ?????????????????? ???? ????????
    ???????????????? ?????? ???? ?????????????? ???? ???????????????????? ???? ???????????? ?????????? ???????? ?????????????????? ???? ????????????????????.
    Collection<T> itemsFromCategories (List<String> categories) - ?????????? ?????? ???? ???? ?????????? ???????????????????? ?????? ???? ?????????????? ???? ?????????????????????? ???????????? ???????? ???????????????? ???? ????????????????????.
    public Map<String, Set<??>> byMonthAndDay()- ?????????? ???????? ???? ???????? ?????? ???????????????????? ???? ?????????????????? ???????????? ?????????????? timestamp
    (?????????????? ?????????????? ?? ?????????? ?????????????????????????? ???? - ???????????? ?????? ????. 12-30, ?????? ?????????????? ???? ????????????????). ?????????????? ???? ???????????? ???? ?????????? ???? ?????????????? getMonth(), ?? ?????????? getDayOfMonth().
    public Map<Integer, Long> countByYear()- ?????????? ???????? ???? ???????? ?????? ?????????????? ???? ???????? ????????????
    ???????? ?????? ???????????????? ?????????? ??????????????, ?? ?????????????????????? ???????????????? ?? ???????????? ???? ???????????????? ???????????????? ???? ?????? ????????????.

???????????? ???????? ?????? ?????? ?????????????????? ???? ????????????????, ???????????? ?????????? ???? ?????????? ?????????????????? ???? ???????????????? ????????????????!

 */