package starPixie;

import fsm.CyclicSequence;
import fsm.ImageState;


import java.util.List;
import java.util.Random;


public class Walking extends CyclicSequence {
    private final StarPixie starPixie;
    public final int randomRange = 10;
    public final int base = 30;
    private final Random randomGenerator = new Random();
    private int remainTime = randomGenerator.nextInt(randomRange) + base;

    public Walking(StarPixie starPixie, List<ImageState> states) {
        super(states);
        this.starPixie = starPixie;
    }

    @Override
    public void update() {
        if (starPixie.isAlive()) {
            remainTime--;
            if (remainTime == 0){
                remainTime = randomGenerator.nextInt(randomRange) + base;
                starPixie.stop();
            }
            super.update();

            starPixie.getWorld().move(starPixie, starPixie.getWalkingDirection().translate());

        }
    }

    @Override
    public String toString() {
        return "Walking";
    }
}