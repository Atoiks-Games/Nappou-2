package org.atoiks.games.seihou2.entities.enemies;

import java.awt.Graphics;

import org.atoiks.games.seihou2.entities.PointBullet;

public final class ShieldTesterEnemy extends AbstractEnemy {

    private static final long serialVersionUID = 5619264522L;

    private float time;
    private boolean fireGate;
    private float alignment;

    public ShieldTesterEnemy(int hp, float x, float y, float r) {
        super(hp, x, y, r);
    }

    @Override
    public void update(float dt) {
        time += dt;

        if (x > 375){
          alignment = -1;
        } else {
          alignment = 1;
        }

        y += 300 * dt;

        if (!fireGate && time > 0.001) {
            fireGate = true;
        }

        if (fireGate && time > 0.001) {
            fireGate = false;
            game.addEnemyBullet(new PointBullet(x, y, 2, alignment * 5000, 0));
            time = 0;
        }
    }

    @Override
    public void render(Graphics g) {
        // Convert to drawImage later on?
        super.render(g);
    }

    @Override
    public int getScore() {
        return 1;
    }
}
