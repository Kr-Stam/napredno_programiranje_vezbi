package av7.benford;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BenfordsLawAnalyzer {
    List<Integer> sample;

    public BenfordsLawAnalyzer() {
        this.sample = new ArrayList<>();
    }

    public void readData(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        sample = br.lines().map(Integer::parseInt).toList();
    }

    public double[] analyze(){
        double[] result = getDigitCount();
//        for(int i = 0; i < result.length; i++)
//            result[i] /= sample.size();
        result = Arrays.stream(result).map(x -> x/sample.size()).toArray();
        return result;
    }

    private double[] getDigitCount() {
        double[] result = new double[9];
        sample.forEach(x ->{
            result[firstDigit(x) - 1]++;
        });
        return result;
    }

    private int firstDigit(int n){
        while(n >= 10)
            n /= 10;
        return n;
    }
}
