package obstacles;

import java.awt.*;
import model.Direction;

public abstract class Obstacle {
    protected Image image;
    protected Point location;
    protected Dimension size;
    protected Direction face;

    public abstract Image getImage();
    public abstract Point getLocation();
    public abstract Dimension getSize();
    public abstract Direction getFace();
}
