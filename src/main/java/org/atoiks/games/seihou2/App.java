package org.atoiks.games.seihou2;

import org.atoiks.games.framework.Frame;
import org.atoiks.games.framework.FrameInfo;

import org.atoiks.games.seihou2.scenes.*;

public class App {

    public static void main(String[] args) {
        final FrameInfo info = new FrameInfo()
                .setTitle("Atoiks Games - Seihou 2")
                .setResizable(false)
                .setSize(900, 600)
                .setScenes(new LoadingScene(), new TitleScene(), new TutorialScene(), new MainScene());
        try (final Frame frame = new Frame(info)) {
            frame.init();
            frame.loop();
        }
    }
}
