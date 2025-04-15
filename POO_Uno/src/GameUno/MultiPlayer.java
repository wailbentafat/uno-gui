package GameUno;

import logicGui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MultiPlayer extends PanelGBLayout {
    private final GameRest game; // Use GameRest instance
    private final JLabel currentCardLabel; // Label to display the middle card
    private final JPanel playerCardPanel; // Panel to display the current player's cards
    private final JButton drawButton; // Button to draw a card
    private JLabel InfoLab; // Label to display player info

    MultiPlayer(JFrame MainWindow, int PlayersCount) {
        super(MainWindow);

        // Initialize the game with the specified number of players
        this.game = new GameRest(PlayersCount, PlayersCount);
        setGBC(0, 0, 3, 1, 1, 1);

        // Display the current card in the middle
        this.currentCardLabel = new JLabel();
        this.currentCardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        updateCurrentCardDisplay();
        this.add(currentCardLabel, getGBC());

        // Player card panel
        setGBC(0, 1, 3, 1, 1, 1);
        this.playerCardPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        updatePlayerCardDisplay();

        JScrollPane scrollPane = new JScrollPane(playerCardPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(256, 256));
        this.add(scrollPane, getGBC());

        // Player info
        setGBC(0, 2, 3, 1, 1, 0);
        JPanel playerInfoPanel = new JPanel();
        InfoLab = new JLabel("Player: " + (game.getCurrentPlayer().getId() + 1) + " | Cards Remaining: " + game.getCurrentPlayerCards().size());
        playerInfoPanel.add(InfoLab);
        this.add(playerInfoPanel, getGBC());

        // Draw button
        setGBC(0, 3, 3, 1, 1, 0);
        this.drawButton = new JButton("Draw Card");
        this.drawButton.setFont(new Font("Arial", Font.BOLD, 14));
        this.drawButton.setBackground(new Color(34, 193, 195)); // Light blue color
        this.drawButton.setForeground(Color.WHITE);
        this.drawButton.addActionListener(e -> handleDrawCard());
        this.add(drawButton, getGBC());

        // Window resizing
        int childWidth = this.getMainWindow().getWidth() - 100;
        scrollPane.setPreferredSize(new Dimension(childWidth, 256));
        this.getMainWindow().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                OnResizeEv(scrollPane);
            }
        });

        // Start the bot's turn if the current player is a bot
        if (game.isCurrentPlayerBot()) {
            botTurn();
        }
    }

    private void updateCurrentCardDisplay() {
        String lastPlayedCard = game.getLastPlayedCardString();
        this.currentCardLabel.setIcon(this.resizeIcon(
                getDirPath() + "\\Cards\\" + lastPlayedCard + ".jpg",
                178, 256
        ));
    }

    private void updatePlayerCardDisplay() {
        playerCardPanel.removeAll();
        List<String> cards = game.getCurrentPlayerCards();

        for (int i = 0; i < cards.size(); i++) {
            int cardIndex = i;
            JLabel label = new JLabel(this.resizeIcon(
                    getDirPath() + "\\Cards\\" + cards.get(i) + ".jpg",
                    152, 225
            ));

            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (game.hasPlayableCard()) {
                        // Handle Wild and DRAW_4_Wild cards
                        String chosenColor = null;
                        if (cards.get(cardIndex).contains("Wild")) {
                            chosenColor = promptForColor(); // Prompt the user to choose a color
                        }
                        game.playCard(cardIndex, chosenColor);
                        updateCurrentCardDisplay();
                        updatePlayerCardDisplay();
                        InfoLab.setText("Player: " + (game.getCurrentPlayer().getId() + 1) + " | Cards Remaining: " + game.getCurrentPlayerCards().size());
                    } else {
                        JOptionPane.showMessageDialog(
                                MultiPlayer.this.getMainWindow(),
                                "This card is not playable.",
                                "Invalid Move",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                }
            });

            playerCardPanel.add(label);
        }

        this.getMainWindow().revalidate();
        this.getMainWindow().repaint();
    }

    private String promptForColor() {
        Object[] options = {"Red", "Blue", "Green", "Yellow"};
        int choice = JOptionPane.showOptionDialog(
                this.getMainWindow(),
                "Choose a color for the Wild card:",
                "Color Selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice >= 0) {
            return options[choice].toString();
        } else {
            throw new IllegalStateException("No color selected");
        }
    }

    private void handleDrawCard() {
        game.drawCard();
        updatePlayerCardDisplay();
    }

    private void botTurn() {
        if (!game.isGameOver() && game.isCurrentPlayerBot()) {
            game.botPlayTurn();
            updateCurrentCardDisplay();
            updatePlayerCardDisplay();
        }
    }

    private void checkGameOver() {
        if (game.isGameOver()) {
            Player winner = game.getWinner();
            JOptionPane.showMessageDialog(
                    this.getMainWindow(),
                    "Game Over! The winner is Player " + (winner.getId() + 1) + "!",
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE
            );
            // Optionally, you can reset the game or close the window here
        }
    }

    public void OnResizeEv(JScrollPane scrollPane) {
        if (this.getMainWindow() != null) {
            boolean isPanelInFrame = this.getMainWindow().getContentPane().isAncestorOf(this);
            if (isPanelInFrame) {
                int childWidth = this.getMainWindow().getWidth() - 50;
                scrollPane.setPreferredSize(new Dimension(childWidth, 256));
                this.getMainWindow().revalidate();
                this.getMainWindow().repaint();
            }
        }
    }
}