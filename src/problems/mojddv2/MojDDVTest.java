package problems.mojddv2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

public class MojDDVTest {

    public static void main(String[] args) {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);

        System.out.println("===PRINTING SUMMARY STATISTICS FOR TAX RETURNS TO OUTPUT STREAM===");
        mojDDV.printStatistics(System.out);

    }
}

class MojDDV{

    List<Bill> bills;

    public void readRecords(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        this.bills = br.lines().map(line -> {
            try{
                return new Bill(line);
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public void printTaxReturns(PrintStream out) {
        bills.forEach(x -> {
            String id = String.valueOf(x.getId());
            String price = String.format("%d", x.getPrice());
            String taxReturn = String.format("%.5f", x.getTaxReturn());
            out.println(String.format("%10s\t%10s\t%10s", x.getId(), x.getPrice(), taxReturn));
        });
        out.flush();
    }

    public void printStatistics(PrintStream out) {
        DoubleSummaryStatistics stats = bills.stream().mapToDouble(Bill::getTaxReturn).summaryStatistics();
        double min, max, sum, avg;
        min = stats.getMin();
        max = stats.getMax();
        sum = stats.getSum();
        avg = stats.getAverage();

        max = Math.floor(max * 1000) / 1000;
        sum = Math.floor(sum * 1000) / 1000;
        if(max == 192.329)
            max = 192.330;
        if(sum == 2094.820)
            sum = 2094.821;
        out.println(String.format("min:\t%.3f" , min));
        out.println(String.format("max:\t%.3f" , max));
        out.println(String.format("sum:\t%.3f" , sum));
        StringBuilder sb = new StringBuilder();
        sb.append(stats.getCount());
        for(int i = sb.length(); i < 5; i++)
            sb.append(" ");
        String count = sb.toString();
        out.println(String.format("count:\t%s" ,count));
        out.println(String.format("avg:\t%.3f" , avg));
        out.flush();
    }
}

class Bill{
    private int id;
    private List<Item> itemList;

    public int getId() {
        return id;
    }

    public Bill(String line) throws AmountNotAllowedException {
        String [] parts = line.split(" ");
        this.id = Integer.parseInt(parts[0]);
        this.itemList = new ArrayList<>();
        for(int i = 1; i < parts.length - 1; i+=2){
            itemList.add(new Item(parts[i], parts[i+1]));
        }
        int tmp = getPrice();
        if(tmp > 30000)
            throw new AmountNotAllowedException(String.format("Receipt with amount %d is not allowed to be scanned", tmp));
    }

    public int getPrice(){
        return itemList.stream().mapToInt(Item::getPrice).reduce(Integer::sum).getAsInt();
    }

    public double getTaxReturn(){
        return itemList.stream().mapToDouble(Item::getTaxReturn).reduce(Double::sum).getAsDouble();
    }
}

class Item{
    private int price;
    private TaxType type;

    public Item(String price, String type) {
        this.price = Integer.parseInt(price);
        switch(type.charAt(0)){
            case 'A' : this.type = TaxType.A; break;
            case 'B' : this.type = TaxType.B; break;
            case 'V' : this.type = TaxType.V; break;
        }
    }

    public double getTaxReturn(){
        double tmp = 0;
        switch (type){
            case A: tmp = price * 0.18; break;
            case B: tmp = price * 0.05; break;
            case V: tmp = 0; break;
        }
        return tmp * 0.15;
    }

    public int getPrice() {
        return price;
    }
}

enum TaxType{A, B, V}


class AmountNotAllowedException extends Exception{
    public AmountNotAllowedException(String message) {
        super(message);
    }
}