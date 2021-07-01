package boss;


import fsm.State;

import java.awt.*;
import java.util.Random;

public class Wait implements State {
    public final int randomRange = 40;
    public final int base = 50;
    private final Random randomGenerator = new Random();
    private int remainTime = randomGenerator.nextInt(randomRange) + base;
    private final Boss boss;
    public Wait(Boss boss){
        this.boss = boss;
    }
    @Override
    public void update() {
        if (--remainTime <= 0){
            remainTime = randomGenerator.nextInt(randomRange) + base;
            boss.jump();
        }
    }

    @Override
    public void render(Graphics g) {
    }
}
