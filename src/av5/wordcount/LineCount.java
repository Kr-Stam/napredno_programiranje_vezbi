package av5.wordcount;

public class LineCount {
    private int lines;
    private int words;
    private int chars;

    @Override
    public String toString() {
        return String.format("%d lines, %d words, %d characters", lines, words, chars);
    }

    public LineCount(String line) {
        lines++;
        for(String w: line.split("\\s+"))
            words++;
        chars += line.length() + 1;
    }

    public LineCount(int lines, int words, int chars) {
        this.lines = lines;
        this.words = words;
        this.chars = chars;
    }

    public static LineCount add(LineCount a, LineCount b){
        return new LineCount(a.lines + b.lines, a.words + b.words, a.chars + b.chars);
    }
}
