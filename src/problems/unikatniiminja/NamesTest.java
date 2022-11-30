package problems.unikatniiminja;

import java.util.*;
import java.util.stream.Collectors;

public class NamesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Names names = new Names();
        for (int i = 0; i < n; ++i) {
            String name = scanner.nextLine();
            names.addName(name);
        }
        n = scanner.nextInt();
        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
        names.printN(n);
        System.out.println("===== FIND NAME =====");
        int len = scanner.nextInt();
        int index = scanner.nextInt();
        System.out.println(names.findName(len, index));
        scanner.close();

    }
}

// vashiot kod ovde

class Names{
    TreeSet<Name> names;

    public Names() {
        this.names = new TreeSet<>();
    }

    public void addName(String name){
        Name tmp = new Name(name);
        if(!names.contains(tmp))
            names.add(tmp);
        else
            names.stream().filter(x -> x.equals(tmp)).findFirst().get().increment();
    }

    public void printN(int n){
        names.stream().filter(x -> x.getN() >= n).collect(Collectors.toList()).forEach(System.out::println);
    }

    public String findName(int len, int x){
        List<Name> tmp = names.stream().filter(name -> name.getName().length() < len).collect(Collectors.toList());
        if(x > tmp.size())
            x %= tmp.size();
        return tmp.get(x).getName();
    }
}/*

    public String findName(int len, int x) - го враќа името кое со наоѓа на позиција x (почнува од 0)
    во листата од уникатни имиња подредени лексикографски,
    по бришење на сите имиња со големина поголема или еднаква на len.
    Позицијата x може да биде поголема од бројот на останати имиња, во тој случај се продожува со броење од почетокот на листата.
    Пример за листа со 3 имиња A, B, C, ако x = 7, се добива B. A0, B1, C2, A3, B4, C5, A6, B7.


 */

class Name implements Comparable<Name>{
    private String name;
    private int n;

    public Name(String name) {
        this.name = name;
        this.n = 1;
    }

    public void increment(){
        n++;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Name))
            return false;
        Name tmp = (Name) obj;
        return tmp.name.equals(name);
    }

    @Override
    public int compareTo(Name o) {
        return this.name.compareTo(o.name);
    }

    public String getName() {
        return name;
    }

    public int getN() {
        return n;
    }

    @Override
    public String toString() {
        return String.format("%s (%d) %d", name, n, parseName());
    }

    private int parseName(){
        int count = 0;
        String tmp = name.toLowerCase();
        for(char c = 'a'; c <= 'z'; c++){
            if(tmp.contains(c + "")){
                count++;
                tmp = tmp.replace(c + "", "");
            }
        }
        return count;
    }
}
