package gravis.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by GreenFigure on 4/18/2015.
 */
public class MyFPSLogger {
    private int fps;
    long startTime;

    public MyFPSLogger () {
        startTime = TimeUtils.nanoTime();
    }

    public void log () {
        if (TimeUtils.nanoTime() - startTime > 1000000000) /* 1,000,000,000ns == one second */{
          //  Gdx.app.log("FPSLogger", "fps: " + Gdx.graphics.getFramesPerSecond());
            fps = Gdx.graphics.getFramesPerSecond();
            startTime = TimeUtils.nanoTime();
        }
    }

    public int getFPS(){
        return fps;
    }

    public String toString(){
        return Integer.toString(fps);
    }
}
