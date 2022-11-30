package av4.bank;

public class NonInterestCheckingAccount extends Account{
    public NonInterestCheckingAccount(String name, double balance) {
        super(name, balance);
    }

    @Override
    public AccType getType() {
        return AccType.NON_INTEREST;
    }


}
