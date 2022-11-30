package av3.date;

import java.util.Arrays;

public class MyDate {

    private int days;

    private static final int START_YEAR = 1800;
    private static final int END_YEAR = 2100;

    private static final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static int[] daysTillMonth;
    private static int[] daysTillYear;

    static {
        initDaysTillMonth();
        initDaysTillYear();
    }

    private static void initDaysTillYear() {
        int n = END_YEAR - START_YEAR;
        daysTillYear = new int[n];
        daysTillYear[0] = 0;
        for(int i = 1; i < n; i++){
            daysTillYear[i] = daysTillYear[i-1] + 365;
            if(isLeapYear(i + START_YEAR))
                daysTillYear[i]++;
        }
    }

    private static void initDaysTillMonth() {
        daysTillMonth = new int[13];
        daysTillMonth[0] = 0;
        for(int i = 1; i < 13; i++){
            daysTillMonth[i] = daysTillMonth[i - 1] + daysOfMonth[i - 1];
        }
        System.out.println(Arrays.toString(daysTillMonth));
    }

    public static boolean isLeapYear(int year){
        return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0);
    }

    public MyDate(int days) {
        this.days = days;
    }

    public MyDate(int d, int m, int y) {
        this.days = 1;
        if(!isValid(y, m, d))
            System.out.println("INVALID DATE");
        else{
            days += daysTillYear[y - START_YEAR];
            days += daysTillMonth[m - 1];
            days += d;
        }
    }

    private boolean isValid(int y, int m , int d){
        if((y < 1800 || y > 2100) || (m < 1 || m > 12) || (d < 1 || d > 31))
            return false;
        if(m == 2 && d > 29)
            return false;
        if(!isLeapYear(y) && d == 29)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("%02d-%02d-%d", getDay(), getMonth(), getYear());
    }

    //Se chudam kolku e pametno vaka da se odvoeni deka iako e pochitko bespotrebna kolicina na pati se povikuvaat getDay, getMonth, getYear
    //Ama realno popametniot nachin bi bilo da se spravish so promenliva za tie raboti u klasata mesto da go duplirash kodot
    private int getDay() {
        return days - daysTillYear[getYear() - START_YEAR] - daysTillMonth[getMonth() - 1] - 1;
    }

    private int getMonth() {
        int n = days;
        int y = getYear();
        n -= daysTillYear[y - START_YEAR];
        int result = 1;
        int leap = isLeapYear(y) ? 1 : 0;

        int[] tmp = new int[13];
        tmp[0] = daysTillMonth[0];
        tmp[1] = daysTillMonth[1];
        for(int i = 2; i < 13; i++){
            tmp[i] = daysTillMonth[i] + 1;
        }
        for(int i = 0; i < 12; i++){
            if(tmp[i] < n && tmp[i + 1] + 1 >= n)
                return i + 1;
        }
        return result;
    }

    public int getYear() {
        int n = END_YEAR - START_YEAR;
        for(int i = 0; i < n; i++){
            if(daysTillYear[i] >= days)
                n = i - 1;
        }
        return n + START_YEAR;
    }

    public static void main(String[] args) {
        MyDate test = new MyDate(30, 4, 1804);
        System.out.println(test.days);
        System.out.println(test);
    }
}
