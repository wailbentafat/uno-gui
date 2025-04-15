package ui.component;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

import logique.Card;

public class PlayerPanel extends JPanel {
    private final Map<CardCompo, Card> handMap = new LinkedHashMap<>();

    public PlayerPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setBorder(BorderFactory.createTitledBorder("Player Hand"));
    }

    public void addCard(Card card) {
        CardCompo comp = new CardCompo(card.getColor().name().toLowerCase(), card.getValue().name().toLowerCase());
        handMap.put(comp, card);
        add(comp);
        revalidate();
        repaint();
    }

    public void removeCard(CardCompo comp) {
        handMap.remove(comp);
        remove(comp);
        revalidate();
        repaint();
    }

    public Map<CardCompo, Card> getHandMap() {
        return handMap;
    }
}
