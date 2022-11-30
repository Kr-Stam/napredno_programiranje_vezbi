package av4.bank;

public class InterestCheckingAccount extends Account implements InterestBearingAccount{

    protected static final int INTEREST_AMOUNT = 3;

    public InterestCheckingAccount(String name, double balance) {
        super(name, balance);
    }

    @Override
    public AccType getType() {
        return AccType.INTEREST;
    }

    @Override
    public void addInterest() {
        addAmount(balance * (double)(100 + INTEREST_AMOUNT) / 100);
    }
}
