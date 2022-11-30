package av8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SieveOfErasthostenes {

    public static void main(String[] args) {

        ArrayList<Integer> test = new ArrayList<>();
        for(int i = 2; i <= 100; i++)
            test.add(i);

//        sieve(test);
        sieveFunc(test);

        System.out.println(test);
    }

    private static void sieve(List<Integer> test) {
        for(int i = 0; i < test.size(); i++) {
            List<Integer> firstPart = test.subList(0, i);
            List<Integer> secondPart = test.subList(i, test.size());
            Iterator<Integer> it = secondPart.iterator();
            while(it.hasNext()){
                int tmp = it.next();
                if(tmp % test.get(i) == 0)
                    secondPart.remove(tmp);
            }
            firstPart.addAll(secondPart);
            test = firstPart;
        }
    }

    private static void sieveFunc(List<Integer> test) {
        for(int i = 0; i < test.size(); i++){
            for(int j = i + 1; j < test.size(); j++){
                if(test.get(j) % test.get(i) == 0){
                    test.remove(j);
                    j--;
                }
            }
        }
    }
}
