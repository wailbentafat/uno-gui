package GameUno;

import logicGui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BotPlay extends PanelGBLayout {
    private final GameRest game; // Use GameRest instance
    private final JLabel currentCardLabel; // Label to display the middle card
    private final JPanel playerCardPanel; // Panel to display the human player's cards
    private final JButton drawButton; // Button to draw a card
    private final JButton skipButton; // Button to skip a turn
    private JLabel InfoLab; // Label to display player info

    BotPlay(JFrame MainWindow) {
        super(MainWindow);

        // Initialize the game with 2 players (1 human, 1 bot)
        this.game = new GameRest(1, 2);
        setGBC(0, 0, 3, 1, 1, 1);

        // Display the current card in the middle
        this.currentCardLabel = new JLabel();
        this.currentCardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        updateCurrentCardDisplay();
        this.add(currentCardLabel, getGBC());

        // Player card panel (only human player's cards)
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

        // Add "Skip" button
        setGBC(0, 4, 3, 1, 1, 0);
        this.skipButton = new JButton("Skip Turn");
        this.skipButton.setFont(new Font("Arial", Font.BOLD, 14));
        this.skipButton.setBackground(new Color(255, 87, 34)); // Orange color
        this.skipButton.setForeground(Color.WHITE);
        this.skipButton.addActionListener(e -> handleSkipTurn());
        this.add(skipButton, getGBC());

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
                            chosenColor = promptForColor(); // Prompt the user or bot to choose a color
                        }
                        game.playCard(cardIndex, chosenColor);

                        // Check if the game is over after the player's turn
                        if (game.isGameOver()) {
                            handleGameOver();
                            return;
                        }

                        updateCurrentCardDisplay();
                        updatePlayerCardDisplay();
                    } else {
                        JOptionPane.showMessageDialog(
                                BotPlay.this.getMainWindow(),
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

    private void handleSkipTurn() {
        if (!game.isGameOver()) {
            game.moveToNextPlayer();
            updateCurrentCardDisplay();
            updatePlayerCardDisplay();

            // If the next player is a bot, trigger its turn
            if (game.isCurrentPlayerBot()) {
                botTurn();
            }
        }
    }

    private void botTurn() {
        if (!game.isGameOver() && game.isCurrentPlayerBot()) {
            if (!game.hasPlayableCard()) {
                // Bot draws a card if no playable card is available
                game.drawCard();
                Card drawnCard = game.getCurrentPlayer().getHand().get(game.getCurrentPlayer().getHand().size() - 1);

                if (drawnCard.isPlayable(game.getLastPlayedCard())) {
                    // If the drawn card is playable, the bot plays it
                    String chosenColor = null;
                    if (drawnCard instanceof ActionCard actionCard) {
                        if (actionCard.getAction() == ActionCard.Actions.Wild || actionCard.getAction() == ActionCard.Actions.DRAW_4_Wild) {
                            chosenColor = game.getCurrentPlayer().chooseColor().toString();
                            drawnCard.setForcedColor(Card.Colors.valueOf(chosenColor)); // Set the forced color
                        }
                    }
                    game.playCard(game.getCurrentPlayer().getHand().size() - 1, chosenColor);
                } else {
                    // If the drawn card is not playable, the bot skips its turn
                    game.moveToNextPlayer();
                }
            } else {
                // Bot plays a card if it has playable cards
                Card chosenCard = game.getCurrentPlayer().chooseCard(game.getLastPlayedCard());
                String chosenColor = null;

                if (chosenCard instanceof ActionCard actionCard) {
                    if (actionCard.getAction() == ActionCard.Actions.Wild || actionCard.getAction() == ActionCard.Actions.DRAW_4_Wild) {
                        chosenColor = game.getCurrentPlayer().chooseColor().toString();
                        chosenCard.setForcedColor(Card.Colors.valueOf(chosenColor)); // Set the forced color
                    }
                }
                game.playCard(game.getCurrentPlayer().getHand().indexOf(chosenCard), chosenColor);
            }

            // Ensure forcedColor is applied correctly
            Card lastPlayedCard = game.getLastPlayedCard();
            if (lastPlayedCard instanceof ActionCard actionCard) {
                if (actionCard.getAction() == ActionCard.Actions.Wild || actionCard.getAction() == ActionCard.Actions.DRAW_4_Wild) {
                    if (lastPlayedCard.getForcedColor() == null) {
                        // If no color was set, choose a default color
                        String chosenColor = game.getCurrentPlayer().chooseColor().toString();
                        lastPlayedCard.setForcedColor(Card.Colors.valueOf(chosenColor));
                    }
                }
            }

            // Check if the game is over after the bot's turn
            if (game.isGameOver()) {
                handleGameOver();
                return;
            }

            updateCurrentCardDisplay();
            updatePlayerCardDisplay();

            // Display the chosen color if the bot played a Wild card
            if (lastPlayedCard instanceof ActionCard actionCard) {
                if (actionCard.getAction() == ActionCard.Actions.Wild || actionCard.getAction() == ActionCard.Actions.DRAW_4_Wild) {
                    String chosenColor = lastPlayedCard.getForcedColor().toString();
                    JOptionPane.showMessageDialog(
                            this.getMainWindow(),
                            "Bot chose color: " + chosenColor,
                            "Bot's Choice",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        }
    }

    private void OnResizeEv(JScrollPane scrollPane) {
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

    private void handleGameOver() {
        Player winner = game.getWinner();
        JOptionPane.showMessageDialog(
                this.getMainWindow(),
                "Game Over! The winner is Player " + (winner.getId() + 1) + "!",
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Disable buttons after the game ends
        drawButton.setEnabled(false);
        skipButton.setEnabled(false);
    }
}