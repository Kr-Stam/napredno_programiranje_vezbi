package avkolok1.mojDDV;

import java.util.ArrayList;

public class Bill implements Comparable<Bill>{
    private int id;
    private ArrayList<Item> items;

    public Bill(int id, ArrayList<Item> items) {
        this.id = id;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public Bill(String line) throws InvalidBillException {
        String[] parts = line.split(" ");
        this.id = Integer.parseInt(parts[0]);
        this.items = new ArrayList<>();
        for(int i = 1; i < parts.length - 1; i+=2){
            items.add(new Item(parts[i], parts[i+1]));
        }
        if(getAmount() > 30000)
            throw new InvalidBillException(getAmount());
    }

    public int getAmount(){
        return items.stream().mapToInt(Item::getPrice).sum();
    }

    public double getTaxReturns(){
        return items.stream().mapToDouble(Item::getTaxReturn).sum();
    }

    @Override
    public String toString() {
        return String.format("%d %d %.2f", this.id, getAmount(), getTaxReturns());
    }

    @Override
    public int compareTo(Bill o) {
        return Double.compare(this.getTaxReturns(), o.getTaxReturns());
    }
}
