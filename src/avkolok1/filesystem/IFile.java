package avkolok1.filesystem;

public interface IFile extends Comparable<IFile> {
    String getFileName();

    long getFileSize();

    String getFileInfo(int indent);

    void sortBySize();

    Long findLargestFile ();
}
