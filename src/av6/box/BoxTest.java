package av6.box;

public class BoxTest {

    public static void main(String[] args) {
        Box<String> box = new Box<>();

        box.add("PRV");
        box.add("VTOR");
        box.add("TRET");

        System.out.println(box.drawItem());
        System.out.println(box.drawItem());
        System.out.println(box.drawItem());
        System.out.println(box.drawItem());
        System.out.println(box.drawItem());
    }
}
