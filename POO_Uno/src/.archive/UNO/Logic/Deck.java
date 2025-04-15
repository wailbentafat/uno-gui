package Logic;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private final ArrayList<Card> deck;
    public Deck() {
        this.deck = new ArrayList<Card>(); // initializing the deck with empty arrayList
        this.initializeDeck(); // filling the deck with the cards
        this.shuffleDeck(); // shuffling the deck
    }
    private void initializeDeck() {
        Card.Colors[] colors = Card.Colors.values(); // getting an array of the values of Colors enum this will return [RED,GREEN,BLACK,...]
        ActionCard.Actions[] actions = ActionCard.Actions.values(); // getting an array of the values of the Action enum this will return [SKIP,REVERSE,DRAW_TWO,...]
        for (int i = 0;i < colors.length;i++) { // looping on the colors
            if (colors[i] != Card.Colors.BLACK) {
                deck.add(new ValueCard(0, colors[i])); // adding only one card that hold the value 0 cause every color must have only one card that hold the value of 0
                for (int j = 1; j <= 9; j++) {
                    // looping from 1 to 9 this loop will create two cards on each iteration that hold the value of j because every color must have two Cards that hold the value j
                    deck.add(new ValueCard(j, colors[i]));
                    deck.add(new ValueCard(j, colors[i]));

                }
                for (int j = 0; j <= 2; j++) {
                    // this loop is responsible for creating the action cards in each iteration the loop will create two action card because for each color must be 2 Cards from each action not black
                    deck.add(new ActionCard(actions[j], colors[i]));
                    deck.add(new ActionCard(actions[j], colors[i]));
                }
            } else {
                for (int j = 3; j < actions.length; j++) {
                    // this will create 4 DRAW_FOUR_WILD and 4 WILD
                    deck.add(new ActionCard(actions[j], colors[i]));
                    deck.add(new ActionCard(actions[j], colors[i]));
                    deck.add(new ActionCard(actions[j], colors[i]));
                    deck.add(new ActionCard(actions[j], colors[i]));
                }
            }
        }
    }
    public Card drawCard() {
        return deck.remove(deck.size() - 1); // this function will remove the last element in the deck and return it
    }
    public void shuffleDeck() {
        Collections.shuffle(deck);
    } // this function is responsible for shuffling the deck
    public ArrayList<Card> getDeck() {
        return this.deck;
    } // this is a getter for the deck attribute
    public void addToDeck(Card card) {
        this.deck.add(card);
    } // this function will add a card to the deck
}



