package knight;

import fsm.Sequence;
import fsm.State;
import fsm.StateMachine;
import media.AudioPlayer;
import model.Sprite;
import model.World;
import skill.Skill;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Skill_I extends Sequence {
    private final Knight knight;
    private final StateMachine stateMachine;
    private final Set<Integer> damagingStateNumbers = new HashSet<>(List.of(1));
    public static final String SKILL_I = "skilli";
    public Skill_I(Knight knight, StateMachine stateMachine, List<? extends State> states) {
        super(states);
        this.knight = knight;
        this.stateMachine = stateMachine;
        damagingStateNumbers.add(5);
    }

    @Override
    public void update() {
        if (knight.isAlive()) {
            if (currentPosition == 0) AudioPlayer.playSounds(SKILL_I);
            super.update();
            if (damagingStateNumbers.contains(currentPosition)) {
                effectDamage();
            }
        }
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        Rectangle damageArea = damageArea();
        g.setColor(Color.BLUE);
        g.drawRect(damageArea.x, damageArea.y, damageArea.width, damageArea.height);
    }

    private void effectDamage() {
        World world = knight.getWorld();
        Rectangle damageArea = damageArea();
        var sprites = world.getSprites(damageArea);

        for (Sprite sprite : sprites) {
            if (knight != sprite) {
                sprite.setResponseDirection(knight.getFace());
                sprite.onDamaged(knight, damageArea, knight.getDamage());
            }
        }
        knight.getWorld().addSprite(new Skill(damageArea.getLocation(), "assets/skillI"));
    }

    private Rectangle damageArea() {
        return knight.getAttackArea(new Dimension(87+15+50+15, 70),
                new Dimension(180, 140));
    }

    @Override
    protected void onSequenceEnd() {
        currentPosition = 0;
        stateMachine.reset();
    }

    @Override
    public String toString() {
        return "Attacking";
    }
}
