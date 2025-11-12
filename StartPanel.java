// StartPanel.java
import java.awt.*;
import javax.swing.*;

public class StartPanel extends JPanel {
    public StartPanel(CardLayout cl, JPanel container) {
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new GridLayout(1, 3));
        JLabel icon = new JLabel("🎮", SwingConstants.RIGHT);
        icon.setFont(new Font("Serif", Font.PLAIN, 40));
        JLabel text = new JLabel("Typing Game", SwingConstants.CENTER);
        text.setFont(new Font("Serif", Font.BOLD, 33));
        JLabel star = new JLabel("⭐️", SwingConstants.LEFT);
        star.setFont(new Font(" Serif", Font.PLAIN, 40));
        titlePanel.add(icon);
        titlePanel.add(text);
        titlePanel.add(star);
        add(titlePanel, BorderLayout.CENTER);

        JButton startBtn = new JButton("New Game");
        startBtn.setFocusPainted(false);
        startBtn.setFont(new Font("SansSerif", Font.PLAIN, 20));
        startBtn.addActionListener(e -> cl.show(container, "game"));

        JButton helpBtn = new JButton("Rules");
        helpBtn.setFocusPainted(false);
        helpBtn.setFont(new Font("SansSerif", Font.PLAIN, 20));
        helpBtn.addActionListener(e -> HelpWindow.show(this));

        JPanel bottom = new JPanel();
        bottom.add(startBtn);
        bottom.add(helpBtn);
        add(bottom, BorderLayout.SOUTH);
    }
}