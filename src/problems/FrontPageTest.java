package problems;

import java.util.*;
import java.util.stream.Collectors;

public class FrontPageTest {
    public static void main(String[] args) {
        // Reading
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] parts = line.split(" ");
        Category[] categories = new Category[parts.length];
        for (int i = 0; i < categories.length; ++i) {
            categories[i] = new Category(parts[i]);
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        FrontPage frontPage = new FrontPage(categories);
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            cal = Calendar.getInstance();
            int min = scanner.nextInt();
            cal.add(Calendar.MINUTE, -min);
            Date date = cal.getTime();
            scanner.nextLine();
            String text = scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            TextNewsItem tni = new TextNewsItem(title, date, categories[categoryIndex], text);
            frontPage.addNewsItem(tni);
        }

        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int min = scanner.nextInt();
            cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -min);
            scanner.nextLine();
            Date date = cal.getTime();
            String url = scanner.nextLine();
            int views = scanner.nextInt();
            scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            MediaNewsItem mni = new MediaNewsItem(title, date, categories[categoryIndex], url, views);
            frontPage.addNewsItem(mni);
        }
        // Execution
        String category = scanner.nextLine();
        System.out.println(frontPage);
        for(Category c : categories) {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
            System.out.println(frontPage.listByCategoryName(category).size());
        } catch(CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}

class Category implements Comparable<Category>{
    String name;

    public Category(String category) {
        this.name = category;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Category o) {
        return name.compareTo(o.name);
    }
}

class FrontPage{
    List<NewsItem> news;
    Set<Category> categories;

    public FrontPage(Category[] categories) {
        this.news = new ArrayList<>();
        this.categories = new TreeSet<>();
        this.categories.addAll(Arrays.asList(categories));
    }

    public void addNewsItem(NewsItem newsItem) {
        categories.add(newsItem.getCategory());
        this.news.add(newsItem);
    }

    public List<NewsItem> listByCategory(Category c) {
        return news.stream().filter(x -> x.getCategory().equals(c)).collect(Collectors.toList());
    }

    public List<NewsItem> listByCategoryName(String category) throws CategoryNotFoundException {
        if(!categories.contains(new Category(category)))
            throw new CategoryNotFoundException(String.format("Category %s was not found", category));
        return news.stream().filter(x -> x.getCategory().getName().equalsIgnoreCase(category)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        news.forEach(x -> {
            sb.append(x.getTeaser());
            sb.append("\n");
        });
        return sb.toString();
    }
}

interface NewsItem{
    String getTitle();
    Date getDate();
    Category getCategory();
    String getTeaser();
}

class TextNewsItem implements NewsItem{

    private String title;
    private Date date;
    private Category category;
    private String text;

    public String getText() {
        return text;
    }

    public TextNewsItem(String title, Date date, Category category, String text) {
        this.title = title;
        this.date = date;
        this.category = category;
        this.text = text;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public String getTeaser() {
        Date now = Calendar.getInstance().getTime();
        StringBuilder sb = new StringBuilder();
        sb.append(title);
        sb.append("\n");
        sb.append((now.getTime() - date.getTime()) / 60000);
        sb.append("\n");
        sb.append(String.format("%.80s", text));
        return sb.toString();
    }
}

class MediaNewsItem implements NewsItem{

    private String title;
    private Date date;
    private Category category;
    private String url;
    private int views;

    public MediaNewsItem(String title, Date date, Category category, String url, int views) {
        this.title = title;
        this.date = date;
        this.category = category;
        this.url = url;
        this.views = views;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public String getTeaser() {
        Date now = Calendar.getInstance().getTime();
        StringBuilder sb = new StringBuilder();
        sb.append(title);
        sb.append("\n");
        sb.append((now.getTime() - date.getTime()) / 60000);
        sb.append("\n");
        sb.append(url);
        sb.append("\n");
        sb.append(views);
        return sb.toString();
    }

    public String getUrl() {
        return url;
    }

    public int getViews() {
        return views;
    }
}

class CategoryNotFoundException extends Exception{
    public CategoryNotFoundException(String category) {
        super(category);
    }
}