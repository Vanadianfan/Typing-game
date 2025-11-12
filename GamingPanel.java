// GamingPanel.java
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamingPanel extends JPanel {
    int score = 0;
    int level = 1;
    int leftTime;
    int time = 0;
    final int startingTime = 3600;
    static final int MAX_SCORE = 10000;
    String prompt = "See you again!";
    boolean isPressing = false;
    String pressedKeys = "";
    boolean paused = false;
    boolean showResult = false;

    JButton[] buttons = new JButton[6];
    MyDrawPanel drawPanel = new MyDrawPanel();

    public GamingPanel() {
        setLayout(new BorderLayout());

        String[] s = {"Pause", "Resume", "Help", "Quit"};
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (int i = 0; i < s.length; i++) {
            buttons[i] = new JButton(s[i]);
            buttons[i].setActionCommand(s[i]);
            buttons[i].addActionListener(drawPanel);
            buttons[i].setFocusPainted(false);
            panel.add(buttons[i]);
        }

        this.add(BorderLayout.WEST, panel);
        this.add(BorderLayout.CENTER, drawPanel);
        drawPanel.requestFocusInWindow();
    }

    class MyDrawPanel extends JPanel implements ActionListener {
        double rate = 1.0;
        ArrayList<ColorfulCircle> al;
        javax.swing.Timer renew;

        public MyDrawPanel() {
            setFocusable(true);
            addKeyListener(new MyKeyListener());

            al = new ArrayList<>();
            leftTime = startingTime;
            renew = new javax.swing.Timer(10, this);
            renew.start();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            requestFocusInWindow();
            g.setColor(Color.white);
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.black);
            g.setFont(new Font("SansSerif", Font.BOLD, 23));
            if (paused) g.drawString("Level " + level + "   Score " + score + "    Paused", 50, 33);
            else g.drawString("Level " + level + "   Score " + score, 50, 33);
            g.drawRect(50, 50, getWidth() - 100, getHeight() - 100);

            int new_level = Math.floorDiv(score, 1000) + 1;
            if (new_level > level) {
                level = new_level;
                leftTime = startingTime - level * 100;
            }
            leftTime--;
            time++;
            rate = 1 + (level / 8.0);
            if (!paused) {
                int seconds = leftTime / 100;
                String label = "Time left ";
                String timeStr = String.format("%2d", seconds);
                g.setColor(Color.BLACK);
                g.drawString(label, 313, 33);
                if (seconds < 5) g.setColor(Color.RED);
                else if (seconds < 10) g.setColor(Color.ORANGE);
                else g.setColor(Color.BLACK);
                FontMetrics fm = g.getFontMetrics();
                int labelWidth = fm.stringWidth(label);
                g.drawString(timeStr, 313 + labelWidth, 33);
            }

            Iterator<ColorfulCircle> it = al.iterator();
            while (it.hasNext()) {
                ColorfulCircle c = it.next();
                if (isPressing && pressedKeys.toLowerCase().endsWith(c.word.toLowerCase())) {
                    score += c.score(level);
                    isPressing = false;
                    pressedKeys = "";
                    it.remove();
                    break;
                }
                if (c.isOver()) it.remove();
                c.update(rate);
                c.disp(g, pressedKeys);
            }

            if (leftTime == 0 && !showResult) {
                prompt = "GAME OVER!";
                showResult = true;
            }
            if (score >= MAX_SCORE && !showResult) {
                prompt = "CONGRATULATIONS!";
                showResult = true;
            }
            if (showResult) {
                renew.stop();
                requestFocusInWindow();
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 180));
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setFont(new Font("SansSerif", Font.BOLD, 40));
                if ("GAME OVER!".equals(prompt)) g2d.setColor(Color.LIGHT_GRAY);
                if ("CONGRATULATIONS!".equals(prompt)) g2d.setColor(Color.GREEN);
                if ("See you again!".equals(prompt)) g2d.setColor(Color.YELLOW);
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(prompt);
                int textHeight = fm.getAscent();
                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() + textHeight) / 3;
                g2d.drawString(prompt, x, y);

                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("SansSerif", Font.BOLD, 32));
                g2d.drawString("Level " + level, getWidth() / 2 - 60, getHeight() / 2 + 30);
                if ("CONGRATULATIONS!".equals(prompt))
                    g2d.drawString("Your Time: " + time / 100.0, getWidth() / 2 - 120, getHeight() / 2 + 70);
                else
                    g2d.drawString("Your Score: " + score, getWidth() / 2 - 120, getHeight() / 2 + 70);
                g2d.dispose();
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == renew) {
                if (!paused && al.size() < 5 && getWidth() > 0 && getHeight() > 0)
                    al.add(new ColorfulCircle(getWidth(), getHeight(), 35));
                repaint();
                return;
            }
            switch (e.getActionCommand()) {
                case "Pause" -> {
                    paused = true;
                    renew.stop();
                    repaint();
                }
                case "Resume" -> {
                    paused = false;
                    renew.start();
                }
                case "Help" -> {
                    renew.stop();
                    HelpWindow.show(this);
                    renew.start();
                }
                case "Quit" -> {
                    showResult = true;
                    prompt = "See you again!";
                    repaint();
                    new javax.swing.Timer(3000, evt -> {System.exit(0);}).start();
                }
            }
            repaint();
        }

        public class MyKeyListener extends KeyAdapter {
            @Override
            public void keyTyped(KeyEvent e) {
                if (paused) return;
                char key = e.getKeyChar();
                isPressing = true;
                if (Character.isLetter(key))
                    pressedKeys += Character.toString(key);
                if (pressedKeys.length() > 20)
                    pressedKeys = pressedKeys.substring(pressedKeys.length() - 20);
                repaint();
            }
        }
    }
}