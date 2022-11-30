package problems.archiveLocalDate;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ArchiveStoreTest {
    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        LocalDate date = LocalDate.of(2013, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();

            LocalDate dateToOpen = date.atStartOfDay().plusSeconds(days * 24 * 60 * 60).toLocalDate();
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
        while(scanner.hasNext()) {
            int open = scanner.nextInt();
            try {
                store.openItem(open, date);
            } catch(NonExistingItemException e) {
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

    public void archiveItem(Archive archive, LocalDate date) {
        archive.setDateArchived(date);
        archiveList.add(archive);
        String tmp = date.toString().replace("GMT", "UTC");
        logs.add(String.format("Item %d archived at %s", archive.getId(), date));
    }

    public void openItem(int open, LocalDate date) throws NonExistingItemException {
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
    private LocalDate dateArchived;

    public Archive(int id) {
        this.id = id;
    }

    public void setDateArchived(LocalDate dateArchived) {
        this.dateArchived = dateArchived;
    }

    public abstract boolean canOpen(LocalDate date);

    public int getId() {
        return id;
    }

    public abstract String open(LocalDate date);
}

class LockedArchive extends Archive {
    private LocalDate dateToOpen;

    public LockedArchive(int id, LocalDate dateToOpen) {
        super(id);
        this.dateToOpen = dateToOpen;
    }

    @Override
    public boolean canOpen(LocalDate date) {
        return dateToOpen.compareTo(date) <= 0;
    }

    @Override
    public String open(LocalDate date) {
        String tmp = date.toString().replace("GMT", "UTC");
        String tmp1 = dateToOpen.toString().replace("GMT", "UTC");
        if (canOpen(date))
            return String.format("Item %d opened at %s", getId(), date);
        else
            return String.format("Item %d cannot be opened before %s", getId(), dateToOpen);
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
    public boolean canOpen(LocalDate date) {
        if (maxOpen > timesOpened) {
            timesOpened++;
            return true;
        }
        return false;
    }

    @Override
    public String open(LocalDate date) {
        String tmp = date.toString().replace("GMT", "UTC");
        if (canOpen(date))
            return String.format("Item %d opened at %s", getId(), date);
        else
            return String.format("Item %d cannot be opened more than %d times", getId(), maxOpen);
    }
}

class NonExistingItemException extends Exception {
    public NonExistingItemException(String message) {
        super(message);
    }
}
