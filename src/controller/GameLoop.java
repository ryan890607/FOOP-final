package controller;

import media.AudioPlayer;
import model.LoginWorld;
import model.World;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class GameLoop {
    public int running;
    private int from;
    private View view;
    private Login login;
    private Game game;
    private Pause pause;

    public GameLoop(Login login, Game game, Pause pause) {
        this.login = login;
        this.game = game;
        this.pause = pause;
        login.setGameLoop(this);
        game.setGameLoop(this);
        pause.setGameLoop(this);
    }

    public void start() {
        new Thread(this::gameLoop).start();
    }

    private void gameLoop() {
        this.view = login.view;
        from = -1; running = 0;
        while(running >= 0) {
            switch(running) {
                case 0: // login
                    getLoginWorld().playSound();
                    while (running == 0) {
                        LoginWorld world = getLoginWorld();
                        world.update();
                        view.render(0, world);
                        delay(15);
                    }
                    from = 0;
                    AudioPlayer.stopSounds(getLoginWorld().clip);
                    break;
                case 1: // game
                    if(from == 0) getWorld().playSound();
                    while (running == 1) {
                        World world = getWorld();
                        world.update();
                        view.render(1, world);
                        delay(15);
                    }
                    from = 1;
                    if(running == 0) AudioPlayer.stopSounds(getWorld().clip);
                    break;
                case 2: // pause
                    while (running == 2) {
                        World world = getWorld();
                        view.render(2, pause);
                        delay(15);
                    }
                    from = 2;
                    if(running == 0) AudioPlayer.stopSounds(getWorld().clip);
                    break;
            }
        }
    }

    protected World getWorld() { return game.getWorld(); }

    protected LoginWorld getLoginWorld() { return login.getWorld(); }

    public void stop(int from, int to) {
        this.from = from;
        running = to;
    }

    private void delay(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public interface View {
        void render(int running, World world);
        void render(int running, LoginWorld loginWorld);
        void render(int running, Pause pause);
    }
}
