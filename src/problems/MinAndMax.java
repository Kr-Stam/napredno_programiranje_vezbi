package problems;

import java.util.Scanner;

public class MinAndMax {
    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        MinMax<String> strings = new MinMax<String>();
        for(int i = 0; i < n; ++i) {
            String s = scanner.next();
            strings.update(s);
        }
        System.out.println(strings);
        MinMax<Integer> ints = new MinMax<Integer>();
        for(int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            ints.update(x);
        }
        System.out.println(ints);
    }
}

class MinMax<T extends Comparable<T>>{
    private T min;
    private T max;

    int count;

    MinMax(){
        min = max = null;
        this.count = 0;
    }

    void update(T obj){
        if(min == null){
            min = max = obj;
        }else{
            if(obj.compareTo(min) != 0  && obj.compareTo(max) != 0)
                count++;
            if(obj.compareTo(min) < 0) {
                min = obj;
            }
            else if(obj.compareTo(max) > 0) {
                max = obj;
            }else if(obj.compareTo(min) != 0  && obj.compareTo(max) != 0) {
                count--;
            }
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s %d\n", min, max, count);
    }
}
