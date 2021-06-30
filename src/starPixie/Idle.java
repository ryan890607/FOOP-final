package starPixie;

import fsm.CyclicSequence;
import fsm.ImageState;
import model.Direction;

import java.util.List;
import java.util.Random;

public class Idle extends CyclicSequence {
    private final StarPixie starPixie;
    public final int randomRange = 10;
    public final int base = 4;
    private final Random randomGenerator = new Random();
    private int remainTime = randomGenerator.nextInt(randomRange) + base;
    public Idle(StarPixie starPixie, List<ImageState> states) {
        super(states);
        this.starPixie = starPixie;
    }

    @Override
    public void update() {
        if (starPixie.isAlive()) {
            remainTime--;
            if (remainTime == 0){
                remainTime = randomGenerator.nextInt(randomRange) + base;
                starPixie.move();
            }
            super.update();
        }
    }

    @Override
    public String toString() {
        return "Idle";
    }
}
