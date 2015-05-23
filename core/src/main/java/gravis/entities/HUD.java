package gravis.entities;

/**
 * Created by GreenFigure on 4/19/2015.
 */
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gravis.handlers.MyFPSLogger;
import gravis.handlers.MyTime;
import gravis.main.Gravis;

public class HUD {

    private Player player;

    private TextureRegion[] font;
    private MyFPSLogger fpsl;


    public HUD(Player player) {

        this.player = player;
        fpsl = new MyFPSLogger();

        Texture tex = Gravis.res.getTexture("hud");

        font = new TextureRegion[37];
        for (int i = 0; i < 6; i++) { // 012345
            font[i] = new TextureRegion(tex, i * 9, 0, 9, 9);
        }
        for (int i = 0; i < 5; i++) { // 6789/
            font[i + 6] = new TextureRegion(tex, i * 9, 9, 9, 9);
        }
        for (int i = 0; i < 6; i++) { // abcdef
            font[i + 11] = new TextureRegion(tex, i * 10 , 18, 10, 20);
        }
        for (int i = 0; i < 6; i++) { // ghijkl
            font[i + 17] = new TextureRegion(tex, i * 10, 38, 10, 20);
        }
        for (int i = 0; i < 6; i++) { // mnoprs
            font[i + 23] = new TextureRegion(tex, i * 10, 58, 10, 20);
        }
        for (int i = 0; i < 6; i++) { // tuvxyz
            font[i + 29] = new TextureRegion(tex, i * 10, 78, 10, 20);
        }
        font[35] = new TextureRegion(tex, 0, 98, 10, 20); // q
        font[36] = new TextureRegion(tex, 10, 98, 13, 20); // w
    }

    public void render(SpriteBatch sb) {

        sb.begin();
        //TODO SCORE HUD LETTERS
        // draw crystal amount
        //Math.addExact(player.getScore(), (int)player.getPosition().x)   + " / " + player.getCrystals()
        //"0123456789/abcdefghijklmnoprstuvxyz"
        drawString(sb,
                "score " + Math.addExact(player.getScore(), (int) player.getPosition().x), 50, 430);
        drawString(sb,
                "rainbows " + player.getCrystals(),
                50, 410);
        drawString(sb, "fps " + fpsl.getFPS(), 600, 430);
        sb.end();

    }

    public void update(){
        fpsl.log();
    }

    private void drawString(SpriteBatch sb, String s, float x, float y) {
        int w = 0;
        int space = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                space++;
            }
            if (c == '/'){
                c = 10;
                sb.draw(font[c], x + i * 9 + w * 3 + space * 7, y + 1);
                continue;
            }
            else if (c >= '0' && c <= '9'){
                c -= '0';
                sb.draw(font[c], x + i * 10 + w * 3 + space * 7, y + 1);
                continue;
            } else if(c >= 'a' && c <= 'p') {
                c -= 'V';
                sb.draw(font[c], x + i * 10 + w * 3 + space * 7, y - 3);
                continue;
            }
            else if(c == 'q' || c == 'Q') {
                c = 35;
                sb.draw(font[c], x + i * 10 + w * 3 + space * 7, y - 3);
                continue;
            }
            else if(c >= 'r' && c <= 'v'){
                c -= 'V' + 1;
                sb.draw(font[c], x + i * 10 + w * 3 + space * 7, y - 3);
                continue;
            }
            else if(c == 'w' || c == 'W'){
                c = 36;
                sb.draw(font[c], x + i * 10 + w * 3 + space * 7, y - 3);
                w++;
                continue;
            }
            else if(c >= 'x' && c <= 'z') {
                c -= 'V' + 2;
                sb.draw(font[c], x + i * 10 + w * 3 + space * 7, y - 3);
                continue;
            }

            else continue;
        }
    }

}

