package ui.component;
import javax.swing.*;
import java.awt.*;

public class TestCard {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Card Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        frame.add(new CardCompo("red", "5"));
        frame.add(new CardCompo("green", "reverse"));
        frame.add(new CardCompo("wild", "wild"));

        frame.pack();
        frame.setVisible(true);
    }
}
