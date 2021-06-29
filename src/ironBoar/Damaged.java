package ironBoar;

import fsm.Sequence;
import fsm.State;
import fsm.StateMachine;

import java.util.List;

public class Damaged extends Sequence {
    private StateMachine stateMachine;
    private IronBoar ironBoar;
    private final int STEP = 15;
    private int step = STEP;
    public Damaged(IronBoar ironBoar, List<? extends State> states){
        super(states);
        this.ironBoar = ironBoar;
    }

    @Override
    public void update() {
        if (ironBoar.isAlive()) {
            currentPosition++;
            if (--step > 0){
                currentPosition = 0;
            } else {
                step = STEP;
            }
            if (ironBoar.getResponseDirection() != null){
                ironBoar.getWorld().move(ironBoar, ironBoar.getResponseDirection().translate());
            }
            if (currentPosition >= states.size()) {
                onSequenceEnd();
            }
        }
    }

    @Override
    protected void onSequenceEnd() {
        currentPosition = 0;
        ironBoar.attack();
    }

    @Override
    public String toString() {
        return "Damaged";
    }
}
