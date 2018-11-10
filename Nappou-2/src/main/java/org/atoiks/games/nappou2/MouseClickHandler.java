/**
 *  Nappou-2
 *  Copyright (C) 2017-2018  Atoiks-Games <atoiks-games@outlook.com>
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

import org.atoiks.games.framework2d.Input;

public final class MouseClickHandler {

    private final int btn;
    private final float delay;

    private int clickCount;
    private float elapsedTime;

    public MouseClickHandler(int btn, float delaySec) {
        this.btn = btn;
        this.delay = delaySec;
    }

    public void reset() {
        clickCount = 0;
        elapsedTime = 0;
    }

    public void update(final float dt) {
        elapsedTime += dt;
        if (Input.mouseMoved() || elapsedTime > delay) {
            clickCount = 0;
        }
        if (Input.isMouseButtonClicked(btn)) {
            clickCount++;
            elapsedTime = 0;
        }
    }

    public int getClickCount() {
        return clickCount;
    }

    public boolean singleClicked() {
        return clickCount == 1;
    }

    public boolean doubleClicked() {
        return clickCount == 2;
    }
}
