package org.atoiks.games.seihou2.entities.enemy;

import java.awt.Color;
import java.awt.Graphics;

import se.tube42.lib.tweeny.Item;

import org.atoiks.games.seihou2.entities.Game;
import org.atoiks.games.seihou2.entities.IEnemy;

public abstract class AbstractEnemy extends IEnemy {

    private static final long serialVersionUID = 7192746L;

    private static final int SCREEN_EDGE_BUFFER = 16;

    public static final int FIELD_X = 0;
    public static final int FIELD_Y = 1;
    public static final int FIELD_R = 2;

    protected final Item xyr;

    protected Game game;

    protected AbstractEnemy(int hp, float x, float y, float r) {
        this(hp, new Item(3));
        this.xyr.setImmediate(FIELD_X, x);
        this.xyr.setImmediate(FIELD_Y, y);
        this.xyr.setImmediate(FIELD_R, r);
    }

    protected AbstractEnemy(int hp, Item tween) {
        super(hp);
        this.xyr = tween;
    }

    @Override
    public final void attachGame(Game game) {
        this.game = game;
    }

    @Override
    public final float getX() {
        return this.xyr.get(FIELD_X);
    }
    
    @Override
    public final float getY() {
        return this.xyr.get(FIELD_Y);
    }

    public final float getR() {
        return this.xyr.get(FIELD_R);        
    }

    public final void setX(float x) {
        this.xyr.setImmediate(FIELD_X, x);
    }

    public final void setY(float y) {
        this.xyr.setImmediate(FIELD_Y, y);
    }

    public final void setR(float r) {
        this.xyr.setImmediate(FIELD_R, r);
    }

    public Item tween() {
        return xyr;
    }

    @Override
    public void update(float dt) {
        // Default do nothing
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);
        // x, y are the center of the enemy
        final float lr = getR();
        final int ld = (int) (lr * 2);
        g.drawOval((int) (getX() - lr), (int) (getY() - lr), ld, ld);
    }

    @Override
    public boolean collidesWith(float x1, float y1, float r1) {
        final float sumRadius = getR() + r1;
        return (Math.abs(x1 - getX()) < sumRadius)
            && (Math.abs(y1 - getY()) < sumRadius);
    }

    @Override
    public boolean isOutOfScreen(final int w, final int h) {
        final float x = getX();
        final float y = getY();
        final float r = getR();
        return (x + r < -SCREEN_EDGE_BUFFER)
            || (x - r > w + SCREEN_EDGE_BUFFER)
            || (y + r < -SCREEN_EDGE_BUFFER)
            || (y - r > h + SCREEN_EDGE_BUFFER);
    }
}