package knight;

import model.Sprite;

import java.awt.*;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class HealthPointBar extends Sprite {
    private final int maxHp;
    private final int maxMp;
    public final double threshold = 0.1;
    private Sprite owner;
    private int hp;
    private int mp;
    private boolean showMp;
    public HealthPointBar(int hp, int mp, boolean showMp) {
        this.maxHp = this.hp = hp;
        this.maxMp = this.mp = mp;
        this.showMp = showMp;
    }

    public void setOwner(Sprite owner) {
        this.owner = owner;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        Rectangle range = getRange();
        int width = (int) (hp * owner.getRange().getWidth() / maxHp);
        int widthMp = (int) (mp * owner.getRange().getWidth() / maxMp);
        g.setColor(Color.RED);
        g.fillRect(range.x, range.y, (int) owner.getRange().getWidth(), range.height);
        g.setColor(Color.GREEN);
        g.fillRect(range.x, range.y, width, range.height);
        if (showMp){
            g.setColor(Color.WHITE);
            g.fillRect(range.x, range.y + range.height, (int) owner.getRange().getWidth(), range.height);
            g.setColor(Color.BLUE);
            g.fillRect(range.x, range.y + range.height, widthMp, range.height);
        }
    }

    @Override
    public void onDamaged(Sprite attacker, Rectangle damageArea, int damage) {
        this.hp = Math.max(hp - damage, 0);
    }

    @Override
    public Rectangle getRange() {
        return new Rectangle(owner.getX(), owner.getY() - 15, (int) owner.getRange().getWidth(), 10);
    }

    @Override
    public Dimension getBodyOffset() {
        return new Dimension();
    }

    @Override
    public Dimension getBodySize() {
        return new Dimension();
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public boolean isHarmless(int damage){
        return (double)damage / maxHp <= threshold;
    }

    public boolean reduceMp(int costMp){
        if (mp < costMp) return false;
        this.mp = Math.max(mp - costMp, 0);
        return true;
    }

    public void addHp(int increased){
        this.hp = Math.min(this.hp + increased, maxHp);
    }
}
