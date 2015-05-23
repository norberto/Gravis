package gravis.handlers;

import gravis.main.Gravis;
import gravis.states.GameState;
import gravis.states.Menu;
import gravis.states.Play;

import java.util.Stack;

/**
 * Created by GreenFigure on 2/19/2015.
 */
public class GameStateManager {
    private Gravis game;
    private Stack<GameState> gameStates;

    public static final int MENU = 83774392;
    public static final int PLAY = 388031654;
    public static final int LEVEL_SELECT = -9238732;

    public GameStateManager(Gravis game) {
        this.game = game;
        gameStates = new Stack<GameState>();
        pushState(MENU);
    }

    public Gravis game() {
        return game;
    }

    public void update(float dt) {
        gameStates.peek().update(dt);
    }

    public void render() {
        gameStates.peek().render();
    }

    private GameState getState(int state) {
        if(state == MENU) return new Menu(this);
        if(state == PLAY) return new Play(this);
        return null;
    }

    public void setState(int state) {
        popState();
        pushState(state);
    }

    public void pushState(int state) {
        gameStates.push(getState(state));
    }

    public void popState() {
        GameState g = gameStates.pop();
        g.dispose();
    }
}
