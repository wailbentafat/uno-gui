package logique;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final List<Card> hand = new ArrayList<>();
    private final String name;

    public Player(String name) {
        this.name = name;
    }

    public void drawCard(Deck deck) {
        hand.add(deck.draw());
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void removeCard(Card card) {
        hand.remove(card);
    }

    public List<Card> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public boolean hasWon() {
        return hand.isEmpty();
    }
}
