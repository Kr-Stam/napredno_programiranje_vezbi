package av5.wordcount;

import java.io.*;

public class WordCount {

    public static void main(String[] args) {
        System.out.println(readLoop());
        System.out.println(readConsumer());
        System.out.println(readMapReduce());
    }

    public static String readLoop(){
        String line;
        int lines, chars, words;
        lines = chars = words = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\krist\\IdeaProjects\\napredno\\src\\av5\\test.dat.txt"));
            while ((line = br.readLine()) != null){
                lines++;
                for(String w: line.split("\\s+"))
                    words++;
                chars += line.length() + 1;
            }
            return String.format("%d lines, %d words, %d characters", lines, words, chars);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String readConsumer(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\krist\\IdeaProjects\\napredno\\src\\av5\\test.dat.txt"));

            WordConsumer wordConsumer = new WordConsumer();
            br.lines().forEach(wordConsumer);

            return wordConsumer.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String readMapReduce(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\krist\\IdeaProjects\\napredno\\src\\av5\\test.dat.txt"));

            LineCount result = br.lines().map(LineCount::new).reduce(new LineCount(0, 0, 0), LineCount::add);

            return result.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
