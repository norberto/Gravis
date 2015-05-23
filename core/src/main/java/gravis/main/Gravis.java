package gravis.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import gravis.handlers.Content;
import gravis.handlers.GameStateManager;
import gravis.handlers.MyInput;
import gravis.handlers.MyInputProcessor;

public class Gravis extends ApplicationAdapter {
    public static final String VERSION = "v1.7";
    public static final String TITLE = "Grav " + VERSION;
    public static final int V_WIDTH = 720;
    public static final int V_HEIGHT = 450;
    public static final int SCALE = 2;

    public static final float STEP = 1 / 60f;
    private float accum;

    protected SpriteBatch sb;
    protected OrthographicCamera cam;
    protected OrthographicCamera hud_cam;

    public SpriteBatch getSpriteBatch() {
        return sb;
    }

    public OrthographicCamera getCamera() {
        return cam;
    }

    public OrthographicCamera getHUDCamera() {
        return hud_cam;
    }

    private GameStateManager gsm;

    public static Content res;

    public void create() {

        Gdx.input.setInputProcessor(new MyInputProcessor());

        res = new Content();
        res.loadTexture("res/images/cat3.png", "cat");
        res.loadTexture("res/images/flag16.png", "rainbow");
        res.loadTexture("res/images/hud.png", "hud");
        res.loadTexture("res/images/bg2.png", "bgs");
        res.loadTexture("res/images/menu_bg.jpg", "menu");
        res.loadTexture("res/images/gameover.png", "gameover");
        res.loadMusic("res/sfx/Game Of Thrones Game Over.mp3", "death_music");
        res.loadMusic("res/sfx/Death.mp3", "death");
        res.getMusic("death").setLooping(false);
        res.loadMusic("res/sfx/Nyan Cat - Ninja.mp3", "music");
        res.loadMusic("res/sfx/8-bit Balloon Kitty.mp3", "menu");
        res.loadSound("res/sfx/Select.wav", "select");
        res.loadSound("res/sfx/jump.wav", "jump");
        res.getSound("jump").setVolume(2 ,0.5f);
        res.getMusic("music").setVolume(0.5f);

        sb = new SpriteBatch();
        cam = new OrthographicCamera();
        cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
        hud_cam = new OrthographicCamera();
        hud_cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
        gsm = new GameStateManager(this);
    }

    public void render() {
        accum += Gdx.graphics.getDeltaTime();
        while (accum >= STEP) {
            accum -= STEP;
            gsm.update(STEP);
            gsm.render();
            MyInput.update();
        }
    }

    public void dispose() {

    }

    public void resize(int w, int h) {
    }

    public void pause() {
    }

    public void resume() {
    }

}