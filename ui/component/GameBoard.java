package ui.component;
import javax.swing.*;
import java.awt.*;

public class GameBoard extends JPanel {
    private final DeckPanel deckPanel;
    private final PlayerPanel playerPanel;

    public GameBoard() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        deckPanel = new DeckPanel();
        playerPanel = new PlayerPanel();

        add(deckPanel, BorderLayout.CENTER);
        add(playerPanel, BorderLayout.SOUTH);
    }

    public DeckPanel getDeckPanel() {
        return deckPanel;
    }

    public PlayerPanel getPlayerPanel() {
        return playerPanel;
    }
}
