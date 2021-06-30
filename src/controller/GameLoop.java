package controller;

import model.LoginWorld;
import model.World;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class GameLoop {
    private boolean running;
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
        running = true;
        while (running) {
            LoginWorld world = getLoginWorld();
            world.update();
            view.render(world);
            delay(15);
        }
        running = true;
        while (running) {
            World world = getWorld();
            world.update();
            view.render(world);
            delay(15);
        }
    }

    protected World getWorld() { return game.getWorld(); }

    protected LoginWorld getLoginWorld() { return login.getWorld(); }

    public void stop() {
        running = false;
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
