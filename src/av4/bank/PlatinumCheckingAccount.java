package av4.bank;

public class PlatinumCheckingAccount extends InterestCheckingAccount {

    public PlatinumCheckingAccount(String name, double balance) {
        super(name, balance);
    }

    @Override
    public void addInterest() {
        addAmount(balance * (double)(100 + INTEREST_AMOUNT * 2) / 100) ;
    }
}
