package org.atoiks.games.seihou2.scenes;

import org.atoiks.games.seihou2.entities.*;
import org.atoiks.games.seihou2.entities.enemies.*;

import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public final class TutorialScene extends AbstractGameScene {

private int waveCounter;
private Image tutorialImg;

    @Override
    public void enter() {
        super.enter();

        game.addEnemy(new DummyEnemy(-10, 50, 8, true));

        game.player.setHp(5);
        waveCounter = 0;
        tutorialImg = (Image) scene.resources().get("z.png");
    }

    @Override
    public void render(final Graphics g) {
        // The bullet-curtain part
        g.setColor(Color.black);
        g.fillRect(0, 0, GAME_BORDER, HEIGHT);
        g.drawImage(tutorialImg, GAME_BORDER/2 - tutorialImg.getWidth(null)/2, HEIGHT/2 - tutorialImg.getHeight(null)/2, null);
        game.render(g);
        if (pause) {
            g.drawImage(pauseImg, 0, 0, PAUSE_OVERLAY, null);
        }

        // The game stats part
        g.setColor(Color.black);
        g.fillRect(GAME_BORDER, 0, WIDTH, HEIGHT);
        g.setColor(Color.white);
        g.drawLine(GAME_BORDER, 0, GAME_BORDER, HEIGHT);

        if (hpImg != null) {
            final int hp = game.player.getHp();
            final int w = hpImg.getWidth(null);
            for (int i = 0; i < hp; ++i) {
                g.drawImage(hpImg, GAME_BORDER + 5 + i * w, 10, null);
            }
        }
    }

    @Override
    public void leave() {
        super.leave();
    }

    @Override
    public boolean postUpdate(float dt) {

      if(game.enemies.isEmpty() && waveCounter == 0){
        tutorialImg = (Image) scene.resources().get("none.png");
        waveCounter = 1;
      }

      if(game.enemies.isEmpty() && waveCounter == 1){
        game.addEnemy(new SingleShotEnemy(250, -10, 8));
        game.addEnemy(new SingleShotEnemy(500, -10, 8));

      }




        return true;
    }
}
