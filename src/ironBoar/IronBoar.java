package ironBoar;


import fsm.FiniteStateMachine;
import fsm.ImageRenderer;
import fsm.State;
import fsm.WaitingPerFrame;
import model.Direction;
import model.HealthPointSprite;
import model.Sprite;
import model.SpriteShape;

import java.awt.*;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import static fsm.FiniteStateMachine.Transition.from;
import static model.Direction.LEFT;
import static model.Direction.RIGHT;
import static utils.ImageStateUtils.imageStatesFromFolder;
import static ironBoar.IronBoar.Event.*;

public class IronBoar extends HealthPointSprite {
    public static final int HP = 1000;

    private final SpriteShape shape;
    private final FiniteStateMachine fsm;
    private final Set<Direction> directions = new CopyOnWriteArraySet<>();
    private final int damage;
    private Direction responseDirection;
    private Direction walkingDirection;
    private Sprite target;


    public enum Event {
        WALK, STOP, DAMAGED, DIE, ATTACK;
    }

    public IronBoar(int damage, Point location) {
        super(HP);
        this.damage = damage;
        this.location = location;
        walkingDirection = LEFT;
        shape = new SpriteShape(new Dimension(146, 176),
                new Dimension(33, 38), new Dimension(66, 105));
        fsm = new FiniteStateMachine();

        ImageRenderer imageRenderer = new IronBoarImageRenderer(this);
        State idle = new WaitingPerFrame(8,
                new Idle(this, imageStatesFromFolder("assets/monster/Iron_Boar/idle", imageRenderer)));
        State walking = new WaitingPerFrame(2,
                new Walking(this, imageStatesFromFolder("assets/monster/Iron_Boar/walking", imageRenderer)));
        State damaged = new WaitingPerFrame(2,
                new Damaged(this, imageStatesFromFolder("assets/monster/Iron_Boar/damaged", imageRenderer)));
        State die = new WaitingPerFrame(6,
                new Die(this, imageStatesFromFolder("assets/monster/Iron_Boar/die", imageRenderer)));
        State attacking = new WaitingPerFrame(2,
                new Attack(this, imageStatesFromFolder("assets/monster/Iron_Boar/walking", imageRenderer)));
        fsm.setInitialState(idle);
        fsm.addTransition(from(idle).when(WALK).to(walking));
        fsm.addTransition(from(walking).when(STOP).to(idle));
        fsm.addTransition(from(idle).when(DAMAGED).to(damaged));
        fsm.addTransition(from(idle).when(DIE).to(die));
        fsm.addTransition(from(walking).when(DAMAGED).to(damaged));
        fsm.addTransition(from(walking).when(DIE).to(die));
        fsm.addTransition(from(damaged).when(ATTACK).to(attacking));
        fsm.addTransition(from(attacking).when(DIE).to(die));
        fsm.addTransition(from(attacking).when(DAMAGED).to(damaged));
    }

    public void setTarget(Sprite target) {
        this.target = target;
    }

    public void attack(){
        fsm.trigger(ATTACK);
    }


    public int getDamage() {
        return damage;
    }

    public void move() {
        face = walkingDirection;
        fsm.trigger(WALK);
    }

    public void stop() {
        walkingDirection = (walkingDirection == LEFT)? RIGHT : LEFT;
        fsm.trigger(STOP);
    }

    public void update() {
        fsm.update();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        fsm.render(g);
    }

    @Override
    public void onDamaged(Sprite attacker, Rectangle damageArea, int damage) {
        hpBar.onDamaged(attacker, damageArea, damage);
        setTarget(attacker);
        if (hpBar.isHarmless(damage))
            setResponseDirection(null);


        if (hpBar.isDead()) {
            fsm.trigger(DIE);
        } else {
            fsm.trigger(DAMAGED);
        }
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

    public void setResponseDirection(Direction responseDirection) {
        this.responseDirection = responseDirection;
    }

    public Direction getResponseDirection() {
        return responseDirection;
    }

    public Direction getWalkingDirection(){
        return walkingDirection;
    }

    public Sprite getTarget(){
        return target;
    }


}
