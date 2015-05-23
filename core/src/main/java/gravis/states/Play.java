package gravis.states;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import gravis.entities.HUD;
import gravis.entities.Player;
import gravis.entities.Rainbow;
import gravis.exceptions.FileHandlingException;
import gravis.exceptions.GravisException;
import gravis.exceptions.InvalidFileException;
import gravis.handlers.*;
import gravis.main.Gravis;
import static gravis.handlers.B2DVars.PPM;

/**
 * Created by GreenFigure on 2/19/2015.
 */
public class Play extends GameState {

    private boolean debug = false;
    private Background[] backgrounds;
    private Background gameoversprite;
    private World world;
    private Box2DDebugRenderer b2dr;

    private OrthographicCamera b2dCam;

    private MyContactListener cl;

    private TiledMap tileMap;
    private float tileSize;
    private OrthogonalTiledMapRenderer tmr;

    private Player player;
    private Player player_clone;
    private Array<Rainbow> rainbows;
    private int crystals = 0;
    private HUD hud;
    private TextureRegion go;

    public Play(GameStateManager gsm) {

        super(gsm);
        Gravis.res.getMusic("music").setLooping(true);
        Gravis.res.getMusic("music").play();

        // setup Box2D
        world = new World(new Vector2(0, -9.81f), true);
        cl = new MyContactListener(player);
        world.setContactListener(cl);
        b2dr = new Box2DDebugRenderer();

        //create player
        createPlayer();

        //create hud
        hud = new HUD(player);

        //create tiles
        createTiles();

        //create rainbows
        createRainbows();

        //create background
        Texture bgs = Gravis.res.getTexture("bgs");
        TextureRegion bg = new TextureRegion(bgs, 0, 0, 720, 450);
        backgrounds = new Background[1];
        backgrounds[0] = new Background(bg, cam, 0.1f);

        //create gameover
        Texture gameover = Gravis.res.getTexture("gameover");
        go = new TextureRegion( gameover, 0, -100, 169, 77);
        gameoversprite = new Background(go, cam, 0.1f);

        // set up B2DCam
        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, Gravis.V_WIDTH / PPM, Gravis.V_HEIGHT / PPM);
        //adjust zoom
        //cam.zoom +=2.3;
    }

    public void handleInput() {

        // player jump
        if (MyInput.isPressed(MyInput.BUTTON1)) {
            if (cl.isPlayerOnGround()) {
                player.getBody().applyForceToCenter(0, 200, true);

                Gravis.res.getSound("jump").play();
            }
        }

        if(MyInput.isPressed(MyInput.RELOAD)) {
            gsm.setState(GameStateManager.PLAY);
            Gravis.res.getMusic("death").stop();
            Gravis.res.getMusic("death_music").stop();
            Gravis.res.getMusic("music").play();
        }
        if(MyInput.isPressed(MyInput.BACK)) {
            gsm.setState(GameStateManager.MENU);
            if(Gravis.res.getMusic("death").isPlaying()) {
                Gravis.res.getMusic("death").stop();
                Gravis.res.getMusic("death_music").stop();
            }
            if(Gravis.res.getMusic("music").isPlaying()) Gravis.res.getMusic("music").stop();
        }
    }

    public void update(float dt) {

        handleInput();

        world.step(dt, 6, 2);

        //is player dead?
        if(cl.isPlayerDead()) {
            Gravis.res.getMusic("music").stop();
            Gravis.res.getMusic("death").play();
            Gravis.res.getMusic("death_music").play();
  //          gsm.setState(GameStateManager.MENU);

        }

        //remove crystals
        Array<Body> bodies = cl.getBodiesToRemove();
        for (int i = 0; i < bodies.size; i++) {
            Body b = bodies.get(i);
            rainbows.removeValue((Rainbow) b.getUserData(), true);
            world.destroyBody(b);
            player.collect();
        }
        bodies.clear();
        if(crystals < player.getCrystals()){
            crystals = player.getCrystals();
            if(crystals <= 10) player.getBody().applyLinearImpulse(0.03f * crystals, 0, 0, 0, true);
        }
        player.update(dt);
//        player_clone.update(dt);

        for (int i = 0; i < rainbows.size; i++) {
            rainbows.get(i).update(dt);
        }
        player.updateScore();
        hud.update();

    }

    public void render() {

        // clear screen
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw bgs
        sb.setProjectionMatrix(hud_cam.combined);
        for(int i = 0; i < backgrounds.length; i++) {
            backgrounds[i].render(sb);
        }

        //set cam to follow player box
        cam.position.set(
                player.getPosition().x * PPM + Gravis.V_WIDTH / 4,
                player.getPosition().y * PPM + Gravis.V_HEIGHT / 14,
                0

        );
        cam.update();

        //set Box2D camera to follow player box
        b2dCam.position.set(
                player.getPosition().x + Gravis.V_WIDTH / PPM / 4,
                player.getPosition().y + Gravis.V_HEIGHT / PPM / 14,
                0
        );
        b2dCam.update();


        //draw tile map
        tmr.setView(cam);
        tmr.render();

        //draw player
        sb.setProjectionMatrix(cam.combined);
        player.render(sb);
//        player_clone.render(sb);

        //draw rainbows
        for (int i = 0; i < rainbows.size; i++) {
            rainbows.get(i).render(sb);
        }

        // draw B2d world
        if (debug) {
            b2dr.render(world, b2dCam.combined);
        }
        if(cl.isPlayerDead()){
            sb.setProjectionMatrix(hud_cam.combined);
            gameoversprite.render(sb);
        } else {
            //draw hud
            sb.setProjectionMatrix(hud_cam.combined);
            hud.render(sb);
        }

    }


    public void dispose() {
    }

    public void createPlayer() {

        // create player
        player = createDefaultPlayer(world, 160, 320);
//        player_clone = player.clone();

    }
    public static Player createDefaultPlayer(World world, float x, float y){
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.position.set(x / PPM, y / PPM);
        bdef.type = BodyType.DynamicBody;
        bdef.linearVelocity.set(1, 0);
        Body body = world.createBody(bdef);

        shape.setAsBox(27 / PPM, 16 / PPM, new Vector2(12 / PPM, 0), 0);
        fdef.shape = shape;
        fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
        fdef.filter.maskBits = B2DVars.BIT_CLOUD | B2DVars.BIT_RAINBOW;
        body.createFixture(fdef).setUserData("player");

        // create foot sensor
        shape.setAsBox(27 / PPM, 2 / PPM, new Vector2(12 / PPM, -16 / PPM), 0);
        fdef.shape = shape;
        fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
        fdef.filter.maskBits = B2DVars.BIT_CLOUD;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("foot");

        // create face sensor
        shape.setAsBox(2 / PPM, 5 / PPM, new Vector2(39 / PPM, 5 / PPM), 0);
        fdef.shape = shape;
        fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
        fdef.filter.maskBits = B2DVars.BIT_CLOUD;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("face");

        Player player = new Player(body);
        body.setUserData(player);
        return player;
    }

    public void createTiles() {
        tileMap = new TmxMapLoader().load("res/maps/test2.tmx");
        tmr = new OrthogonalTiledMapRenderer(tileMap);
        TiledMapTileLayer layer = (TiledMapTileLayer) tileMap.getLayers().get("clouds");
        tileSize = layer.getTileWidth();
        createLayer(layer, B2DVars.BIT_CLOUD);
    }

    public void createLayer(TiledMapTileLayer layer, short bits) {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        // go through all cells in the layer
        for (int row = 0; row < layer.getHeight(); row++) {
            for (int col = 0; col < layer.getWidth(); col++) {
                // get cell
                TiledMapTileLayer.Cell cell = layer.getCell(col, row);

                // check if cell exists
                if (cell == null) continue;
                if (cell.getTile() == null) continue;

                // create a body + fixture from cell
                bdef.type = BodyType.StaticBody;
                bdef.position.set((col + 0.5f) * tileSize / PPM, (row + 0.5f) * tileSize / PPM);

                // create shape
                ChainShape cs = new ChainShape();
                Vector2[] v = new Vector2[3];
                v[0] = new Vector2(-tileSize / 2 / PPM, -tileSize / 2 / PPM);
                v[1] = new Vector2(-tileSize / 2 / PPM, tileSize / 2 / PPM);
                v[2] = new Vector2(tileSize / 2 / PPM, tileSize / 2 / PPM);
                cs.createChain(v);
                fdef.friction = 0;
                fdef.shape = cs;
                fdef.filter.categoryBits = bits;
                fdef.filter.maskBits = B2DVars.BIT_PLAYER;
                fdef.isSensor = false;
                world.createBody(bdef).createFixture(fdef);

            }
        }
    }

    public void createRainbows() {
        rainbows = new Array<Rainbow>();

        MapLayer layer = tileMap.getLayers().get("rainbow");

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        for (MapObject mo : layer.getObjects()) {
            bdef.type = BodyType.StaticBody;

            float x = (Float) mo.getProperties().get("x") / PPM;
            float y = (Float) mo.getProperties().get("y") / PPM;

            bdef.position.set(x, y);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(8 / PPM, 5 / PPM, new Vector2(3 / PPM, 0 / PPM), 0);
            fdef.shape = shape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = B2DVars.BIT_RAINBOW;
            fdef.filter.maskBits = B2DVars.BIT_PLAYER;

            Body body = world.createBody(bdef);
            body.createFixture(fdef).setUserData("rainbow");

            Rainbow r = new Rainbow(body);
            rainbows.add(r);

            body.setUserData(r);
        }
    }

}
