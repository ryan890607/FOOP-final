package knight;

import fsm.FiniteStateMachine;
import fsm.ImageRenderer;
import fsm.State;
import fsm.WaitingPerFrame;
import media.AudioPlayer;
import model.Direction;
import model.HealthPointSprite;
import model.Sprite;
import model.SpriteShape;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
    public static final int KNIGHT_HP = 2000;
    private final SpriteShape shape;
    private final FiniteStateMachine fsm;
    private final Set<Direction> directions = new CopyOnWriteArraySet<>();
    private final int damage;
    public int jumpStep;
    private final ArrayList<Integer> jumpSequence = new ArrayList<>(Arrays.asList(-26,-23,-22,-20,-18,-18,-15,-15,-13,-12,-10,-10,-8,-8,-6,-6,-5,-4,-3,-3,-2,-2,-1,-1,0));
    public static final String JUMP = "jump";
    private Direction responseDirection;
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
        State damaged = new WaitingPerFrame(2,
                new Damaged(this, fsm, imageStatesFromFolder("assets/damaged", imageRenderer)));
        fsm.setInitialState(idle);
        fsm.addTransition(from(idle).when(WALK).to(walking));
        fsm.addTransition(from(walking).when(STOP).to(idle));
        fsm.addTransition(from(idle).when(ATTACK).to(attacking));
        fsm.addTransition(from(walking).when(ATTACK).to(attacking));
        fsm.addTransition(from(idle).when(DAMAGED).to(damaged));
        fsm.addTransition(from(walking).when(DAMAGED).to(damaged));
        fsm.addTransition(from(attacking).when(DAMAGED).to(damaged));

        jumpStep = -1;
        int size = jumpSequence.size();
        for(int i = size-2; i >= 0; --i) jumpSequence.add(-(jumpSequence.get(i)));
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

        if(this.world == null) return;
        jump(jumpStep);
        if(getX() < 0) location.x = 0;
        if(getY() < 0) location.y = 0;
        if(getX() > world.getBackground().getWidth(null)-getRange().width) location.x = world.getBackground().getWidth(null)-getRange().width;
        if(getY() > world.getBackground().getHeight(null)-getRange().height) location.y = world.getBackground().getHeight(null)-getRange().height;
    }

    public void jump(int now) {
        if(now < 0) return;
        if(now == 0) AudioPlayer.playSounds(JUMP);
        world.jump(this, new Dimension(0, jumpSequence.get(now)));
        jumpStep = now == jumpSequence.size()-1? -1:now+1;
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

    @Override
    public void setResponseDirection(Direction responseDirection) {
        this.responseDirection = responseDirection;
    }

    @Override
    public Direction getResponseDirection() {
        return responseDirection;
    }

    @Override
    public void onDamaged(Sprite attacker, Rectangle damageArea, int damage) {
        if (fsm.currentState() instanceof Damaged) return;
        super.onDamaged(attacker, damageArea, damage);
        fsm.trigger(DAMAGED);
    }

    public boolean isBeingDamaged(){
        return fsm.currentState().toString().equals("Damaged");
    }

}
