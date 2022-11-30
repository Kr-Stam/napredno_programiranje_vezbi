import av2.BigComplex;
import av3.cards.Deck;
import av4.bank.*;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;

public class BufferedReaderVezbi {


    public static void main(String[] args) throws IOException {
;
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(new InterestCheckingAccount("PrvInterest", 10));
        accounts.add(new InterestCheckingAccount("VtorInterest", 10));
        accounts.add(new PlatinumCheckingAccount("PrvPlatinum", 10));
        accounts.add(new NonInterestCheckingAccount("PrvNonInterest", 10));

        Bank b = new Bank("Banka", accounts.toArray(Account[]::new));

        System.out.println(b);
        System.out.println(b.totalAssets());
        b.addInterest();
        System.out.println(b);


    }
}
