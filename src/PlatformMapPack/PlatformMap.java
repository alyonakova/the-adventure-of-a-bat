package PlatformMapPack;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;

public class PlatformMap {

    //position
    private double x;
    private double y;

    //bounds
    private int xmin;
    private int ymin;
    private int xmax;
    private int ymax;

    private double tween;

    //map
    private int[][] map;
    private final int platformSize;
    private int numRows;
    private int numCols;
    private int width;
    private int height;

    private BufferedImage platformSet;
    private int numPlatformsAcross;
    private Platform[][] platforms;

    //drawing
    private int rowOffset;
    private int colOffset;
    private final int numRowsToDraw;
    private final int numColsToDraw;

    public PlatformMap(int platformSize) {
        this.platformSize = platformSize;
        numRowsToDraw = GamePanel.HEIGHT / platformSize + 2;
        numColsToDraw = GamePanel.WIDTH / platformSize + 2;
        tween = 0.07;

    }


    public void loadPlatforms(String s) {

        try {

           platformSet = ImageIO.read(                        // картинка платформ
            getClass().getResourceAsStream(s)
           );
           numPlatformsAcross = platformSet.getWidth() / platformSize;  // кол-во платформ горизонтально
           platforms = new Platform[2][numPlatformsAcross];   // массив 2 строки и | столбцов
           BufferedImage subimage;
           for (int col = 0; col < numPlatformsAcross; col++) {
                 subimage = platformSet.getSubimage(
                col * platformSize,
                0,
                         platformSize,
                         platformSize
                );
           platforms[0][col] = new Platform(subimage, PlatformMapPack.Platform.NORMAL);
           subimage = platformSet.getSubimage(
                col * platformSize,
                   platformSize,
                   platformSize,
                   platformSize
           );
           platforms[1][col] = new Platform(subimage, PlatformMapPack.Platform.BLOCKED);
           }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadMap(String s) {

        try {

            InputStream in = getClass().getResourceAsStream(s);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            numCols = Integer.parseInt(br.readLine());   // 107 колонок
            numRows = Integer.parseInt(br.readLine());   // 8 строк
            map = new int[numRows][numCols];         // вся карта
            width = numCols * platformSize;
            height = numRows * platformSize;

            xmin = GamePanel.WIDTH - width;
            xmax = 0;
            ymin = GamePanel.HEIGHT - height;
            ymax = 0;

            String delims = "\\s+";
            for (int row = 0; row < numRows; row++) {
                String line = br.readLine();
                String[] tokens = line.split(delims);         // в массив записали чиселки из строки
                for (int col = 0; col < numCols; col++) {
                    map[row][col] = Integer.parseInt(tokens[col]);    // записали все числа из файла в двумерный массив
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getPlatformSize() {
        return platformSize;
    }

    public int getx() {
        return  (int)x;
    }

    public int gety() {
        return (int)y;
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getType(int row, int col) {
        int rc = map[row][col];
        int r = rc / numPlatformsAcross;
        int c = rc % numPlatformsAcross;
        return platforms[r][c].getType();
    }

    public void setTween(double d) {
        tween = d;
    }

    public void setPosition(double x, double y) {

        this.x += (x - this.x) * tween;
        this.y += (y - this.y) * tween;

        fixBounds();

        colOffset = (int)-this.x / platformSize;
        rowOffset = (int)-this.y / platformSize;

    }

    private void fixBounds() {
        if(x < xmin) x = xmin;
        if(y < ymin) y = ymin;
        if(x > xmax) x = xmax;
        if(y > ymax) y = ymax;
    }

    public void draw(Graphics2D g) {
        for (int row = rowOffset; row < rowOffset+numRowsToDraw; row++) {
            if (row >= numRows) break;

            for (int col = colOffset; col < colOffset+numColsToDraw; col++) {
                if(col >= numCols) break;
                if(map[row][col] == 0) continue;
                int rc = map[row][col];
                int r = rc / numPlatformsAcross;
                int c = rc % numPlatformsAcross;
                g.drawImage(
                        platforms[r][c].getImage(),
                        (int)x + col * platformSize,
                        (int)y + row * platformSize,
                        null
                );

            }
        }
    }
}
