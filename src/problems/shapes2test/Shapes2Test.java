package problems.shapes2test;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Shapes2Test {

    public static void main(String[] args) {

        ShapesApplication shapesApplication = new ShapesApplication(10000);

        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
        shapesApplication.readCanvases(System.in);

        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
        shapesApplication.printCanvases(System.out);


    }
}

class ShapesApplication{
    List<Canvas> canvases;
    double maxArea;

    public ShapesApplication(double maxArea) {
        this.maxArea = maxArea;
        canvases = new ArrayList<>();
    }

    public int readCanvases(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        canvases = br.lines().map(line -> {
            try {
                return new Canvas(line, maxArea);
            } catch (IrregularCanvasException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
        return canvases.stream().mapToInt(Canvas::getSize).sum();
    }

    public void printCanvases(PrintStream out) {
        PrintWriter printWriter = new PrintWriter(out);
        canvases.stream().sorted(Comparator.reverseOrder()).forEach(printWriter::println);
        printWriter.flush();
    }
}

class Canvas implements Comparable<Canvas>{
    String id;
    List<Shape> shapes;
    double maxArea;

    public Canvas(String line, double maxArea) throws IrregularCanvasException {
        this.maxArea = maxArea;
        String[] parts = line.split(" ");
        this.id = parts[0];
        this.shapes = new ArrayList<>();
        Shape tmp = new Square(0);
        for(int i = 1; i < parts.length - 1; i+=2){
            if(parts[i].equals("C"))
                tmp = new Circle(Integer.parseInt(parts[i + 1]));
            else if(parts[i].equals("S"))
                tmp = new Square(Integer.parseInt(parts[i + 1]));

            if(tmp.getArea() > maxArea)
                throw new IrregularCanvasException(this);

            shapes.add(tmp);
        }
    }

    public double getArea(){
        return shapes.stream().mapToDouble(Shape::getArea).sum();
    }

    public int getSize(){
        return shapes.size();
    }

    public int getSizeCircles(){
        return shapes.stream().filter(s -> s.getType() == TYPE.CIRCLE).collect(Collectors.toList()).size();
    }

    public int getSizeSquares(){
        return shapes.stream().filter(s -> s.getType() == TYPE.SQUARE).collect(Collectors.toList()).size();
    }

    public double getMinimumArea(){
        return shapes.stream().mapToDouble(Shape::getArea).summaryStatistics().getMin();
    }

    public double getMaximumArea(){
        return shapes.stream().mapToDouble(Shape::getArea).summaryStatistics().getMax();
    }

    public double getAverageArea(){
        return shapes.stream().mapToDouble(Shape::getArea).summaryStatistics().getAverage();
    }

    @Override
    public String toString() {
        return String.format("%s %d %d %d %.2f %.2f %.2f", id, getSize(), getSizeCircles(), getSizeSquares(), getMinimumArea(), getMaximumArea(), getAverageArea());
    }

    @Override
    public int compareTo(Canvas o) {
        return Double.compare(this.getArea(), o.getArea());
    }

    public String getId() {
        return id;
    }

    public double getMaxArea() {
        return maxArea;
    }
}

interface Shape{
    public double getArea();
    public TYPE getType();
}

enum TYPE{SQUARE, CIRCLE}

class Square implements Shape {
    private int a;

    public Square(int a){
        this.a = a;
    }

    public int getA() {
        return a;
    }

    @Override
    public double getArea() {
        return a * a;
    }

    @Override
    public TYPE getType() {
        return TYPE.SQUARE;
    }
}

class Circle implements Shape {

    int r;

    public Circle(int r){
        this.r = r;
    }

    @Override
    public double getArea() {
        return r * r * Math.PI;
    }

    @Override
    public TYPE getType() {
        return TYPE.CIRCLE;
    }
}

class IrregularCanvasException extends Exception{
    public IrregularCanvasException(Canvas canvas) {
        super(String.format("Canvas %s has a shape with area larger than %.2f", canvas.getId(), canvas.getMaxArea()));
    }
}