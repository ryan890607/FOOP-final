package controller;

import ironBoar.IronBoar;
import knight.Knight;
import knight.KnightCollisionHandler;
import media.AudioPlayer;
import model.*;
import obstacles.Obstacle1;
import obstacles.Obstacle2;
import obstacles.Obstacle3;
import starPixie.StarPixie;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class GameLoop {
    public final int LOGIN = 0;
    public final int G1 = 1;
    public final int G2 = 2;
    public final int PAUSE = 100;
    public int running;
    private int from;
    private View view;
    private Login login;
    public ArrayList<Game> games;
    private Pause pause;

    public GameLoop(Login login, Pause pause) {
        this.login = login;
        this.games = pause.games;
        this.pause = pause;
        login.setGameLoop(this);
        for(Game g : games) g.setGameLoop(this);
        pause.setGameLoop(this);
    }

    public void start() {
        new Thread(this::gameLoop).start();
    }

    public void restart(int i) {

        Game newGame = new GameCreator().create(i);

        games.remove(i);
        games.add(i, newGame);
        pause.restart(i, games.get(i));
    }

    private void gameLoop() {
        this.view = login.view;
        from = -1; running = 0;
        while(running >= 0) {
            switch(running) {
                case LOGIN: // login
                    getLoginWorld().playSound();
                    while (running == LOGIN) {
                        LoginWorld world = getLoginWorld();
                        world.update();
                        view.render(LOGIN, world);
                        delay(15);
                    }
                    from = LOGIN;
                    AudioPlayer.stopSounds(getLoginWorld().clip);
                    break;
                case G1: // game1
                    getWorld(G1).playSound();
                    while (running == G1) {
                        World world = getWorld(G1);
                        world.update();
                        view.render(G1, world);
                        delay(15);
                        if(!games.get(G1-1).getWorld().getPlayer().isAlive()) {
                            running = PAUSE; 00000
                        }
                    }
                    from = G1;
                    if(running == LOGIN && getWorld(G1).clip != null) AudioPlayer.stopSounds(getWorld(G1).clip);
                    break;
                case G2: // game1
                    getWorld(G2).playSound();
                    while (running == G2) {
                        World world = getWorld(G2);
                        world.update();
                        view.render(G2, world);
                        delay(15);
                        if(!games.get(G2-1).getWorld().getPlayer().isAlive()) {
                            running = PAUSE;
                        }
                    }
                    from = G2;
                    if(running == LOGIN && getWorld(G2).clip != null) AudioPlayer.stopSounds(getWorld(G2).clip);
                    break;
                case PAUSE: // pause
                    while (running == PAUSE) {
                        view.render(PAUSE, pause);
                        delay(15);
                    }
                    from = PAUSE;
                    if(running == LOGIN && getWorld(pause.nowGame).clip != null) AudioPlayer.stopSounds(getWorld(pause.nowGame).clip);
                    break;
            }
        }
    }

    protected World getWorld(int i) { return games.get(i-1).getWorld(); }

    protected LoginWorld getLoginWorld() { return login.getWorld(); }

    public void stop(int from, int to) {
        this.from = from;
        running = to;
        if(to == PAUSE) pause.nowGame = from;
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