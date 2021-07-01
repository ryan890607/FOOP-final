package model;

import ironBoar.IronBoar;
import starPixie.StarPixie;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class MonsterGenerator {
    private final Random random = new Random();


    public Sprite[] generateIronBoar(Point start, Point end, int number){
        ArrayList<Sprite> monsters = new ArrayList<>();
        int X;
        for (int i = 0; i < number; i++){
            X = random.nextInt(end.x - start.x);
            monsters.add(new IronBoar(100, new Point(X, start.y)));
        }
        return translate(monsters);
    }

    public Sprite[] generateStarPixie(Point start, Point end, int number){
        ArrayList<Sprite> monsters = new ArrayList<>();
        int X;
        for (int i = 0; i < number; i++){
            X = random.nextInt(end.x - start.x);
            monsters.add(new StarPixie(30, new Point(X, start.y)));
        }
        return translate(monsters);
    }

    private Sprite[] translate(ArrayList<Sprite> monsters){
        Sprite[] rtn = new Sprite[monsters.size()];
        for(int i = 0; i < monsters.size(); i++){
            rtn[i] = monsters.get(i);
        }
        return rtn;
    }



}
