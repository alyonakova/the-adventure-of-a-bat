package theadventureofabat.GameState;

import theadventureofabat.PlatformMapPack.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class HelpState extends GameState {

    private Background bg;
    private int currentChoice = 0;

    private final String[] options = {
            "Back to menu",
            "Quit"
    };

    private Color titleColor;
    private Font titleFont;
    private Font font;

    public HelpState(GameStateManager gsm) {
        this.gsm = gsm;
        try {
            bg = new Background("/Backgrounds/bgmenu.gif", 1);
            bg.setVector(-0.1, 0);
            titleColor = new Color(128, 0, 0);
            titleFont = new Font("Century Gothic", Font.PLAIN, 24);
            font = new Font("Arial", Font.PLAIN, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() {
    }

    public void update() {
        bg.update();
    }

    public void draw(java.awt.Graphics2D g) {
        bg.draw(g);
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("Help", 134, 46);
        g.setFont(font);
        g.drawString("\u21e8 - move right", 65, 100);
        g.drawString("\u21e6 - move left", 170, 100);
        g.drawString("W - jump", 65, 120);
        g.drawString("E - glide", 170, 120);
        g.drawString("R - scratch", 65, 140);
        g.drawString("F - fire", 170, 140);
        for (int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(Color.BLACK);
                g.drawString(">", 50 + i * 110, 196);
            } else {
                g.setColor(Color.RED);
            }
            g.drawString(options[i], 65 + i * 110, 196);
        }
    }

    private void select() {
        if (currentChoice == 0) {
            gsm.setState(GameStateManager.MENUSTATE);
        }
        if (currentChoice == 1) {
            System.exit(0);
        }
    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            select();
        }
        if (k == KeyEvent.VK_LEFT) {
            currentChoice--;
            if (currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        if (k == KeyEvent.VK_RIGHT) {
            currentChoice++;
            if (currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }

    public void keyReleased(int k) {
    }


}
