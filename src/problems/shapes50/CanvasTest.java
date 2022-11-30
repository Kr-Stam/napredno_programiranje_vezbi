package problems.shapes50;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class CanvasTest {

    public static void main(String[] args) {
        Canvas canvas = new Canvas();

        System.out.println("READ SHAPES AND EXCEPTIONS TESTING");
        canvas.readShapes(System.in);

        System.out.println("BEFORE SCALING");
        canvas.printAllShapes(System.out);
        canvas.scaleShapes("123456", 1.5);
        System.out.println("AFTER SCALING");
        canvas.printAllShapes(System.out);

        System.out.println("PRINT BY USER ID TESTING");
        canvas.printByUserId(System.out);

        System.out.println("PRINT STATISTICS");
        canvas.statistics(System.out);
    }
}

class Canvas{
    Collection<Shape> shapes;
    Collection<User> users;
    DoubleSummaryStatistics stats;

    public Canvas() {
        this.shapes = new TreeSet<>();
        this.users = new ArrayList<>();
    }

    public void readShapes(InputStream in){
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        br.lines().forEach(line -> {
            try {
                String[] parts = line.split(" ");
                Shape shape;

                if (parts[0].equals("1"))
                    shape = new Circle(parts[1], Double.parseDouble(parts[2]));
                else if (parts[0].equals("2"))
                    shape = new Square(parts[1], Double.parseDouble(parts[2]));
                else if (parts[0].equals("3"))
                    shape = new Rectangle(parts[1], Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
                else
                    shape = null;

                if(shape != null) {
                    shapes.add(shape);
                    if (users.stream().noneMatch(x -> x.getId().equals(parts[1])))
                        users.add(new User(parts[1]));
                    users.stream().filter(x -> x.getId().equals(parts[1])).findFirst().get().addShape(shape);
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        });
    }

    public void scaleShapes (String userID, double coef){
        shapes.stream().filter(x -> x.id.equals(userID)).forEach(x -> x.scale(coef));
    }

    public void printAllShapes(OutputStream os){
        PrintWriter pw = new PrintWriter(os);
        shapes.forEach(pw::println);
        pw.flush();
    }

    public void printByUserId (OutputStream os){
        PrintWriter pw = new PrintWriter(os);
        users.stream().sorted(Comparator.naturalOrder()).forEach(pw::println);
        pw.flush();
    }

    public void statistics (OutputStream os){
        PrintWriter pw = new PrintWriter(os);
        stats = shapes.stream().mapToDouble(Shape::getArea).summaryStatistics();
        pw.println(String.format("count: %d", stats.getCount()));
        pw.println(String.format("sum: %.2f", stats.getSum()));
        pw.println(String.format("min: %.2f", stats.getMin()));
        pw.println(String.format("average: %.2f", stats.getAverage()));
        pw.println(String.format("max: %.2f", stats.getMax()));
        pw.flush();
    }
}

class User implements Comparable<User>{
    private String id;
    private Collection<Shape> shapes;

    public User(String id) {
        this.id = id;
        this.shapes = new TreeSet<>(Comparator.comparing(Shape::getPerimeter));
    }

    public void addShape(Shape shape){
        shapes.add(shape);
    }

    @Override
    public int compareTo(User o) {
        return Comparator.comparing(User::getNumOfShapes).reversed().thenComparing(User::getId, Comparator.reverseOrder()).compare(this, o);
    }

    public int getNumOfShapes(){
        return shapes.size();
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Shapes of user: %s\n", id));
        shapes.forEach(x -> sb.append(x).append("\n"));
        if(shapes.size() > 0)
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}

abstract class Shape implements Comparable<Shape>{
    protected double perimeter;
    protected double area;
    protected String id;

    public abstract void scale(double x);

    @Override
    public int compareTo(Shape o) {
        return Double.compare(this.area, o.area);
    }

    public double getPerimeter() {
        return perimeter;
    }

    public double getArea() {
        return area;
    }

    public String getId() {
        return id;
    }

    protected boolean isValidID(String id){
        if(id.length() != 6)
            return false;

        for(char c: id.toCharArray()){
            char tmp = Character.toLowerCase(c);
            if(!((tmp >= '0' && tmp <= '9') || (tmp >= 'a' && tmp <= 'z')))
                return false;
        }
        return true;
    }
}

class Circle extends Shape{
    private double radius;

    public Circle(String id, double radius) throws InvalidIDException, InvalidDimensionException {
        if(!isValidID(id))
            throw new InvalidIDException(id);
        if(radius == 0)
            throw new InvalidDimensionException();
        this.radius = radius;
        this.id = id;
        setArea();
        setPerimeter();
    }

    private void setArea(){
        this.area = Math.PI * radius  * radius;
    }

    private void setPerimeter(){
        this.perimeter = 2* Math.PI * radius;
    }

    @Override
    public void scale(double x) {
        this.radius *= x;
        setArea();
        setPerimeter();
    }

    @Override
    public String toString() {
        return String.format("Circle -> Radius: %.2f Area: %.2f Perimeter: %.2f", radius, area, perimeter);
    }
}

class Square extends Shape{
    private double a;

    public Square(String id, double a) throws InvalidDimensionException, InvalidIDException {
        if(!isValidID(id))
            throw new InvalidIDException(id);
        if(a == 0)
            throw new InvalidDimensionException();
        this.a = a;
        this.id = id;
        setArea();
        setPerimeter();
    }

    private void setArea(){
        this.area = a * a;
    }

    private void setPerimeter(){
        this.perimeter = 4 * a;
    }

    @Override
    public void scale(double x) {
        this.a *= x;
        setArea();
        setPerimeter();
    }

    @Override
    public String toString() {
        return String.format("Square: -> Side: %.2f Area: %.2f Perimeter: %.2f", a, area, perimeter);
    }
}

class Rectangle extends Shape{
    private double a;
    private double b;

    public Rectangle(String id, double a, double b) throws InvalidIDException, InvalidDimensionException {
        if(!isValidID(id))
            throw new InvalidIDException(id);
        if(a == 0 || b == 0)
            throw new InvalidDimensionException();
        this.a = a;
        this.b = b;
        this.id = id;
        setArea();
        setPerimeter();
    }

    private void setArea(){
        this.area = a * b;
    }

    private void setPerimeter(){
        this.perimeter = 2 * a + 2 * b;
    }

    @Override
    public void scale(double x) {
        this.a *= x;
        this.b *= x;
        setArea();
        setPerimeter();
    }

    @Override
    public String toString() {
        return String.format("Rectangle: -> Sides: %.2f, %.2f Area: %.2f Perimeter: %.2f", a, b, area, perimeter);
    }
}

class InvalidIDException extends Exception{
    public InvalidIDException(String id) {
        super(String.format("ID %s is not valid", id));
    }
}

class InvalidDimensionException extends Exception{
    public InvalidDimensionException() {
        super("Dimension 0 is not allowed!");
    }
}