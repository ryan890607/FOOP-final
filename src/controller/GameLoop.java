package controller;

import ironBoar.IronBoar;
import knight.Knight;
import knight.KnightCollisionHandler;
import media.AudioPlayer;
import model.Direction;
import model.LoginWorld;
import model.MonsterGenerator;
import model.World;
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
    public int running;
    private int from;
    private View view;
    private Login login;
    public Game game;
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

    public void restart() {
        Knight p1 = new Knight(300, new Point(0, 400+768));
        Knight p2 = new Knight(300, new Point(300, 300+768));
        IronBoar m1 = new IronBoar(100, new Point(300, 150));
        IronBoar m2 = new IronBoar(100, new Point(500, 150));
        StarPixie m3 = new StarPixie(50, new Point(300, 430));
        MonsterGenerator generator = new MonsterGenerator();
        int obstacleCount = 1;
        Direction d = Direction.RIGHT;
        ArrayList<Obstacle1> o1 = new ArrayList<Obstacle1>();
        o1.add(new Obstacle1("assets/obstacle/Obstacle4.jpg", new Point(0, 1350), d));

        obstacleCount = 3;
        d = Direction.RIGHT;
        ArrayList<Obstacle2> o2 = new ArrayList<Obstacle2>();
        for (int i = 0; i < obstacleCount; i++) {
            if (i == 0)
                ;
                //o2.add(new Obstacle2("assets/obstacle/Obstacle2.png", new Point((250)%2048, (500)%1536), d));
            else if (i == 1)
                o2.add(new Obstacle2("assets/obstacle/Obstacle2.png", new Point((445+750)%2048, (250+300)%1536), d));
            else if (i == 2)
                o2.add(new Obstacle2("assets/obstacle/Obstacle2.png", new Point((800)%2048, (200)%1536), d));

            if (d == Direction.RIGHT)  d = Direction.LEFT;
            else
                d = Direction.RIGHT;
        }

        obstacleCount = 6;
        d = Direction.LEFT;
        ArrayList<Obstacle3> o3 = new ArrayList<Obstacle3>();
        for (int i = 0; i < obstacleCount; i++) {
            if (i == 0)
                o3.add(new Obstacle3("assets/obstacle/Obstacle3.png", new Point((680)%2048, (800)%1536), d));
            else if (i == 1)
                o3.add(new Obstacle3("assets/obstacle/Obstacle3.png", new Point((680+800)%2048, (950+1000)%1536), d));
            else if (i == 2)
                o3.add(new Obstacle3("assets/obstacle/Obstacle3.png", new Point((250)%2048, (1036)%1536), d));
            else if (i == 3)
                o3.add(new Obstacle3("assets/obstacle/Obstacle3.png", new Point((400)%2048, (330)%1536), d));
            else if (i == 4)
                o3.add(new Obstacle3("assets/obstacle/Obstacle3.png", new Point((100)%2048, (600)%1536), d));
            else if (i == 5)
                o3.add(new Obstacle3("assets/obstacle/Obstacle3.png", new Point((0)%2048, (330)%1536), d));

            if (d == Direction.RIGHT)
                d = Direction.LEFT;
            else
                d = Direction.RIGHT;
        }

        World world = new World("assets/background/fallguys4times.jpg", o1, o2, o3, new KnightCollisionHandler(), p1, p2, m1, m2, m3);  // model
        world.addSprites(generator.generateIronBoar(new Point(0, 1350-170), new Point(2048, 1350-170), 3));
        world.addSprites(generator.generateStarPixie(new Point(0, 1350-170), new Point(2048, 1350-170), 3));

        game = new Game(world, p1, p2);
        pause.restart(world);
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
                    getWorld().playSound();
                    while (running == 1) {
                        World world = getWorld();
                        world.update();
                        view.render(1, world);
                        delay(15);
                    }
                    from = 1;
                    //if(running == 0 && getWorld().clip != null) AudioPlayer.stopSounds(getWorld().clip);
                    break;
                case 2: // pause
                    while (running == 2) {
                        view.render(2, pause);
                        delay(15);
                    }
                    from = 2;
                    if(running == 0 && getWorld().clip != null) AudioPlayer.stopSounds(getWorld().clip);
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
