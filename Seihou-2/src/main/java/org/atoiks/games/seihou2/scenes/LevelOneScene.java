package org.atoiks.games.seihou2.scenes;

import se.tube42.lib.tweeny.Item;
import se.tube42.lib.tweeny.TweenEquation;

import org.atoiks.games.seihou2.entities.*;
import org.atoiks.games.seihou2.entities.enemy.*;
import org.atoiks.games.seihou2.entities.bullet.*;

public final class LevelOneScene extends AbstractGameScene {

    private float time;
    private int wave;

    public LevelOneScene() {
        super(0);
    }

    @Override
    public void enter(final int prevSceneId) {
        super.enter(prevSceneId);

        time = 0;
        wave = 0;

        game.player = new Player(GAME_BORDER / 2, HEIGHT / 6 * 5, (IShield) scene.resources().get("shield"));
        game.player.setHp(5);
        game.setScore(0);
    }

    @Override
    public boolean postUpdate(float dt) {
        time ++;
        float t = time/1000;
        switch(difficulty){
          case EASY:
          switch (wave) {
              case 0:
                  if(t == 2){
                    game.addEnemy(new SingleShotEnemy(1, 250, -10, 8));
                    game.addEnemy(new SingleShotEnemy(1, 500, -10, 8));
                  }
                  if(t == 4){
                    game.addEnemy(new SingleShotEnemy(1, 200, -10, 8));
                    game.addEnemy(new SingleShotEnemy(1, 550, -10, 8));
                  }
                  if(t == 6){
                    game.addEnemy(new SingleShotEnemy(1, 150, -10, 8));
                    game.addEnemy(new SingleShotEnemy(1, 600, -10, 8));
                  }
                  if(t == 8){
                    game.addEnemy(new SingleShotEnemy(1, 100, -10, 8));
                    game.addEnemy(new SingleShotEnemy(1, 650, -10, 8));
                  }
                  if(t == 10){
                    game.addEnemy(new SingleShotEnemy(1, 50, -10, 8));
                    game.addEnemy(new SingleShotEnemy(1, 700, -10, 8));
                  }
                  if (t == 11){
                    game.addEnemy(new DropEnemy(1, -10, 10, 8));
                    game.addEnemy(new DropEnemy(1, 760, 10, 8));
                  }
                  if(t == 20){
                    game.addEnemy(new CircularPathEnemy(1, 375, 300, 8, 1, 1, 1));
                  }
                  break;
              case 1:
                  if (game.enemies.isEmpty()) {

                  }
                  break;
              case 2:
                  if (game.enemies.isEmpty()) {

                  }
                  break;
              case 3:
                  if (game.enemies.isEmpty()) {

                  }
                  break;
              case 4:
                  if (game.enemies.isEmpty()) {

                  }
                  break;
          }
            break;
        }
        return true;
    }

    @Override
    public void leave() {
        super.leave();
    }
}
