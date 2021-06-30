import controller.Game;
import knight.Attacking;
import knight.Knight;
import knight.KnightCollisionHandler;
import knight.Walking;
import model.HealthPointSprite;
import model.World;
import views.GameView;
import ironBoar.IronBoar;
import java.awt.*;
import java.io.File;

import static media.AudioPlayer.addAudioByFilePath;

/**
 * Demo route: Main, GameView, Game, GameLoop, World, Sprite, Knight, FiniteStateMachine
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Main {
    public static void main(String[] args) {
        addAudioByFilePath(Walking.AUDIO_STEP1, new File("assets/audio/step1.wav"));
        addAudioByFilePath(Walking.AUDIO_STEP2, new File("assets/audio/step2.wav"));
        addAudioByFilePath(Attacking.AUDIO_SWORD_CLASH_1, new File("assets/audio/sword-clash1.wav"));
        addAudioByFilePath(Attacking.AUDIO_SWORD_CLASH_2, new File("assets/audio/sword-clash2.wav"));
        addAudioByFilePath(HealthPointSprite.AUDIO_DIE, new File("assets/audio/die.wav"));
        addAudioByFilePath(World.BGM, new File("assets/audio/background_BGM.wav"));
//        addAudioByFilePath(IronBoar.GET_ATTACK, new File("assets/audio/jump.wav"));
        addAudioByFilePath(Knight.JUMP, new File("assets/audio/jump.wav"));

        // initialization

        Knight p1 = new Knight(150, new Point(0, 400+768));
        Knight p2 = new Knight(150, new Point(300, 300+768));
        IronBoar m1 = new IronBoar(30, new Point(300, 150));
        IronBoar m2 = new IronBoar(30, new Point(500, 150));
        IronBoar m3 = new IronBoar(30, new Point(300, 350));
        World world = new World("assets/backgound/fallguys4times.jpg", new KnightCollisionHandler(), p1, p2, m1, m2, m3);  // model

        Game game = new Game(world, p1, p2);  // controller
        GameView view = new GameView(game);  // view
        game.start();  // run the game and the game loop
        view.launch(); // launch the GUI
    }
}