package controller;

import media.AudioPlayer;
import model.LoginWorld;
import model.World;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class GameLoop {
    public int running;
    private View view;
    private Login login;
    private Game game;

    public GameLoop(Login login, Game game) {
        this.login = login;
        this.game = game;
        login.setGameLoop(this);
        game.setGameLoop(this);
    }

    public void start() {
        new Thread(this::gameLoop).start();
    }

    private void gameLoop() {
        this.view = login.view;
        running = 0;
        getLoginWorld().playSound();
        while (running == 0) {
            LoginWorld world = getLoginWorld();
            world.update();
            view.render(world);
            delay(15);
        }
        AudioPlayer.stopSounds(getLoginWorld().clip);
        getWorld().playSound();
        while (running == 1) {
            World world = getWorld();
            world.update();
            view.render(world);
            delay(15);
        }
    }

    protected World getWorld() { return game.getWorld(); }

    protected LoginWorld getLoginWorld() { return login.getWorld(); }

    public void stop() {
        running++;
    }

    private void delay(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public interface View {
        void render(World world);
        void render(LoginWorld loginWorld);
    }
}
