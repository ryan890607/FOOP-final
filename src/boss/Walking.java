package boss;

import fsm.CyclicSequence;
import fsm.ImageState;

import java.util.List;
import java.util.Random;


public class Walking extends CyclicSequence {
    private final Boss boss;
    public final int randomRange = 10;
    public final int base = 30;
    private final Random randomGenerator = new Random();
    private int remainTime = randomGenerator.nextInt(randomRange) + base;

    public Walking(Boss boss, List<ImageState> states) {
        super(states);
        this.boss = boss;
    }

    @Override
    public void update() {
        if (boss.isAlive()) {
            remainTime--;
            if (remainTime == 0){
                remainTime = randomGenerator.nextInt(randomRange) + base;
                boss.stop();
            }
            super.update();

            boss.getWorld().move(boss, boss.getWalkingDirection().translate());

        }
    }

    @Override
    public String toString() {
        return "Walking";
    }
}