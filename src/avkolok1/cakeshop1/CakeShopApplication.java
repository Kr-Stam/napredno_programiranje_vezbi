package avkolok1.cakeshop1;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CakeShopApplication {
    private List<Order> orders;

    public CakeShopApplication() {
        this.orders = new ArrayList<>();
    }

    public void addOrder(Order order){
        orders.add(order);
    }

    public int readCakeOrders(InputStream inputStream){
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        this.orders = br.lines().map(Order::new).collect(Collectors.toList());

        return orders.stream().mapToInt(Order::size).sum();
    }

    public void printLongestOrder(PrintStream output){
        if(!orders.isEmpty())
            output.println(orders.stream().max(Comparator.naturalOrder()).get());
    }
}
