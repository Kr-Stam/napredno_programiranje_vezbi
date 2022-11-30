package av4.bank;

import java.util.Arrays;

public class Bank {
    String name;
    Account[] accounts;

    public Bank(String name, Account[] accounts) {
        this.name = name;
        this.accounts = accounts;
    }

    public double totalAssets() {
        double result = 0;
        for (Account acc : accounts)
            result += acc.getBalance();
        return result;
    }

    public void addInterest() {
        for (Account acc : accounts) {
            if (acc.getType() == AccType.INTEREST)
                ((InterestBearingAccount) acc).addInterest();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Bank{");
        sb.append("name='").append(name).append('\'');
        sb.append("\naccounts=\n");
        Arrays.stream(accounts).forEach(acc ->
        {
            sb.append(acc).append("\n");
        });
        sb.append('}');
        return sb.toString();
    }

    public void addAcc(Account acc) {
        Account[] tmp = new Account[accounts.length + 1];
        tmp[accounts.length] = acc;
    }
}
