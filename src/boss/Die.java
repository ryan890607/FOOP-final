package boss;

import fsm.ImageState;
import fsm.Sequence;

import java.util.List;

public class Die extends Sequence {
    private Boss boss;
    public Die(Boss boss, List<ImageState> states) {
        super(states);
        this.boss = boss;
    }

    @Override
    public String toString() {
        return "Die";
    }

    @Override
    protected void onSequenceEnd() {
        boss.getWorld().removeSprite(boss);
    }


}