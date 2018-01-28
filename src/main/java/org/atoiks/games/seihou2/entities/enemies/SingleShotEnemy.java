package org.atoiks.games.seihou2.entities.enemies;

import java.awt.Graphics;

import org.atoiks.games.seihou2.entities.Beam;
import org.atoiks.games.seihou2.entities.PointBullet;

public final class SingleShotEnemy extends AbstractEnemy {

    private static final long serialVersionUID = 5619264522L;

    private static final double PI_OVER_12 = Math.PI / 12;

    private float time;
    private boolean fireGate;

    public SingleShotEnemy(float x, float y, float r) {
        super(x, y, r);
    }

    @Override
    public void update(float dt) {
        // Placeholder
        time += dt;

        final double mult = Math.cos(2 * time) / 2;
        y += 300 * dt;

        if (!fireGate && Math.cos(6 * time) < 0.5) {
            fireGate = true;
        }

        if (fireGate && Math.cos(6 * time) > 0.5) {
            fireGate = false;
            final double angle = Math.atan2(game.player.getY() - y, game.player.getX() - x);
            game.addEnemyBullet(new PointBullet(x, y, 2, (float) (1000 * Math.cos(angle)), (float)(1000 * Math.sin(angle))));

        }
    }

    @Override
    public void render(Graphics g) {
        // Convert to drawImage later on?
        super.render(g);
    }
}
