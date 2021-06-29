package knight;

import fsm.FiniteStateMachine;
import fsm.ImageRenderer;
import fsm.State;
import fsm.WaitingPerFrame;
import model.Direction;
import model.HealthPointSprite;
import model.SpriteShape;

import java.awt.*;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import static fsm.FiniteStateMachine.Transition.from;
import static knight.Knight.Event.*;
import static model.Direction.LEFT;
import static utils.ImageStateUtils.imageStatesFromFolder;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Knight extends HealthPointSprite {
    public static final int KNIGHT_HP = 500;
    private final SpriteShape shape;
    private final FiniteStateMachine fsm;
    private final Set<Direction> directions = new CopyOnWriteArraySet<>();
    private final int damage;
    public Dimension jumpStep;

    public enum Event {
        WALK, STOP, ATTACK, DAMAGED
    }

    public Knight(int damage, Point location) {
        super(KNIGHT_HP);
        this.damage = damage;
        this.location = location;
        shape = new SpriteShape(new Dimension(146, 176),
                new Dimension(33, 38), new Dimension(66, 105));
        fsm = new FiniteStateMachine();

        ImageRenderer imageRenderer = new KnightImageRenderer(this);
        State idle = new WaitingPerFrame(8,
                new Idle(imageStatesFromFolder("assets/idle", imageRenderer)));
        State walking = new WaitingPerFrame(1,
                new Walking(this, imageStatesFromFolder("assets/walking", imageRenderer)));
        State attacking = new WaitingPerFrame(5,
                new Attacking(this, fsm, imageStatesFromFolder("assets/attack", imageRenderer)));

        fsm.setInitialState(idle);
        fsm.addTransition(from(idle).when(WALK).to(walking));
        fsm.addTransition(from(walking).when(STOP).to(idle));
        fsm.addTransition(from(idle).when(ATTACK).to(attacking));
        fsm.addTransition(from(walking).when(ATTACK).to(attacking));

        jumpStep = new Dimension(0, 0);
    }

    public void attack() {
        fsm.trigger(ATTACK);
    }

    public int getDamage() {
        return damage;
    }

    public void move(Direction direction) {
        if (direction == LEFT || direction == Direction.RIGHT) {
            face = direction;
        }
        if (!directions.contains(direction)) {
            this.directions.add(direction);
            fsm.trigger(WALK);
        }
     }

    public void stop(Direction direction) {
        directions.remove(direction);
        if (directions.isEmpty()) {
            fsm.trigger(STOP);
        }
    }

    public void update() {
        fsm.update();

        jump(jumpStep.height);
        if(getX() < 0) location.x = 0;
        if(getY() < 0) location.y = 0;
        if(getX() > world.getBackground().getWidth(null)-getRange().width) location.x = world.getBackground().getWidth(null)-getRange().width;
        if(getY() > world.getBackground().getHeight(null)-getRange().height) location.y = world.getBackground().getHeight(null)-getRange().height;
    }

    public void jump(int now) {
        jumpStep.setSize(0, now);
        world.move(this, jumpStep);
        // System.out.println(jumpStep);
        switch (now) {
            case -49:
                jumpStep.setSize(0, -36);
                break;
            case -36:
                jumpStep.setSize(0, -25);
                break;
            case -25:
                jumpStep.setSize(0, -16);
                break;
            case -16:
                jumpStep.setSize(0, -9);
                break;
            case -9:
                jumpStep.setSize(0, -4);
                break;
            case -4:
                jumpStep.setSize(0, -1);
                break;
            case -1:
                jumpStep.setSize(0, 1);
                break;
            case 1:
                jumpStep.setSize(0, 4);
                break;
            case 4:
                jumpStep.setSize(0, 9);
                break;
            case 9:
                jumpStep.setSize(0, 16);
                break;
            case 16:
                jumpStep.setSize(0, 25);
                break;
            case 25:
                jumpStep.setSize(0, 36);
                break;
            case 36:
                jumpStep.setSize(0, 49);
                break;
            case 49:
            case 0:
                jumpStep.setSize(0, 0);
                break;
        }
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        fsm.render(g);
    }

    public Set<Direction> getDirections() {
        return directions;
    }

    @Override
    public Point getLocation() {
        return location;
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
