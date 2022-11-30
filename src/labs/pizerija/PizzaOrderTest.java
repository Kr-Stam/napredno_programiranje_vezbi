package labs.pizerija;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class PizzaOrderTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test Item
            try {
                String type = jin.next();
                String name = jin.next();
                Item item = null;
                if (type.equals("Pizza")) item = new PizzaItem(name);
                else item = new ExtraItem(name);
                System.out.println(item.getPrice());
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
        if (k == 1) { // test simple order
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 2) { // test order with removing
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (jin.hasNextInt()) {
                try {
                    int idx = jin.nextInt();
                    order.removeItem(idx);
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 3) { //test locking & exceptions
            Order order = new Order();
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.addItem(new ExtraItem("Coke"), 1);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.removeItem(0);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
    }

}

interface Item {
    public int getPrice();

    public String getType();
}

class ExtraItem implements Item {
    ExtraType type;
    int price;

    public ExtraItem(String type) throws InvalidExtraTypeException {
        if (Arrays.stream(ExtraType.values()).noneMatch(x -> x.toString().equals(type)))
            throw new InvalidExtraTypeException();
        switch (ExtraType.valueOf(type)) {
            case Ketchup:
                this.price = 3;
                break;
            case Coke:
                this.price = 5;
                break;
        }
        this.type = ExtraType.valueOf(type);
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String getType() {
        return type.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtraItem extraItem = (ExtraItem) o;
        return type.equals(extraItem.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}

class PizzaItem implements Item {
    PizzaType type;
    int price;

    public PizzaItem(String type) throws InvalidPizzaTypeException {
        if (Arrays.stream(PizzaType.values()).noneMatch(x -> x.toString().equals(type)))
            throw new InvalidPizzaTypeException();
        switch (PizzaType.valueOf(type)) {
            case Standard:
                this.price = 10;
                break;
            case Pepperoni:
                this.price = 12;
                break;
            case Vegetarian:
                this.price = 8;
                break;
        }
        this.type = PizzaType.valueOf(type);
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String getType() {
        return type.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PizzaItem pizzaItem = (PizzaItem) o;
        return type.equals(pizzaItem.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }


}

enum ExtraType {Coke, Ketchup}

enum PizzaType {Standard, Pepperoni, Vegetarian}

class InvalidExtraTypeException extends Exception {
}

class InvalidPizzaTypeException extends Exception {
}

class EmptyOrder extends Exception {
}

class OrderLockedException extends Exception {
}

class ItemOutOfStockException extends Exception {
    public ItemOutOfStockException(Item item) {
        super(String.format("This item is out of stock"));
    }
}

class ArrayIndexOutOfBоundsException extends Exception {
    public ArrayIndexOutOfBоundsException(int idx) {
        super(String.format("Index out of bounds"));
    }
}

class Order {
    LinkedHashMap<Item, Integer> orders;
    boolean locked;

    public Order() {
        this.orders = new LinkedHashMap<>();
    }

    public void addItem(Item item, int count) throws Exception {
        if (locked)
            throw new OrderLockedException();
        if (count > 10)
            throw new ItemOutOfStockException(item);
        orders.put(item, count);
    }

    public int getPrice() {
        AtomicInteger result = new AtomicInteger();
        orders.forEach((item, count) -> {
            result.addAndGet(item.getPrice() * count);
        });
        return result.get();
    }

    public void displayOrder() {
        StringBuilder sb = new StringBuilder();
        AtomicInteger pos = new AtomicInteger(1);
        orders.forEach((item, count) -> {
            sb.append(String.format("%3d.%-15sx%2d%5d$\n", pos.getAndIncrement(), item.getType(), count, item.getPrice() * count));
        });
        sb.append(String.format("%-22s%5d$", "Total:", getPrice()));
        System.out.println(sb);
    }

    public void removeItem(int index) throws Exception {
        if (locked)
            throw new OrderLockedException();
        Item tmp = orders.keySet().stream().collect(Collectors.toList()).remove(index);
        if (tmp == null)
            throw new ArrayIndexOutOfBоundsException(index);
        orders.remove(tmp);
    }

    public void lock() throws EmptyOrder {
        if(orders.isEmpty())
            throw new EmptyOrder();
        this.locked = true;
    }
}