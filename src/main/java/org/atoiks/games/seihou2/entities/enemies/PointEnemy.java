package org.atoiks.games.seihou2.entities.enemies;

import java.awt.Graphics;

import org.atoiks.games.seihou2.entities.Beam;

public final class PointEnemy extends AbstractEnemy {

    private static final long serialVersionUID = 5619264522L;

    private static final double PI_OVER_12 = Math.PI / 12;

    private float time;
    private boolean fireGate;

    public PointEnemy(float x, float y, float r) {
        super(x, y, r);
    }

    @Override
    public void update(float dt) {
        // Placeholder
        time += dt;

        final double mult = Math.cos(2 * time) / 2;
        y += 300 * (mult + 0.5) * dt;
        x += 300 * (0.5 - mult) * dt;

        if (!fireGate && Math.cos(6 * time) < 0.5) {
            fireGate = true;
        }

        if (fireGate && Math.cos(6 * time) > 0.5) {
            fireGate = false;
            final double angle = Math.atan2(game.player.getY() - y, game.player.getX() - x);
            game.addEnemyBullet(new Beam(x, y, 2.5f, 45, (float) (angle + PI_OVER_12), 720));
            game.addEnemyBullet(new Beam(x, y, 2.5f, 45, (float) (angle), 720));
            game.addEnemyBullet(new Beam(x, y, 2.5f, 45, (float) (angle - PI_OVER_12), 720));
        }
    }

    @Override
    public void render(Graphics g) {
        // Convert to drawImage later on?
        super.render(g);
    }
}