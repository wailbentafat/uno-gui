package Logic;

public  class Card {
    public enum Colors {
        RED,GREEN,BLACK,YELLOW,BLUE
    } // enum that hold every possible color that the color attribute can have
    private final Colors color;
    public Card(Colors color) {
        this.color = color;
    }
    public Colors getColor() {
        return this.color;
    } // getter to get the value of the attribute color because the color attr is private
    public  boolean isPlayable(Card lastPlayedCard) {
        return lastPlayedCard.getColor() == this.color || this.color == Colors.BLACK; // check if this card have the same color as the lastPlayedColor or this color have a color of black(wild)(cause wild can be played above any card)
    } // function to check if the current is playable based on the last card introduced in the discardPile
}

