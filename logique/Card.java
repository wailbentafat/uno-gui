package logique;
public class Card {
    public enum Color {
        RED, GREEN, BLUE, YELLOW, WILD
    }

    public enum Value {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE,
        SKIP, REVERSE, DRAW_TWO,
        WILD, WILD_DRAW_FOUR
    }

    private final Color color;
    private final Value value;

    public Card(Color color, Value value) {
        this.color = color;
        this.value = value;
    }

    public Color getColor() {
        return color;
    }

    public Value getValue() {
        return value;
    }

    public boolean isPlayableOn(Card topCard, Color currentColor) {
        return this.color == Color.WILD ||
               this.color == topCard.getColor() ||
               this.value == topCard.getValue() ||
               this.color == currentColor;
    }

    @Override
    public String toString() {
        return color + " " + value;
    }
}
