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

import java.io.IOException;
import java.io.ByteArrayOutputStream;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public final class SoundEffect {

    public final AudioFormat format;
    public final byte[] data;

    public SoundEffect(AudioFormat format, byte[] data) {
        this.format = format;
        this.data = data;
    }

    public Clip makeClip() throws LineUnavailableException {
        final Clip clip = AudioSystem.getClip(null);
        clip.open(format, data, 0, data.length);
        return clip;
    }

    public static SoundEffect getFromAudioInputStream(final AudioInputStream ais) throws IOException, UnsupportedAudioFileException {
        final AudioFormat fmt = ais.getFormat();
        final ByteArrayOutputStream buf = new ByteArrayOutputStream();
        final byte[] xchg = new byte[1024];

        int len;
        while ((len = ais.read(xchg)) != -1) {
            buf.write(xchg, 0, len);
        }
        return new SoundEffect(fmt, buf.toByteArray());
    }
}
