package logique;

import ui.component.CardCompo;
import ui.component.GameBoard;
import javax.swing.JOptionPane;
import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private final GameBoard gameBoard;
    private final Deck deck;
    private final List<Player> players;
    private int currentPlayerIndex;
    private Card topCard;
    private Card.Color currentColor;

    public GameController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.deck = new Deck();
        this.players = new ArrayList<>();

        // Setup the players
        Player player = new Player("You");
        Player player2 = new Player("AI");
        players.add(player2);
        for (int i = 0; i < 7; i++) {
            Card card = deck.draw();
            player2.addCard(card);
        }

        players.add(player);
        currentPlayerIndex = 0;  // Human player starts first

        // Draw 7 cards to start for human player
        for (int i = 0; i < 7; i++) {
            Card card = deck.draw();
            player.addCard(card);
            CardCompo comp = createCardComponentWithClick(card);
            gameBoard.getPlayerPanel().getHandMap().put(comp, card);
            gameBoard.getPlayerPanel().add(comp);
        }

        // Flip first card to start the game
        topCard = deck.draw();
        currentColor = topCard.getColor();
        gameBoard.getDeckPanel().setTopDiscard(
            topCard.getColor().name().toLowerCase(),
            topCard.getValue().name().toLowerCase()
        );

        setupListeners();
    }

    private void advanceToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        System.out.println("Turn: " + players.get(currentPlayerIndex).getName());

        // Auto-run AI turn if it's AI's turn
        if (players.get(currentPlayerIndex).getName().equals("AI")) {
            playAITurn();
        }
    }

    private void setupListeners() {
        // Draw pile click
        gameBoard.getDeckPanel().getDrawPile().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                drawCardForCurrentPlayer();
            }
        });

        // Card click listeners for hand (already implemented)
    }

    private CardCompo createCardComponentWithClick(Card card) {
        // Create card component using the correct class name
        CardCompo comp = new CardCompo(card.getColor().name().toLowerCase(), card.getValue().name().toLowerCase());

        // Add mouse click listener for the card component
        comp.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                handleCardClick(comp, card);
            }
        });

        return comp;
    }

    private void playAITurn() {
        Player ai = players.get(currentPlayerIndex);
        System.out.println("AI's turn...");

        // Step 1: Try to play a valid card from hand
        for (Card card : ai.getHand()) {
            if (card.isPlayableOn(topCard, currentColor)) {
                topCard = card;
                currentColor = card.getColor(); // Handle WILD if necessary

                ai.removeCard(card);
                gameBoard.getDeckPanel().setTopDiscard(
                    card.getColor().name().toLowerCase(),
                    card.getValue().name().toLowerCase()
                );

                System.out.println("AI played: " + card);

                if (ai.hasWon()) {
                    JOptionPane.showMessageDialog(null, ai.getName() + " wins!");
                    return;
                }

                // After AI play, proceed to next player
                advanceToNextPlayer();
                return;
            }
        }

        // Step 2: No valid card, AI draws a card
        Card drawn = deck.draw();
        ai.addCard(drawn);
        System.out.println("AI drew a card.");

        if (drawn.isPlayableOn(topCard, currentColor)) {
            topCard = drawn;
            currentColor = drawn.getColor();

            ai.removeCard(drawn);
            gameBoard.getDeckPanel().setTopDiscard(
                drawn.getColor().name().toLowerCase(),
                drawn.getValue().name().toLowerCase()
            );

            System.out.println("AI played drawn card: " + drawn);

            if (ai.hasWon()) {
                JOptionPane.showMessageDialog(null, ai.getName() + " wins!");
                return;
            }
        }

        // After AI draws a card, proceed to next player
        advanceToNextPlayer();
    }

    private void handleCardClick(CardCompo comp, Card card) {
        Player currentPlayer = players.get(currentPlayerIndex);

        // Allow action only if it's the human player's turn
        if (!currentPlayer.getName().equals("You")) {
            System.out.println("It's not your turn!");
            return;
        }

        // Ensure the card is playable
        if (!card.isPlayableOn(topCard, currentColor)) {
            System.out.println("Invalid move: " + card);
            return;
        }

        // Play the card
        currentPlayer.removeCard(card);
        gameBoard.getPlayerPanel().removeCard(comp);

        topCard = card;
        currentColor = card.getColor(); // Handle WILD if needed

        gameBoard.getDeckPanel().setTopDiscard(
            card.getColor().name().toLowerCase(),
            card.getValue().name().toLowerCase()
        );

        System.out.println(currentPlayer.getName() + " played: " + card);

        if (currentPlayer.hasWon()) {
            JOptionPane.showMessageDialog(null, currentPlayer.getName() + " wins!");
            return;
        }

        // After valid play, proceed to next player
        advanceToNextPlayer();
    }

    private void drawCardForCurrentPlayer() {
        Player currentPlayer = players.get(currentPlayerIndex);

        // Allow action only if it's the human player's turn
        if (!currentPlayer.getName().equals("You")) {
            System.out.println("It's not your turn!");
            return;
        }

        // Draw card for the current player
        Card card = deck.draw();
        currentPlayer.addCard(card);

        // Update the UI with the drawn card
        CardCompo comp = createCardComponentWithClick(card);
        gameBoard.getPlayerPanel().getHandMap().put(comp, card);
        gameBoard.getPlayerPanel().add(comp);

        System.out.println(currentPlayer.getName() + " drew: " + card);
    }
}
