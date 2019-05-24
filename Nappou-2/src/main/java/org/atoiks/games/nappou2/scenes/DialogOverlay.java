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
import java.awt.Image;

import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.SceneManager;

import org.atoiks.games.nappou2.entities.Message;

import static org.atoiks.games.nappou2.App.SANS_FONT;

import static org.atoiks.games.nappou2.scenes.RefittedGameScene.HEIGHT;
import static org.atoiks.games.nappou2.scenes.RefittedGameScene.GAME_BORDER;

/* package */ final class DialogOverlay {

    private String msgSpeaker;
    private String[] msgLines;
    private Image imgMsg;

    private int xoffMsgImg;
    private int yoffMsgImg;

    private SceneManager scene;

    public void attachSceneManager(final SceneManager mgr) {
        this.scene = mgr;
    }

    public void clearMessage() {
        this.imgMsg = null;
        this.msgSpeaker = null;
        this.msgLines = null;
    }

    public void displayMessage(final Message msg) {
        if (msg == null) {
            clearMessage();
            return;
        }

        this.msgSpeaker = msg.speaker == null ? null : msg.speaker + ":";
        this.msgLines = msg.lines;
        this.imgMsg = loadMessageResource(msg);

        adjustVertAlign(msg.getImageVerticalAlignment());
        adjustHorizAlign(msg.getImageHorizontalAlignment());
    }

    public void render(final IGraphics g) {
        if (imgMsg != null) {
            g.drawImage(imgMsg, xoffMsgImg, yoffMsgImg);
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
        g.setFont(TitleScene.OPTION_FONT);
        g.drawString(msgSpeaker, 28, HEIGHT - 162);

        // Draw message inside msgbox
        g.setFont(SANS_FONT);
        for (int i = 0; i < msgLines.length; ++i) {
            g.drawString(msgLines[i], 28, HEIGHT - 142 + i * (SANS_FONT.getSize()) + 10);
        }

        // Draw footer
        g.drawString("Press Enter to continue", GAME_BORDER - 180, HEIGHT - 26);
    }

    private Image loadMessageResource(final Message msg) {
        if (msg.imgRes != null) {
            return (Image) scene.resources().get(msg.imgRes);
        }
        return null;
    }

    private void adjustVertAlign(Message.VerticalAlignment vAlign) {
        switch (vAlign) {
            case TOP:
                this.yoffMsgImg = 0;
                break;
            case BOTTOM:
                this.yoffMsgImg = HEIGHT - getImageHeight();
                break;
            case CENTER:
                this.yoffMsgImg = (HEIGHT - getImageHeight()) / 2;
                break;
            default:
                // Assumes center alignment, ut prints out warning
                System.err.println("Unknown vertical alignment: " + vAlign);
            case ABOVE_DIALOGUE:
                this.yoffMsgImg = 400 - getImageHeight();
                break;
        }
    }

    private void adjustHorizAlign(Message.HorizontalAlignment hAlign) {
        switch (hAlign) {
            case LEFT:
                this.xoffMsgImg = 0;
                break;
            case RIGHT:
                this.xoffMsgImg = GAME_BORDER - getImageWidth();
                break;
            default:
                // Assumes center alignment, but prints out warning
                System.err.println("Unknown horizontal alignment: " + hAlign);
            case CENTER:
                this.xoffMsgImg = (GAME_BORDER - getImageWidth()) / 2;
                break;
        }
    }

    private int getImageWidth() {
        return imgMsg != null ? imgMsg.getWidth(null) : 0;
    }

    private int getImageHeight() {
        return imgMsg != null ? imgMsg.getHeight(null) : 0;
    }
}
