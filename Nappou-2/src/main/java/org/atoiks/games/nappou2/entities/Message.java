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

package org.atoiks.games.nappou2.entities;

import java.io.Serializable;

import java.util.Arrays;

public final class Message implements Serializable {

    private static final long serialVersionUID = 3123811734L;

    public enum HorizontalAlignment {
        LEFT, CENTER, RIGHT;
    };

    // If necessary, add VerticalAlignment as well

    public final String speaker;
    public final String[] lines;

    public final String imgRes;
    private HorizontalAlignment imgHoriz;

    public Message(String speaker, String... lines) {
        this(null, HorizontalAlignment.CENTER, speaker, lines);
    }

    public Message(String imgRes, HorizontalAlignment horiz, String speaker, String... lines) {
        this.imgRes = imgRes;
        this.speaker = speaker;
        this.lines = lines;

        this.setImageHorizontalAlignment(horiz);
    }

    public final void setImageHorizontalAlignment(HorizontalAlignment horiz) {
        this.imgHoriz = horiz == null ? HorizontalAlignment.CENTER : horiz;
    }

    public HorizontalAlignment getImageHorizontalAlignment() {
        return this.imgHoriz;
    }

    @Override
    public String toString() {
        return "Message of " + speaker + ": " + Arrays.toString(lines);
    }
}
