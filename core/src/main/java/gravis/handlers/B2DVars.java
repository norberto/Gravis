package gravis.handlers;

import gravis.entities.B2DSprite;

/**
 * Created by GreenFigure on 2/24/2015.
 */
public class B2DVars extends B2DSprite {

    // pixel per meter ratio
    public static final float PPM = 100;

    // category bits
    public static final short BIT_PLAYER = 2;
    public static final short BIT_CLOUD = 4;
    public static final short BIT_RAINBOW = 8;

    public static final short BIT_TOP_BLOCK = 2;
    public static final short BIT_BOTTOM_BLOCK = 4;
    public static final short BIT_TOP_PLATFORM = 8;
    public static final short BIT_BOTTOM_PLATFORM = 16;

}
