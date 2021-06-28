package knight;

import fsm.CyclicSequence;
import fsm.ImageState;

import java.util.List;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Jumping extends CyclicSequence {
    public Jumping(List<ImageState> states) {
        super(states);
    }

    @Override
    public String toString() {
        return "Jumping";
    }
}
