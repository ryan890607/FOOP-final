package ironBoar;

import fsm.CyclicSequence;
import fsm.ImageState;


import java.util.List;
import java.util.Random;


public class Walking extends CyclicSequence {
    private final IronBoar ironBoar;
    public final int randomRange = 10;
    public final int base = 30;
    private final Random randomGenerator = new Random();
    private int remainTime = randomGenerator.nextInt(randomRange) + base;

    public Walking(IronBoar ironBoar, List<ImageState> states) {
        super(states);
        this.ironBoar = ironBoar;
    }

    @Override
    public void update() {
        if (ironBoar.isAlive()) {
            remainTime--;
            if (remainTime == 0){
                remainTime = randomGenerator.nextInt(randomRange) + base;
                ironBoar.stop();
            }
            super.update();

            ironBoar.getWorld().move(ironBoar, ironBoar.getWalkingDirection().translate());

        }
    }

    @Override
    public String toString() {
        return "Walking";
    }
}