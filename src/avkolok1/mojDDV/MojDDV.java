package avkolok1.mojDDV;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Objects;

public class MojDDV {
    private List<Bill> bills;

    public void readRecords(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        bills = br.lines().map(line -> {
            try {
                return new Bill(line);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            return null;
        }).filter(Objects::nonNull).toList();
    }

    public void printSorted(PrintStream out) {
        bills.stream().sorted().forEach(System.out::println);
    }

    public void printStatistics(PrintStream out) {
        DoubleSummaryStatistics statistics = bills.stream().mapToDouble(Bill::getTaxReturns).summaryStatistics();
        System.out.println(String.format("min: %.2f max: %.2f sum: %.2f count: %d average: %.2f", statistics.getMin(), statistics.getMax(), statistics.getSum(), statistics.getCount(), statistics.getAverage()));
    }
}
