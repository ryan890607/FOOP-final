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
import java.util.ArrayList;
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
    private ArrayList<Game> games;
    private final Login login;
    private final Pause pause;
    private final GameLoop gameLoop;

    public GameView(Login login, Pause pause) throws HeadlessException {
        this.login = login;
        this.games = pause.games;
        this.pause = pause;
        login.setView(canvas);
        for(Game g : games) g.setView(canvas);
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
                            case 0: case 4:
                                if(keyEvent.getKeyCode() == keyEvent.VK_TAB) login.getWorld().state = 1;
                                break;
                            case 1:
                                if(keyEvent.getKeyCode() == keyEvent.VK_TAB || keyEvent.getKeyCode() == keyEvent.VK_DOWN) login.getWorld().state = 2;
                                if(Character.isLetterOrDigit(keyEvent.getKeyChar()))
                                    login.getWorld().account += keyEvent.getKeyChar();
                                if(keyEvent.getKeyCode() == keyEvent.VK_BACK_SPACE && login.getWorld().account.length() > 0)
                                    login.getWorld().account = login.getWorld().account.substring(0, login.getWorld().account.length()-1);
                                break;
                            case 2:
                                if(keyEvent.getKeyCode() == keyEvent.VK_UP) login.getWorld().state = 1;
                                if(keyEvent.getKeyCode() == keyEvent.VK_TAB) login.getWorld().state = 3;
                                if(Character.isLetterOrDigit(keyEvent.getKeyChar()))
                                    login.getWorld().password += keyEvent.getKeyChar();
                                if(keyEvent.getKeyCode() == keyEvent.VK_BACK_SPACE && login.getWorld().password.length() > 0)
                                    login.getWorld().password = login.getWorld().password.substring(0, login.getWorld().password.length()-1);
                                break;
                            case 3:
                                if(keyEvent.getKeyCode() == keyEvent.VK_TAB) login.getWorld().state = 1;
                                if(keyEvent.getKeyCode() == keyEvent.VK_LEFT) login.getWorld().state = 1;
                        }
                        break;
                    case 1: case 2:
                        switch (keyEvent.getKeyCode()) {
                            case KeyEvent.VK_W:
                                games.get(gameLoop.running-1).pick(P1);
                                break;
//                            case KeyEvent.VK_S:
//                                game.moveKnight(P1, Direction.DOWN);
//                                break;
                            case KeyEvent.VK_A:
                                games.get(gameLoop.running-1).moveKnight(P1, Direction.LEFT);
                                break;
                            case KeyEvent.VK_D:
                                games.get(gameLoop.running-1).moveKnight(P1, Direction.RIGHT);
                                break;
                            case KeyEvent.VK_E:
                                games.get(gameLoop.running-1).attack(P1);
                                break;
                            case KeyEvent.VK_SPACE:
                                games.get(gameLoop.running-1).jump(P1);
                                break;
                            case KeyEvent.VK_I:
                                games.get(gameLoop.running-1).skillI(P1);
                                break;
                            case KeyEvent.VK_U:
                                games.get(gameLoop.running-1).skillU(P1);
                                break;
                            case KeyEvent.VK_O:
                                games.get(gameLoop.running-1).skillO(P1);
                                break;
//                            case KeyEvent.VK_K:
//                                game.moveKnight(P2, Direction.DOWN);
//                                break;
//                            case KeyEvent.VK_J:
//                                game.moveKnight(P2, Direction.LEFT);
//                                break;
//                            case KeyEvent.VK_L:
//                                game.moveKnight(P2, Direction.RIGHT);
//                                break;
//                            case KeyEvent.VK_U:
//                                game.skillU(P1);
//                                break;
                            case KeyEvent.VK_1:
                                games.get(gameLoop.running-1).useHpPotion(P1);
                                break;
                            case KeyEvent.VK_2:
                                games.get(gameLoop.running-1).useMpPotion(P1);
                                break;
                            case KeyEvent.VK_3:
                                break;
                            case KeyEvent.VK_ESCAPE:
                                gameLoop.stop(gameLoop.running, 100);
                                break;
                        }
                        break;
                    case 100:
                        switch (keyEvent.getKeyCode()) {
                            case KeyEvent.VK_ESCAPE:
                                gameLoop.stop(100, pause.nowGame);
                        }
                }

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                switch (gameLoop.running) {
                    case 1: case 2:
                        switch (keyEvent.getKeyCode()) {
//                            case KeyEvent.VK_W:
//                                game.stopKnight(P1, Direction.UP);
//                                break;
//                            case KeyEvent.VK_S:
//                                game.stopKnight(P1, Direction.DOWN);
//                                break;
                            case KeyEvent.VK_A:
                                games.get(gameLoop.running-1).stopKnight(P1, Direction.LEFT);
                                break;
                            case KeyEvent.VK_D:
                                games.get(gameLoop.running-1).stopKnight(P1, Direction.RIGHT);
                                break;
//                            case KeyEvent.VK_I:
//                                game.stopKnight(P2, Direction.UP);
//                                break;
//                            case KeyEvent.VK_K:
//                                game.stopKnight(P2, Direction.DOWN);
//                                break;
//                            case KeyEvent.VK_J:
//                                game.stopKnight(P2, Direction.LEFT);
//                                break;
//                            case KeyEvent.VK_L:
//                                game.stopKnight(P2, Direction.RIGHT);
//                                break;
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
                    case 1: case 2:
                        if(e.getX() >= 965 && e.getX() <= 1007 && e.getY() >= 39 && e.getY() <= 78) {   // pause
                            gameLoop.stop(gameLoop.running, 100);
                        }
                        break;
                    case 100:
                        if(pause.games.get(pause.nowGame-1).getPlayer(P1).isAlive()) {
                            //System.out.print(pause.nowGame-1);
                            if(e.getX() >= 605 && e.getX() <= 661 && e.getY() >= 262 && e.getY() <= 314) {  // resume
                                gameLoop.stop(100, pause.nowGame);
                            }
                            if(e.getX() >= 358 && e.getX() <= 606 && e.getY() >= 341 && e.getY() <= 409) {  // restart
                                AudioPlayer.stopSounds(pause.games.get(pause.nowGame-1).getWorld().clip);
                                gameLoop.restart(pause.nowGame-1);
                                games = gameLoop.games;
                                games.get(pause.nowGame-1).setView(canvas);
                                gameLoop.stop(0, pause.nowGame);
                            }
                            if(e.getX() >= 358 && e.getX() <= 606 && e.getY() >= 419 && e.getY() <= 486) {  // login
                                gameLoop.stop(100, 0);
                            }
                        }
                        else {
                            if(e.getX() >= 358 && e.getX() <= 606 && e.getY() >= 341 && e.getY() <= 409) {  // restart
                                AudioPlayer.stopSounds(pause.games.get(pause.nowGame-1).getWorld().clip);
                                gameLoop.restart(pause.nowGame-1);
                                games = gameLoop.games;
                                games.get(pause.nowGame-1).setView(canvas);
                                gameLoop.stop(0, pause.nowGame);
                            }
                            if(e.getX() >= 358 && e.getX() <= 606 && e.getY() >= 419 && e.getY() <= 486) {  // login
                                AudioPlayer.stopSounds(pause.games.get(pause.nowGame-1).getWorld().clip);
                                gameLoop.restart(pause.nowGame-1);
                                games = gameLoop.games;
                                games.get(pause.nowGame-1).setView(canvas);
                                gameLoop.stop(100, 0);
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
                case 1: case 2:
                    if(world != null) world.render(g);
                    break;
                case 100:
                    if(world != null) pause.render(g);
                    break;
            }
        }
    }
}
