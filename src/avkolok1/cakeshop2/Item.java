package avkolok1.cakeshop2;

public abstract class Item {
    private String name;
    private int price;

    public Item(String name, String price) {
        this.name = name;
        this.price = Integer.parseInt(price);
    }

    public abstract ItemType getType();

    public int getPrice() {
        return price;
    }
}
