package av7.benford;

import java.util.Arrays;

public class BenfordsLawTest {

    public static void main(String[] args) {
         BenfordsLawAnalyzer b = new BenfordsLawAnalyzer();
         b.readData(System.in);
        System.out.println(Arrays.toString(b.analyze()));
    }
}
