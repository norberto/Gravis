package gravis.handlers;

import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by GreenFigure on 4/18/2015.
 */
public class MyTime {

    private long startTime;
    private long newTime;

    public MyTime () {
        startTime = TimeUtils.nanoTime();
    }


    public long getTime(){
        return TimeUtils.nanoTime() - startTime;
    }

    public long getNewTime(){
        newTime = TimeUtils.nanoTime() - startTime;
        return newTime;
    }

    public boolean timer(){
        if(newTime - startTime > 500000000) return true;
        else return false;
    }

}
