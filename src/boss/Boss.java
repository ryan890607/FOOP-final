package boss;


import fsm.FiniteStateMachine;
import fsm.ImageRenderer;
import fsm.State;
import fsm.WaitingPerFrame;
import media.AudioPlayer;
import model.*;

import java.awt.*;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import static fsm.FiniteStateMachine.Transition.from;
import static model.Direction.LEFT;
import static model.Direction.RIGHT;
import static boss.Boss.Event.*;
import static utils.ImageStateUtils.imageStatesFromFolder;

public class Boss extends HealthPointSprite implements Dangerous {
    public static final int HP = 20000;

    private final SpriteShape shape;
    private final FiniteStateMachine fsm;
    //private final FiniteStateMachine fsm_jump;
    private final Set<Direction> directions = new CopyOnWriteArraySet<>();
    private final int damage;
    private Direction responseDirection;
    private Direction walkingDirection;
    private Sprite target;
    public static final String DIE = "die";




    public enum Event {
        WALK, STOP, DAMAGED, DIEQQ, ATTACK, JUMP, APPROACH, SKILL1, SKILL2, SKILL3
    }

    public Boss(int damage, Point location) {
        super(HP, 0, false);
        this.damage = damage;
        this.location = location;
        walkingDirection = LEFT;
        shape = new SpriteShape(new Dimension(450, 450),
                new Dimension(50, 50), new Dimension(350, 350));
        fsm = new FiniteStateMachine();
        //fsm_jump = new FiniteStateMachine();

        ImageRenderer imageRenderer = new BossImageRenderer(this);
        State idle = new WaitingPerFrame(8,
                new Idle(this, imageStatesFromFolder("assets/monster/Boss/idle", imageRenderer)));
        State walking = new WaitingPerFrame(2,
                new Walking(this, imageStatesFromFolder("assets/monster/Boss/walking", imageRenderer)));
        State damaged = new WaitingPerFrame(2,
                new Damaged(this, imageStatesFromFolder("assets/monster/Boss/damaged", imageRenderer)));
        State die = new WaitingPerFrame(6,
                new Die(this, imageStatesFromFolder("assets/monster/Boss/die", imageRenderer)));
        State attacking = new WaitingPerFrame(24,
                new Attacking(this, imageStatesFromFolder("assets/monster/Boss/attack", imageRenderer)));
        State approaching = new WaitingPerFrame(2,
                new Approaching(this, imageStatesFromFolder("assets/monster/Boss/walking", imageRenderer)));
        State skill1 = new WaitingPerFrame(2,
                new Skill1(this, imageStatesFromFolder("assets/monster/Boss/skill1/action", imageRenderer)));
        State skill2 = new WaitingPerFrame(2,
                new Skill2(this, imageStatesFromFolder("assets/monster/Boss/skill2/action", imageRenderer)));
        State skill3 = new WaitingPerFrame(2,
                new Skill3(this, imageStatesFromFolder("assets/monster/Boss/skill3/action", imageRenderer)));
        /*State wait = new WaitingPerFrame(2,
                new Wait(this));
        State jump = new WaitingPerFrame(2,
                new Jump(this, fsm_jump));*/
        fsm.setInitialState(idle);
        fsm.addTransition(from(idle).when(WALK).to(walking));
        fsm.addTransition(from(walking).when(STOP).to(idle));
        fsm.addTransition(from(idle).when(DAMAGED).to(damaged));
        fsm.addTransition(from(idle).when(DIEQQ).to(die));
        fsm.addTransition(from(walking).when(DAMAGED).to(damaged));
        fsm.addTransition(from(walking).when(DIEQQ).to(die));
        fsm.addTransition(from(damaged).when(ATTACK).to(attacking));
        fsm.addTransition(from(attacking).when(DIEQQ).to(die));
        fsm.addTransition(from(attacking).when(DAMAGED).to(damaged));
        fsm.addTransition(from(attacking).when(APPROACH).to(approaching));
        fsm.addTransition(from(approaching).when(ATTACK).to(attacking));
        fsm.addTransition(from(attacking).when(SKILL1).to(skill1));
        fsm.addTransition(from(attacking).when(SKILL2).to(skill2));
        fsm.addTransition(from(attacking).when(SKILL3).to(skill3));
        fsm.addTransition(from(skill1).when(ATTACK).to(attacking));
        fsm.addTransition(from(skill2).when(ATTACK).to(attacking));
        fsm.addTransition(from(skill3).when(ATTACK).to(attacking));
        /*fsm_jump.setInitialState(wait);
        fsm_jump.addTransition(from(wait).when(JUMP).to(jump));*/
    }



    public void setTarget(Sprite target) {
        this.target = target;
    }

    public void attack(){
        fsm.trigger(ATTACK);
    }

    public void skill1(){
        fsm.trigger(SKILL1);
    }

    public void skill2(){
        fsm.trigger(SKILL2);
    }

    public void skill3(){
        fsm.trigger(SKILL3);
    }

    public void approach(){ fsm.trigger(APPROACH); }

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
        //fsm_jump.update();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        fsm.render(g);
    }

    @Override
    public void onDamaged(Sprite attacker, Rectangle damageArea, int damage) {
        if (fsm.currentState().toString().equals("Die")) return;
        hpBar.onDamaged(attacker, damageArea, damage);
        setTarget(attacker);
        if (hpBar.isHarmless(damage))
            setResponseDirection(null);


        if (hpBar.isDead()) {
            AudioPlayer.playSounds(DIE);
            fsm.trigger(DIEQQ);
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

    public void jump(){
        //fsm_jump.trigger(JUMP);
    }
}
