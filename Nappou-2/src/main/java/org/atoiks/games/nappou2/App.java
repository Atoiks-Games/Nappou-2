package org.atoiks.games.nappou2;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import org.atoiks.games.framework2d.FrameInfo;
import org.atoiks.games.framework2d.swing.Frame;

import org.atoiks.games.nappou2.scenes.*;

public class App {

    public static void main(String[] args) {
        final FrameInfo info = new FrameInfo()
                .setTitle("Atoiks Games - Seihou 2")
                .setResizable(false)
                .setSize(900, 600)
                .setScenes(new LoadingScene(), new TitleScene(), new TutorialScene(), new ScoreScene(), new ConfigScene(), new DiffOptionScene(), new ShieldOptionScene(), new LevelOneScene(), new LevelTwoScene());
        final Frame frame = new Frame(info);
        try {
            frame.init();
            frame.loop();
        } finally {
            // Saves config
            try (final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./game.cfg"))) {
                oos.writeObject(frame.getSceneManager().resources().get("game.cfg"));
            } catch (IOException ex) {
                // Next time, game will launch with default configurations
            }

            // Saves user score
            try (final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./score.dat"))) {
                oos.writeObject(frame.getSceneManager().resources().get("score.dat"));
            } catch (IOException ex) {
                // Oh well... to bad... the user's score does not get saved...
            }

            frame.close();
        }
    }
}
