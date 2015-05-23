package gravis.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import gravis.handlers.MyTime;
import gravis.main.Gravis;
import gravis.states.Play;

import java.sql.Time;

import static gravis.handlers.B2DVars.PPM;

/**
 * Created by GreenFigure on 4/10/2015.
 */
public class Player extends B2DSprite {

    private int crystals = 0;
    private int score = 0;
    private MyTime time;

    public Player(Body body) {
        super(body);

        time = new  MyTime();

        Texture tex = Gravis.res.getTexture("cat");
        TextureRegion[] sprites = TextureRegion.split(tex, 80, 32)[0];
        setAnimation(sprites, 1 / 12f);

    }

    public void collect() {
        crystals++;
        score += 10;
    }

    public void collect(int i){
        crystals += i;
        score += i * 10;
    }

    public int getScore() {
        return score;
    }

    public void updateScore(){
        if(time.timer()) score += 1;
    }

    public int getCrystals() {
        return crystals;
    }

    public String toString(){
        String ss;
        Body body = getBody();
        ss = "pos [" + body.getPosition().x + " " + body.getPosition().y + "] score: " + score;
        return ss;
    }

    public Player clone(){
        Player player = Play.createDefaultPlayer(body.getWorld(),
                                                130,
                                                320);
        player.crystals = this.crystals;
        player.score = this.score;
        Texture tex = Gravis.res.getTexture("cat");
        TextureRegion[] sprites = TextureRegion.split(tex, 80, 32)[0];
        setAnimation(sprites, 1 / 12f);
        return player;
    }



}
