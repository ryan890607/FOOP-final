package model;

import knight.HealthPointBar;
import media.AudioPlayer;

import java.awt.*;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public abstract class HealthPointSprite extends Sprite {
    public static final String AUDIO_DIE = "Die";

    protected HealthPointBar hpBar;

    public HealthPointSprite(int hp, int mp, boolean showMp) {
        this.hpBar = new HealthPointBar(hp, mp, showMp);
        hpBar.setOwner(this);
    }

    public void addHp(int increased){
        hpBar.addHp(increased);
    }

    public boolean reducedMp(int costMp){
        return hpBar.reduceMp(costMp);
    }

    @Override
    public void onDamaged(Sprite attacker, Rectangle damageArea, int damage) {
        hpBar.onDamaged(attacker, damageArea, damage);
        if (hpBar.isDead()) {
            world.removeSprite(this);
            AudioPlayer.playSounds(AUDIO_DIE);
        }
    }

    @Override
    public void render(Graphics g) {
        hpBar.render(g);
    }

    @Override
    public boolean isAlive() {
        return !hpBar.isDead();
    }
}
