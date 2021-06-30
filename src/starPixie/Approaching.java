package starPixie;

import fsm.CyclicSequence;
import fsm.ImageState;
import fsm.Sequence;
import magic.Magic;
import model.Direction;

import java.util.List;
import java.util.Random;

public class Approaching extends CyclicSequence {
    private StarPixie starPixie;
    public final int randomRange = 10;
    public final int base = 4;
    private final Random randomGenerator = new Random();
    private int remainTime = randomGenerator.nextInt(randomRange) + base;
    public Approaching(StarPixie starPixie, List<ImageState> states) {
        super(states);
        this.starPixie = starPixie;
    }

    @Override
    public void update() {
        if (starPixie.isAlive()) {
            remainTime--;
            if (remainTime == 0){
                remainTime = randomGenerator.nextInt(randomRange) + base;
                starPixie.attack();
                return;
            }
            super.update();
            if (starPixie.getX() < starPixie.getTarget().getX()){
                starPixie.setFace(Direction.RIGHT);
                starPixie.getWorld().move(starPixie, Direction.RIGHT.translate());
            } else {
                starPixie.setFace(Direction.LEFT);
                starPixie.getWorld().move(starPixie, Direction.LEFT.translate());
            }
        }
    }

}
