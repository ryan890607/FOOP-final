package controller;

import model.LoginWorld;
import model.World;

public class Login {
    private final LoginWorld loginWorld;
    GameLoop.View view;
    GameLoop gameLoop;

    public Login(LoginWorld loginWorld) {
        this.loginWorld = loginWorld;
    }

    public void loginSuccess() { gameLoop.stop(); }

    public void setGameLoop(GameLoop gameLoop) { this.gameLoop = gameLoop; }

    public void setView(GameLoop.View view) {
        this.view = view;
    }

    protected LoginWorld getWorld() {
        return loginWorld;
    }
}
