package dropItem;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class HpPotion extends DropItem {
    public HpPotion(int x, int y) {
        super(x, y);
        name = "HpPotion";

        try {
            image = ImageIO.read(new File("assets/dropitem/3.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
