//ColorfulCircle.java
import java.awt.*;

public class ColorfulCircle {
    private int sec;
    private final WordLoader easyWords = new WordLoader("Dictionary/dictionary.txt");
    private final WordLoader hardWords = new WordLoader("Dictionary/bonus_dictionary.txt");
    final int border_width = 50;
    String word;
    int w, h, r;
    int x, y, vx, vy;
    private final Color color;
    private final boolean is_bonus;
    public ColorfulCircle(int w, int h, int r) {
        this.w = w; this.h = h; this.r = r;
        sec = 800;  // = 8s
        x = urand(w - (border_width + r + 5) * 2) + border_width + r;
        y = urand(h - (border_width + r + 5) * 2) + border_width + r;
        vx = 1 + (int)(Math.random() * 2);
        vy = 1 + (int)(Math.random() * 2);
        color = new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256));
        is_bonus = ((int)(Math.random() * 9) == 8);
        if (is_bonus) {
            vx += 2; vy += 2; this.r += 30;
            word = hardWords.getRandomWord();
        }
        else word = easyWords.getRandomWord();
    }

    static int urand(int m) {
        return (int)(Math.random() * m);
    }
    public boolean isOver() {
        return sec <= 0;
    }
    private boolean in_border() {
        return x - r >= border_width && x + r <= w - border_width
            && y - r >= border_width && y + r <= h - border_width;
    }

    public void update(double rate) {
        int dx = (int)(vx * rate);
        int dy = (int)(vy * rate);
        dx = Math.min(dx, 8);
        dy = Math.min(dy, 7);
        if (dx == 0 || dy == 0) {dx = 2; dy = 1;}
        x += dx; y += dy;
        while (!in_border()) {
            if (x - r < border_width) {x = 2 * (border_width + r) - x; vx = -vx;}
            if (x + r > w - border_width) {x = 2 * (w - border_width - r) - x; vx = -vx;}
            if (y - r < border_width) {y = 2 * (border_width + r) - y; vy = -vy;}
            if (y + r > h - border_width) {y = 2 * (h - border_width - r) - y; vy = -vy;}
        }
    }

    int score(int level) {
        if (word.equals("byOistsuAYAHARA"))
            return 700;
        if (is_bonus) {
            if (level < 5) return 450;
            else return 600;
        }
        else return 100;
    }
    void disp(Graphics g, String pressedKeys) {
        sec--;  // 10msごとに
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(10));
        if (is_bonus) {
            Color randColor = new Color((int)(Math.random() * 256),  (int)(Math.random() * 256), (int)(Math.random() * 256));
            g2.setColor(randColor);
        }
        else g2.setColor(color);
        g2.drawOval(x - r, y - r, 2 * r, 2 * r);

        String text = word;
        Font font = new Font("SansSerif", Font.BOLD, r / 2);
        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        int textX = x - textWidth / 2;
        int textY = y + textHeight / 2 + r / 5;
        int overlap = 0;
        for (int i = 1; i <= text.length(); i++) {
            if (pressedKeys.endsWith(text.substring(0, i))) {
                overlap = i;
            }
        }
        FontMetrics fm2 = g2.getFontMetrics();
        int xOffset = 0;
        if (overlap > 0) {
            String matched = text.substring(0, overlap);
            g2.setColor(Color.PINK);
            g2.drawString(matched, textX, textY);
            xOffset = fm2.stringWidth(matched);
        }
        if (overlap < text.length()) {
            String remaining = text.substring(overlap);
            g2.setColor(Color.BLACK);
            g2.drawString(remaining, textX + xOffset, textY);
        }

        double leftTime = sec / 100.0;
        g2.setColor(new Color(0, 180, 90));
        if (leftTime < 5) g2.setColor(Color.ORANGE);
        if (leftTime < 3) g2.setColor(Color.RED);
        String timer = String.format("%.1f", leftTime);
        int timerWidth = fm.stringWidth(timer);
        int timerX = x - timerWidth / 2;
        int timerY = y - r + textHeight + 10;
        g2.drawString(timer, timerX, timerY);
    }
}