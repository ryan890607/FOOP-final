package knight;

import fsm.CyclicSequence;
import fsm.State;
import fsm.StateMachine;

import java.awt.*;
import java.util.List;

public class Damaged extends CyclicSequence {
    private Knight knight;
    private StateMachine stateMachine;
    private final int a = 8;
    private final int v = 40;
    private int currentV = v;
    public Damaged(Knight knight, StateMachine stateMachine, List<? extends State> states){
        super(states);
        this.knight = knight;
        this.stateMachine = stateMachine;
    }

    @Override
    public void update() {
        if (knight.isAlive()) {
            knight.getWorld().move(knight, new Dimension(0, -currentV));
            Dimension responseDirection = knight.getResponseDirection().translate();
            knight.getWorld().move(knight, new Dimension(responseDirection.width * 2, responseDirection.height));
            if (currentV == -v) {
                currentV = v;
                stateMachine.reset();
		this.knight.fallCount = 0;  //start falling
            } else currentV -= a;
        }
    }

    @Override
    public String toString() {
        return "Damaged";
    }

}
