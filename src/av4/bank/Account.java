package av4.bank;

public abstract class Account {
    private String name;
    private static int lastID = 0;
    private int id;
    protected double balance;

    public Account(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.id = ++lastID;
    }

    public Account(Account acc) {
        this.name = acc.name;
        this.balance = acc.balance;
        this.id = acc.id;
    }

    public double getBalance() {
        return balance;
    }

    public void addAmount(double a){
        balance += a;
    }
    public void subAmount(double a){
        balance -= a;
    }

    public String getName() {
        return name;
    }

    public static int getLastID() {
        return lastID;
    }

    public int getId() {
        return id;
    }

    public abstract AccType getType();

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("name='").append(name).append('\'');
        sb.append(", id=").append(id);
        sb.append(", balance=").append(balance);
        sb.append('}');
        return sb.toString();
    }
}

