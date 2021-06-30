package starPixie;

import fsm.CyclicSequence;
import fsm.ImageState;
import fsm.Sequence;

import java.util.List;

public class Die extends Sequence {
    private StarPixie starPixie;
    public Die(StarPixie starPixie, List<ImageState> states) {
        super(states);
        this.starPixie = starPixie;
    }

    @Override
    public String toString() {
        return "Die";
    }

    @Override
    protected void onSequenceEnd() {starPixie.getWorld().removeSprite(starPixie);
    }


}