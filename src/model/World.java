package model;

import knight.Knight;
import media.AudioPlayer;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

import obstacles.Obstacle1;
import obstacles.Obstacle2;
import obstacles.Obstacle3;
import java.util.ArrayList;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class World {
    private Image background;
    private List<Obstacle1> floors;
    private List<Obstacle2> stairs;
    private List<Obstacle3> rocks;
    int sx, sy; // left-down corner's axis of background
    private Knight player;
    private final List<Sprite> sprites = new CopyOnWriteArrayList<>();
    private final CollisionHandler collisionHandler;
    public static final String BGM = "bgm";
    public Clip clip;
    private Image pause;

    public World(String backgroundName, List<Obstacle1> floors, List<Obstacle2> stairs, List<Obstacle3> rocks, CollisionHandler collisionHandler, Knight player, Sprite... sprites) {
        try {
            background = ImageIO.read(new File(backgroundName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // int imgW = background.getWidth(null), imgH = background.getHeight(null);
        this.floors = floors;
        this.stairs = stairs;
        this.rocks = rocks;
        sx = 0;
        sy = background.getHeight(null);
        this.player = player;
        this.collisionHandler = collisionHandler;
        addSprite(player);
        addSprites(sprites);

        try {
            pause = ImageIO.read(new File("assets/others/pause.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void playSound() {
        clip = AudioPlayer.playSoundsloop(BGM);
    }

    public void update() {
        for (Sprite sprite : sprites) {
            sprite.update();
        }
        // adjust position
        if(player.getX() <= 300) {
            sx = 0;
        }
        else if(player.getX() >= background.getWidth(null)-1024+300) {
            sx = background.getWidth(null)-1024;
        }
        else{
            sx = player.getX()-300;
        }
        if(sx < 0) sx = 0;
        if(sx > background.getWidth(null)-1024) sx = background.getWidth(null)-1024;

        //if(player.jumpStep >= 0) return;

        if(player.getY() >= background.getHeight(null)-300) {
            sy = background.getHeight(null);
        }
        else if(player.getY() <= 300) {
            sy = background.getHeight(null)-768;
        }
        else{
            sy = player.getY()+768-300;
        }
        if(sy < 768) sy = 768;
        if(sy > background.getHeight(null)) sy = background.getHeight(null);
    }

    public void addSprites(Sprite... sprites) {
        stream(sprites).forEach(this::addSprite);
    }

    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
        sprite.setWorld(this);
    }

    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
        sprite.setWorld(null);
    }

    public void move(Sprite from, Dimension offset) {
        Point originalLocation = new Point(from.getLocation());
        Rectangle originalBody = new Rectangle(from.getBody());
        from.getLocation().translate(offset.width, offset.height);

        Rectangle body = from.getBody();
        // collision detection
        for (Sprite to : sprites) {
            if (to != from && body.intersects(to.getBody())) {
                collisionHandler.handle(originalLocation, from, to);
            }
        }
        
        for (Obstacle1 to : floors) {
	    Rectangle range = new Rectangle(to.getLocation(), to.getSize());
            if (body.intersects(range)) {
                to.collisionHandler(originalLocation, from);
            }
        }

        for (Obstacle2 to : stairs) {
	    Rectangle range = new Rectangle(to.getLocation(), to.getSize());
            if (body.intersects(range)) {
                to.collisionHandler(originalLocation, from);
            } else if (from instanceof Knight) {
	        if (originalBody.x + body.width >= range.x && originalBody.x <= range.x + range.width && (body.x + body.width < range.x || body.x > range.x + range.width))
		    if (originalBody.y + body.height < range.y + range.height && range.y - originalBody.y - body.height < 20)
			((Knight)from).fallCount = 0;  //start falling
	    }
        }

        for (Obstacle3 to : rocks) {
	    Rectangle range = new Rectangle(to.getLocation(), to.getSize());
            if (body.intersects(range)) {
                to.collisionHandler(originalLocation, from);
            } else if (from instanceof Knight) {
	        if (originalBody.x + body.width >= range.x && originalBody.x <= range.x + range.width && (body.x + body.width < range.x || body.x > range.x + range.width))
		    if (originalBody.y + body.height < range.y + range.height && range.y - originalBody.y - body.height < 20)
			((Knight)from).fallCount = 0;  //start falling
            }
        }
    }

    public void jump(Sprite from, Dimension offset) {
        Point originalLocation = new Point(from.getLocation());
        from.getLocation().translate(offset.width, offset.height);

        Rectangle body = from.getBody();
        // collision detection
        for (Sprite to : sprites) {
            if (to != from && body.intersects(to.getBody())) {
                collisionHandler.handle(originalLocation, from, to);
            }
        }
        
        for (Obstacle1 to : floors) {
	    Rectangle range = new Rectangle(to.getLocation(), to.getSize());
            if (body.intersects(range)) {
                to.collisionHandler(originalLocation, from);
            }
        }

        for (Obstacle2 to : stairs) {
	    Rectangle range = new Rectangle(to.getLocation(), to.getSize());
            if (body.intersects(range)) {
                to.collisionHandler(originalLocation, from);
            }
        }

        for (Obstacle3 to : rocks) {
	    Rectangle range = new Rectangle(to.getLocation(), to.getSize());
            if (body.intersects(range)) {
                to.collisionHandler(originalLocation, from);
            }
        }
    }

    public Collection<Sprite> getSprites(Rectangle area) {
        return sprites.stream()
                .filter(s -> area.intersects(s.getBody()))
                .collect(toSet());
    }

    public List<Sprite> getSprites() {
        return sprites;
    }

    public Sprite getPlayer() { return player; }

    public Image getBackground() { return background; }

    // Actually, directly couple your model with the class "java.awt.Graphics" is not a good design
    // If you want to decouple them, create an interface that encapsulates the variation of the Graphics.
    public void render(Graphics g) {
        int sxtemp = sx, sytemp = sy;
        g.drawImage(background, 0, 0, 1024, 768, sxtemp, sytemp-768, sxtemp+1024, sytemp, null);
        
        for (Obstacle1 obstacle : floors) {
	    Rectangle range = new Rectangle(obstacle.getLocation(), obstacle.getSize());
            g.drawImage(obstacle.getImage(), range.x - sx              , range.y - sy + 768, range.width, range.height, null);
            //g.drawImage(obstacle, 666, 444, obstacle.getWidth(), obstacle.getHeight(), null);
	}
	for (Obstacle2 stair : stairs) {
	    Rectangle range = new Rectangle(stair.getLocation(), stair.getSize());
            if (stair.getFace() == Direction.RIGHT) {
                g.drawImage(stair.getImage(), range.x + range.width - sx, range.y - sy + 768, -range.width, range.height, null);
            } else {
                g.drawImage(stair.getImage(), range.x - sx              , range.y - sy + 768, range.width, range.height, null);
            }
            //g.drawImage(obstacle, 666, 444, obstacle.getWidth(), obstacle.getHeight(), null);
	}
	for (Obstacle3 obstacle : rocks) {
	    Rectangle range = new Rectangle(obstacle.getLocation(), obstacle.getSize());
            g.drawImage(obstacle.getImage(), range.x - sx              , range.y - sy + 768, range.width, range.height, null);
	}
	
        for (Sprite sprite : sprites) {
            //System.out.println(sprite.location);
            //System.out.printf("%d %d\n", sxtemp, sytemp);
            sprite.setLocation(new Point(sprite.getX()-sxtemp, sprite.getY()-sytemp+768));
            //System.out.printf("%d %d\n", sxtemp, sytemp);
            sprite.render(g);
            sprite.setLocation(new Point(sprite.getX()+sxtemp, sprite.getY()+sytemp-768));
        }
        sx = sxtemp; sy = sytemp;
        g.setColor(Color.black);
        g.fillRect(0, 0, 128, 96);
        for (Sprite sprite : sprites) {
            g.setColor(Color.RED);
            Point p = sprite.getLocation();
            g.fillOval((int)(p.getX()/16), (int)(p.getY()/16), 6, 6);
        }
        g.setColor(Color.green);
        Point p = player.getLocation();
        g.fillOval((int)(p.getX()/16), (int)(p.getY()/16), 8, 8);

        g.drawImage(pause, 950, 0, null);
    }
}
