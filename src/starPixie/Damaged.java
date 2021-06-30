package starPixie;

import fsm.Sequence;
import fsm.State;
import fsm.StateMachine;

import java.util.List;

public class Damaged extends Sequence {
    private StarPixie starPixie;
    private final int STEP = 15;
    private int step = STEP;
    public Damaged(StarPixie starPixie, List<? extends State> states){
        super(states);
        this.starPixie = starPixie;
    }

    @Override
    public void update() {
        if (starPixie.isAlive()) {
            currentPosition++;
            if (--step > 0){
                currentPosition = 0;
            } else {
                step = STEP;
            }
            if (starPixie.getResponseDirection() != null) {
                starPixie.getWorld().move(starPixie, starPixie.getResponseDirection().translate());
            }
            if (currentPosition >= states.size()) {
                onSequenceEnd();
            }
        }
    }

    @Override
    protected void onSequenceEnd() {
        currentPosition = 0;
        starPixie.attack();
    }

    @Override
    public String toString() {
        return "Damaged";
    }
}
