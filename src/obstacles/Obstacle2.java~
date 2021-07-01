package obstacles;

import model.Direction;
import model.Sprite;
import knight.Knight;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Obstacle2 extends Obstacle {
	
	public Obstacle2(String path, Point place, Direction d) {
	    this.location = place;
	    this.size = new Dimension(333, 300);
	    this.face = d;
            try {
                this.image = ImageIO.read(new File(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
	    Rectangle body = from.getBody();
	    if (this.face == Direction.LEFT) {
	        if (body.x + body.width - this.location.x < 105 && body.y + body.height - this.location.y < 165)
			return;
		else if (body.x + body.width - this.location.x < 220 && body.y + body.height - this.location.y < 75)
			return;
	    } else {
	        if (this.location.x + this.size.width - body.x < 105 && body.y + body.height - this.location.y < 165)
			return;
		else if (this.location.x + this.size.width - body.x < 220 && body.y + body.height - this.location.y < 75)
			return;
	    }
	    from.setLocation(originalLocation);
	    body = from.getBody();
	    if (from instanceof Knight && body.x + body.width > this.getLocation().x && body.x < this.getLocation().x + this.getSize().width) {
	        ((Knight)from).fallCount = -1;
		if (body.y + body.height < this.getLocation().y) {
	            ((Knight)from).jumpLV = 0;
	            ((Knight)from).jumpStep = -1;
		}
	    }
	}
}
	    
	/*
	    Rectangle range = from.getRange();
            int offsetLeft = range.x + range.width - this.getLocation().x;
            int offsetRight = this.getLocation().x + this.getSize().width - range.x;
            int offsetUp = range.y + range.height - this.getLocation().y;
            int offsetDown = this.getLocation().y + this.getSize().height - range.y;
	*/
	/*
	    if (offsetLeft > 0 && offsetLeft < 60)
                from.setLocation(new Point(from.getX() - 12, from.getY()));
	    else if (offsetRight > 0 && offsetLeft < 60)
                from.setLocation(new Point(from.getX() + 12, from.getY()));
	    if (offsetUp > 0 && offsetUp < 60)
                from.setLocation(new Point(from.getX(), from.getY() - 12));
	    else if (offsetDown > 0 && offsetLeft < 60)
                from.setLocation(new Point(from.getX(), from.getY() + 12));
	*/
	/*   
	    int min = 1000;
	    if (offsetLeft > 0 && offsetLeft < min)
		    min = offsetLeft;
	    if (offsetRight > 0 && offsetRight < min)
		    min = offsetRight;
	    if (offsetUp > 0 && offsetUp < min)
		    min = offsetUp;
	    if (offsetDown > 0 && offsetDown < min)
		    min = offsetDown;

	    if (offsetLeft == min)
                from.setLocation(new Point(from.getX() - offsetLeft, from.getY()));
	    if (offsetRight == min)
                from.setLocation(new Point(from.getX() + offsetRight, from.getY()));
	    if (offsetUp == min)
                from.setLocation(new Point(from.getX(), from.getY() - offsetUp));
	    if (offsetDown == min)
                from.setLocation(new Point(from.getX(), from.getY() + offsetDown));
	*/  
	   
	    //Rectangle range = from.getRange();
	    //Point newPoint = new Point(2 * originalLocation.x - range.x, 2 * originalLocation.y - range.y);
            //from.setLocation(newPoint);
        

        /*    
	 *  if (from instanceof Knight) {
                Rectangle body = from.getBody();
                int offsetLeft = from.getX() - this.getLocation().x;
                int offsetRight = this.getLocation().x + this.getSize().width - from.getX();
                if (offsetLeft < 0) {
                    from.setLocation(originalLocation);
                } else {
                    from.setLocation(originalLocation);
                }
            }
	 */
