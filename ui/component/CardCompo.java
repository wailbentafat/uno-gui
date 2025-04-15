package ui.component;
import javax.swing.*;
import java.awt.*;

public class CardCompo extends JPanel {
    private final String color;
    private final String value;

    public CardCompo(String color, String value) {
        this.color = color;
        this.value = value;
        setPreferredSize(new Dimension(80, 120));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Background color
        switch (color.toLowerCase()) {
            case "red" -> g.setColor(Color.RED);
            case "green" -> g.setColor(Color.GREEN);
            case "blue" -> g.setColor(Color.BLUE);
            case "yellow" -> g.setColor(Color.YELLOW);
            default -> g.setColor(Color.LIGHT_GRAY); // For wild
        }
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw value
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(value.toUpperCase(), getWidth() / 2 - 10, getHeight() / 2);
    }
}
