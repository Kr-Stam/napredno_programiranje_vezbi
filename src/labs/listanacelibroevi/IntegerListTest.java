package labs.listanacelibroevi;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntegerListTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test standard methods
            int subtest = jin.nextInt();
            if (subtest == 0) {
                IntegerList list = new IntegerList();
                while (true) {
                    int num = jin.nextInt();
                    if (num == 0) {
                        list.add(jin.nextInt(), jin.nextInt());
                    }
                    if (num == 1) {
                        list.remove(jin.nextInt());
                    }
                    if (num == 2) {
                        print(list);
                    }
                    if (num == 3) {
                        break;
                    }
                }
            }
            if (subtest == 1) {
                int n = jin.nextInt();
                Integer a[] = new Integer[n];
                for (int i = 0; i < n; ++i) {
                    a[i] = jin.nextInt();
                }
                IntegerList list = new IntegerList(a);
                print(list);
            }
        }
        if (k == 1) { //test count,remove duplicates, addValue
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for (int i = 0; i < n; ++i) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while (true) {
                int num = jin.nextInt();
                if (num == 0) { //count
                    System.out.println(list.count(jin.nextInt()));
                }
                if (num == 1) {
                    list.removeDuplicates();
                }
                if (num == 2) {
                    print(list.addValue(jin.nextInt()));
                }
                if (num == 3) {
                    list.add(jin.nextInt(), jin.nextInt());
                }
                if (num == 4) {
                    print(list);
                }
                if (num == 5) {
                    break;
                }
            }
        }
        if (k == 2) { //test shiftRight, shiftLeft, sumFirst , sumLast
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for (int i = 0; i < n; ++i) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while (true) {
                int num = jin.nextInt();
                if (num == 0) { //count
                    list.shiftLeft(jin.nextInt(), jin.nextInt());
                }
                if (num == 1) {
                    list.shiftRight(jin.nextInt(), jin.nextInt());
                }
                if (num == 2) {
                    System.out.println(list.sumFirst(jin.nextInt()));
                }
                if (num == 3) {
                    System.out.println(list.sumLast(jin.nextInt()));
                }
                if (num == 4) {
                    print(list);
                }
                if (num == 5) {
                    break;
                }
            }
        }
    }

    public static void print(IntegerList il) {
        if (il.size() == 0) System.out.print("EMPTY");
        for (int i = 0; i < il.size(); ++i) {
            if (i > 0) System.out.print(" ");
            System.out.print(il.get(i));
        }
        System.out.println();
    }

}

class IntegerList {
    List<Integer> list;

    public IntegerList(Integer... numbers) {
        list = Arrays.stream(numbers).collect(Collectors.toList());
    }


    public IntegerList() {
        this.list = new ArrayList<>();
    }

//    public void add(int el, int idx) {
//        if (idx < list.size() && idx >= 0) {
////            list.remove(idx);
//            list.add(idx, el);
//        } else {
//            IntStream.range(list.size(), idx - 1).forEach(i -> list.add(0));
//            list.add(el);
//        }
//    }

    public void add(int el, int idx) {
        if (idx < list.size() && idx >= 0)
            list.add(idx, el);
        else {
            for (int i = list.size(); i < idx; i++)
                list.add(0);
            list.add(el);
        }
    }

    public int remove(int idx) {
        return list.remove(idx);
    }

    public int get(int idx) {
        return list.get(idx);

    }

    public void set(int el, int idx) {
        list.set(idx, el);
    }

    public int size() {
        return list.size();
    }

    public int count(int el) {
        return (int) list.stream().filter(x -> x.equals(el)).count();
    }

    public int contains(int el) {
        return list.indexOf(el);
    }

    public void removeDuplicates() {
        IntegerList il = new IntegerList();
        for (int i = 0; i < size(); i++) {
            int indexOfElement = il.contains(list.get(i));
            if (indexOfElement != -1)
                il.remove(indexOfElement);
            il.add(list.get(i), il.size());
        }
        list = il.list;
    }

    public void shiftRight(int index, int count) {
        isValidIndex(index);
        int shiftIndex = (index + count) % list.size();
        Integer element = list.remove(index);
        list.add(shiftIndex, element);
    }

    public boolean isValidIndex(int index) {
        if (index < 0 || index > list.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return true;
    }

    public void shiftLeft(int index, int count) {
        isValidIndex(index);
        int shiftIndex = (index - count) % list.size();
        if (shiftIndex < 0) {
            shiftIndex = list.size() + shiftIndex;
        }
        Integer element = list.remove(index);
        list.add(shiftIndex, element);
    }

    public IntegerList addValue(int value) {
        IntegerList il = new IntegerList();
        for (int i = 0; i < size(); i++)
            il.add(list.get(i) + value, il.size());

        return il;
    }

    public int sumFirst(int k) {
        List<Integer> result = list;
        if (k < size())
            result = list.subList(0, k);
        return result.stream().mapToInt(x -> x).sum();
    }

    public int sumLast(int k) {
        return list.stream().skip(size() - Math.min(k, size())).mapToInt(x -> x).sum();
    }
}

/*
    add(int el, int idx) - го додава елементот на соодветниот индекс.
    Доколку има други елементи после таа позиција истите се поместуваат на десно за едно место
    (нивните индекси им се зголемуваат за 1). Доколку idx е поголемо од сегашната големина на листата ја зголемуваме листата
     и сите нови елементи ги иницијалираме на нула (освен тој на позиција idx кој го поставуваме на el).
    remove(int idx):int - го отстранува елементот на дадена позиција од листата и истиот го враќа. Доколку после таа позиција има други елементи истите се поместуваат во лево (нивните индекси се намалуваат за 1).
    set(int el, int idx) - го поставува елементот на соодветната позиција.
    get(int idx):int - го враќа елементот на соодветната позиција.
    size():int - го враќа бројот на елементи во листата.

Освен овие методи IntegerList треба да нуди и неколку методи згодни за работа со цели броеви:

    count(int el):int - го враќа бројот на појавувања на соодветниот елемент во листата.
    removeDuplicates() - врши отстранување на дупликат елементите од листата. Доколку некој елемент се сретнува повеќе пати во листата ја оставаме само последната копија од него. Пр: 1,2,4,3,4,5. -> removeDuplicates() -> 1,2,3,4,5
    sumFirst(int k):int - ја дава сумата на првите k елементи.
    sumLast(int k):int - ја дава сумата на последните k елементи.
    shiftRight(int idx, int k) - го поместува елементот на позиција idx за k места во десно. При поместувањето листата ја третираме како да е кружна. Пр: list = [1,2,3,4]; list.shiftLeft(1,2); list = [1,3,4,2] - (листата е нула индексирана така да индексот 1 всушност се однесува на елементот 2 кој го поместуваме две места во десно) list = [1,2,3,4]; list.shiftLeft(2, 3); list = [1,3,2,4] - елементот 3 го поместуваме 3 места во десно. По две поместувања стигнуваме до крајот на листата и потоа продолжуваме да итерираме од почетокот на листата уште едно место и овде го сместуваме.
    shiftLeft(int idx , int k) - аналогно на shiftRight.
    addValue(int value):IntegerList - враќа нова листа каде елементите се добиваат од оригиналната листа со додавање на value на секој елемент. Пр: list = [1,4,3]; addValue(5) -> [6,9,8]

Забелешка која важи за сите методи освен add: Ако индексот е негативен или поголем од тековната големина на листата фрламе исклучок ArrayIndexOutOfBoundsException.
 */