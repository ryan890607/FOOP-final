package magic;

import fsm.ImageState;
import fsm.Sequence;

import java.util.List;

public class Ending extends Sequence {
    private Magic magic;

    public Ending(Magic magic, List<ImageState> states){
        super(states);
        this.magic = magic;
    }
    @Override
    protected void onSequenceEnd() {
        magic.getWorld().removeSprite(magic);
    }
}
