package theadventureofabat.GameState;

import theadventureofabat.Entity.Enemies.Slugger;
import theadventureofabat.Entity.*;
import theadventureofabat.GamePanel;
import theadventureofabat.PlatformMapPack.Background;
import theadventureofabat.PlatformMapPack.PlatformMap;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class LevelState extends GameState {

    private PlatformMap platformMap;
    private Background bg;
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Explosion> explosions;
    private HUD hud;       //жизни и взрывы
    private LevelEndObject lvlEnd;

    public LevelState(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }

    public void init() {

        platformMap = new PlatformMap(30);
        platformMap.loadPlatforms("/Tilesets/tileset.gif");
        platformMap.loadMap("/Maps/lv.map");
        platformMap.setPosition(0, 0);
        platformMap.setTween(1);

        bg = new Background("/Backgrounds/bglvl.gif", 0.1);

        player = new Player(platformMap);
        player.setPosition(100, 100);

        lvlEnd = new LevelEndObject(platformMap);
        lvlEnd.setPosition(3050, 200);

        populateEnemies();

        explosions = new ArrayList<>();

        hud = new HUD(player);


    }
    private void reInit(){
        platformMap.setPosition(0, 0);
        platformMap.setTween(1);
        player.setPosition(100, 100);
    }


    public void keyPressed(int k) {
        if(k == KeyEvent.VK_LEFT) player.setLeft(true);
        if(k == KeyEvent.VK_RIGHT) player.setRight(true);
        if(k == KeyEvent.VK_UP) player.setUp(true);
        if(k == KeyEvent.VK_DOWN) player.setDown(true);
        if(k == KeyEvent.VK_W) player.setJumping(true);
        if(k == KeyEvent.VK_E) player.setGliding(true);
        if(k == KeyEvent.VK_R) player.setScratching();
        if(k == KeyEvent.VK_F) player.setFiring();
    }

    public void keyReleased(int k) {
        if(k == KeyEvent.VK_LEFT) player.setLeft(false);
        if(k == KeyEvent.VK_RIGHT) player.setRight(false);
        if(k == KeyEvent.VK_UP) player.setUp(false);
        if(k == KeyEvent.VK_DOWN) player.setDown(false);
        if(k == KeyEvent.VK_W) player.setJumping(false);
        if(k == KeyEvent.VK_E) player.setGliding(false);
    }


    private void populateEnemies() {
        enemies = new ArrayList<>();
        Slugger s;
        Point[] points = new Point[] {
                new Point(600, 100),
                new Point(1100, 100),
                new Point(950, 100),
                new Point(1400, 200),
                new Point(1525, 200),
                new Point(2000, 100),
                new Point(2700, 200)
        };
        for (Point point : points) {
            s = new Slugger(platformMap);
            s.setPosition(point.x, point.y);
            enemies.add(s);
        }

    }


    public void update() {

        if (player.getRectangle().y>=200) {
            int health=player.getHealth()-1;
            if (health <= 0) {
                gsm.setState(GameStateManager.GAMEOVERSTATE);
            } else {
                player.setHealth(health);
                reInit();
            }
        }

        // update player
        player.update();
        platformMap.setPosition(
                GamePanel.WIDTH / 2 - player.getx(),
                GamePanel.HEIGHT / 2 - player.gety()
        );

        // set background
        bg.setPosition(platformMap.getx(), platformMap.gety());

        // attack enemies
        player.checkAttack(enemies);

        // update all enemies
        for(int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();
            if (e.isDead()) {
                enemies.remove(i);
                i--;
                explosions.add(new Explosion(e.getx(), e.gety()));
            }
        }

        // update explosions
        for(int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();
            if(explosions.get(i).shouldRemove()) {
                explosions.remove(i);
                i--;
            }
        }

        if(player.checkLevelEnd(lvlEnd)){
            gsm.setState(GameStateManager.LEVEL2STATE);
        }

        if (player.getDead()) {

            gsm.setState(GameStateManager.GAMEOVERSTATE);

        }

    }


    public void draw(Graphics2D g) {

        // draw bg
        bg.draw(g);

        // draw tilemap
        platformMap.draw(g);

        // draw player
        player.draw(g);

        // draw enemies
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }

        // draw explosions
        for (Explosion explosion : explosions) {
            explosion.setMapPosition(
                    platformMap.getx(), (int) platformMap.gety());
            explosion.draw(g);
        }

        // draw hud
        hud.draw(g);

        lvlEnd.draw(g);

    }


}
