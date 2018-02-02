package org.atoiks.games.seihou2;

import java.io.FileOutputStream;
import java.io.IOException;

import org.atoiks.games.framework.Frame;
import org.atoiks.games.framework.FrameInfo;

import org.atoiks.games.seihou2.scenes.*;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;

public class App {

    public static void main(String[] args) {
        final FrameInfo info = new FrameInfo()
                .setTitle("Atoiks Games - Seihou 2")
                .setResizable(false)
                .setSize(900, 600)
                .setScenes(new LoadingScene(), new TitleScene(), new TutorialScene(), new ScoreScene(), new PlayerOptionScene(), new LevelOneScene());
        final Frame frame = new Frame(info);
        try {
            frame.init();
            frame.loop();
        } finally {
            try (final MessagePacker packer = MessagePack.newDefaultPacker(new FileOutputStream("./score.dat"))) {
                // Saves user score
                final int[][] scoreDat = (int[][]) frame.getSceneManager().resources().get("score.dat");
                packer.packArrayHeader(scoreDat.length);
                for (final int[] scoreSet : scoreDat) {
                    packer.packArrayHeader(scoreSet.length);
                    for (final int score : scoreSet) {
                        packer.packInt(score);
                    }
                }
            } catch (IOException ex) {
                // Oh well... to bad... the user's score does not get saved...
            }

            frame.close();
        }
    }
}
