package avkolok1.cakeshop1;

import java.util.ArrayList;
import java.util.List;

public class Order implements Comparable<Order>{
    int orderID;
    List<Item> items;

    public Order(int orderID) {
        this.orderID = orderID;
    }

    public Order(int orderID, List<Item> items) {
        this.orderID = orderID;
        this.items = items;
    }

    public Order(String line) {
        this.items = new ArrayList<>();
        String[] parts = line.split(" ");
        this.orderID = Integer.parseInt(parts[0]);
        for(int i = 1; i < parts.length - 1; i+=2){
            items.add(new Item(parts[i], Integer.parseInt(parts[i+1])));
        }
    }

    public void addItem(Item item){
        items.add(item);
    }

    public int size(){
        return items.size();
    }

    @Override
    public int compareTo(Order o) {
        return Integer.compare(this.size(), o.size());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(orderID);
        sb.append(" ");
        sb.append(items.size());
        return sb.toString();
    }
}
