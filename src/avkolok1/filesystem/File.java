package avkolok1.filesystem;

public class File implements IFile{
    private String name;
    private long size;


    public File(String name, long size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public long getFileSize() {
        return size;
    }

    @Override
    public String getFileInfo(int indent) {
        return String.format("%sFile name: %10s File size %10d\n", IndentPrinter.printIndents(indent), getFileName(), getFileSize());
    }

    @Override
    public void sortBySize() {

    }

    @Override
    public Long findLargestFile() {
        return getFileSize();
    }

    @Override
    public int compareTo(IFile o) {
        return Long.compare(this.getFileSize(), o.getFileSize());
    }
}
