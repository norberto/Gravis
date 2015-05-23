package gravis.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import gravis.main.Gravis;

/**
 * Created by GreenFigure on 3/15/2015.
 */
public class Rainbow extends B2DSprite {
    public Rainbow(Body body) {

        super(body);

        Texture tex = Gravis.res.getTexture("rainbow");
        TextureRegion[] sprites = TextureRegion.split(tex, 21, 16)[0];

        setAnimation(sprites, 1 / 6.1f);
    }
}
