package av9.mostcommonnames;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NamesTest {

    public Map<String, Integer> readData(InputStream in){
        Map<String, Integer> result = new HashMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        br.lines().forEach(line -> {
            String[] parts = line.split(" ");
            result.put(parts[0], Integer.parseInt(parts[1]));
        });
        return result;
    }

    public List<String> sharedNames(Map<String, Integer> map1, Map<String, Integer> map2){
        return map1.keySet().stream().map(name ->{
            if(map2.containsKey(name))
                return name;
            return null;
        }).filter(Objects::nonNull).toList();
    }

    public static void main(String[] args) {

    }
}
