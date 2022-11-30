package av3.cards;

import java.util.Arrays;

public class MultipleDecks {
    Deck[] decks;

    public MultipleDecks(int size) {
        decks = new Deck[size];
        for(int i = 0; i < size; i++){
            decks[i] = new Deck();
        }
    }

    public void shuffle(){
        for(Deck d: decks)
            d.shuffle();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MultipleDecks{");
        sb.append("decks=").append(Arrays.toString(decks));
        sb.append('}');
        return sb.toString();
    }
}
