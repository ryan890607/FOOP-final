package dropItem;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MpPotion extends DropItem {
    public MpPotion(int x, int y) {
        super(x, y);
        name = "MpPotion";
        try {
            image = ImageIO.read(new File("assets/dropitem/2.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
