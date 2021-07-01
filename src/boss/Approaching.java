package boss;

import fsm.CyclicSequence;
import fsm.ImageState;
import model.Direction;

import java.util.List;
import java.util.Random;

public class Approaching extends CyclicSequence {
    private Boss boss;
    public final int randomRange = 10;
    public final int base = 4;
    private final Random randomGenerator = new Random();
    private int remainTime = randomGenerator.nextInt(randomRange) + base;
    public Approaching(Boss boss, List<ImageState> states) {
        super(states);
        this.boss = boss;
    }

    @Override
    public void update() {
        if (boss.isAlive()) {
            remainTime--;
            if (remainTime == 0){
                remainTime = randomGenerator.nextInt(randomRange) + base;
                boss.attack();
                return;
            }
            super.update();
            if (boss.getX() < boss.getTarget().getX()){
                boss.setFace(Direction.RIGHT);
                boss.getWorld().move(boss, Direction.RIGHT.translate());
            } else {
                boss.setFace(Direction.LEFT);
                boss.getWorld().move(boss, Direction.LEFT.translate());
            }
        }
    }

}
