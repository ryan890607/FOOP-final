package obstacles;

import model.Direction;
import model.Sprite;
import knight.Knight;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Obstacle3 extends Obstacle {
	//private final ImageRenderer imageRenderdr;
	
	public Obstacle3(String path, Point place, Direction d) {
	    this.location = place;
	    this.size = new Dimension(414, 98);
	    this.face = d;
            try {
                this.image = ImageIO.read(new File(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
	    //this.imageRenderdr = new ObstacleRenderer(this);
	}

        public Image getImage() {
		return this.image;
	}

        public Point getLocation() {
		return this.location;
	}

        public Dimension getSize() {
		return this.size;
	}

        public Direction getFace() {
		return this.face;
	}

	public void collisionHandler(Point originalLocation, Sprite from) {
            from.setLocation(originalLocation);
	    if (from instanceof Knight)
	        ((Knight)from).fallCount = -1;
            
            //Rectangle range = from.getRange();
	    //Point newPoint = new Point(range.x + 2 * (originalLocation.x - range.x), range.y + 2 * (originalLocation.y - range.y));
            //from.setLocation(newPoint);
	}
}
