package ironBoar;

import fsm.Sequence;
import fsm.StateMachine;

import java.awt.*;


public class Jump extends Sequence {
    private final StateMachine stateMachine;
    private final IronBoar ironBoar;
    private final int a = 2;
    private final int v = 10;
    private int currentV = v;

    public Jump(IronBoar ironBoar, StateMachine fsm){
        super(null);
        this.ironBoar = ironBoar;
        stateMachine = fsm;
    }

    @Override
    public void update() {
        if (ironBoar.isAlive()) {
            ironBoar.getWorld().move(ironBoar, new Dimension(0, -currentV));
            if (currentV == -v) {
                currentV = v;
                onSequenceEnd();
            } else currentV -= a;
        }
    }

    @Override
    protected void onSequenceEnd() {
        stateMachine.reset();
    }

    @Override
    public String toString() {
        return "Jump";
    }
}