import controller.Game;
import controller.GameLoop;
import controller.Login;
import knight.Attacking;
import knight.Knight;
import knight.KnightCollisionHandler;
import knight.Walking;
import model.*;
import starPixie.StarPixie;
import views.GameView;
import ironBoar.IronBoar;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import obstacles.Obstacle1;
import obstacles.Obstacle2;
import obstacles.Obstacle3;

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
        addAudioByFilePath(IronBoar.WAIL, new File("assets/audio/wail.wav"));
        addAudioByFilePath(Knight.JUMP, new File("assets/audio/jump.wav"));
        addAudioByFilePath(LoginWorld.BGM, new File("assets/audio/LoginTheme.wav"));

        // initialization

        Knight p1 = new Knight(300, new Point(0, 400+768));
        Knight p2 = new Knight(300, new Point(300, 300+768));
        IronBoar m1 = new IronBoar(100, new Point(300, 150));
        IronBoar m2 = new IronBoar(100, new Point(500, 150));
        StarPixie m3 = new StarPixie(50, new Point(300, 350));
		MonsterGenerator generator = new MonsterGenerator();




        int obstacleCount = 1;
	    Direction d = Direction.RIGHT;
	    ArrayList<Obstacle1> o1 = new ArrayList<Obstacle1>();
	    o1.add(new Obstacle1("assets/obstacle/Obstacle4.jpg", new Point(0, 1350), d));

	    obstacleCount = 2;
	    d = Direction.LEFT;
    	    ArrayList<Obstacle2> o2 = new ArrayList<Obstacle2>();
	    for (int i = 0; i < obstacleCount; i++) {
		if (i == 0)
	            o2.add(new Obstacle2("assets/obstacle/Obstacle2.png", new Point((445)%2048, (250)%1536), d));
		else
	            o2.add(new Obstacle2("assets/obstacle/Obstacle2.png", new Point((445+750)%2048, (250+300)%1536), d));

	        if (d == Direction.RIGHT)  d = Direction.LEFT;
	        else
	            d = Direction.RIGHT;
	    }

	    obstacleCount = 3;
	    d = Direction.LEFT;
	    ArrayList<Obstacle3> o3 = new ArrayList<Obstacle3>();
	    for (int i = 0; i < obstacleCount; i++) {
		if (i == 0)
	            o3.add(new Obstacle3("assets/obstacle/Obstacle3.png", new Point((680)%2048, (950)%1536), d));
		else if (i == 1)
	            o3.add(new Obstacle3("assets/obstacle/Obstacle3.png", new Point((680+800)%2048, (950+1000)%1536), d));
		else
	            o3.add(new Obstacle3("assets/obstacle/Obstacle3.png", new Point((250)%2048, (700)%1536), d));

	        if (d == Direction.RIGHT)
	            d = Direction.LEFT;
	        else
	            d = Direction.RIGHT;
	    }
	
        World world = new World("assets/background/fallguys4times.jpg", o1, o2, o3, new KnightCollisionHandler(), p1, p2, m1, m2, m3);  // model
		world.addSprites(generator.generateIronBoar(new Point(0, 1350-170), new Point(2048, 1350-170), 3));
		world.addSprites(generator.generateStarPixie(new Point(0, 1350-170), new Point(2048, 1350-170), 3));
		LoginWorld loginWorld = new LoginWorld("assets/background/MapleStory.png");

        Game game = new Game(world, p1, p2);  // controller

        Login login = new Login(loginWorld);
        GameLoop gameLoop = new GameLoop(login, game);
        GameView view = new GameView(login, game);  // view
        gameLoop.start();  // run the game and the game loop
        view.launch(); // launch the GUI
    }
}
