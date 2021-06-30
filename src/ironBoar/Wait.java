package ironBoar;


import fsm.State;

import java.awt.*;
import java.util.Random;

public class Wait implements State {
    public final int randomRange = 40;
    public final int base = 50;
    private final Random randomGenerator = new Random();
    private int remainTime = randomGenerator.nextInt(randomRange) + base;
    private final IronBoar ironBoar;
    public Wait(IronBoar ironBoar){
        this.ironBoar = ironBoar;
    }
    @Override
    public void update() {
        if (--remainTime <= 0){
            remainTime = randomGenerator.nextInt(randomRange) + base;
            ironBoar.jump();
        }
    }

    @Override
    public void render(Graphics g) {
    }
}
