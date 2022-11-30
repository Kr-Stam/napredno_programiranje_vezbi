//package av9.filesystem;
//
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//public class FileSystem {
//    List<File> files;
//
//    public FileSystem() {
//        this.files = new ArrayList<>();
//    }
//
//    public void addFile(char folder, String name, int size, LocalDateTime date) {
//        files.add(new File(name, size, date, folder));
//    }
//
//    public List<File> findAllHiddenFilesWithSizeLessThen(int size) {
//        return files.stream().filter(x -> x.getFolder() == '.' && x.getSize() < size).sorted().collect(Collectors.toList());
//    }
//
//    public int totalSizeOfFilesFromFolders(List<Character> collect) {
//        return files.stream().filter(collect::contains).mapToInt(File::getSize).sum();
//    }
//
//    public Map<Integer, Set<File>> byYear() {
//        return files.stream().
//                collect(Collectors.groupingBy(
//                        file -> file.getCreated().getYear(),
//                        TreeMap::new,
//                        Collectors.toCollection(TreeSet::new)));
//    }
//
//    public Map<String, Long> sizeByMonthAndDay() {
//        return files.stream().
//                collect(Collectors.groupingBy(
//                        File::getMonthAndDay,
//                        Collectors.summingLong(File::getSize)
//                ));
//    }
//
//    private final Comparator<File> baseComparator = Comparator.comparing(File::getSize).thenComparing(File::getName).thenComparing(File::getCreated);
//    private final Comparator<File> byYearComparator = Comparator.comparing(File::getCreated).thenComparing(File::getName).thenComparing(File::getSize);
//}
