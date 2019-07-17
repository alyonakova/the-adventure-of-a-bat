package Main;

import javax.swing.*;

public class Game {

    public static void main(String[] args) {
        JFrame window = new JFrame("The adventure of a Bat");
        window.setContentPane(new GamePanel());
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }

}
