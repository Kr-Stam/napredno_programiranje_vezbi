package av3.cards;

public class PlayingCard {
    private final Suit suit;
    private final int value;
    private boolean isDealt;

    public PlayingCard(Suit suit, int value) {
        this.suit = suit;
        this.value = value;
        this.isDealt = false;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public boolean isDealt() {
        return isDealt;
    }

    public void setDealt(boolean dealt) {
        isDealt = dealt;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PlayingCard{");
        sb.append("suit=").append(suit);
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}
