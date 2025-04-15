package ui.component;
import javax.swing.*;

public class TestGameBoard {
    public static void main(String[] args) {
        JFrame frame = new JFrame("UNO GameBoard Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        GameBoard gameBoard = new GameBoard();

        // Simulate a player's hand
        gameBoard.getPlayerPanel().addCard("red", "3");
        gameBoard.getPlayerPanel().addCard("green", "skip");
        gameBoard.getPlayerPanel().addCard("wild", "wild");

        // Simulate discard pile update
        gameBoard.getDeckPanel().setTopDiscard("yellow", "5");

        frame.setContentPane(gameBoard);
        frame.setVisible(true);
    }
}
