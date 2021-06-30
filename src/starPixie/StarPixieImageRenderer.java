package starPixie;

import fsm.ImageRenderer;
import model.Direction;

import java.awt.*;

public class StarPixieImageRenderer implements ImageRenderer {
    protected StarPixie starPixie;

     public StarPixieImageRenderer(StarPixie starPixie){
         this.starPixie = starPixie;
     }

    @Override
    public void render(Image image, Graphics g) {
        Direction face = starPixie.getFace();
        Rectangle range = starPixie.getRange();
        Rectangle body = starPixie.getBody();
        if (face == Direction.RIGHT) {
            g.drawImage(image, range.x + range.width, range.y, -range.width, range.height, null);
        } else {
            g.drawImage(image, range.x, range.y, range.width, range.height, null);
        }
        g.setColor(Color.RED);
        g.drawRect(body.x, body.y, body.width, body.height);
    }
}
