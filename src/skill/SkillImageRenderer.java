package skill;


import fsm.ImageRenderer;
import ironBoar.IronBoar;
import magic.Magic;
import model.Direction;

import java.awt.*;

public class SkillImageRenderer implements ImageRenderer {
    protected Skill skill;

    public SkillImageRenderer(Skill skill) { this.skill = skill; }

    @Override
    public void render(Image image, Graphics g) {
        Direction face = skill.getFace();
        Rectangle range = skill.getRange();
        if (face == Direction.RIGHT) {
            g.drawImage(image, range.x + range.width, range.y, -range.width, range.height, null);
        } else {
            g.drawImage(image, range.x, range.y, range.width, range.height, null);
        }

    }
}

