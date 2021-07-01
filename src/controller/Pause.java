package controller;

import model.LoginWorld;
import model.Sprite;
import model.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.util.Arrays.stream;

public class Pause {
    public ArrayList<Game> games = new ArrayList<>();
    GameLoop.View view;
    public GameLoop gameLoop;
    private Image pauseWindow, gameover;
    public int nowGame;

    public Pause(Game ... games) {
        addGames(games);
        try {
            pauseWindow = ImageIO.read(new File("assets/background/pause.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            gameover = ImageIO.read(new File("assets/background/gameover.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addGames(Game... games) {
        stream(games).forEach(this::addGame);
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public void setGameLoop(GameLoop gameLoop) { this.gameLoop = gameLoop; }

    public void setView(GameLoop.View view) {
        this.view = view;
    }

    public World getWorld() {
        //System.out.print(nowGame);
        return games.get(nowGame-1).getWorld();
    }

    public void restart(int i, Game game) {
        this.games.remove(i);
        this.games.add(i, game);
    }

    public void render(Graphics g) {
        games.get(nowGame-1).getWorld().render(g);
        if(games.get(nowGame-1).getWorld().getPlayer().isAlive())
            g.drawImage(pauseWindow, 0, 0, null);
        else
            g.drawImage(gameover, 0, 0, null);
    }
}
