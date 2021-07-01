package controller;

import knight.Knight;
import model.Direction;
import model.Sprite;
import model.World;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Game {
    private final Knight p1;
    private final Knight p2;
    private final World world;
    GameLoop.View view;
    GameLoop gameLoop;

    public Game(World world, Knight p1, Knight p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.world = world;
    }

    public void setGameLoop(GameLoop gameLoop) { this.gameLoop = gameLoop; }

    public void setView(GameLoop.View view) {
        this.view = view;
    }

    public void moveKnight(int playerNumber, Direction direction) {
        getPlayer(playerNumber).move(direction);
    }

    public void stopKnight(int playerNumber, Direction direction) {
        getPlayer(playerNumber).stop(direction);
    }

    public void attack(int playerNumber) {
        getPlayer(playerNumber).attack();
    }

    public void jump(int playerNumber) {
        if(getPlayer(playerNumber).fallCount >= 0) return;
        getPlayer(playerNumber).jumpLV++;
        if(getPlayer(playerNumber).jumpLV < 3) getPlayer(playerNumber).jump(0);
        else getPlayer(playerNumber).jumpLV--;
    }

    public Knight getPlayer(int playerNumber) {
        return playerNumber == 1 ? p1 : p2;
    }

    protected World getWorld() {
        return world;
    }
}
