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

package org.atoiks.games.nappou2.sizer;

public final class SizerSwitch implements Sizer {

    public static interface FloatPredicate {

        public boolean test(float value);
    }

    private final FloatPredicate predicate;
    private final Sizer ifTrue;
    private final Sizer ifFalse;

    public SizerSwitch(FloatPredicate predicate, Sizer ifTrue, Sizer ifFalse) {
        this.predicate = predicate;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
    }

    @Override
    public float getNextSize(final float prev, final float dt) {
        final Sizer dispatch = this.predicate.test(prev) ? this.ifTrue : this.ifFalse;
        return dispatch.getNextSize(prev, dt);
    }
}
