package av2;

public class CombinationLock<T> {
    private int combination;
    private boolean isOpen;
    private T data;

    public CombinationLock(int combination) {
        if (combination / 1000 == 0)
            this.combination = combination;
        else
            this.combination = 123;

        isOpen = false;
    }

    public void open(int combination) {
        if (this.combination == combination)
            isOpen = true;
    }

    public void close() {
        isOpen = false;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void changeCombo(int oldCombo, int newCombo) {
        if (this.combination == oldCombo && newCombo / 1000 == 0)
            this.combination = newCombo;
    }

    public T getData() {
        if (isOpen)
            return data;
        else
            return null;
    }

    public void insertData(T data) {
        if (isOpen)
            this.data = data;
    }
}
