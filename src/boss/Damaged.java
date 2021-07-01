package boss;

import fsm.Sequence;
import fsm.State;

import java.util.List;

public class Damaged extends Sequence {
    private Boss boss;
    private final int STEP = 15;
    private int step = STEP;
    public Damaged(Boss boss, List<? extends State> states){
        super(states);
        this.boss = boss;
    }

    @Override
    public void update() {
        if (boss.isAlive()) {
            currentPosition++;
            if (--step > 0){
                currentPosition = 0;
            } else {
                step = STEP;
            }
            if (boss.getResponseDirection() != null) {
                boss.getWorld().move(boss, boss.getResponseDirection().translate());
            }
            if (currentPosition >= states.size()) {
                onSequenceEnd();
            }
        }
    }

    @Override
    protected void onSequenceEnd() {
        currentPosition = 0;
        boss.attack();
    }

    @Override
    public String toString() {
        return "Damaged";
    }
}
