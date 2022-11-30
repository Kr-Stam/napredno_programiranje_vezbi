package avkolok1.cakeshop2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CakeShopApplication {
    int minItems;
    private List<Order> orders;

    public CakeShopApplication(int min) {
        this.orders = new ArrayList<>();
        this.minItems = min;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void readCakeOrders(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        this.orders = br.lines().map(line -> {
            try {
                return new Order(line, minItems);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public void printLongestOrder(PrintStream output) {
        if (!orders.isEmpty())
            output.println(orders.stream().max(Comparator.naturalOrder()).get());
    }

    public void printAllOrders(PrintStream out) {
        orders.forEach(System.out::println);
    }
}
