package gravis.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import gravis.main.Gravis;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = Gravis.TITLE;
        config.width = Gravis.V_WIDTH * Gravis.SCALE;
        config.height = Gravis.V_HEIGHT * Gravis.SCALE;

        new LwjglApplication(new Gravis(), config);
    }
}
