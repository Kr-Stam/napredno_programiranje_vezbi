package avkolok1.mojDDV;

public class InvalidBillException extends Exception {
    public InvalidBillException(int amount) {
        super(String.format("Receipt with amount %d is not allowed to be scanned", amount));
    }
}
