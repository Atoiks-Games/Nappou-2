package org.atoiks.games.seihou2.entities;

import java.io.Serializable;

import org.atoiks.games.framework.IRender;
import org.atoiks.games.framework.IUpdate;

public interface IBullet extends IRender, IUpdate, Serializable {

    public float getX();
    public float getY();
    public float getDx();
    public float getDy();

    public boolean collidesWith(float x, float y, float r);
    public boolean isOutOfScreen(int width, int height);
}