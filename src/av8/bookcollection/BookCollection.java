package av8.bookcollection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookCollection {
    List<Book> bookList;

    public BookCollection() {
        this.bookList = new ArrayList<>();
    }

    public void addBook(Book book){
        bookList.add(book);
    }

    public void printByCategory(String category){
        bookList.stream().filter(i -> i.getCategory().equalsIgnoreCase(category)).sorted(categoryComparor).forEach(System.out::println);
    }

//    public List<Book> getCheapestN(int n){
//        List<Book> result = bookList.stream().sorted((bookLeft, bookRight) -> {
//            int res = Float.compare(bookLeft.getPrice(), bookRight.getPrice());
//            if(res != 0)
//                return res;
//            return bookLeft.getTitle().compareTo(bookRight.getTitle());
//        }).collect(Collectors.toList());
//
//        if(n >= result.size())
//            return result;
//        return result.subList(0, n);
//    }

    public List<Book> getCheapestN(int n){
        List<Book> result = bookList.stream().sorted(priceComparator).collect(Collectors.toList());

        if(n >= result.size())
            return result;
        return result.subList(0, n);
    }

    private final Comparator<Book> categoryComparor = Comparator.comparing(Book::getTitle).thenComparing(Book::getPrice);
    private final Comparator<Book> priceComparator = Comparator.comparing(Book::getPrice).thenComparing(Book::getTitle);
}
