package starPixie;


import fsm.State;

import java.awt.*;
import java.util.Random;

public class Wait implements State {
    public final int randomRange = 40;
    public final int base = 50;
    private final Random randomGenerator = new Random();
    private int remainTime = randomGenerator.nextInt(randomRange) + base;
    private final StarPixie starPixie;
    public Wait(StarPixie starPixie){
        this.starPixie = starPixie;
    }
    @Override
    public void update() {
        if (--remainTime <= 0){
            remainTime = randomGenerator.nextInt(randomRange) + base;
            starPixie.jump();
        }
    }

    @Override
    public void render(Graphics g) {
    }
}
