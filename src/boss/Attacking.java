package boss;

import fsm.ImageState;
import fsm.Sequence;
import magic.Magic;
import model.Direction;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Attacking extends Sequence {
    private final Boss boss;
    private final Random randomGenerator = new Random();
    private Point start;
    private Point end;
    public Attacking(Boss boss, List<ImageState> states) {
        super(states);
        this.boss = boss;
    }

    @Override
    public void update() {
        if (boss.isAlive()) {
            switch (randomGenerator.nextInt(100) % 6){
                case 0:
                    boss.skill1();
                case 1,2:
                    boss.skill2();
                case 3,4,5:
                    boss.skill3();
            }
        }
    }

    @Override
    public String toString() {
        return "Attacking";
    }

    @Override
    protected void onSequenceEnd() {
        currentPosition = 0;
        if (randomGenerator.nextInt(100) % 4 == 0)
            boss.approach();
    }
}
