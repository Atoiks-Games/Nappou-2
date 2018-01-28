package org.atoiks.games.seihou2.scenes;

import org.atoiks.games.seihou2.entities.*;
import org.atoiks.games.seihou2.entities.enemies.*;

public final class MainScene extends AbstractGameScene {

    @Override
    public void enter() {
        super.enter();

        game.addEnemyBullet(new PointBullet(GAME_BORDER / 2, -10, 10, 20, 60));

        game.addEnemy(new EnemyGroup(0.17f, 5, () -> new PointEnemy(30, 10, 8)));
        game.addEnemy(new EnemyGroup(0.17f, 5, () -> new PointEnemy(50, 10, 8)));
        game.addEnemy(new DummyEnemy(-10, 50, 8, true));

        game.player.setHp(5);
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