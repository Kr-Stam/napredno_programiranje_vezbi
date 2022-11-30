package avkolok1.filesystem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Folder extends File implements IFile {
    private List<IFile> files;

    public Folder(String name, long size) {
        super(name, size);
        this.files = new ArrayList<>();
    }

    public Folder(String name) {
        super(name, 0L);
        this.files = new ArrayList<>();
    }

    public void addFile(IFile file) throws FileNameExistsException {
        if (hasFileName(file.getFileName()))
            throw new FileNameExistsException(file.getFileName(), getFileName());

        files.add(file);
    }

    private boolean hasFileName(String name) {
        return files.stream().map(IFile::getFileName).anyMatch(name1 -> name1.equals(name));
    }

    @Override
    public long getFileSize() {
        return files.stream().mapToLong(IFile::getFileSize).sum();
    }

    @Override
    public String getFileInfo(int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%sFolder name: %10s Folder size: %10d\n", IndentPrinter.printIndents(indent), getFileName(), getFileSize()));
        files.forEach(file -> sb.append(file.getFileInfo(indent + 1)));

        return sb.toString();
    }

    @Override
    public void sortBySize() {
        files.sort(Comparator.naturalOrder());
        files.forEach(IFile::sortBySize);
    }

    @Override
    public Long findLargestFile() {
        if (!files.isEmpty())
            return files.stream().mapToLong(IFile::findLargestFile).max().getAsLong();

        return 0L;
    }
}
