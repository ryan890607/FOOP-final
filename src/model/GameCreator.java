package model;

import controller.Game;
import ironBoar.IronBoar;
import knight.Knight;
import knight.KnightCollisionHandler;
import obstacles.Obstacle1;
import obstacles.Obstacle2;
import obstacles.Obstacle3;
import starPixie.StarPixie;

import java.awt.*;
import java.util.ArrayList;

public class GameCreator {
    public GameCreator() {
    }

    public Game create(int id) {
        Knight p1 = new Knight(300, new Point(0, 400+768));
        //Knight p2 = new Knight(300, new Point(300, 300+768));
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

        World world = new World("assets/background/fallguys4times.jpg", o1, o2, o3, new KnightCollisionHandler(), p1, m1, m2, m3);  // model
        world.addSprites(generator.generateIronBoar(new Point(0, 1350-170), new Point(2048, 1350-170), 3));
        world.addSprites(generator.generateStarPixie(new Point(0, 1350-170), new Point(2048, 1350-170), 3));

        return new Game(world, p1, id);
    }
}
