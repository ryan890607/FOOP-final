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
        int d_x, d_y = magic.start.y - magic.getEndY();
        if (magic.getFace() == Direction.LEFT){
            d_x = magic.start.x - magic.getEndX();
            if (magic.getEndX() > magic.getX()){
                magic.goEnding();
            }
            else magic.getWorld().move(magic, new Dimension(-6, -6 * (d_y/d_x)));
        } else {
            d_x = magic.getEndX() - magic.start.x;
            if (magic.getEndX() < magic.getX()){
                magic.goEnding();
            }
            else magic.getWorld().move(magic, new Dimension(6, -6 * (d_y/d_x)));
        }
    }

}
