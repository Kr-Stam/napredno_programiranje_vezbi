package problems.wordvectors;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Word vectors test
 */
public class WordVectorsTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] words = new String[n];
        List<List<Integer>> vectors = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            words[i] = parts[0];
            List<Integer> vector = Arrays.stream(parts[1].split(":"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            vectors.add(vector);
        }
        n = scanner.nextInt();
        scanner.nextLine();
        List<String> wordsList = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            wordsList.add(scanner.nextLine());
        }
        WordVectors wordVectors = new WordVectors(words, vectors);
        wordVectors.readWords(wordsList);
        n = scanner.nextInt();
        List<Integer> result = wordVectors.slidingWindow(n);
        System.out.println(result.stream()
                .map(Object::toString)
                .collect(Collectors.joining(",")));
        scanner.close();
    }
}

class WordVectors{
    Map<String, List<Integer>> wordMap;
    List<String> words;
    List<Integer> defaultVectors;

    public WordVectors(String[] words, List<List<Integer>> vectors){
        this.wordMap = new HashMap<>();
        this.words = new ArrayList<>();
        this.defaultVectors = new ArrayList<>();
        IntStream.range(0, 5).forEach(i -> defaultVectors.add(5));
        for(int i = 0; i < words.length; i++)
            wordMap.put(words[i], vectors.get(i));
    }

    public void readWords(List<String> words){
        this.words = words;
    }

    public List<Integer> slidingWindow(int n){
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < words.size() - n + 1; i++){
            int[] vectorSum = new int[5];
            words.subList(i, i + n).stream().map(word -> {
                List<Integer> tmp;
                if (wordMap.containsKey(word))
                    tmp = wordMap.get(word);
                else
                    tmp = defaultVectors;
                return tmp;
            }).forEach(vectors -> {
                IntStream.range(0, 5).forEach(p -> {
                    vectorSum[p] += vectors.get(p);
                });
            });
            result.add(Arrays.stream(vectorSum).max().getAsInt());
        }
        return result;
    }
}