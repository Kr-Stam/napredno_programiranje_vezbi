package problems.stadium;

import java.util.*;

public class StaduimTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] sectorNames = new String[n];
        int[] sectorSizes = new int[n];
        String name = scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            sectorNames[i] = parts[0];
            sectorSizes[i] = Integer.parseInt(parts[1]);
        }
        Stadium stadium = new Stadium(name);
        stadium.createSectors(sectorNames, sectorSizes);
        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            try {
                stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]));
            } catch (SeatNotAllowedException e) {
                System.out.println("SeatNotAllowedException");
            } catch (SeatTakenException e) {
                System.out.println("SeatTakenException");
            }
        }
        stadium.showSectors();
    }
}

class Sector{
    private String code;
    private int numOfSeats;
    private boolean[] occupied;
    private int type = 0;

    public Sector(String code, int numOfSeats) {
        this.code = code;
        this.numOfSeats = numOfSeats;
        this.occupied = new boolean[numOfSeats];
    }

    public void buyTicket(int seat, int type) throws SeatTakenException, SeatNotAllowedException {
        if(occupied[seat - 1])
            throw new SeatTakenException();
        if((this.type != 0) && (this.type != type) && (type != 0))
            throw new SeatNotAllowedException();
        occupied[seat - 1] = true;
        if(type != 0)
            this.type = type;
    }

    public String getCode() {
        return code;
    }

    public int getNumOfSeats() {
        return numOfSeats;
    }

    public int getNumOfFreeSeats(){
        int result = 0;
        for(int i = 0; i < occupied.length; i++){
            if (!occupied[i])
                result++;
        }
        return result;
    }

    @Override
    public String toString() {
        int free = getNumOfFreeSeats();
        int total = getNumOfSeats();
        double percent = ((double) total - free) / (double) total * 100;
        return String.format("%s\t%d/%d\t%.1f%%", code, free, total, percent);
    }
}

class Stadium{
    private String name;
    private List<Sector> sectors;

    public Stadium(String name) {
        this.name = name;
        this.sectors = new ArrayList<>();
    }

    public void createSectors(String[] sectorNames, int[] sizes){
        for(int i = 0; i < sectorNames.length; i++){
            sectors.add(new Sector(sectorNames[i], sizes[i]));
        }
    }

    public void buyTicket(String sectorName, int seat, int type) throws SeatTakenException, SeatNotAllowedException {
        Optional<Sector> tmp = sectors.stream().filter(x -> x.getCode().equals(sectorName)).findFirst();
        if(tmp.isPresent()){
            tmp.get().buyTicket(seat, type);
        }
    }

    public void showSectors(){
        sectors.stream()
                .sorted(Comparator
                        .comparing(Sector::getNumOfFreeSeats)
                        .reversed()
                        .thenComparing(Sector::getCode))
                .forEach(System.out::println);
    }
}

class SeatTakenException extends Exception{}
class SeatNotAllowedException extends Exception{}
/*
    void showSectors() ги печати сите сектори сортирани според бројот на слободни места во опаѓачки редослед (ако повеќе сектори имаат ист број на слободни места,
     се подредуваат според името).


 */