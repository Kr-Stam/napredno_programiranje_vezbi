package av8.bookcollection;

public class Book implements Comparable<Book>{
    private String title;
    private String category;
    private float price;

    public Book(String title, String category, float price) {
        this.title = title;
        this.category = category;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public int compareTo(Book o) {
        if(!this.title.equalsIgnoreCase(o.title))
            return this.title.compareTo(o.title);
        return Float.compare(this.price, o.price);
    }

    @Override
    public String toString() {
        return String.format("%s (%s) %.2f", title, category, price);
    }
}
