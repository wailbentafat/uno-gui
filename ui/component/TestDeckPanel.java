package ui.component;
import javax.swing.*;

public class TestDeckPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Deck Panel Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        DeckPanel deckPanel = new DeckPanel();

        // Simulate updating discard after 2 seconds
        new javax.swing.Timer(2000, e -> {
            deckPanel.setTopDiscard("red", "7");
        }).start();

        frame.add(deckPanel);
        frame.setVisible(true);
    }
}
