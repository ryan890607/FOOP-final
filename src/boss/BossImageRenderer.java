package boss;

import fsm.ImageRenderer;
import model.Direction;

import java.awt.*;

public class BossImageRenderer implements ImageRenderer {
    protected Boss boss;

     public BossImageRenderer(Boss boss){
         this.boss = boss;
     }

    @Override
    public void render(Image image, Graphics g) {
        Direction face = boss.getFace();
        Rectangle range = boss.getRange();
        Rectangle body = boss.getBody();
        if (face == Direction.RIGHT) {
            g.drawImage(image, range.x + range.width, range.y, -range.width, range.height, null);
        } else {
            g.drawImage(image, range.x, range.y, range.width, range.height, null);
        }
        g.setColor(Color.RED);
        g.drawRect(body.x, body.y, body.width, body.height);
    }
}
