//package problems;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class Shapes1Test {
//
//    public static void main(String[] args) {
//        ShapesApplication shapesApplication = new ShapesApplication();
//
//        System.out.println("===READING SQUARES FROM INPUT STREAM===");
//        System.out.println(shapesApplication.readCanvases(System.in));
//        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
//        shapesApplication.printLargestCanvasTo(System.out);
//
//    }
//}
//
//class ShapesApplication{
//    List<Canvas> canvases;
//
//    public int readCanvases(InputStream in) {
//        BufferedReader br = new BufferedReader(new InputStreamReader(in));
//        canvases = br.lines().map(Canvas::new).collect(Collectors.toList());
//        return canvases.stream().mapToInt(Canvas::getSize).sum();
//    }
//
//    public void printLargestCanvasTo(PrintStream out) {
//        PrintWriter printWriter = new PrintWriter(out);
//
//        printWriter.println(canvases.stream().max(Comparator.naturalOrder()).get());
//        printWriter.flush();
//    }
//}
//
//class Canvas implements Comparable<Canvas>{
//    String id;
//    List<Square> shapes;
//
//    public Canvas(String line){
//        String[] parts = line.split(" ");
//        this.id = parts[0];
//        this.shapes = new ArrayList<>();
//        for(int i = 1; i < parts.length; i++)
//            shapes.add(new Square(parts[i]));
//    }
//
//    public int getPerimeter(){
//        return shapes.stream().mapToInt(Square::getP).sum();
//    }
//
//    public int getSize(){
//        return shapes.size();
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%s %d %d", id, getSize(), getPerimeter());
//    }
//
//    @Override
//    public int compareTo(Canvas o) {
//        return Integer.compare(this.getPerimeter(), o.getPerimeter());
//    }
//}
//
//class Square{
//    private int a;
//
//    public Square(String a){
//        this.a = Integer.parseInt(a);
//    }
//
//    public int getA() {
//        return a;
//    }
//
//    public int getP(){
//        return a * 4;
//    }
//}