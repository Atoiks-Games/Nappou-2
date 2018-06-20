package org.atoiks.games.seihou2.entities.bullet;

import java.awt.Color;
import java.awt.geom.AffineTransform;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.seihou2.entities.IBullet;

public final class Beam implements IBullet {

    private static final long serialVersionUID = 4412375L;

    private final float angle, dmag, cos, sin;
    private float x, y, thickness, length;

    private final float[] dest = new float[8];

    public Beam(float x, float y, float thickness, float length, float angle, float dmag) {
        this.x = x;
        this.y = y;
        this.thickness = thickness;
        this.length = length;
        this.dmag = dmag;

        final float rad = normalizeRadians(angle);
        this.angle = rad;
        this.cos = (float) Math.cos(rad);
        this.sin = (float) Math.sin(rad);
    }

    private static float normalizeRadians(float radians) {
        radians = (float) (radians % (2 * Math.PI));
        if (radians < 0) {
            radians += 2 * Math.PI;
        }
        return radians;
    }

    @Override
    public void render(final IGraphics g) {
        g.setColor(Color.white);
        g.fillPolygon(dest);
    }

    @Override
    public void update(final float dt) {
        this.x += getDx() * dt;
        this.y += getDy() * dt;

        final float angle = (float) (3 * Math.PI / 2 - this.angle);
        final float t2 = thickness / 2;
        final float l2 = length / 2;

        final AffineTransform t = AffineTransform.getRotateInstance(-angle, x, y);

        final float[] input = {
            x - t2, y - l2,
            x + t2, y - l2,
            x + t2, y + t2,
            x - t2, y + t2,
        };

        t.transform(input, 0, dest, 0, input.length / 2);
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    @Override
    public float getDx() {
        return cos * this.dmag;
    }

    @Override
    public float getDy() {
        return sin * this.dmag;
    }

    @Override
    public boolean collidesWith(float x1, float y1, float r1) {
        // origin0 --- [body0] --- head0 ->
        // origin1 --- [body1] --- head1 ->
        // origin2 --- [body2] --- head2 ->

        // Basic testing
        if (helper(dest[0], dest[1], x1, y1, r1)) return true;
        if (helper(dest[2], dest[3], x1, y1, r1)) return true;
        if (helper(dest[4], dest[5], x1, y1, r1)) return true;
        if (helper(dest[6], dest[7], x1, y1, r1)) return true;

        // Midpoint testing
        if (helper((dest[0] + dest[2]) / 2, dest[1], x1, y1, r1)) return true;
        if (helper(dest[2], (dest[1] + dest[3]) / 2, x1, y1, r1)) return true;
        if (helper((dest[4] + dest[6]) / 2, dest[5], x1, y1, r1)) return true;
        if (helper(dest[6], (dest[5] + dest[7]) / 2, x1, y1, r1)) return true;
        return false;
    }

    private static boolean helper(float x, float y, float cx, float cy, float r) {
        return Math.hypot(x - cx, y - cy) < r;
    }

    @Override
    public boolean isOutOfScreen(final int w, final int h) {
        if (angle == 0) return x > w;
        if (angle > 0 && angle < Math.PI / 2) return x > w && y > h;
        if (angle == Math.PI / 2) return y > h;
        if (angle > Math.PI / 2) return y > h && x < 0;
        if (angle == Math.PI) return x < 0;
        if (angle > Math.PI && angle < Math.PI * 3 / 2) return x < 0 && y < 0;
        if (angle == Math.PI * 3 / 2) return y < 0;
        return y < 0 && x > w;
    }
}