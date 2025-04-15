package logicGui;

public class Card {
    public enum Colors {
        Red, Green, Black, Yellow, Blue
    }

    private final Colors color;
    private Colors forcedColor; // Add this field

    public Card(Colors color) {
        this.color = color;
        this.forcedColor = null; // Initialize to null
    }

    public Colors getColor() {
        return this.color;
    }

    public Colors getForcedColor() {
        return this.forcedColor;
    }

    public void setForcedColor(Colors forcedColor) {
        this.forcedColor = forcedColor;
    }

    public boolean isPlayable(Card lastPlayedCard) {
        // If a forced color is set, only cards of that color or Wild cards are playable
        if (lastPlayedCard.getForcedColor() != null) {
            return this.color == lastPlayedCard.getForcedColor() || this.color == Colors.Black;
        }
        // Otherwise, follow the normal rules
        return lastPlayedCard.getColor() == this.color || this.color == Colors.Black;
    }
}