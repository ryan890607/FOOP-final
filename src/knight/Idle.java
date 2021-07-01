package knight;

import fsm.CyclicSequence;
import fsm.ImageState;

import java.util.List;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Idle extends CyclicSequence {
    private final Knight knight;

    public Idle(Knight knight, List<ImageState> states) {
        super(states);
        this.knight = knight;
    }

    public void update() {
        super.update();
	if (this.knight.fallCount < 0)
	    this.knight.fallCount = 0;
    }
    @Override
    public String toString() {
        return "Idle";
    }
}
