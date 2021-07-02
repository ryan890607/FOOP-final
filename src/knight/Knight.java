package knight;

import dropItem.DropItem;
import dropItem.HpPotion;
import dropItem.MpPotion;
import dropItem.Ring;
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
    public int KNIGHT_HP = 1000;
    public int KNIGHT_MP = 1000;
    private final SpriteShape shape;
    private final FiniteStateMachine fsm;
    private final Set<Direction> directions = new CopyOnWriteArraySet<>();
    private int damage;
    public int jumpLV;
    public int jumpStep;
    public int fallCount;
    private final ArrayList<Integer> jumpSequence = new ArrayList<>(Arrays.asList(-26,-23,-22,-20,-18,-18,-15,-15,-13,-12,-10,-10,-8,-8,-6,-6,-5,-4,-3,-3,-2,-2,-1,-1,0));
    public static final String JUMP = "jump";
    public int lv = 1, exp = 0, levelUping = 0;
    public int hpPotionCount = 0, mpPotionCount = 0, ringCount = 0;

    private Direction responseDirection;
    public enum Event {
        WALK, STOP, ATTACK, DAMAGED, SKILLU, SKILLI
    }

    public Knight(int damage, Point location) {
        super(1000, 1000, true);
        this.damage = damage;
        this.location = location;
        shape = new SpriteShape(new Dimension(300, 300),
                new Dimension(67+30+20, 100), new Dimension(69, 87));
        fsm = new FiniteStateMachine();

        ImageRenderer imageRenderer = new KnightImageRenderer(this);
        State idle = new WaitingPerFrame(8,
                new Idle(this, imageStatesFromFolder("assets/idle", imageRenderer)));
        State walking = new WaitingPerFrame(1,

                new Walking(this, imageStatesFromFolder("assets/walking", imageRenderer)));
        State attacking = new WaitingPerFrame(2,
                new Attacking(this, fsm, imageStatesFromFolder("assets/attack", imageRenderer)));
        State damaged = new WaitingPerFrame(2,
                new Damaged(this, fsm, imageStatesFromFolder("assets/damaged", imageRenderer)));
        State skill_u_state = new  WaitingPerFrame(2,
                new Skill_U(this, fsm, imageStatesFromFolder("assets/attack", imageRenderer)));
        State skill_i_state = new  WaitingPerFrame(2,
                new Skill_I(this, fsm, imageStatesFromFolder("assets/attack", imageRenderer)));
        fsm.setInitialState(idle);
        fsm.addTransition(from(idle).when(WALK).to(walking));
        fsm.addTransition(from(walking).when(STOP).to(idle));
        fsm.addTransition(from(idle).when(ATTACK).to(attacking));
        fsm.addTransition(from(walking).when(ATTACK).to(attacking));
        fsm.addTransition(from(idle).when(DAMAGED).to(damaged));
        fsm.addTransition(from(walking).when(DAMAGED).to(damaged));
        fsm.addTransition(from(attacking).when(DAMAGED).to(damaged));
        fsm.addTransition(from(idle).when(SKILLU).to(skill_u_state));
        fsm.addTransition(from(walking).when(SKILLU).to(skill_u_state));
        fsm.addTransition(from(idle).when(SKILLI).to(skill_i_state));
        fsm.addTransition(from(walking).when(SKILLI).to(skill_i_state));


        jumpLV = 0;
        jumpStep = -1;
	    fallCount = -1;
        int size = jumpSequence.size();
        // for(int i = size-2; i >= 0; --i) jumpSequence.add(-(jumpSequence.get(i)));
    }

    public void attack() {
        fsm.trigger(ATTACK);
    }

    public int getDamage() {
        return damage;
    }

    public void skillU() {
        if (reducedMp(30)) {
            fsm.trigger(SKILLU);
        }
        else fsm.trigger(STOP);
    }
    public void skillI() {
        if (lv < 3) return;
        if (reducedMp(80)) {
            fsm.trigger(SKILLI);
        }
        else fsm.trigger(STOP);
    }
    public void skillO() {
        if (lv < 4) return;
        if (reducedMp(200)) {
            addHp(200);
        }
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
        hpBar.addMp(lv);
        jump(jumpStep);
        if (fallCount >= 0)
            fall(fallCount++);
        if(getLocation().getX()+getBodyOffset().width < 0) location.x = -getBodyOffset().width;
        if(getLocation().getY()+getBodyOffset().height < 0) location.y = -getBodyOffset().height;
        if(getLocation().getX()+getBodyOffset().width > world.getBackground().getWidth(null)-getBodySize().width) location.x = world.getBackground().getWidth(null)-300+getBodyOffset().width;
        if(getLocation().getY()+getBodyOffset().height > world.getBackground().getHeight(null)-getBodySize().height) location.y = world.getBackground().getHeight(null)-300+getBodyOffset().height;
    }

    public void jump(int now) {
        if(now < 0) return;
        if(now == 0) AudioPlayer.playSounds(JUMP);
        world.jump(this, new Dimension(0, jumpSequence.get(now)));
        fallCount = -1;
        jumpStep = now == jumpSequence.size()-1? -1:now+1;
        if (jumpStep < 0) {
            fallCount = 0;
            jumpLV = 0;
        }
    }

    public void fall(int count) {
        if (count < 0)
            return;
        if (count == 0)
            count++;
        world.jump(this, new Dimension(0, count));
    }


    @Override
    public void render(Graphics g) {
        //super.render(g);
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

    @Override
    public void getEXP(int exp) {
        this.exp += exp;
    }

    public boolean isBeingDamaged(){
        return fsm.currentState().toString().equals("Damaged");
    }

    public void LVup() {
        exp -= lv * 100;
        lv++;
        KNIGHT_HP = KNIGHT_MP = lv * 1000;
        hpBar.setMax(KNIGHT_HP, KNIGHT_MP);
        hpBar.setHp(KNIGHT_HP);
        hpBar.setMp(KNIGHT_MP);
        damage += 100;
        levelUping = 100;
    }

    public DropItem pickItem() {
        int xr = getX()+getBodyOffset().width+getBodySize().width, xl = getX()+getBodyOffset().width,
                yd = getY()+getBodyOffset().height+getBodySize().height, yu = getY()+getBodyOffset().width;
        for(DropItem dropItem : getWorld().getDropItems()) {
            //System.out.println(dropItem.getX() + "," + (getX()+getBodyOffset().width+getBodySize().width) + "," + dropItem.getX()+50 + "," + getX()+getBodyOffset().width);
            if(dropItem.getX() < xr && dropItem.getX()+50 > xl && dropItem.getY() < yd && dropItem.getY()+50 > yu) {
                if(dropItem instanceof HpPotion) {
                    hpPotionCount++;
                }
                if(dropItem instanceof MpPotion) {
                    mpPotionCount++;
                }
                if(dropItem instanceof Ring) {
                    ringCount++;
                }
                return dropItem;
            }
        }
        return null;
    }

    public void useHpPotion() {
        if(hpPotionCount > 0) {
            hpPotionCount--;
            hpBar.addHp(300);
        }
    }
    public void useMpPotion() {
        if(mpPotionCount > 0) {
            mpPotionCount--;
            hpBar.addMp(300);
        }
    }
}
