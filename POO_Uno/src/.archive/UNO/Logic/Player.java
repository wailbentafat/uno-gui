package Logic;
import java.util.ArrayList;

public abstract class Player {
    private final int id; // constant id for each player
    private  ArrayList<Card> hand; // arrayList of Cards represent the hand of the player
    private Card.Colors forcedColor; // attribute that hold the Color that the player ForcedToPlay (example:if a player play wild the next player will force to play a certain color chose by the player who played the wild card)
    public Player(int id) {
        this.id = id;
        this.hand = new ArrayList<>();
        this.forcedColor = null;
    }
    public int getId() {
        return this.id;
    }
    public ArrayList<Card> getHand() {
        return this.hand;
    }
    public Card.Colors getForcedColor() {
        return this.forcedColor;
    }
    public void setForcedColor(Card.Colors forcedColor) {
        this.forcedColor = forcedColor;
    }

    public void drawCard(Deck deck) {
        this.hand.add(deck.drawCard());
    }

    public ArrayList<Card> getPlayableCards(Card lastPlayedCard) { // a function to get all playable cards that a player have based on the lastPlayedCard
        ArrayList<Card> playableCards = new ArrayList<>();
        for (int i = 0; i < this.hand.size(); i++) { // looping on the hand of the player
            if (forcedColor == null) { // checking if the player isNotForced to play a certain Card
                if (this.hand.get(i).isPlayable(lastPlayedCard)) { // if so check if the current Card is playable
                    playableCards.add(this.hand.get(i)); // if the card isPlayable add it to the playableCards
                }
            } else { // else if the player is forced to play a certain color
                if (this.hand.get(i).getColor() == forcedColor) { // check if the current Card have the forced color
                    playableCards.add(this.hand.get(i)); // if so add it to playable cards
                }
            }
        }
        return playableCards;
    }
    public boolean hasPlayableCard(Card lastPlayedCard) {// a function to check if the player have a playableCard
        return !this.getPlayableCards(lastPlayedCard).isEmpty();
    }
    // !!!!!!!!!!!!!!!!!! to me i think that the only difference between humanPlayer and botPlayer is how they choose A card From there Playable Cards example:(The Human choose the card as prompt from the console the bot choose the card randomly from the playableCards that he have ) not how they playTheCard so the playMethod is the same for both !!!!!!!!!!!!!!!!!!!!!
    public abstract Card.Colors chooseColor(); // a function that give me the ability for the user to choose a color (this function is used to make the user choose a color if he play a wildCard);
    public abstract Card chooseCard(Card lastPlayedCard); // a function to choose a card from playableCards differ from bot and human
    public Card play(Card lastPlayedCard) {
        Card choosedCard = chooseCard(lastPlayedCard); // choose A card
        this.hand.remove(choosedCard); // remove it from the hand of the player
        return choosedCard; // return the card choosed to use it in the excuteTurn Function
    }
}