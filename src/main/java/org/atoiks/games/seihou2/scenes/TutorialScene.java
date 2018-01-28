package org.atoiks.games.seihou2.scenes;

import java.awt.Image;
import java.awt.Graphics;

import org.atoiks.games.seihou2.entities.enemies.*;

public final class TutorialScene extends AbstractGameScene {

    private int waveCounter;
    private Image tutorialImg;

    @Override
    public void enter() {
        super.enter();
        
        game.addEnemy(new DummyEnemy(1, -10, 50, 8, true));
        
        game.player.setHp(5);
        waveCounter = 0;
        tutorialImg = (Image) scene.resources().get("z.png");
    }

    @Override
    public void renderBackground(final Graphics g) {
        super.renderBackground(g);
        if (tutorialImg != null) {
            g.drawImage(tutorialImg, (GAME_BORDER - tutorialImg.getWidth(null)) / 2, (HEIGHT - tutorialImg.getHeight(null)) / 2, null);
        }
    }

    @Override
    public void leave() {
        super.leave();
    }

    @Override
    public boolean postUpdate(float dt) {
        if (game.enemies.isEmpty() && waveCounter == 0) {
            tutorialImg = null;
            waveCounter = 1;
        }

        if (game.enemies.isEmpty() && waveCounter == 1) {
            game.addEnemy(new SingleShotEnemy(1, 250, -10, 8));
            game.addEnemy(new SingleShotEnemy(1, 500, -10, 8));
        }

        return true;
    }
}
