package problems;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

/**
 * I partial exam 2016
 */
public class DailyTemperatureTest {
    public static void main(String[] args) {
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        dailyTemperatures.readTemperatures(System.in);
        System.out.println("=== Daily temperatures in Celsius (C) ===");
        dailyTemperatures.writeDailyStats(System.out, 'C');
        System.out.println("=== Daily temperatures in Fahrenheit (F) ===");
        dailyTemperatures.writeDailyStats(System.out, 'F');
    }
}

class DailyTemperatures{
    List<DailyTemperature> dailyTemperatureList;

    public DailyTemperatures() {
        this.dailyTemperatureList = new ArrayList<>();
    }

    public void readTemperatures(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        this.dailyTemperatureList = br.lines().map(DailyTemperature::new).collect(Collectors.toList());
    }

    public void writeDailyStats(PrintStream out, char type) {
        dailyTemperatureList.stream().sorted(Comparator.comparingInt(DailyTemperature::getDay)).forEach(x -> out.println(x.getStats(type)));
        out.flush();
    }
}

class DailyTemperature{
    int day;
    List<Temperature> temps;
    DoubleSummaryStatistics statsC;
    DoubleSummaryStatistics statsF;

    public int getDay() {
        return day;
    }

    public DailyTemperature(String line) {
        this.temps = new ArrayList<>();
        String[] parts = line.split(" ");
        this.day = Integer.parseInt(parts[0]);
        for(int i = 1; i < parts.length; i++){
            temps.add(new Temperature(parts[i]));
        }
        statsC = temps.stream().mapToDouble(Temperature::getAsC).summaryStatistics();
        statsF = temps.stream().mapToDouble(Temperature::getAsF).summaryStatistics();
    }

    public String getStats(char type){
        String count, min, max, avg;
        if(type == 'C') {
            count = String.format("%d", statsC.getCount());
            min = String.format("%.2f", statsC.getMin());
            max = String.format("%.2f", statsC.getMax());
            avg = String.format("%.2f", statsC.getAverage());
            return String.format("%3s: Count: %3s Min: %6sC Max: %6sC Avg: %6sC", day, count, min, max, avg);
        }
        else {
            count = String.format("%d", statsF.getCount());
            min = String.format("%.2f", statsF.getMin());
            max = String.format("%.2f", statsF.getMax());
            avg = String.format("%.2f", statsF.getAverage());
            return String.format("%3s: Count: %3s Min: %6sF Max: %6sF Avg: %6sF", day, count, min, max, avg);
        }
    }
}

class Temperature{
    private double value;
    private TemperatureType type;

    public Temperature(String temp) {
        this.value = Double.parseDouble(temp.substring(0, temp.length() - 1));
        if(temp.contains("C"))
            this.type = TemperatureType.C;
        else if(temp.contains("F"))
            this.type = TemperatureType.F;
        else
            this.type = TemperatureType.C;
    }

    public double getValue() {
        return value;
    }

    public TemperatureType getType() {
        return type;
    }

    public double getAsC(){
        if(type == TemperatureType.C)
            return value;
        else
            return (value - 32.0)*5.0/9.0;
    }

    public double getAsF(){
        if(type == TemperatureType.F)
            return value;
        else
            return value * 9.0 / 5.0 + 32;
    }
}

enum TemperatureType{C, F}