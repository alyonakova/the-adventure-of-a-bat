package theadventureofabat.GameState;

public class GameStateManager {

    private final GameState[] gameStates;
    private int currentState;

    public static final int NUMGAMESTATES = 6;
    public static final int MENUSTATE = 0;
    public static final int LEVELSTATE = 1;
    public static final int LEVEL2STATE = 2;
    public static final int GAMEOVERSTATE = 3;
    public static final int HELPSTATE = 4;
    public static final int WINSTATE = 5;

    public GameStateManager() {

        gameStates = new GameState[NUMGAMESTATES];

        currentState = MENUSTATE;
        loadState(currentState);

    }

    private void loadState(int state) {
        if(state == MENUSTATE)
            gameStates[state] = new MenuState(this);
        if(state == LEVELSTATE)
            gameStates[state] = new LevelState(this);
        if(state == LEVEL2STATE)
            gameStates[state] = new Level2State(this);
        if (state == GAMEOVERSTATE)
            gameStates[state] = new GameOverState(this);
        if (state == HELPSTATE)
            gameStates[state] = new HelpState(this);
        if (state == WINSTATE)
            gameStates[state] = new GameWinState(this);
    }

    private void unloadState(int state) {
        gameStates[state] = null;
    }

    public void setState(int state) {
        unloadState(currentState);
        currentState = state;
        loadState(currentState);
    }

    public void update() {
        try {
            gameStates[currentState].update();
        } catch(Exception e) {}
    }

    public void draw(java.awt.Graphics2D g) {
        try {
            gameStates[currentState].draw(g);
        } catch(Exception e) {}
    }

    public void keyPressed(int k) {
        try {
            gameStates[currentState].keyPressed(k);
        }catch (Exception e){}
    }

    public void keyReleased(int k) {
        try {
            gameStates[currentState].keyReleased(k);
        }catch (Exception e){}
    }

}
