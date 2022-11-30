package avkolok1.cakeshop2;

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

    public Order(String line, int min) throws InvalidOrderException {
        this.items = new ArrayList<>();
        String[] parts = line.split(" ");
        this.orderID = Integer.parseInt(parts[0]);
        for(int i = 1; i < parts.length - 1; i+=2){
            if(parts[i].charAt(0) == 'C')
                items.add(new Cake(parts[i], parts[i+1]));
            else if(parts[i].charAt(0) == 'P')
                items.add(new Pie(parts[i], parts[i+1]));
        }
        if(items.size() < min)
            throw new InvalidOrderException(this.orderID);
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
        return String.format("%d %d %d %d %d", orderID, items.size(), numOfPies(), numOfCakes(), totalAmount());
    }

    public int numOfCakes(){
        return items.stream().filter(item -> item.getType() == ItemType.C ).toList().size();
    }

    public int numOfPies(){
        return items.stream().filter(item -> item.getType() == ItemType.P ).toList().size();
    }

    public int totalAmount(){
        return items.stream().mapToInt(Item::getPrice).sum();
    }
}
