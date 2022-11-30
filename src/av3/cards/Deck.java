package av3.cards;

import java.util.*;

public class Deck {
    private PlayingCard[] cards;
    private int numDealt;

    public Deck() {
        cards = new PlayingCard[52];
        for(int i = 0; i < Suit.values().length; i++){
            for(int j = 0; j < 13; j++){
                cards[i*13 + j] = new PlayingCard(Suit.values()[i], j + 1);
            }
        }
    }

    public void shuffle(){
        List<PlayingCard> tmp = Arrays.asList(cards);
        Collections.shuffle(tmp);
    }

    public PlayingCard draw(){
        if(numDealt == 52)
            return null;

        Random r = new Random();
        int tmp = r.nextInt(52);

        while(cards[tmp].isDealt())
            tmp = r.nextInt();

        numDealt++;
        cards[tmp].setDealt(true);
        return cards[tmp];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Deck{");
        sb.append("\ncards=\n");
        Arrays.stream(cards).forEach(c -> sb.append(c).append("\n"));
        sb.append('}');
        return sb.toString();
    }
}
