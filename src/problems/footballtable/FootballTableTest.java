package problems.footballtable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Partial exam II 2016/2017
 */
public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}

class FootballTable {
    Map<String, Stats> teams;

    public FootballTable() {
        teams = new HashMap<>();
    }

    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
        if (!teams.containsKey(homeTeam))
            teams.put(homeTeam, new Stats(homeTeam));
        if (!teams.containsKey(awayTeam))
            teams.put(awayTeam, new Stats(awayTeam));
        teams.get(homeTeam).update(homeGoals, awayGoals);
        teams.get(awayTeam).update(awayGoals, homeGoals);
    }

    public void printTable() {
        AtomicReference<Integer> count = new AtomicReference<>(1);
        teams.values().stream().sorted(Comparator.reverseOrder()).forEach(x -> {
                System.out.println(String.format("%2s. %s", count.get(), x));
                count.getAndSet(count.get() + 1);
        });
    }
}

class Stats implements Comparable<Stats> {
    private String name;
    private int wins;
    private int loses;
    private int tied;
    private int scored;
    private int received;


    public Stats(String name) {
        this.name = name;
        scored = received = wins = loses = tied = 0;
    }

    public void update(int scored, int received) {
        this.scored += scored;
        this.received += received;
        if (scored > received)
            wins++;
        else if (scored < received)
            loses++;
        else
            tied++;
    }

    public int getPoints() {
        return wins * 3 + tied;
    }

    public int getGoalDiff() {
        return scored - received;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%-15s%5s%5s%5s%5s%5s", name, wins + loses + tied, wins, tied, loses, getPoints());
    }

    @Override
    public int compareTo(Stats o) {
        return Comparator
                .comparing(Stats::getPoints).thenComparing(Stats::getGoalDiff).thenComparing(Stats::getName, Comparator.reverseOrder()).compare(this, o);
    }
}
