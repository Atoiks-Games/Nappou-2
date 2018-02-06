package org.atoiks.games.seihou2.entities.bullet;

import java.io.Serializable;

import org.atoiks.games.framework.IRender;
import org.atoiks.games.framework.IUpdate;
import org.atoiks.games.seihou2.entities.ICollidable;

public interface IBullet extends ICollidable, IRender, IUpdate, Serializable {

    public float getX();
    public float getY();
    public float getDx();
    public float getDy();

    public default float getR() {
        return -1;
    }
}