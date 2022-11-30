package av7.finalist;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomPicker {
    private final int n;
    private static final Random RANDOM = new Random();

    public RandomPicker(int n) {
        this.n = n;
    }

    public List<Integer> pick(int x) throws InvalidPickerArgumentException {
        if(x > n)
            throw new InvalidPickerArgumentException("The number of picks cannot be greater than the number of finalists");
        List<Integer> result = new ArrayList<>();
        while(result.size() != x){
            int tmp = RANDOM.nextInt(n) + 1;
            if(!result.contains(tmp))
                result.add(tmp);
        }
        return result;
    }

    public List<Integer> pickStream(int x) throws InvalidPickerArgumentException {
        return RANDOM.ints(2 * n, 1, n + 1).boxed().limit(n).toList();
    }
}
