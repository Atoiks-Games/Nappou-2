package org.atoiks.games.seihou2.entities.enemies;

import java.awt.Graphics;

public final class DummyEnemy extends AbstractEnemy {

    private static final long serialVersionUID = 56192645221L;
    //This needs to be different for every enemy...

    private boolean right;

    public DummyEnemy(float x, float y, float r, boolean right) {
        super(x, y, r);
        this.right = right;
    }

    @Override
    public void update(float dt) {
        if (right && x > 700) {
            right = false;
        }
        if (!right && x < 50) {
            right = true;
        }

        if (right) {
            x += 100 * dt;
        } else {
            x -= 100 *dt;
        }
    }

    @Override
    public void render(Graphics g) {
        // Convert to drawImage later on?
        super.render(g);
    }
}
