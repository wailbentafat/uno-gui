package logique;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck {
    private final Stack<Card> cards = new Stack<>();

    public Deck() {
        reset();
    }

    public void reset() {
        cards.clear();
        for (Card.Color color : Card.Color.values()) {
            if (color == Card.Color.WILD) continue;

            for (int i = 0; i <= 9; i++) {
                cards.push(new Card(color, Card.Value.values()[i])); // 0-9
                if (i != 0) cards.push(new Card(color, Card.Value.values()[i])); // Duplicates
            }

            cards.push(new Card(color, Card.Value.SKIP));
            cards.push(new Card(color, Card.Value.SKIP));
            cards.push(new Card(color, Card.Value.REVERSE));
            cards.push(new Card(color, Card.Value.REVERSE));
            cards.push(new Card(color, Card.Value.DRAW_TWO));
            cards.push(new Card(color, Card.Value.DRAW_TWO));
        }

        // Add wilds
        for (int i = 0; i < 4; i++) {
            cards.push(new Card(Card.Color.WILD, Card.Value.WILD));
            cards.push(new Card(Card.Color.WILD, Card.Value.WILD_DRAW_FOUR));
        }

        Collections.shuffle(cards);
    }

    public Card draw() {
        if (cards.isEmpty()) reset();
        return cards.pop();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
