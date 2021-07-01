package boss;

import fsm.CyclicSequence;
import fsm.ImageState;

import java.util.List;
import java.util.Random;

public class Idle extends CyclicSequence {
    private final Boss boss;
    public final int randomRange = 10;
    public final int base = 4;
    private final Random randomGenerator = new Random();
    private int remainTime = randomGenerator.nextInt(randomRange) + base;
    public Idle(Boss boss, List<ImageState> states) {
        super(states);
        this.boss = boss;
    }

    @Override
    public void update() {
        if (boss.isAlive()) {
            remainTime--;
            if (remainTime == 0){
                remainTime = randomGenerator.nextInt(randomRange) + base;
                boss.move();
            }
            super.update();
        }
    }

    @Override
    public String toString() {
        return "Idle";
    }
}
