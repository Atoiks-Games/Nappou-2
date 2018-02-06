package org.atoiks.games.seihou2.entities.bullet;

import java.awt.Color;
import java.awt.Graphics;

public final class Beam implements IBullet {

    private static final long serialVersionUID = 4412375L;

    private float x, y, thickness, length, angle, dmag;

    // Update will fill in these values
    // b1---c2
    // |     |
    // a0---d3
    private final int[] xs = new int[4];
    private final int[] ys = new int[4];

    public Beam(float x, float y, float thickness, float length, float angle, float dmag) {
        this.x = x;
        this.y = y;
        this.thickness = thickness;
        this.length = length;
        this.angle = (float) (angle % (2 * Math.PI)); // angle in radians
        this.dmag = dmag;

        // Normalize the angles
        if (this.angle < 0) {
            this.angle += 2 * Math.PI;
        }
    }

    @Override
    public void render(final Graphics g) {
        g.setColor(Color.white);
        g.fillPolygon(xs, ys, 4);
    }

    @Override
    public void update(final float dt) {
        this.x += getDx() * dt;
        this.y += getDy() * dt;

        // Calculate collision boundary and render image
        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);

        final double tcos = thickness * cos / 2;
        final double tsin = thickness * sin / 2;
        final double lcos = length * cos;
        final double lsin = length * sin;

        // Point a
        xs[0] = (int) (x - tcos);
        ys[0] = (int) (y + tsin);

        // Point b
        xs[1] = (int) (x + tcos);
        ys[1] = (int) (y - tsin);

        // Point c
        xs[2] = (int) (xs[1] + lcos);
        ys[2] = (int) (ys[1] + lsin);

        // Point d
        xs[3] = (int) (xs[0] + lcos);
        ys[3] = (int) (ys[0] + lsin);
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
        return (float) (Math.cos(this.angle) * this.dmag);
    }

    @Override
    public float getDy() {
        return (float) (Math.sin(this.angle) * this.dmag);
    }

    @Override
    public boolean collidesWith(float x1, float y1, float r1) {
        for (int i = 0; i < 4; ++i){
            if (i == 0) {
                if (intersectSegmentCircle(xs[3], ys[3], xs[i], ys[i], x1, y1, r1))
                    return true;
            } else {
                if (intersectSegmentCircle(xs[i - 1], ys[i - 1], xs[i], ys[i], x1, y1, r1))
                    return true;
            }
        }
        return false;
    }

    public static boolean intersectSegmentCircle(float startX, float startY, float endX, float endY,
                                                 float centerX, float centerY, float r) {
        // Based on https://www.gamedevelopment.blog/collision-detection-circles-rectangles-and-polygons/
        final float t0X = endX - startX;
        final float t0Y = endX - startY;
        final float t1X = centerX - startX;
        final float t1Y = centerY - startY;

        final float l = (float) Math.hypot(t0X, t0Y);
        final float u = t1X * t0X / l + t1Y * t0Y / l;

        final float t2X, t2Y;
        if (u <= 0) {
            t2X = startX; t2Y = startY;
        } else if (u >= l) {
            t2X = endX; t2Y = endY;
        } else {
            final float t3X = t0X * u;
            final float t3Y = t0Y * u;
            t2X = t3X + startX; t2Y = t3Y + startY;
        }
        float x = centerX - t2X;
        float y = centerY - t2Y;
        return x * x + y * y <= r * r;
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