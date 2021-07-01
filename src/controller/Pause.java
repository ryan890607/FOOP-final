package controller;

import model.LoginWorld;
import model.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Pause {
    private final World world;
    GameLoop.View view;
    public GameLoop gameLoop;
    private Image pauseWindow;

    public Pause(World world) {
        this.world = world;
        try {
            pauseWindow = ImageIO.read(new File("assets/background/pause.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setGameLoop(GameLoop gameLoop) { this.gameLoop = gameLoop; }

    public void setView(GameLoop.View view) {
        this.view = view;
    }

    public World getWorld() {
        return world;
    }

    public void render(Graphics g) {
        world.render(g);
        g.drawImage(pauseWindow, 0, 0, null);
    }
}
