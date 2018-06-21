package org.atoiks.games.nappou2.entities;

import java.io.Serializable;

import org.atoiks.games.framework2d.IUpdate;
import org.atoiks.games.framework2d.IRender;

public interface IBullet extends ICollidable, IRender, IUpdate, Serializable {

    public float getX();
    public float getY();
    public float getDx();
    public float getDy();
}