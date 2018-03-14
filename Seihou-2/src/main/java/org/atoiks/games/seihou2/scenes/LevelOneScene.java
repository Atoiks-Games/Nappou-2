package org.atoiks.games.seihou2.scenes;

import se.tube42.lib.tweeny.Item;
import se.tube42.lib.tweeny.TweenEquation;

import org.atoiks.games.seihou2.entities.*;
import org.atoiks.games.seihou2.entities.enemy.*;
import org.atoiks.games.seihou2.entities.bullet.*;

public final class LevelOneScene extends AbstractGameScene {

    private int cycles;
    private int wave;

    public LevelOneScene() {
        super(0);
    }

    @Override
    public void enter(final int prevSceneId) {
        super.enter(prevSceneId);

        cycles = 0;
        wave = 0;

        game.player = new Player(GAME_BORDER / 2, HEIGHT / 6 * 5, (IShield) scene.resources().get("shield"));
        game.player.setHp(5);
        game.setScore(0);
    }

    @Override
    public boolean postUpdate(float dt) {
        ++cycles;
        switch (difficulty) {
            case EASY:
            switch (wave) {
                case 0:
                    switch (cycles) {
                        case 2000:
                        case 4000:
                        case 6000:
                        case 8000:
                        case 10000:
                            final int k = cycles / 1000 * 25;
                            game.addEnemy(new SingleShotEnemy(1, 300 - k, -10, 8));
                            game.addEnemy(new SingleShotEnemy(1, 450 + k, -10, 8));
                            break;
                        case 11000:
                            game.addEnemy(new DropEnemy(1, -10, 10, 8));
                            game.addEnemy(new DropEnemy(1, 760, 10, 8));
                            break;
                        case 20000:
                            game.addEnemy(new CircularPathEnemy(1, 375, 300, 8, 1, 1, 1));
                            break;
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
