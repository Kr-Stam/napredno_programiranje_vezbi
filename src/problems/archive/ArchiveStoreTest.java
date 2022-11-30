package problems.archive;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ArchiveStoreTest {

    static Calendar c = new Calendar.Builder().build();

    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        Date date = new Date(113, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();
            Date dateToOpen = new Date(date.getTime() + (days * 24 * 60
                    * 60 * 1000));
            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
            store.archiveItem(lockedArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            int maxOpen = scanner.nextInt();
            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        while (scanner.hasNext()) {
            int open = scanner.nextInt();
            try {
                store.openItem(open, date);
            } catch (NonExistingItemException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }
}


class ArchiveStore {
    List<Archive> archiveList;
    List<String> logs;

    public ArchiveStore() {
        this.archiveList = new ArrayList<>();
        this.logs = new ArrayList<>();
    }

    public void archiveItem(Archive archive, Date date) {
        archive.setDateArchived(date);
        archiveList.add(archive);
        String tmp = date.toString().replace("GMT", "UTC");
        logs.add(String.format("Item %d archived at %s", archive.getId(), tmp));
    }

    public void openItem(int open, Date date) throws NonExistingItemException {
        List<Archive> tmp = archiveList.stream().filter(x -> x.getId() == open).collect(Collectors.toList());
        if (tmp.isEmpty())
            throw new NonExistingItemException(String.format("Item with id %d doesn't exist", open));

        logs.add(tmp.get(0).open(date));
    }

    public String getLog() {
        StringBuilder sb = new StringBuilder();
        logs.forEach(x -> sb.append(x).append("\n"));
        return sb.toString();
    }
}

abstract class Archive {
    private int id;
    private Date dateArchived;

    public Archive(int id) {
        this.id = id;
    }

    public void setDateArchived(Date dateArchived) {
        this.dateArchived = dateArchived;
    }

    public abstract boolean canOpen(Date date);

    public int getId() {
        return id;
    }

    public abstract String open(Date date);
}

class LockedArchive extends Archive {
    private Date dateToOpen;

    public LockedArchive(int id, Date dateToOpen) {
        super(id);
        this.dateToOpen = dateToOpen;
    }

    @Override
    public boolean canOpen(Date date) {
        return dateToOpen.getTime() <= date.getTime();
    }

    @Override
    public String open(Date date) {
        String tmp = date.toString().replace("GMT", "UTC");
        String tmp1 = dateToOpen.toString().replace("GMT", "UTC");
        if (canOpen(date))
            return String.format("Item %d opened at %s", getId(), tmp);
        else
            return String.format("Item %d cannot be opened before %s", getId(), tmp1);
    }
}

class SpecialArchive extends Archive {
    int maxOpen;
    int timesOpened;

    public SpecialArchive(int id, int maxOpen) {
        super(id);
        this.maxOpen = maxOpen;
        this.timesOpened = 0;
    }

    @Override
    public boolean canOpen(Date date) {
        if (maxOpen > timesOpened) {
            timesOpened++;
            return true;
        }
        return false;
    }

    @Override
    public String open(Date date) {
        String tmp = date.toString().replace("GMT", "UTC");
        if (canOpen(date))
            return String.format("Item %d opened at %s", getId(), tmp);
        else
            return String.format("Item %d cannot be opened more than %d times", getId(), maxOpen);
    }
}

class NonExistingItemException extends Exception {
    public NonExistingItemException(String message) {
        super(message);
    }
}
