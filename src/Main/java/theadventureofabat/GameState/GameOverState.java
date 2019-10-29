package theadventureofabat.GameState;

import theadventureofabat.PlatformMapPack.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverState extends GameState{

    private Background bg;
    private int currentChoice = 0;

    private final String[] options = {
            "Back to menu",
            "Quit"
    };

    private Color titleColor;
    private Font titleFont;
    private Font font;

    public GameOverState(GameStateManager gsm) {

        this.gsm = gsm;

        try {
            bg = new Background("/Backgrounds/bgmenu.gif", 1);
            bg.setVector(-0.1, 0);
            titleColor = new Color(128, 0, 0);
            titleFont = new Font("Century Gothic", Font.PLAIN, 28);
            font = new Font("Arial", Font.PLAIN, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void init() {}

    public void update() {
        bg.update();
    }

    public void draw(java.awt.Graphics2D g) {
        bg.draw(g);
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("theadventureofabat.Game Over", 80, 70);
        g.setFont(font);

        for (int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(Color.BLACK);
                g.drawString(">", 118, 140 + i*15);
            } else {
                g.setColor(Color.RED);
            }
            g.drawString(options[i], 129, 140 + i*15);
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
        if (k == KeyEvent.VK_UP) {
            currentChoice--;
            if (currentChoice == -1) {
                currentChoice = options.length -1;
            }
        }
        if (k == KeyEvent.VK_DOWN) {
            currentChoice++;
            if (currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }

    public void keyReleased(int k) {}

}
