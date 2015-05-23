package gravis.handlers;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import gravis.entities.Player;

/**
 * Created by GreenFigure on 2/24/2015.
 */
public class MyContactListener implements ContactListener {


    private int numFootContacts;
    private Array<Body> bodiesToRemove;
    private Player player;
    private boolean playerDead = false;

    public MyContactListener(Player player) {
        bodiesToRemove = new Array<Body>();
        player = this.player;
    }

    // called when two fixtures start to collide
    public void beginContact(Contact c) {
        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        if (fa.getUserData() != null && fa.getUserData().equals("foot")) {
            numFootContacts++;
        }
        if (fb.getUserData() != null && fb.getUserData().equals("foot")) {
            numFootContacts++;
        }

        if (fa.getUserData() != null && fa.getUserData().equals("rainbow")) {
            //remove crystal
            bodiesToRemove.add(fa.getBody());
        }

        if (fb.getUserData() != null && fb.getUserData().equals("rainbow")) {
            //remove crystal
            bodiesToRemove.add(fb.getBody());


        }
        if (fa.getUserData() != null && fa.getUserData().equals("face")) {
            playerDead = true;
        }
        if (fb.getUserData() != null && fb.getUserData().equals("face")) {
            playerDead = true;
        }


    }

    // called when two fixtures no longer collide
    public void endContact(Contact c) {
        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        if (fa.getUserData() != null && fa.getUserData().equals("foot")) {
            numFootContacts--;
        }
        if (fb.getUserData() != null && fb.getUserData().equals("foot")) {
            numFootContacts--;
        }
    }

    //getter for playerOnGround
    public boolean isPlayerOnGround() {
        return numFootContacts > 0;
    }

    //
    public Array<Body> getBodiesToRemove() {
        return bodiesToRemove;
    }

    // collision detection
    public void preSolve(Contact c, Manifold m) {
    }

    // collision handling
    public void postSolve(Contact c, ContactImpulse ci) {
    }
    public boolean isPlayerDead() { return playerDead; }

}
