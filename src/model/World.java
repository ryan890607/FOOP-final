package model;

import knight.Knight;
import media.AudioPlayer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class World {
    private Image background;
    int sx, sy; // left-down corner's axis of background
    private Knight player;
    private final List<Sprite> sprites = new CopyOnWriteArrayList<>();
    private final CollisionHandler collisionHandler;
    public static final String BGM = "bgm";

    public World(String backgroundName, CollisionHandler collisionHandler, Knight player, Sprite... sprites) {
        try {
            background = ImageIO.read(new File(backgroundName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // int imgW = background.getWidth(null), imgH = background.getHeight(null);
        sx = 0;
        sy = background.getHeight(null);
        this.player = player;
        this.collisionHandler = collisionHandler;
        addSprite(player);
        addSprites(sprites);
        AudioPlayer.playSoundsloop(BGM);
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


        if(player.jumpStep >= 0) return;


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
        from.getLocation().translate(offset.width, offset.height);

        Rectangle body = from.getBody();
        // collision detection
        for (Sprite to : sprites) {
            if (to != from && body.intersects(to.getBody())) {
                collisionHandler.handle(originalLocation, from, to);
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
        g.drawImage(background, 0, 0, 1024, 768, sx, sy-768, sx+1024, sy, null);
        for (Sprite sprite : sprites) {
            sprite.setLocation(new Point(sprite.getX()-sx, sprite.getY()-sy+768));
            sprite.render(g);
            sprite.setLocation(new Point(sprite.getX()+sx, sprite.getY()+sy-768));
        }
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
    }
}