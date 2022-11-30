package av6.priorityqueue;

import java.util.ArrayList;
import java.util.Comparator;

public class MyPriorityQueue<T extends Comparable<T>> {
    ArrayList<PriorityNode<T>> list;

    class PriorityNode<E extends Comparable<E>> implements Comparable<PriorityNode<E>>{
        private final int priority;
        private final E element;

        public PriorityNode(int priority, E element) {
            this.priority = priority;
            this.element = element;
        }

        public int getPriority() {
            return priority;
        }

        public E getElement() {
            return element;
        }

        @Override
        public int compareTo(PriorityNode<E> o) {
            return Integer.compare(this.priority, o.priority);
        }
    }

    public MyPriorityQueue() {
        this.list = new ArrayList<>();
    }

    public void add(T element, int priority){
        list.add(new PriorityNode<>(priority, element));
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public T remove(){
        if(isEmpty())
            return null;
        int index = 0;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).compareTo(list.get(index)) == 1){
                index = i;
            }
        }
        return list.remove(index).getElement();
    }
}
