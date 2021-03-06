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

package org.atoiks.games.nappou2.scenes;

import java.awt.Color;

import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.framework2d.resource.Font;
import org.atoiks.games.framework2d.resource.Texture;

import org.atoiks.games.nappou2.entities.Message;

import static org.atoiks.games.nappou2.scenes.GameLevelScene.HEIGHT;
import static org.atoiks.games.nappou2.scenes.GameLevelScene.GAME_BORDER;

public final class DialogOverlay {

    private String msgSpeaker;
    private String[] msgLines;
    private Texture imgMsg;

    private int xoffMsgImg;
    private int yoffMsgImg;

    private final Font font16;
    private final Font font30;

    /* package */ DialogOverlay(Font font) {
        this.font16 = font.deriveSize(16f);
        this.font30 = font.deriveSize(30f);
    }

    public void clearMessage() {
        this.imgMsg = null;
        this.msgSpeaker = null;
        this.msgLines = null;
    }

    public void displayMessage(final Message msg) {
        this.msgSpeaker = msg.speaker == null ? null : msg.speaker + ":";
        this.msgLines = msg.lines;
        this.imgMsg = loadMessageResource(msg);

        this.yoffMsgImg = alignVertical(msg.getImageVerticalAlignment(), imgMsg);
        this.xoffMsgImg = alignHorizontal(msg.getImageHorizontalAlignment(), imgMsg);
    }

    public void render(final IGraphics g) {
        if (imgMsg != null) {
            g.drawTexture(imgMsg, xoffMsgImg, yoffMsgImg);
        }

        if (msgSpeaker == null) {
            return;
        }

        // Draw msgbox border
        g.setColor(Color.white);
        g.fillRect(12, HEIGHT - 200, GAME_BORDER - 12, HEIGHT - 12);

        // Draw grey area
        g.setColor(Color.gray);
        g.fillRect(20, HEIGHT - 192, GAME_BORDER - 20, HEIGHT - 20);

        // Draw name inside msgbox
        g.setColor(Color.white);
        this.font30.renderText(g, msgSpeaker, 28, HEIGHT - 162);

        // Draw message inside msgbox
        for (int i = 0; i < msgLines.length; ++i) {
            this.font16.renderText(g, msgLines[i], 28, HEIGHT - 142 + i * (this.font16.getSize()) + 10);
        }

        // Draw footer
        this.font16.renderText(g, "Press Enter to continue", GAME_BORDER - 180, HEIGHT - 26);
    }

    private Texture loadMessageResource(final Message msg) {
        if (msg.imgRes != null) {
            return ResourceManager.get("/image/" + msg.imgRes);
        }
        return null;
    }

    public static int alignVertical(Message.VerticalAlignment vAlign, Texture img) {
        final int imgH = img != null ? img.getHeight() : 0;
        switch (vAlign) {
            case TOP:
                return 0;
            case BOTTOM:
                return HEIGHT - imgH;
            case CENTER:
                return (HEIGHT - imgH) / 2;
            default:
                // Assumes above dialog alignment, prints out warning
                System.err.println("Unknown vertical alignment: " + vAlign);
            case ABOVE_DIALOGUE:
                return 400 - imgH;
        }
    }

    public static int alignHorizontal(Message.HorizontalAlignment vAlign, Texture img) {
        final int imgW = img != null ? img.getWidth() : 0;
        switch (vAlign) {
            case LEFT:
                return 0;
            case RIGHT:
                return GAME_BORDER - imgW;
            default:
                // Assumes center alignment, prints out warning
                System.err.println("Unknown vertical alignment: " + vAlign);
            case CENTER:
                return (GAME_BORDER - imgW) / 2;
        }
    }
}
