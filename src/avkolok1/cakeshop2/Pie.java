package avkolok1.cakeshop2;

public class Pie extends Item{
    public Pie(String name, String price) {
        super(name, price);
    }

    @Override
    public ItemType getType() {
        return ItemType.P;
    }

    @Override
    public int getPrice() {
        return super.getPrice() + 50;
    }


}
