package problems.najdobrifilmovi;

import java.util.*;
import java.util.stream.Collectors;

public class MoviesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MoviesList moviesList = new MoviesList();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int x = scanner.nextInt();
            int[] ratings = new int[x];
            for (int j = 0; j < x; ++j) {
                ratings[j] = scanner.nextInt();
            }
            scanner.nextLine();
            moviesList.addMovie(title, ratings);
        }
        scanner.close();
        List<Movie> movies = moviesList.top10ByAvgRating();
        System.out.println("=== TOP 10 BY AVERAGE RATING ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        movies = moviesList.top10ByRatingCoef();
        System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}

// vashiot kod ovde
class Movie{
    String title;
    List<Integer> ratings;
    DoubleSummaryStatistics stats;

    public Movie(String title, int[] ratings) {
        this.title = title;
        this.ratings = Arrays.stream(ratings).boxed().collect(Collectors.toList());
        this.stats = this.ratings.stream().mapToDouble(x -> x).summaryStatistics();
    }

    public double getAvg(){
        return stats.getAverage();
    }

    public double getRatingCoefficient(){
        return getAvg() * stats.getCount() / stats.getMax();
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f) of %d ratings", title, getAvg(), ratings.size());
    }
}

class MoviesList{
    List<Movie> movies;

    public MoviesList() {
        this.movies = new ArrayList<>();
    }

    public void addMovie(String title, int[] ratings){
        movies.add(new Movie(title, ratings));
    }

    public List<Movie> top10ByAvgRating(){
        List<Movie> tmp = movies.stream().sorted(Comparator.comparing(Movie::getAvg).reversed().thenComparing(Movie::getTitle)).collect(Collectors.toList());
        if(tmp.size() > 10)
            tmp = tmp.subList(0, 10);
        return tmp;
    }

    public List<Movie> top10ByRatingCoef(){
        List<Movie> tmp = movies.stream().sorted(Comparator.comparing(Movie::getRatingCoefficient).reversed().thenComparing(Movie::getTitle)).collect(Collectors.toList());
        if(tmp.size() > 10)
            tmp = tmp.subList(0, 10);
        return tmp;
    }
}
