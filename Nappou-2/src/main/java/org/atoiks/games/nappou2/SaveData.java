/**
 *  Nappou-2
 *  Copyright (C) 2017-2019  Atoiks-Games <atoiks-games@outlook.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.atoiks.games.nappou2;

import java.io.Serializable;
import org.atoiks.games.nappou2.Difficulty;
import org.atoiks.games.nappou2.entities.shield.*;

public final class SaveData implements Serializable {

    private static final long serialVersionUID = -6315543815579288169L;

    private int checkpoint = 0;
    private Difficulty dif = Difficulty.NORMAL;
    private IShield shield = new NullShield();

    public void setCheck(int n){
        this.checkpoint = n;
    }

    public int getCheck(){
        return this.checkpoint;
    }

    public void setDif(Difficulty d){
        this.dif = d;
    }


    public void setShield(IShield s){
        this.shield = s;
    }

    public Difficulty getDif(){
        return this.dif;
    }

    public IShield getSheild(){
        return this.shield;
    }
}
