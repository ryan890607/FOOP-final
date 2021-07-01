package boss;

import fsm.ImageState;
import fsm.Sequence;
import magic.Magic;
import model.Direction;
import starPixie.StarPixie;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Skill1 extends Sequence {
    private final Boss boss;
    private final Random randomGenerator = new Random();
    private Point start;
    private Point end;
    public Skill1(Boss boss, List<ImageState> states) {
        super(states);
        this.boss= boss;
    }

    @Override
    public void update() {
        if (boss.isAlive()) {
            if (currentPosition == 0){
                start = new Point(boss.getLocation());
                end = new Point(boss.getTarget().getLocation());
                if (boss.getX() < boss.getTarget().getX()){
                    boss.setFace(Direction.RIGHT);
                } else {
                    boss.setFace(Direction.LEFT);
                }
            } else if (currentPosition == 13){
                boss.getWorld().addSprite(new Magic(start, end, "assets/monster/Boss/skill1", 300, true));
            }
            super.update();


        }
    }

    @Override
    public String toString() {
        return "Skill1";
    }

    @Override
    protected void onSequenceEnd() {
        currentPosition = 0;
        if (randomGenerator.nextInt(100) % 4 == 0)
            boss.approach();
    }
}
