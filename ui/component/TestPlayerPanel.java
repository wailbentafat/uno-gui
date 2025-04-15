package ui.component;
import javax.swing.*;
import logique.Card;
public class TestPlayerPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Player Hand Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 200);

        PlayerPanel playerPanel = new PlayerPanel();
      // Make sure this import is at the top

playerPanel.addCard(new Card(Card.Color.RED, Card.Value.ONE));
playerPanel.addCard(new Card(Card.Color.GREEN, Card.Value.TWO));
playerPanel.addCard(new Card(Card.Color.BLUE, Card.Value.REVERSE));
playerPanel.addCard(new Card(Card.Color.YELLOW, Card.Value.DRAW_TWO));

        frame.add(playerPanel);
        frame.setVisible(true);
    }
}
