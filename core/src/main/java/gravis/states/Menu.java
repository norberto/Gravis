package gravis.states;
import static gravis.handlers.B2DVars.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import gravis.entities.B2DSprite;
import gravis.handlers.*;
import gravis.main.Gravis;

/**
 * Created by GreenFigure on 4/30/2015.
 */

public class Menu extends GameState {

    private boolean debug = false;

    private Background bg;
    private Animation animation;
    private GameButton playButton;
    private GameButton exitButton;
    private int Counter = 0;
    private int scrollSpeed = 20;
    private int scrollDirection = -1; // 1 = Rigth, -1 = Left

    private World world;
    private Box2DDebugRenderer b2dRenderer;

    private Array<B2DSprite> blocks;

    public Menu(GameStateManager gsm) {

        super(gsm);
        Gravis.res.getMusic("menu").setLooping(true);
        Gravis.res.getMusic("menu").play();
        //set Menu bg
        Texture tex = Gravis.res.getTexture("menu");
        bg = new Background(new TextureRegion(tex), cam, 1f);
        bg.setVector(scrollSpeed * scrollDirection, 0);
        //set Cat
        tex = Gravis.res.getTexture("cat");
        TextureRegion[] reg = new TextureRegion[4];
        for(int i = 0; i < reg.length; i++) {
            reg[i] = new TextureRegion(tex, i * 80, 0, 80, 32);
        }
        animation = new Animation(reg, 1 / 12f);

        tex = Gravis.res.getTexture("hud");
        playButton = new GameButton(new TextureRegion(tex, 0, 120, 60, 30), Gravis.V_WIDTH / 2 , Gravis.V_HEIGHT / 2, cam);
        //exitButton = new GameButton();

        cam.setToOrtho(false, Gravis.V_WIDTH, Gravis.V_HEIGHT);

        world = new World(new Vector2(0, -9.8f * 5), true);
        b2dRenderer = new Box2DDebugRenderer();

        //createTitleBodies();

    }

 /*   private void createTitleBodies() {

        // top platform
        BodyDef tpbdef = new BodyDef();
        tpbdef.type = BodyType.StaticBody;
        tpbdef.position.set(160 / PPM, 180 / PPM);
        Body tpbody = world.createBody(tpbdef);
        PolygonShape tpshape = new PolygonShape();
        tpshape.setAsBox(120 / PPM, 1 / PPM);
        FixtureDef tpfdef = new FixtureDef();
        tpfdef.shape = tpshape;
        tpfdef.filter.categoryBits = B2DVars.BIT_TOP_PLATFORM;
        tpfdef.filter.maskBits = B2DVars.BIT_TOP_BLOCK;
        tpbody.createFixture(tpfdef);
        tpshape.dispose();

        // bottom platform
        BodyDef bpbdef = new BodyDef();
        bpbdef.type = BodyType.StaticBody;
        bpbdef.position.set(160 / PPM, 130 / PPM);
        Body bpbody = world.createBody(bpbdef);
        PolygonShape bpshape = new PolygonShape();
        bpshape.setAsBox(120 / PPM, 1 / PPM);
        FixtureDef bpfdef = new FixtureDef();
        bpfdef.shape = bpshape;
        bpfdef.filter.categoryBits = B2DVars.BIT_BOTTOM_PLATFORM;
        bpfdef.filter.maskBits = B2DVars.BIT_BOTTOM_BLOCK;
        bpbody.createFixture(bpfdef);
        bpshape.dispose();

        Texture tex = Gravis.res.getTexture("hud");
        TextureRegion[] blockSprites = new TextureRegion[3];
        for(int i = 0; i < blockSprites.length; i++) {
            blockSprites[i] = new TextureRegion(tex, 58 + i * 5, 34, 5, 5);
        }
        blocks = new Array<B2DSprite>();


        // top blocks
        for(int row = 0; row < 5; row++) {
            for(int col = 0; col < 29; col++) {
                BodyDef tbbdef = new BodyDef();
                tbbdef.type = BodyDef.BodyType.DynamicBody;
                tbbdef.fixedRotation = true;
                tbbdef.position.set((62 + col * 6 + col) / PPM, (270 - row * 6  + row) / PPM);
                Body tbbody = world.createBody(tbbdef);
                PolygonShape tbshape = new PolygonShape();
                tbshape.setAsBox(2f / PPM, 2f / PPM);
                FixtureDef tbfdef = new FixtureDef();
                tbfdef.shape = tbshape;
                tbfdef.filter.categoryBits = B2DVars.BIT_TOP_BLOCK;
                tbfdef.filter.maskBits = B2DVars.BIT_TOP_PLATFORM | B2DVars.BIT_TOP_BLOCK;
                tbbody.createFixture(tbfdef);
                tbshape.dispose();
            }
        }

        // bottom blocks
        for(int row = 0; row < 5; row++) {
            for(int col = 0; col < 29; col++) {
                BodyDef bbbdef = new BodyDef();
                bbbdef.type = BodyType.DynamicBody;
                bbbdef.fixedRotation = true;
                bbbdef.position.set((62 + col * 6 + col) / PPM, (270 - row * 6 + row) / PPM);
                Body bbbody = world.createBody(bbbdef);
                PolygonShape bbshape = new PolygonShape();
                bbshape.setAsBox(2f / PPM, 2f / PPM);
                FixtureDef bbfdef = new FixtureDef();
                bbfdef.shape = bbshape;
                bbfdef.filter.categoryBits = B2DVars.BIT_BOTTOM_BLOCK;
                bbfdef.filter.maskBits = B2DVars.BIT_BOTTOM_PLATFORM | B2DVars.BIT_BOTTOM_BLOCK;
                bbbody.createFixture(bbfdef);
                bbshape.dispose();
            }
        }

    }*/ //title body

    public void handleInput() {

        // mouse/touch input
        if(playButton.isClicked()) {
            Gravis.res.getSound("select").play();
            Gravis.res.getMusic("menu").stop();
            gsm.setState(GameStateManager.PLAY);
        }

    }

    public void update(float dt) {

        handleInput();

        world.step(dt / 5, 8, 3);

        bg.update(dt);
        animation.update(dt);

        playButton.update(dt);

    }

    public void render() {
        Counter += scrollSpeed;

        if(Counter >= 3600){
            if(scrollDirection == 1) scrollDirection = -1;
            else scrollDirection = 1;
            Counter = 0;
            bg.setVector(scrollDirection * scrollSpeed, 0);
        }

        sb.setProjectionMatrix(cam.combined);

        // draw background
        bg.render(sb);

        // draw button
        playButton.render(sb);

        // draw cat
        sb.begin();
        sb.draw(animation.getFrame(), 146, 31);
        sb.end();

        // debug draw box2d
        if(debug) {
            cam.setToOrtho(false, Gravis.V_WIDTH / PPM, Gravis.V_HEIGHT / PPM);
            b2dRenderer.render(world, cam.combined);
            cam.setToOrtho(false, Gravis.V_WIDTH, Gravis.V_HEIGHT);
        }
    }

    public void dispose() {
    }

}
