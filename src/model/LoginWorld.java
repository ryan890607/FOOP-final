package model;

import knight.Knight;
import media.AudioPlayer;

import javax.imageio.ImageIO;
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

    public LoginWorld(String backgroundName) {
        try {
            background = ImageIO.read(new File(backgroundName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // int imgW = background.getWidth(null), imgH = background.getHeight(null);
        sx = 0;
        sy = background.getHeight(null);
        AudioPlayer.playSoundsloop(BGM);
    }

    public void update() {
    }

    public Image getBackground() { return background; }

    // Actually, directly couple your model with the class "java.awt.Graphics" is not a good design
    // If you want to decouple them, create an interface that encapsulates the variation of the Graphics.
    public void render(Graphics g) {
        g.drawImage(background, 0, 0, null);
    }
}