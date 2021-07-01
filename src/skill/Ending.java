package skill;

import fsm.ImageState;
import fsm.Sequence;


import java.util.List;

public class Ending extends Sequence {
    private Skill skill;

    public Ending(Skill skill, List<ImageState> states){
        super(states);
        this.skill = skill
        ;
    }
    @Override
    protected void onSequenceEnd() {
        skill.getWorld().removeSprite(skill);
    }
}