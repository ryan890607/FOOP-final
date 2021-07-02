import controller.Game;
import controller.GameLoop;
import controller.Login;
import controller.Pause;
import knight.*;
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
		addAudioByFilePath(IronBoar.DIE, new File("assets/audio/iron_die.wav"));
		addAudioByFilePath(Skill_I.SKILL_I, new File("assets/audio/skill_i.wav"));
		addAudioByFilePath(Skill_U.SKILL_U, new File("assets/audio/skill_u.wav"));
		addAudioByFilePath(World.BOSS_BGM, new File("assets/audio/boss_bgm.wav"));
		GameCreator gameCreator = new GameCreator();

		// initialization
		Game game1 = gameCreator.create(1), game2 = gameCreator.create(2);
		LoginWorld loginWorld = new LoginWorld("assets/background/MapleStory.png");

		Login login = new Login(loginWorld);
		Pause pause = new Pause(game1, game2);
		GameLoop gameLoop = new GameLoop(login, pause);
		GameView view = new GameView(login, pause);  // view
		gameLoop.start();  // run the game and the game loop
		view.launch(); // launch the GUI
	}

}
