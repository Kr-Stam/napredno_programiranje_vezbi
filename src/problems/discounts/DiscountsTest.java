package problems.discounts;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Discounts
 */
public class DiscountsTest {
    public static void main(String[] args) {
        Discounts discounts = new Discounts();
        int stores = discounts.readStores(System.in);
        System.out.println("Stores read: " + stores);
        System.out.println("=== By average discount ===");
        discounts.byAverageDiscount().forEach(System.out::println);
        System.out.println("=== By total discount ===");
        discounts.byTotalDiscount().forEach(System.out::println);
    }
}

// Vashiot kod ovde

class Discounts {
    List<Store> stores;

    public Discounts() {
        this.stores = new ArrayList<>();
    }

    public int readStores(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        this.stores = br.lines().map(line -> {
            //            System.out.println(line);
            return new Store(line);
        }).collect(Collectors.toList());

        return stores.size();
    }

    public List<Store> byAverageDiscount() {
        return stores.stream().sorted(Comparator.comparing(Store::getAvgDiscount).reversed().thenComparing(Store::getName)).collect(Collectors.toList()).subList(0, 3);
    }

    public List<Store> byTotalDiscount() {
        return stores.stream().sorted(Comparator.comparing(Store::getTotalDiscount).thenComparing(Store::getName)).collect(Collectors.toList()).subList(0, 3);
    }
}

class Store {
    String name;
    TreeSet<Price> prices;

    public Store(String line) {
        String[] parts = line.split(" ");
        this.name = parts[0];
        String[] parts1 = line.substring(name.length()).split("  ");
        this.prices = Arrays.stream(parts1).map(Price::new).collect(Collectors.toCollection(TreeSet::new));
    }

    public double getAvgDiscount() {
        return prices.stream().mapToDouble(Price::getPercent).summaryStatistics().getAverage();
    }

    public int getTotalDiscount() {
        return prices.stream().mapToInt(Price::getDiscount).sum();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("\n");
        sb.append(String.format("Average discount: %.1f%%\n", getAvgDiscount()));
        sb.append(String.format("Total discount: %d\n", getTotalDiscount()));
        prices.forEach(x -> sb.append(x).append("\n"));
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}

class Price implements Comparable<Price>{
    private int oldPrice;
    private int newPrice;
    private int discount;
    private int percent;

    public Price(String prices) {
        String[] parts = prices.split(":");
        this.newPrice = Integer.parseInt(parts[0].trim());
        this.oldPrice = Integer.parseInt(parts[1].trim());
        this.discount = oldPrice - newPrice;
        this.percent = (int)((double) discount / (double) oldPrice * 100);
    }

    public int getOldPrice() {
        return oldPrice;
    }

    public int getNewPrice() {
        return newPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public double getPercent() {
        return percent;
    }

    @Override
    public String toString() {
        return String.format("%2s%% %d/%d", (int) Math.floor(percent), newPrice, oldPrice);
    }

    @Override
    public int compareTo(Price o) {
        return Comparator.comparing(Price::getPercent).reversed().thenComparing(Price::getDiscount, Comparator.reverseOrder()).compare(this, o);
    }
}
