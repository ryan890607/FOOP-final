package skill;

import fsm.ImageState;
import fsm.Sequence;


import java.awt.*;
import java.util.List;

public class Ending extends Sequence {
    private Skill skill;

    public Ending(Skill skill, List<ImageState> states){
        super(states);
        this.skill = skill;
        System.out.println(currentPosition);
    }


    @Override
    protected void onSequenceEnd() {
        currentPosition = 0;
        skill.getWorld().removeSprite(skill);
    }
}