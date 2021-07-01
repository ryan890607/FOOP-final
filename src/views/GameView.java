package views;

import controller.Game;
import controller.GameLoop;
import controller.Login;
import controller.Pause;
import media.AudioPlayer;
import model.Direction;
import model.LoginWorld;
import model.Sprite;
import model.World;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class GameView extends JFrame {
    public static final int HEIGHT = 768;
    public static final int WIDTH = 1024;
    public static final int P1 = 1;
    public static final int P2 = 2;
    private final Canvas canvas = new Canvas();
    private Game game;
    private final Login login;
    private final Pause pause;
    private final GameLoop gameLoop;

    public GameView(Login login, Game game, Pause pause) throws HeadlessException {
        this.login = login;
        this.game = game;
        this.pause = pause;
        login.setView(canvas);
        game.setView(canvas);
        pause.setView(canvas);
        gameLoop = login.gameLoop;
    }

    public void launch() {
        // GUI Stuff
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(canvas);
        setSize(WIDTH, HEIGHT);
        setContentPane(canvas);
        setVisible(true);

        // Keyboard listener
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                setFocusTraversalKeysEnabled(false);
                switch (gameLoop.running) {
                    case 0:
                        if(keyEvent.getKeyCode() == keyEvent.VK_ENTER) {
                            login.loginSuccess();
                            break;
                        }
                        switch (login.getWorld().state) {
                            case 0: case 3: case 4:
                                if(keyEvent.getKeyCode() == keyEvent.VK_TAB) login.getWorld().state = 1;
                                break;
                            case 1:
                                if(keyEvent.getKeyCode() == keyEvent.VK_TAB) login.getWorld().state = 2;
                                if(Character.isLetterOrDigit(keyEvent.getKeyChar()))
                                    login.getWorld().account += keyEvent.getKeyChar();
                                if(keyEvent.getKeyCode() == keyEvent.VK_BACK_SPACE && login.getWorld().account.length() > 0)
                                    login.getWorld().account = login.getWorld().account.substring(0, login.getWorld().account.length()-1);
                                break;
                            case 2:
                                if(keyEvent.getKeyCode() == keyEvent.VK_TAB) login.getWorld().state = 3;
                                if(Character.isLetterOrDigit(keyEvent.getKeyChar()))
                                    login.getWorld().password += keyEvent.getKeyChar();
                                if(keyEvent.getKeyCode() == keyEvent.VK_BACK_SPACE && login.getWorld().password.length() > 0)
                                    login.getWorld().password = login.getWorld().password.substring(0, login.getWorld().password.length()-1);
                                break;
                        }
                        break;
                    case 1:
                        switch (keyEvent.getKeyCode()) {
                            case KeyEvent.VK_W:
                                game.moveKnight(P1, Direction.UP);
                                break;
                            case KeyEvent.VK_S:
                                game.moveKnight(P1, Direction.DOWN);
                                break;
                            case KeyEvent.VK_A:
                                game.moveKnight(P1, Direction.LEFT);
                                break;
                            case KeyEvent.VK_D:
                                game.moveKnight(P1, Direction.RIGHT);
                                break;
                            case KeyEvent.VK_E:
                                game.attack(P1);
                                break;
                            case KeyEvent.VK_SPACE:
                                game.jump(P1);
                                break;
                            case KeyEvent.VK_I:
                                game.skillI(P1);
                                break;
                            case KeyEvent.VK_K:
                                game.moveKnight(P2, Direction.DOWN);
                                break;
                            case KeyEvent.VK_J:
                                game.moveKnight(P2, Direction.LEFT);
                                break;
                            case KeyEvent.VK_L:
                                game.moveKnight(P2, Direction.RIGHT);
                                break;
                            case KeyEvent.VK_U:
                                game.skillU(P1);
                                break;
                            case KeyEvent.VK_ESCAPE:
                                gameLoop.stop(1, 2);
                        }
                        break;
                    case 2:
                        switch (keyEvent.getKeyCode()) {
                            case KeyEvent.VK_ESCAPE:
                                gameLoop.stop(2, 1);
                        }
                }

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                switch (gameLoop.running) {
                    case 1:
                        switch (keyEvent.getKeyCode()) {
                            case KeyEvent.VK_W:
                                game.stopKnight(P1, Direction.UP);
                                break;
                            case KeyEvent.VK_S:
                                game.stopKnight(P1, Direction.DOWN);
                                break;
                            case KeyEvent.VK_A:
                                game.stopKnight(P1, Direction.LEFT);
                                break;
                            case KeyEvent.VK_D:
                                game.stopKnight(P1, Direction.RIGHT);
                                break;
                            case KeyEvent.VK_I:
                                game.stopKnight(P2, Direction.UP);
                                break;
                            case KeyEvent.VK_K:
                                game.stopKnight(P2, Direction.DOWN);
                                break;
                            case KeyEvent.VK_J:
                                game.stopKnight(P2, Direction.LEFT);
                                break;
                            case KeyEvent.VK_L:
                                game.stopKnight(P2, Direction.RIGHT);
                                break;
                        }
                        break;
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println(e.getX() + "," + e.getY());
                switch (gameLoop.running) {
                    case 0:
                        if(e.getX() >= 565 && e.getX() <= 756 && e.getY() >= 330 && e.getY() <= 361) {      // account
                            login.getWorld().state = 1;
                        }
                        else if(e.getX() >= 565 && e.getX() <= 756 && e.getY() >= 373 && e.getY() <= 398) { // password
                            login.getWorld().state = 2;
                        }
                        else if(e.getX() >= 778 && e.getX() <= 874 && e.getY() >= 340 && e.getY() <= 383) { // confirm
                            login.loginSuccess();
                        }
                        else login.getWorld().state = 0;
                        break;
                    case 1:
                        if(e.getX() >= 965 && e.getX() <= 1007 && e.getY() >= 39 && e.getY() <= 78) {   // pause
                            gameLoop.stop(1, 2);
                        }
                        break;
                    case 2:
                        if(game.getPlayer(P1).isAlive()) {
                            if(e.getX() >= 605 && e.getX() <= 661 && e.getY() >= 262 && e.getY() <= 314) {  // resume
                                    gameLoop.stop(2, 1);
                            }
                            if(e.getX() >= 358 && e.getX() <= 606 && e.getY() >= 341 && e.getY() <= 409) {  // restart
                                AudioPlayer.stopSounds(game.getWorld().clip);
                                gameLoop.restart();
                                game = gameLoop.game;
                                game.setView(canvas);
                                gameLoop.stop(2, 1);
                            }
                            if(e.getX() >= 358 && e.getX() <= 606 && e.getY() >= 419 && e.getY() <= 486) {  // login
                                gameLoop.stop(2, 0);
                            }
                        }
                        else {
                            if(e.getX() >= 358 && e.getX() <= 606 && e.getY() >= 341 && e.getY() <= 409) {  // restart
                                AudioPlayer.stopSounds(game.getWorld().clip);
                                gameLoop.restart();
                                game = gameLoop.game;
                                game.setView(canvas);
                                gameLoop.stop(2, 1);
                            }
                            if(e.getX() >= 358 && e.getX() <= 606 && e.getY() >= 419 && e.getY() <= 486) {  // login
                                AudioPlayer.stopSounds(game.getWorld().clip);
                                gameLoop.restart();
                                game = gameLoop.game;
                                game.setView(canvas);
                                gameLoop.stop(2, 0);
                            }
                        }
                        break;
                }
            }
        });
    }

    public static class Canvas extends JPanel implements GameLoop.View {
        private World world;
        private LoginWorld loginWorld;
        private int running;
        private Pause pause;

        @Override
        public void render(int running, World world) {
            this.running = running;
            this.world = world;
            repaint(); // ask the JPanel to repaint, it will invoke paintComponent(g) after a while.
        }

        @Override
        public void render(int running, LoginWorld loginWorld) {
            this.running = running;
            this.loginWorld = loginWorld;
            repaint(); // ask the JPanel to repaint, it will invoke paintComponent(g) after a while.
        }

        @Override
        public void render(int running, Pause pause) {
            this.running = running;
            this.pause = pause;
            this.world = pause.getWorld();
            repaint(); // ask the JPanel to repaint, it will invoke paintComponent(g) after a while.
        }

        @Override
        protected void paintComponent(Graphics g /*paintbrush*/) {
            super.paintComponent(g);
            // Now, let's paint
            g.setColor(Color.WHITE); // paint background with all white
            g.fillRect(0, 0, GameView.WIDTH, GameView.HEIGHT);

            switch (running) {
                case 0:
                    if(loginWorld != null) loginWorld.render(g);
                    break;
                case 1:
                    if(world != null) world.render(g);
                    break;
                case 2:
                    if(world != null) pause.render(g);
                    break;
            }
        }
    }
}
