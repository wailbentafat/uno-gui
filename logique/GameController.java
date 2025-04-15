package logique;

import ui.component.CardCompo;
import ui.component.GameBoard;
import javax.swing.JOptionPane;

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

      
        Player player = new Player("You");
        Player player2 = new Player("AI");
       players.add(player2);
      for (int i = 0; i < 7; i++) {
      Card card = deck.draw();
      player2.addCard(card);
    
      }

        players.add(player);
        currentPlayerIndex = 0;

        // Draw 7 cards to start
        for (int i = 0; i < 7; i++) {
            Card card = deck.draw();
            player.addCard(card);
            CardCompo comp = createCardComponentWithClick(card); // Updated class name
            gameBoard.getPlayerPanel().getHandMap().put(comp, card);
            gameBoard.getPlayerPanel().add(comp);
        }

        // Flip first card
        topCard = deck.draw();
        currentColor = topCard.getColor();
        gameBoard.getDeckPanel().setTopDiscard(topCard.getColor().name().toLowerCase(), topCard.getValue().name().toLowerCase());

        setupListeners();
    }
    private void advanceToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        System.out.println("Turn: " + players.get(currentPlayerIndex).getName());
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

    private void handleCardClick(CardCompo comp, Card card) {
        Player currentPlayer = players.get(currentPlayerIndex);
    
        if (!currentPlayer.getName().equals("You")) {
            System.out.println("It's not your turn!");
            return;
        }
    
        if (!card.isPlayableOn(topCard, currentColor)) {
            System.out.println("Invalid move: " + card);
            return;
        }
    
        // Play the card
        currentPlayer.removeCard(card);
        gameBoard.getPlayerPanel().removeCard(comp);
    
        topCard = card;
        currentColor = card.getColor(); // TODO: handle WILD
    
        gameBoard.getDeckPanel().setTopDiscard(
            card.getColor().name().toLowerCase(),
            card.getValue().name().toLowerCase()
        );
    
        System.out.println(currentPlayer.getName() + " played: " + card);
    
        if (currentPlayer.hasWon()) {
            JOptionPane.showMessageDialog(null, currentPlayer.getName() + " wins!");
            return;
        }
    
        advanceToNextPlayer(); // advance turn AFTER valid play
    }
    

    private void drawCardForCurrentPlayer() {
        Player player = players.get(currentPlayerIndex);
        Card card = deck.draw();
        player.addCard(card);
    
        CardCompo comp = createCardComponentWithClick(card);
        gameBoard.getPlayerPanel().getHandMap().put(comp, card);
        gameBoard.getPlayerPanel().add(comp);
    
        System.out.println(player.getName() + " drew: " + card);
    }
}
    
