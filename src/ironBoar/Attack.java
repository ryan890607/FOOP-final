package ironBoar;

import fsm.CyclicSequence;
import fsm.ImageState;
import model.Direction;

import java.util.List;

public class Attack extends CyclicSequence {
    private final IronBoar ironBoar;

    public Attack(IronBoar ironBoar, List<ImageState> states) {
        super(states);
        this.ironBoar = ironBoar;
    }

    @Override
    public void update() {
        if (ironBoar.isAlive()) {
            super.update();
            if (ironBoar.getX() < ironBoar.getTarget().getX() + (ironBoar.getFace() == Direction.RIGHT ? 50 : -50)){
                ironBoar.setFace(Direction.RIGHT);
                ironBoar.getWorld().move(ironBoar, Direction.RIGHT.translate());
            } else {
                ironBoar.setFace(Direction.LEFT);
                ironBoar.getWorld().move(ironBoar, Direction.LEFT.translate());
            }
        }
    }

    @Override
    public String toString() {
        return "Walking";
    }
}
