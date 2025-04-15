package GameUno;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerTurnWindow extends JPanel {
    public PlayerTurnWindow() {
        // Set up the window
        setSize(300, 200);
        setLayout(new BorderLayout());

        // Add a label with a message
        JLabel messageLabel = new JLabel("It's Player 2's turn!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(messageLabel, BorderLayout.CENTER);

        // Add "Ready" button
        JButton readyButton = new JButton("Ready");
        readyButton.setFont(new Font("Arial", Font.PLAIN, 14));
        add(readyButton, BorderLayout.SOUTH);

        // Add action listener to the button
        readyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
    }

}
