package problems.stackedcanvas;

import java.util.*;

public class ShapesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Canvas canvas = new Canvas();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int type = Integer.parseInt(parts[0]);
            String id = parts[1];
            if (type == 1) {
                Color color = Color.valueOf(parts[2]);
                float radius = Float.parseFloat(parts[3]);
                canvas.add(id, color, radius);
            } else if (type == 2) {
                Color color = Color.valueOf(parts[2]);
                float width = Float.parseFloat(parts[3]);
                float height = Float.parseFloat(parts[4]);
                canvas.add(id, color, width, height);
            } else if (type == 3) {
                float scaleFactor = Float.parseFloat(parts[2]);
                System.out.println("ORIGNAL:");
                System.out.print(canvas);
                canvas.scale(id, scaleFactor);
                System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
                System.out.print(canvas);
            }

        }
    }
}

enum Color {
    RED, GREEN, BLUE
}

class Canvas {
    List<Shape> shapeList;

    public Canvas() {
        this.shapeList = new ArrayList<>();
    }

    public void add(String id, Color color, float radius) {
        shapeList.add(new Circle(id, color, radius));
        shapeList.sort(Comparator.reverseOrder());
    }

    public void add(String id, Color color, float width, float height) {
        shapeList.add(new Rectangle(id, color, width, height));
        shapeList.sort(Comparator.reverseOrder());
    }

    public void scale(String id, float scaleFactor) {
        Optional<Shape> tmp = shapeList.stream().filter(x -> x.getId().equals(id)).findFirst();
        tmp.ifPresent(shape -> {
            shape.scale(scaleFactor);
            shapeList.sort(Comparator.reverseOrder());
        });

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        shapeList.forEach( x -> sb.append(x).append("\n"));
        return sb.toString();
    }
}

interface Scalable{
    public void scale(float a);
}

interface Stackable{
    public float weight();
}

abstract class Shape implements Scalable, Stackable, Comparable<Shape>{
    String id;
    Color color;

    public Shape(String id, Color color) {
        this.id = id;
        this.color = color;
    }

    @Override
    public int compareTo(Shape o) {
        return Float.compare(this.weight(), o.weight());
    }

    public String getId() {
        return id;
    }
}

class Circle extends Shape{
    private float r;

    public Circle(String id, Color color, float radius) {
        super(id, color);
        this.r = radius;
    }

    @Override
    public void scale(float a) {
        this.r *= a;
    }

    @Override
    public float weight() {
        return (float) (r * r * Math.PI);
    }

    @Override
    public String toString() {
        return String.format("C: %-4s %-9s %10.2f", id, color, weight());
    }
}

class Rectangle extends Shape{
    private float a;
    private float b;

    public Rectangle(String id, Color color, float width, float height) {
        super(id, color);
        this.a = width;
        this.b = height;
    }

    @Override
    public void scale(float a) {
        this.a *= a;
        this.b *= a;
    }

    @Override
    public float weight() {
        return a * b;
    }

    @Override
    public String toString() {
        return String.format("R: %-4s %-9s %10.2f", id, color, weight());
    }
}

