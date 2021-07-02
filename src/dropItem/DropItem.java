package dropItem;

import controller.Pause;

import java.awt.*;

public abstract class DropItem {
    String name;
    Image image;
    Point location;
    Dimension bodysize, bodyOffset;

    public DropItem(int x, int y) {
        //System.out.println(x + "," + y);
        if(y >= 768) y = 620+768-94;
        else if(y <= 450-94) y = 360-94;
        else y = 630-94;
        location = new Point(x, y);
        //System.out.println(location);
        bodysize = new Dimension(50, 50);
        bodyOffset = new Dimension(20, 20);
    }

    public void render(Graphics g) {
        g.drawImage(image, location.x, location.y, null);
        g.setColor(Color.RED);
        g.drawRect(location.x+bodyOffset.width, location.y+bodyOffset.height, bodysize.width, bodysize.height);
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point p) {
        location = p;
    }

    public int getX() {
        return location.x+bodyOffset.width;
    }

    public int getY() {
        return location.y+bodyOffset.height;
    }
}
