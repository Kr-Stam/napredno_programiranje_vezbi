package av6.priorityqueue;

public class PriorityQueueTest {

    public static void main(String[] args) {
        MyPriorityQueue<String> queue = new MyPriorityQueue<>();

        queue.add("10", 10);
        queue.add("20", 20);
        queue.add("2", 2);
        queue.add("3", 3);

        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
    }
}
