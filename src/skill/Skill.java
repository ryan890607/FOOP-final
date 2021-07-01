package skill;

import fsm.ImageRenderer;
import fsm.State;
import fsm.WaitingPerFrame;
import model.Direction;
import model.Sprite;
import model.SpriteShape;

import java.awt.*;

import static utils.ImageStateUtils.imageStatesFromFolder;

public class Skill extends Sprite {
    private State ending;
    private ImageRenderer imageRenderer;
    private final SpriteShape shape;

    public Skill(Point location, String folderName){
        setLocation(location);
        shape = new SpriteShape(new Dimension(180, 140),
                new Dimension(50, 50), new Dimension(50, 50));
        imageRenderer = new SkillImageRenderer(this);
        ending = new WaitingPerFrame(2,
                new Ending(this, imageStatesFromFolder(folderName, imageRenderer)));
    }


    @Override
    public void update() {
        ending.update();
    }

    @Override
    public void render(Graphics g) {
        ending.render(g);
    }

    @Override
    public void onDamaged(Sprite attacker, Rectangle damageArea, int damage) {

    }

    @Override
    public Rectangle getRange() {
        return new Rectangle(location, shape.size);
    }

    @Override
    public Dimension getBodyOffset() {
        return shape.bodyOffset;
    }

    @Override
    public Dimension getBodySize() {
        return shape.bodySize;
    }

}
