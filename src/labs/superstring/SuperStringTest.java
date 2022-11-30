package labs.superstring;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SuperStringTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (  k == 0 ) {
            SuperString s = new SuperString();
            while ( true ) {
                int command = jin.nextInt();
                if ( command == 0 ) {//append(String s)
                    s.append(jin.next());
                }
                if ( command == 1 ) {//insert(String s)
                    s.insert(jin.next());
                }
                if ( command == 2 ) {//contains(String s)
                    System.out.println(s.contains(jin.next()));
                }
                if ( command == 3 ) {//reverse()
                    s.reverse();
                }
                if ( command == 4 ) {//toString()
                    System.out.println(s);
                }
                if ( command == 5 ) {//removeLast(int k)
                    s.removeLast(jin.nextInt());
                }
                if ( command == 6 ) {//end
                    break;
                }
            }
        }
    }

}

class SuperString{
    LinkedList<String> list;
    LinkedList<String> added;

    public SuperString() {
        this.list = new LinkedList<>();
        this.added = new LinkedList<>();
    }

    public void append(String s){
        list.addLast(s);
        added.addLast(s);
    }

    public void insert(String s){
        list.addFirst(s);
        added.addLast(s);
    }

    public boolean contains(String s){
        StringBuilder result = new StringBuilder();
        list.forEach(x -> result.append(x));
        return result.toString().contains(s);
    }

    public void reverse(){
        LinkedList<String> result = new LinkedList<>();
        LinkedList<String> result1 = new LinkedList<>();
        list.stream().map(x -> {
            StringBuilder sb = new StringBuilder();
            for(int i = x.length() - 1; i >= 0; i--){
                sb.append(x.charAt(i));
            }
            return sb.toString();
        }).collect(Collectors.toCollection(LinkedList::new))
                .descendingIterator().forEachRemaining(x -> {
                    result.add(x);
                });
        added.stream().map(x -> {
            StringBuilder sb = new StringBuilder();
            for(int i = x.length() - 1; i >= 0; i--){
                sb.append(x.charAt(i));
            }
            return sb.toString();
        }).collect(Collectors.toCollection(LinkedList::new))
                .iterator().forEachRemaining(x -> {
                    result1.add(x);
                });
        this.list = result;
        this.added = result1;
    }

    @Override
    public String toString() {
        return list.stream().reduce("", String::concat);
    }

    public void removeLast(int k){
        for(int i = 0;(i < k) && !added.isEmpty(); i++){
            list.remove(added.removeLast());
        }
    }
}
/*
    reverse() - го превртува стрингот на следниов начин. Ги превртува сите елементи во листата,
    а потоа и секој подстринг како елемент посебно го превртува. list = [ "st" , "arz" , "andrej: ]; reverse(); list = [ "jerdna", "zra", "ts"]
    toString():String - ги враќа конкатенирани сите елементи во листата list = [ "st" , "arz" , "andrej"]; toString() -> "starzandrej"
    removeLast(int k) – ги отстранува последнo додадените k подстрингови

 */