package org.atoiks.games.seihou2.scenes;

import org.atoiks.games.seihou2.entities.*;
import org.atoiks.games.seihou2.entities.enemies.*;

public final class LevelOneScene extends AbstractGameScene {

    @Override
    public void enter(final int prevSceneId) {
        super.enter(prevSceneId);

        game.player = new Player(GAME_BORDER / 2, HEIGHT / 6 * 5, (IShield) scene.resources().get("shield"));

        game.addEnemyBullet(new PointBullet(GAME_BORDER / 2, -10, 10, 20, 60));

        game.addEnemy(new EnemyGroup(0.17f, 5, () -> new PointEnemy(1, 30, 10, 8)));
        game.addEnemy(new EnemyGroup(0.17f, 5, () -> new PointEnemy(1, 50, 10, 8)));
        game.addEnemy(new DummyEnemy(1, -10, 50, 8, true));

        game.player.setHp(5);
        game.setScore(0);
    }

    @Override
    public void leave() {
        super.leave();
    }

    @Override
    public boolean postUpdate(float dt) {
        return true;
    }
}