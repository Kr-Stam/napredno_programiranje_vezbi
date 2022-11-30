package avkolok1.cakeshop2;

public class Cake extends Item{
    public Cake(String name, String price) {
        super(name, price);
    }

    @Override
    public ItemType getType() {
        return ItemType.C;
    }
}
