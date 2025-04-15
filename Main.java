import javax.swing.*;
import logique.GameController;
import ui.component.GameBoard;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("UNO - Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 600);

            GameBoard gameBoard = new GameBoard();
            frame.setContentPane(gameBoard);
            frame.setVisible(true);

            new GameController(gameBoard);
        });
    }
}
