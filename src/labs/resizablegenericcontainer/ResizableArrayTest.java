package labs.resizablegenericcontainer;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ResizableArrayTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int test = jin.nextInt();
        if (test == 0) { //test ResizableArray on ints
            ResizableArray<Integer> a = new ResizableArray<Integer>();
            System.out.println(a.count());
            int first = jin.nextInt();
            a.addElement(first);
            System.out.println(a.count());
            int last = first;
            while (jin.hasNextInt()) {
                last = jin.nextInt();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
        }
        if (test == 1) { //test ResizableArray on strings
            ResizableArray<String> a = new ResizableArray<String>();
            System.out.println(a.count());
            String first = jin.next();
            a.addElement(first);
            System.out.println(a.count());
            String last = first;
            for (int i = 0; i < 4; ++i) {
                last = jin.next();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
            ResizableArray<String> b = new ResizableArray<String>();
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));

            System.out.println(a.removeElement(first));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
        }
        if (test == 2) { //test IntegerArray
            IntegerArray a = new IntegerArray();
            System.out.println(a.isEmpty());
            while (jin.hasNextInt()) {
                a.addElement(jin.nextInt());
            }
            jin.next();
            System.out.println(a.sum());
            System.out.println(a.mean());
            System.out.println(a.countNonZero());
            System.out.println(a.count());
            IntegerArray b = a.distinct();
            System.out.println(b.sum());
            IntegerArray c = a.increment(5);
            System.out.println(c.sum());
            if (a.sum() > 100)
                ResizableArray.copyAll(a, a);
            else
                ResizableArray.copyAll(a, b);
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.contains(jin.nextInt()));
            System.out.println(a.contains(jin.nextInt()));
        }
        if (test == 3) { //test insanely large arrays
            LinkedList<ResizableArray<Integer>> resizable_arrays = new LinkedList<ResizableArray<Integer>>();
            for (int w = 0; w < 500; ++w) {
                ResizableArray<Integer> a = new ResizableArray<Integer>();
                int k = 2000;
                int t = 1000;
                for (int i = 0; i < k; ++i) {
                    a.addElement(i);
                }

                a.removeElement(0);
                for (int i = 0; i < t; ++i) {
                    a.removeElement(k - i - 1);
                }
                resizable_arrays.add(a);
            }
            System.out.println("You implementation finished in less then 3 seconds, well done!");
        }
    }

}

class ResizableArray<T> {
    private T[] elements;
    private int size;
    private int length;
    private int count;

    public ResizableArray() {
        this.elements = (T[]) new Object[10];
        this.size = 10;
        this.length = 0;
        this.count = 0;
    }

    public void addElement(T element) {
        if (length == size) {
            T[] tmp = (T[]) new Object[size * 2];
            System.arraycopy(elements, 0, tmp, 0, length);
            this.elements = tmp;
            this.size *= 2;
        }
        elements[length++] = element;
        count++;
    }

    public boolean removeElement(T element) {
        for (int i = 0; i < length; i++) {
            if (element.equals(elements[i])) {
                elements[i] = null;
                count--;
                return true;
            }
        }
        return false;
    }

    public boolean contains(T element) {
        for (int i = 0; i < length; i++) {
              if (element.equals(elements[i])) {
                return true;
            }
        }
        return false;
    }

    public Object[] toArray() {
        return Arrays.copyOf(elements, length);
    }

    public T elementAt(int idx) {
        if (idx < 0 || idx >= length)
            throw new ArrayIndexOutOfBoundsException();
        return elements[idx];
    }

    public static <T> void copyAll(ResizableArray<? super T> dest, ResizableArray<? extends T> src) {
        ResizableArray<? extends T> tmp = new ResizableArray<T>();
        Arrays.stream(Arrays.copyOf(src.elements, src.getLength())).filter(Objects::nonNull).forEach(dest::addElement);
    }

    public T[] getElements() {
        return elements;
    }

    public int getSize() {
        return size;
    }

    public int getLength() {
        return length;
    }

    public int count() {
        return count;
    }

    public boolean isEmpty() {
        return length == 0;
    }
}

class IntegerArray extends ResizableArray<Integer> {

    public IntegerArray() {
    }

    public double sum() {
        Object[] elements = getElements();
        int result = 0;
        for (int i = 0; i < getLength(); i++) {
            if (elements[i] != null)
                result += (Integer) elements[i];
        }
        return result;
    }

    public double mean() {
        return sum() / count();
    }

    public int countNonZero() {
        Object[] elements = getElements();
        int result = 0;
        for (int i = 0; i < getLength(); i++) {
            if (elements[i] != null)
                if ((Integer) elements[i] != 0)
                    result++;
        }
        return result;
    }

    public IntegerArray distinct() {
        Stream<Integer> tmp = (Stream<Integer>) Arrays.stream(getElements()).distinct();
        IntegerArray result = new IntegerArray();
        tmp.forEach(result::addElement);
        return result;
    }

    public IntegerArray increment(int offset) {
        IntegerArray result = new IntegerArray();
        Arrays.stream(Arrays.copyOf(getElements(), getLength())).forEach(x -> {
            int tmp = 0;
            if(x != null)
                tmp = (Integer) x;
            result.addElement(tmp + offset);
        });
        return result;
    }
}
