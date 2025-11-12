import java.awt.*;
import javax.swing.*;

public class HelpWindow {
    static String message = """

        🎮 Typing Game - by Oitsu Ayahara
        Rule Instructions:

        - Type the word shown inside the circle (case-insensitive) to remove it and earn points.
        - Levels 1-4: Normal circle gives 100 points, bonus circle gives 450 points.
        - Levels 5-10: Normal circle gives 100 points, bonus circle gives 600 points.
        - The higher the level, the less time you have — be faster!
        - Reach 10,000 points to win!

        Have fun!
        """.formatted(GamingPanel.MAX_SCORE);

    public static void show(Component parentComponent) {
        JOptionPane.showMessageDialog(
            parentComponent,
            message,
            "Help - Game rules",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}