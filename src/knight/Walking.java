package knight;

import fsm.CyclicSequence;
import fsm.ImageState;
import model.Direction;

import java.awt.*;
import java.util.List;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Walking extends CyclicSequence {
    public static final String AUDIO_STEP1 = "step1";
    public static final String AUDIO_STEP2 = "step2";
    private final Knight knight;

    public Walking(Knight knight, List<ImageState> states) {
        super(states);
        this.knight = knight;
    }

    @Override
    public void update() {
        if (knight.isAlive()) {
            if(knight.jumpStep >= 0) super.currentPosition = 10;
            super.update();
            for (Direction direction : knight.getDirections()) {
		    if (direction != Direction.UP)
                knight.getWorld().move(knight, new Dimension(direction.translate().width *2, direction.translate().height *2));
            }
	    if (this.knight.jumpStep < 0 && this.knight.fallCount < 0)
		this.knight.fallCount = 0;
        }
    }

    @Override
    public String toString() {
        return "Walking";
    }
}
