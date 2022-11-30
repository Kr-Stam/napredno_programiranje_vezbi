//package av9.filesystem;
//
//import java.time.LocalDateTime;
//import java.util.Comparator;
//
//public class File implements Comparable<File>{
//    String name;
//    int size;
//    LocalDateTime created;
//    char folder;
//
//    public File(String name, int size, LocalDateTime created, char folder) {
//        this.name = name;
//        this.size = size;
//        this.created = created;
//        this.folder = folder;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public int getSize() {
//        return size;
//    }
//
//    public LocalDateTime getCreated() {
//        return created;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%10s %5dB %s", name, size, created);
//    }
//
//    @Override
//    public int compareTo(File o) {
//        return Comparator.comparing(File::getCreated).thenComparing(File::getName).thenComparing(File::getSize).compare(this, o);
//    }
//
//    public char getFolder() {
//        return folder;
//    }
//
//    public String getMonthAndDay(){
//        return String.format("%s-%d", created.getMonth().toString(), created.getDayOfMonth());
//    }
//}
