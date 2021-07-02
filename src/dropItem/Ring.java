package dropItem;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Ring extends DropItem {
    public Ring(int x, int y) {
        super(x, y);
        name = "Ring";
        try {
            image = ImageIO.read(new File("assets/dropitem/1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
