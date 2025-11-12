// GameLauncher.java
import java.awt.*;
import javax.swing.*;

public class GameLauncher {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Typing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        CardLayout cl = new CardLayout();
        JPanel cards = new JPanel(cl);

        StartPanel startPanel = new StartPanel(cl, cards);
        GamingPanel gamePanel = new GamingPanel();

        cards.add(startPanel, "start");
        cards.add(gamePanel, "game");

        frame.getContentPane().add(cards);
        frame.setVisible(true);
    }
}