package magic;

import fsm.ImageRenderer;
import ironBoar.IronBoar;
import model.Direction;

import java.awt.*;

public class MagicImageRenderer implements ImageRenderer {
    protected Magic magic;

    public MagicImageRenderer(Magic magic) { this.magic = magic; }

    @Override
    public void render(Image image, Graphics g) {
        Direction face = magic.getFace();
        Rectangle range = magic.getRange();
        if (face == Direction.RIGHT) {
            g.drawImage(image, range.x + range.width, range.y, -range.width, range.height, null);
        } else {
            g.drawImage(image, range.x, range.y, range.width, range.height, null);
        }

    }
}
