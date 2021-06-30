package magic;

import fsm.CyclicSequence;
import fsm.ImageState;
import fsm.Sequence;
import fsm.State;
import model.Direction;

import java.awt.*;
import java.util.List;

public class Attacking extends CyclicSequence {
    private Magic magic;
    public Attacking(Magic magic, List<ImageState> states){
        super(states);
        this.magic = magic;
    }

    @Override
    public void update() {
        super.update();
        if (magic.getFace() == Direction.LEFT){
            if (magic.getEndX() > magic.getX())
                magic.goEnding();
            else magic.getWorld().move(magic, Direction.LEFT.translate());
        } else {
            if (magic.getEndX() < magic.getX())
                magic.goEnding();
            else magic.getWorld().move(magic, Direction.RIGHT.translate());
        }

    }

}
