package av7.finalist;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class FinalistTest {

    private static final int FINALIST_NUM = 30;
    private static final Random random = new Random();

    public static void main(String[] args) {
        RandomPicker picker = new RandomPicker(30);
        try {
            System.out.println(picker.pick(3));
        } catch (InvalidPickerArgumentException e) {
            System.out.println(e.getMessage());;
        }
    }

}
