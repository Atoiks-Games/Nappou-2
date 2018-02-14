package org.atoiks.games.seihou2.entities.bullet;

import java.awt.Color;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.seihou2.entities.Game;
import org.atoiks.games.seihou2.entities.IBullet;

public final class TrackPointBullet implements IBullet {

    private static final long serialVersionUID = -1696891011951230605L;

    private static final int SCREEN_EDGE_BUFFER = 16;

    private final Game game;
    private final float scale;
    private final float moveTime;
    private final float delay;
    
    private float x, y, r;
    private float dx, dy;

    private float time;
    private boolean moving;

    public TrackPointBullet(float x, float y, float r, Game game, float pathScale, float moveTime, float delay) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.game = game;
        this.scale = pathScale;
        this.moveTime = moveTime;
        this.delay = delay;

        // These values force endpoints to be calculated
        time = delay;
        moving = false;
    }

    @Override
    public boolean collidesWith(float x1, float y1, float r1) {
        final float sumRadius = r + r1;
        return (Math.abs(x1 - x) < sumRadius)
            && (Math.abs(y1 - y) < sumRadius);
    }

    @Override
    public boolean isOutOfScreen(final int w, final int h) {
        return (x + r < -SCREEN_EDGE_BUFFER)
            || (x - r > w + SCREEN_EDGE_BUFFER)
            || (y + r < -SCREEN_EDGE_BUFFER)
            || (y - r > h + SCREEN_EDGE_BUFFER);
    }

    @Override
    public void render(IGraphics g) {
        // Can change this to using textures later
        g.setColor(Color.white);
        // x, y are the center of the bullet
        g.drawOval(x - r, y - r, x + r, y + r);
    }

    @Override
    public void update(float dt) {
        if (game.player == null) return;
        
        time += dt;
        if (moving) {
            if (time >= moveTime) {
                moving = false;
                time = 0;
            } else {
                x += dx * dt;
                y += dy * dt;
            }
        } else if (time >= delay) {
            // Re-calculate endpoints
            dx = scale * (game.player.getX() - x) / moveTime;
            dy = scale * (game.player.getY() - y) / moveTime;
            moving = true;
            time = 0;
        }
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getDx() {
        return dx;
    }

    @Override
    public float getDy() {
        return dy;
    }
}