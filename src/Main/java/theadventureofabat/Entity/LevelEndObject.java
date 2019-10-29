package theadventureofabat.Entity;

import theadventureofabat.PlatformMapPack.PlatformMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelEndObject extends MapObject {

    private BufferedImage[] sprites;
    private boolean flinching;
    private long flinchTimer;

    public LevelEndObject(PlatformMap tm) {
        super(tm);

        width = 30;
        height = 30;



        try {

            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/Sprites/LevelEndObj/LevelEndObject.gif"
                    )
            );

            sprites = new BufferedImage[1];
            for(int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(
                        i * width,
                        0,
                        width,
                        height
                );
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(300);


    }

    public void update() {
        if(flinching) {
            long elapsed =
                    (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed > 1000) {
                flinching = false;
            }
        }
    }


    public void setFlinching() {
        this.flinching = true;
        flinchTimer = System.nanoTime();
    }


    public void draw(Graphics2D g) {

        //if(notOnScreen()) return;

        setMapPosition();
        if(flinching) {
            long elapsed =
                    (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed / 100 % 2 == 0) {
                return;
            }
        }

        super.draw(g);

    }


}
