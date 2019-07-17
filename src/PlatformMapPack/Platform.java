package PlatformMapPack;

import java.awt.image.BufferedImage;

public class Platform {

    private final BufferedImage image;
    private final int type;

    // platform types
    public static final int NORMAL = 0;
    public static final int BLOCKED = 1;

    public Platform(BufferedImage image, int type) {
        this.image = image;
        this.type = type;
    }

    public BufferedImage getImage() {
        return image;
    }
    public int getType() {
        return type;
    }

}
