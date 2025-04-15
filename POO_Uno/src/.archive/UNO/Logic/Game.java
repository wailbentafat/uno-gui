package Logic;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Game {
    private final ArrayList<Player> players;
    private boolean isReversed;
    private boolean isGameOver;
    private final Deck deck;
    private final ArrayList<Card> discardPile;
    private int currentPlayerIndex;
    private Player winner;
    public Game(int numberOfPlayers,int numberOfHumanPlayers) {
        this.players = new ArrayList<>();
        this.isGameOver = false;
        this.isReversed = false;
        this.deck = new Deck();
        this.discardPile = new ArrayList<>();
        this.winner = null;
        this.initializeGame(numberOfPlayers,numberOfHumanPlayers);
        printGameStart();
    }
    private void distributeCards() {
        System.out.println("#######Distributing cards to players...#######");
        for (Player player : players) {
            for (int j = 0; j < 7; j++) {
                player.drawCard(this.deck);
            }
            System.out.println("#######Player " + player.getId() + " received 7 cards#######");
        }
    }
    private void printGameStart() {
        System.out.println("\n========= UNO GAME STARTED =========");
        System.out.println("Number of players: " + players.size());
        System.out.println("First player: Player " + currentPlayerIndex);
        System.out.println("===================================\n");
    }
    private void initializePlayers(int numberOfHumanPlayers, int numberOfPlayers) {
        for (int i = 0;i < numberOfHumanPlayers;i++) {
            this.players.add(new HumanPlayer(i));
        }
        for (int i = numberOfHumanPlayers;i < numberOfPlayers;i++) {
            this.players.add(new BotPlayer(i));
        }
    }
    private void initializeGame(int numberOfHumanPlayers, int numberOfPlayers) {
        this.initializePlayers(numberOfHumanPlayers,numberOfPlayers);
        this.distributeCards();
        this.currentPlayerIndex = (int) (Math.random() * players.size());
        System.out.println("Drawing first card...");
        Card firstCard = deck.drawCard();
        while (firstCard instanceof ActionCard firstActionCard) {
            if (firstActionCard.getAction() == ActionCard.Actions.DRAW_FOUR_WILD) {
                System.out.println("Drew a Draw Four Wild card - reshuffling...");
                deck.addToDeck(firstCard);
                deck.shuffleDeck();
                firstCard = deck.drawCard();
            } else if (firstActionCard.getAction() == ActionCard.Actions.WILD) {
                System.out.println("First card is Wild - Player " + currentPlayerIndex + " chooses color");
                this.players.get(this.currentPlayerIndex).chooseColor();
                this.moveToNextPlayer();
                break;
            } else {
                System.out.println("First card is an Action card: " + firstActionCard.getAction());
                this.isReversed = true;
                this.moveToNextPlayer();
                this.isReversed = false;
                this.applyCardEffect(firstActionCard);
                if (firstActionCard.getAction() != ActionCard.Actions.REVERSE) {
                    this.moveToNextPlayer();
                }
                break;
            }
        }
        System.out.println("First card: " + cardToString(firstCard) + "\n");
        discardPile.add(firstCard);
    }
    private String cardToString(Card card) {
        if (card instanceof ActionCard) {
            return ((ActionCard) card).getAction() + " (" + card.getColor() + ")";
        } else if (card instanceof ValueCard) {
            return ((ValueCard) card).getValue() + " (" + card.getColor() + ")";
        }
        return card.getColor().toString();
    }
    public void executeTurn() {
        Player currentPlayer = this.getCurrentPlayer();
        System.out.println("\n========= PLAYER " + currentPlayer.getId() + "'s TURN =========");
        System.out.println("Current card: " + cardToString(getLastPlayedCard()));
        if (!currentPlayer.hasPlayableCard(this.getLastPlayedCard())) {
            System.out.println("Player " + currentPlayer.getId() + " has no playable cards - drawing a card");
            currentPlayer.drawCard(this.deck);
            if (!currentPlayer.hasPlayableCard(this.getLastPlayedCard())) {
                System.out.println("Still no playable cards - skipping turn");
                if (currentPlayer.getForcedColor() != null) {
                    this.getNextPlayer().setForcedColor(currentPlayer.getForcedColor());
                }
                this.moveToNextPlayer();
                executeTurn();
            }
            System.out.println("Drew a playable card!");
        }
        Card lastPlayedCard = currentPlayer.play(this.getLastPlayedCard());
        System.out.println("Player " + currentPlayer.getId() + " plays: " + cardToString(lastPlayedCard));

        this.discardPile.add(lastPlayedCard);
        currentPlayer.setForcedColor(null);
        if (currentPlayer.getHand().isEmpty()) {
            this.winner = currentPlayer;
            System.out.println("\n🎉 PLAYER " + this.winner.getId() + " WINS! 🎉");
            this.isGameOver = true;
        } else {
            int cardsLeft = currentPlayer.getHand().size();
            if (cardsLeft == 1) {
                System.out.println("UNO! Player " + currentPlayer.getId() + " has 1 card left!");
            } else {
                System.out.println("Player " + currentPlayer.getId() + " has " + cardsLeft + " cards left");

            }
        }
        if (lastPlayedCard instanceof ActionCard) {
            System.out.println("\nApplying card effect...");
            applyCardEffect((ActionCard) lastPlayedCard);
        }
        this.moveToNextPlayer();
        System.out.println("Next player: Player " + currentPlayerIndex);
    }
    public void applyCardEffect(ActionCard actionCard) {
        switch(actionCard.getAction()) {
            case SKIP:
                System.out.println("Player " + this.getNextPlayer().getId() + " is skipped!");
                this.moveToNextPlayer();
                break;
            case REVERSE:
                this.isReversed = !isReversed;
                System.out.println("Game direction reversed!");

                break;
            case DRAW_TWO:
                Player nextPlayer = this.getNextPlayer();
                nextPlayer.drawCard(this.deck);
                nextPlayer.drawCard(this.deck);
                System.out.println("Player " + nextPlayer.getId() + " draws two cards");
                this.moveToNextPlayer();
                break;
            case DRAW_FOUR_WILD:
                Card.Colors chosenColor = this.getCurrentPlayer().chooseColor();
                System.out.println("Color changed to: " + chosenColor);
                nextPlayer = this.getNextPlayer();
                for (int i = 0;i < 4;i++) {
                    nextPlayer.drawCard(this.deck);
                }
                System.out.println("Player " + nextPlayer.getId() + " draws four cards");
                this.moveToNextPlayer();
                this.getNextPlayer().setForcedColor(chosenColor);
                break;
            case WILD:
                Card.Colors color = this.getCurrentPlayer().chooseColor();
                System.out.println("Color changed to: " + color);
                this.getNextPlayer().setForcedColor(color);
                break;
        }
    }
    private Player getNextPlayer() {
        return this.players.get((this.currentPlayerIndex  + (this.isReversed ? this.players.size() - 1: 1) ) % players.size());
    }
    private void moveToNextPlayer() {
        this.currentPlayerIndex = isReversed ? (this.currentPlayerIndex + this.players.size() - 1) % this.players.size() : (this.currentPlayerIndex + 1) % this.players.size();
    }
    private Player getCurrentPlayer() {
        return this.players.get(this.currentPlayerIndex);
    }
    private Card getLastPlayedCard() {
        return this.discardPile.getLast();
    }
    public boolean isGameOver() {
        return this.isGameOver;
    }
}
