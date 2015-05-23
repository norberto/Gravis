package gravis.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gravis.handlers.GameStateManager;
import gravis.main.Gravis;

/**
 * Created by GreenFigure on 2/19/2015.
 */
public abstract class GameState {
    protected GameStateManager gsm;
    protected Gravis game;

    protected SpriteBatch sb;
    protected OrthographicCamera cam;
    protected OrthographicCamera hud_cam;

    protected GameState(GameStateManager gsm) {
        this.gsm = gsm;
        game = gsm.game();
        sb = game.getSpriteBatch();
        cam = game.getCamera();
        hud_cam = game.getHUDCamera();
    }

    public abstract void handleInput();

    public abstract void update(float dt);

    public abstract void render();

    public abstract void dispose();
}


