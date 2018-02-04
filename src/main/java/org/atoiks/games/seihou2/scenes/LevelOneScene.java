package org.atoiks.games.seihou2.scenes;

import org.atoiks.games.seihou2.entities.*;
import org.atoiks.games.seihou2.entities.enemies.*;

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

        game.addEnemy(new EnemyGroup(0.17f, 5, () -> new PointEnemy(4, 30, 10, 8)));
        game.addEnemy(new EnemyGroup(0.17f, 5, () -> new PointEnemy(4, 50, 10, 8)));
        game.addEnemy(new EnemyGroup(0.17f, 5, () -> new PointEnemy(4, GAME_BORDER - 30, 10, 8)));
        game.addEnemy(new EnemyGroup(0.17f, 5, () -> new PointEnemy(4, GAME_BORDER - 50, 10, 8)));

        game.player.setHp(5);
        game.setScore(0);
    }

    @Override
    public boolean postUpdate(float dt) {
        time += dt;
        switch (wave) {
            case 0:
                if (time > 2) {
                    game.addEnemy(new EnemyGroup(0.19f, 4, () -> new ShiftEnemy(5, 0, 15, 8)));
                    time = 0;
                    ++wave;
                }
                break;
            case 1:
                if (time > 0.5) {
                    final IEnemy[] arr = new IEnemy[8];
                    for (int i = 0; i < arr.length; ++i) {
                        arr[i] = new DropEnemy(5, GAME_BORDER / 8 * i - 20, 0, 12);
                    }
                    game.addEnemy(new EnemyGroup(0.2f, arr));
                    time = 0;
                    ++wave;
                }
                break;
            case 2:
            case 3:
            case 4:
                if (time > 0.75) {
                    final float playerX = game.player.getX();
                    final float playerY = game.player.getY();

                    game.addEnemyBullet(new Beam(-9, -9, 4f, 100, (float) Math.atan2(playerY, playerX), 900));
                    game.addEnemyBullet(new Beam(GAME_BORDER + 9, -9, 4f, 100, (float) Math.atan2(playerY, playerX - GAME_BORDER), 900));
                    game.addEnemyBullet(new Beam(-9, HEIGHT + 9, 4f, 100, (float) Math.atan2(playerY - HEIGHT, playerX), 900));
                    game.addEnemyBullet(new Beam(GAME_BORDER + 9, HEIGHT + 9, 4f, 100, (float) Math.atan2(playerY - HEIGHT, playerX - GAME_BORDER), 900));
                    time = 0;
                    ++wave;
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