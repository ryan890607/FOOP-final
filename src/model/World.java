package model;

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
    int sx = 0; // left of background
    private Sprite player;
    int pX;     // player's x-position on the background
    private final List<Sprite> sprites = new CopyOnWriteArrayList<>();
    private final CollisionHandler collisionHandler;

    public World(String backgroundName, CollisionHandler collisionHandler, Sprite player, Sprite... sprites) {
        try {
            background = ImageIO.read(new File(backgroundName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // int imgW = background.getWidth(null), imgH = background.getHeight(null);

        this.player = player;
        pX = player.getX();
        this.collisionHandler = collisionHandler;
        addSprite(player);
        addSprites(sprites);
    }

    public void update() {
        for (Sprite sprite : sprites) {
            sprite.update();
        }
        // adjust position
        if(pX <= 300) {
            pX = player.getX();
        }
        else if(pX >= background.getWidth(null)-1024+300) {
            pX = player.getX() + background.getWidth(null)-1024;
            //sx = background.getWidth(null)-1024;
            // player.setLocation();
        }
        else {
            pX += player.getX()-300;
            //sx += player.getX()-300;
            player.setLocation(new Point(300, player.getY()));
        }
        if(pX < 300) {
            sx = 0;
        }
        else {
            sx = pX-300;
        }
        if(sx < 0) sx = 0;
        if(sx > background.getWidth(null)-1024) sx = background.getWidth(null)-1024;
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

    public Collection<Sprite> getSprites(Rectangle area) {
        return sprites.stream()
                .filter(s -> area.intersects(s.getBody()))
                .collect(toSet());
    }

    public List<Sprite> getSprites() {
        return sprites;
    }

    public Sprite getPlayer() { return player; }

    // Actually, directly couple your model with the class "java.awt.Graphics" is not a good design
    // If you want to decouple them, create an interface that encapsulates the variation of the Graphics.
    public void render(Graphics g) {
        g.drawImage(background, 0, 0, 1024, 768, sx, 0, sx+1024, 768, null);
        for (Sprite sprite : sprites) {
            sprite.render(g);
        }
    }
}
