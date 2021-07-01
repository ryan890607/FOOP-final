package magic;

import fsm.ImageRenderer;
import fsm.State;
import fsm.WaitingPerFrame;
import ironBoar.Attack;
import ironBoar.Idle;
import model.Dangerous;
import model.Direction;
import model.Sprite;
import model.SpriteShape;

import java.awt.*;

import static utils.ImageStateUtils.imageStatesFromFolder;

public class Magic extends Sprite implements Dangerous {
    public Point end;
    public Point start;
    private State currentState;
    private State attacking;
    private State ending;
    private ImageRenderer imageRenderer;
    private final SpriteShape shape;
    private int damage;

    public Magic(Point start, Point end, String folderName, int damage, boolean endOnly){
        this.damage = damage;
        setLocation(endOnly ? end : start);
        this.end = end;
        this.start = start;
        setFace(start.x > end.x ? Direction.LEFT : Direction.RIGHT);
        shape = new SpriteShape(new Dimension(150, 150),
                new Dimension(50, 50), new Dimension(100, 100));
        imageRenderer = new MagicImageRenderer(this);
        attacking = new WaitingPerFrame(2,
                new Attacking(this, imageStatesFromFolder(folderName + "/magic", imageRenderer)));
        ending = new WaitingPerFrame(2,
                new Ending(this, imageStatesFromFolder(folderName + "/magic_end", imageRenderer)));
        currentState = endOnly ? ending : attacking;
    }
    public int getDamage(){
        return damage;
    }
    public void goEnding(){
        this.currentState = ending;
    }

    public int getEndX(){
        return end.x;
    }
    public int getEndY(){
        return end.y;
    }
    @Override
    public void update() {
        currentState.update();
    }

    @Override
    public void render(Graphics g) {
        currentState.render(g);
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

    public void inform(){
        goEnding();
    }
}
