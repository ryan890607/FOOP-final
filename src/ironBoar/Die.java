package ironBoar;

import fsm.CyclicSequence;
import fsm.ImageState;
import fsm.Sequence;

import java.util.List;

public class Die extends Sequence {
    private IronBoar ironBoar;
    public Die(IronBoar ironBoar, List<ImageState> states) {
        super(states);
        this.ironBoar = ironBoar;
    }

    @Override
    public String toString() {
        return "Die";
    }

    @Override
    protected void onSequenceEnd() {
        ironBoar.getWorld().removeSprite(ironBoar);
    }


}