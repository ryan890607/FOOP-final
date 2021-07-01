package controller;

import model.LoginWorld;
import model.World;

public class Login {
    private final LoginWorld loginWorld;
    GameLoop.View view;
    public GameLoop gameLoop;

    public Login(LoginWorld loginWorld) {
        this.loginWorld = loginWorld;
    }

    public void loginSuccess() {
        if(loginWorld.account.equals("kuo2020") && loginWorld.password.equals("e04e04e04")) gameLoop.stop(0, 1);
        else loginWorld.state = 4;
    }

    public void setGameLoop(GameLoop gameLoop) { this.gameLoop = gameLoop; }

    public void setView(GameLoop.View view) {
        this.view = view;
    }

    public LoginWorld getWorld() {
        return loginWorld;
    }
}
