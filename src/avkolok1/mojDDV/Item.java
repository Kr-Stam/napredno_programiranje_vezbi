package avkolok1.mojDDV;

public class Item {
    private int price;
    private TaxType type;

    public Item(int price, TaxType type) {
        this.price = price;
        this.type = type;
    }

    public Item(String price, String type) {
        this.price = Integer.parseInt(price);
        switch(type){
            case "A": this.type = TaxType.A; break;
            case "B": this.type = TaxType.B; break;
            case "V": this.type = TaxType.V; break;
        }
    }

    public int getPrice() {
        return price;
    }

    public TaxType getType() {
        return type;
    }

    public double getTaxReturn(){
        switch (type){
            case A: return price * 0.18;
            case B: return price * 0.05;
            case V: return 0;
        }
        return 0;
    }
}
