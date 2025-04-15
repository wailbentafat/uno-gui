package ui.component;
import javax.swing.*;
import java.awt.*;

public class DeckPanel extends JPanel {
    private final CardCompo drawPile;
    private CardCompo topDiscard;

    public DeckPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        setBorder(BorderFactory.createTitledBorder("Deck"));

        // Draw pile - generic back
        drawPile = new CardCompo("black", "UNO");
        add(drawPile);

        // Discard pile - initially empty
        topDiscard = new CardCompo("gray", "");
        add(topDiscard);
    }

    public void setTopDiscard(String color, String value) {
        remove(topDiscard);
        topDiscard = new CardCompo(color, value);
        add(topDiscard);
        revalidate();
        repaint();
    }

    public CardCompo getDrawPile() {
        return drawPile;
    }

    public CardCompo getTopDiscard() {
        return topDiscard;
    }
}
