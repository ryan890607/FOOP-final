package ironBoar;

import fsm.CyclicSequence;
import fsm.ImageState;
import model.Direction;

import java.util.List;
import java.util.Random;

public class Idle extends CyclicSequence {
    private final IronBoar ironBoar;
    public final int randomRange = 10;
    public final int base = 4;
    private final Random randomGenerator = new Random();
    private int remainTime = randomGenerator.nextInt(randomRange) + base;
    public Idle(IronBoar ironBoar, List<ImageState> states) {
        super(states);
        this.ironBoar = ironBoar;
    }

    @Override
    public void update() {
        if (ironBoar.isAlive()) {
            remainTime--;
            if (remainTime == 0){
                remainTime = randomGenerator.nextInt(randomRange) + base;
                ironBoar.move();
            }
            super.update();
        }
    }

    @Override
    public String toString() {
        return "Idle";
    }
}
