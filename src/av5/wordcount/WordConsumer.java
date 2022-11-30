package av5.wordcount;

import java.util.function.Consumer;

class WordConsumer implements Consumer<String> {
    private int lines;
    private int words;
    private int chars;

    @Override
    public void accept(String line) {
        lines++;
        for(String w: line.split("\\s+"))
            words++;
        chars += line.length() + 1;
    }

    @Override
    public String toString() {
        return String.format("%d lines, %d words, %d characters", lines, words, chars);
    }

    public WordConsumer() {
        this.lines = this.chars = this.words = 0;
    }
}