package problems.blokovastruktura;

import java.util.*;

public class BlockContainerTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int size = scanner.nextInt();
        BlockContainer<Integer> integerBC = new BlockContainer<Integer>(size);
        scanner.nextLine();
        Integer lastInteger = null;
        for(int i = 0; i < n; ++i) {
            int element = scanner.nextInt();
            lastInteger = element;
            integerBC.add(element);
        }
        System.out.println("+++++ Integer Block Container +++++");
        System.out.println(integerBC);
        System.out.println("+++++ Removing element +++++");
        integerBC.remove(lastInteger);
        System.out.println("+++++ Sorting container +++++");
        integerBC.sort();
        System.out.println(integerBC);
        BlockContainer<String> stringBC = new BlockContainer<String>(size);
        String lastString = null;
        for(int i = 0; i < n; ++i) {
            String element = scanner.next();
            lastString = element;
            stringBC.add(element);
        }
        System.out.println("+++++ String Block Container +++++");
        System.out.println(stringBC);
        System.out.println("+++++ Removing element +++++");
        stringBC.remove(lastString);
        System.out.println("+++++ Sorting container +++++");
        stringBC.sort();
        System.out.println(stringBC);
    }
}

// Вашиот код овде

class BlockContainer<T extends Comparable<T>>{
    private List<TreeSet<T>> blocks;
    private int maxSize;

    public BlockContainer(int n) {
        this.maxSize = n;
        this.blocks = new ArrayList<>();
    }

    public void add(T a){
        Optional<TreeSet<T>> tmp = blocks.stream().filter(x -> x.size() < maxSize).findFirst();

        if(!tmp.isPresent()) {
            TreeSet<T> tmpList = new TreeSet<>();
            tmpList.add(a);
            blocks.add(tmpList);
        }else{
            tmp.get().add(a);
        }
    }

    public boolean remove(T a){
        TreeSet<T> tmp = blocks.get(blocks.size() - 1);
        if(!tmp.contains(a))
            return false;
        tmp.remove(a);
        if(tmp.isEmpty())
            blocks.remove(blocks.size() - 1);
        return true;
    }

    public void sort(){
        TreeSet<T> tmp = new TreeSet<>();
        blocks.forEach(tmp::addAll);
        List<TreeSet<T>> tmpBlocks = new ArrayList<>();
        tmp.forEach(x -> {
            if(tmpBlocks.isEmpty())
                tmpBlocks.add(new TreeSet<>());
            TreeSet<T> target = tmpBlocks.get(tmpBlocks.size() - 1);
            if(target.size() == maxSize) {
                target = new TreeSet<>();
                tmpBlocks.add(target);
            }
            target.add(x);
        });
        blocks = tmpBlocks;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        blocks.forEach(x -> sb.append(x).append(","));
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}

/*

    public BlockContainer(int n) - конструктор со еден аргумент, максималниот број на елементи во блокот
    public void add(T a) - метод за додавање елемент во последниот блок од контејнерот (ако блокот е полн, се додава нов блок)
    public boolean remove(T a) - метод за бришње на елемент од последниот блок (ако се избришат сите елементи од еден блок, тогаш и блокот се брише)
    public void sort() - метод за сортирање на сите елементи во контејнерот
    public String toString() - препокривање на методот да враќа String во следниот формат: пример: [7, 8, 9],[1, 2, 3],[5, 6, 12],[4, 10, 8]


 */