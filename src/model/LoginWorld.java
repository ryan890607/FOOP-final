package model;

import knight.Knight;
import media.AudioPlayer;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class LoginWorld {
    private Image background;
    int sx, sy; // left-down corner's axis of background
    public static final String BGM = "loginbgm";
    public Clip clip;
    public int state = 1;
    public String account = "", password = "";

    public LoginWorld(String backgroundName) {
        try {
            background = ImageIO.read(new File(backgroundName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // int imgW = background.getWidth(null), imgH = background.getHeight(null);
        sx = 0;
        sy = background.getHeight(null);
    }

    public void playSound() {
        clip = AudioPlayer.playSoundsloop(BGM);
    }

    public void update() {
    }

    public Image getBackground() { return background; }

    // Actually, directly couple your model with the class "java.awt.Graphics" is not a good design
    // If you want to decouple them, create an interface that encapsulates the variation of the Graphics.
    public void render(Graphics g) {
        g.drawImage(background, 0, 0, null);
        //System.out.print(state);
        switch (state) {
            case 0:
                break;
            case 1:
                g.setColor(Color.white);
                g.fillRect(557, 300, 195, 31);

                break;
            case 2:
                g.setColor(Color.white);
                g.fillRect(557, 340, 195, 31);
                break;
            case 3:
                g.setColor(Color.white);
                g.drawRect(765, 310, 100, 43);
                break;
            case 4:
                g.setColor(Color.red);
                g.setFont(new Font("TimesRoman", Font.BOLD, 18));
                g.drawString("帳號或密碼錯誤！", 565, 280);
                break;
        }
        g.setColor(Color.black);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        g.drawString(account, 565, 320);
        String temp = "";
        for(int i = 0; i < password.length(); ++i) temp += '*';
        g.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        g.drawString(temp, 565, 360);
    }
}