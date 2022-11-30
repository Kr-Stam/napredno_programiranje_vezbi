package problems.generickiklaster;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class ClusterTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cluster<Point2D> cluster = new Cluster<>();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            long id = Long.parseLong(parts[0]);
            float x = Float.parseFloat(parts[1]);
            float y = Float.parseFloat(parts[2]);
            cluster.addItem(new Point2D(id, x, y));
        }
        int id = scanner.nextInt();
        int top = scanner.nextInt();
        cluster.near(id, top);
        scanner.close();
    }
}

abstract class MyObject<T> {
    abstract public long getId();

    abstract public double getDistance(T object);
}

class Cluster<T extends MyObject<T>> {
    List<T> objects;

    public Cluster() {
        this.objects = new ArrayList<>();
    }

    public void addItem(T obj) {
        objects.add(obj);
    }

    public void near(long id, int top) {
        MyObject<T> obj = objects.stream().filter(x -> x.getId() == id).findFirst().get();
        List<T> tmp = objects.stream().sorted((left, right) -> Double.compare(obj.getDistance(left), obj.getDistance(right))).collect(Collectors.toList());
        if (top < tmp.size())
            tmp = tmp.subList(1, top + 1);
        AtomicInteger count = new AtomicInteger(1);
        tmp.forEach(x -> {
            System.out.println(String.format("%d. %d -> %.3f", count.getAndIncrement(), x.getId(), obj.getDistance(x)));
        });
    }
}

class Point2D extends MyObject<Point2D> {
    private long id;
    private float x;
    private float y;

    public Point2D(long id, float x, float y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public double getDistance(Point2D object) {
        return Math.sqrt(Math.pow(x - object.x, 2) + Math.pow(y - object.y, 2));
    }
}
