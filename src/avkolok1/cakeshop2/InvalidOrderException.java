package avkolok1.cakeshop2;

public class InvalidOrderException extends Exception{

    public InvalidOrderException(int id) {
        super(String.format("The order with id %d has less items than the minimum allowed.", id));
    }

}
