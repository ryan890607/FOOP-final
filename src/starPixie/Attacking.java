package starPixie;

import fsm.CyclicSequence;
import fsm.ImageState;
import fsm.Sequence;
import magic.Magic;
import model.Direction;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Attacking extends Sequence {
    private final StarPixie starPixie;
    private final Random randomGenerator = new Random();
    private Point start;
    private Point end;
    public Attacking(StarPixie starPixie, List<ImageState> states) {
        super(states);
        this.starPixie = starPixie;
    }

    @Override
    public void update() {
        if (starPixie.isAlive()) {
            if (currentPosition == 0){
                start = new Point(starPixie.getLocation());
                end = new Point(starPixie.getTarget().getLocation());
                if (starPixie.getX() < starPixie.getTarget().getX()){
                    starPixie.setFace(Direction.RIGHT);
                } else {
                    starPixie.setFace(Direction.LEFT);
                }
            } else if (currentPosition == 6){
                starPixie.getWorld().addSprite(new Magic(start, end, "assets/monster/Star_Pixie", 70, false));
            }
            super.update();


        }
    }

    @Override
    public String toString() {
        return "Walking";
    }

    @Override
    protected void onSequenceEnd() {
        currentPosition = 0;
        if (randomGenerator.nextInt(100) % 4 == 0)
            starPixie.approach();
    }
}
