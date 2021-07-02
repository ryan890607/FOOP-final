package ironBoar;

import fsm.ImageRenderer;
import model.Direction;

import java.awt.*;

public class IronBoarImageRenderer implements ImageRenderer {
    protected IronBoar ironBoar;

    public IronBoarImageRenderer(IronBoar ironBoar){
         this.ironBoar = ironBoar;
     }

    @Override
    public void render(Image image, Graphics g) {
        Direction face = ironBoar.getFace();
        Rectangle range = ironBoar.getRange();
        Rectangle body = ironBoar.getBody();
        if (face == Direction.RIGHT) {
            g.drawImage(image, range.x + range.width, range.y, -range.width, range.height, null);
        } else {
            g.drawImage(image, range.x, range.y, range.width, range.height, null);
        }
//        g.setColor(Color.RED);
//        g.drawRect(body.x, body.y, body.width, body.height);
    }
}
