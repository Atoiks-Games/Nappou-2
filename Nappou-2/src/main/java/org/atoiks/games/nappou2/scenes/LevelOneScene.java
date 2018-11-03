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

package org.atoiks.games.nappou2.scenes;

import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import se.tube42.lib.tweeny.Item;
import se.tube42.lib.tweeny.TweenEquation;

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.entities.*;
import org.atoiks.games.nappou2.entities.enemy.*;
import org.atoiks.games.nappou2.entities.bullet.*;

import org.atoiks.games.nappou2.GameConfig;

import static org.atoiks.games.nappou2.Utils.tweenRadialGroupPattern;

public final class LevelOneScene extends AbstractGameScene {

    private static final String[][] PREBOSS_MSG = {
        { "Elle", "Why are you here?" },
        { "Player", "Oh you know, humans." },
        { "Elle", "I no longer find joy in another's pain." },
        { "CAI", "Why so moody?" },
        { "Elle", "..." },
        { "Player", "Yeah, give me a few centuries and things will be back to normal!" },
        { "Elle", "You haven't changed at all *Player*" },
        { "Elle", "You took everything away from me. Do you know how much I suffered?" },
    };
    private static final String[] POSTBOSS_MSG = {
        "I just want to go home..."
    };

    private int cycles;
    private int wave;
    private Clip bgm;
    private int phase;

    private int prebossMsgPhase;

    // loop frame for level
    private static final int LEVEL_LOOP = 1229110;

    // wave-number-diff-name = { bomber1A, bomber2A, bomber1B, bomber2B, ... }
    private static final float[] w1eX = {-10, 760, -7, 754, -12, 760, -11, 755, -11, 755, -11, 755, -11, 755, -11, 755, -11, 755, -11, 755};
    private static final float[] w1eY = {30, 30, 10, 50, 25, 40, 32, 16, 50, 37, 15, 48, 76, 89, 98, 76, 56, 56, 32, 16};
    private static final float[] w1eS = {12, 25, 10, 23, 4, 7, 17, 2, 10, 5, 7, 12, 9, 18, 19, 16, 100, 100, 17, 2};

    private static final float[] w4eX = { 30, GAME_BORDER - 30, 10, GAME_BORDER - 10 };
    private static final float[] w4eR = { 0, (float) Math.PI, (float) (Math.PI / 2), (float) (Math.PI / 2) };

    public LevelOneScene() {
        super(0);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void enter(final int prevSceneId) {
        super.enter(prevSceneId);

        drift.clampSpeed(0,0,0,0);

        resetDialogue();
        cycles = 0;
        wave = 0;
        phase = 0;

        prebossMsgPhase = -1;

        final GameConfig cfg = (GameConfig) scene.resources().get("game.cfg");

        game.player = new Player(GAME_BORDER / 2, HEIGHT / 6 * 5, (IShield) scene.resources().get("shield"));
        game.player.setHp(cfg.challengeMode ? 1 : 5);
        game.setScore(0);

        bgm = (Clip) scene.resources().get("Level_One.wav");
        if (cfg.bgm) {
            bgm.setMicrosecondPosition(0);
            bgm.start();
            bgm.setLoopPoints(LEVEL_LOOP, -1);
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    @Override
    public void leave() {
        super.leave();

        // Stop bgm just in case we forgot
        bgm.stop();
    }

    private boolean displayNextPrebossDialogue() {
        if (++prebossMsgPhase < PREBOSS_MSG.length) {
            final String[] arr = PREBOSS_MSG[prebossMsgPhase];
            updateDialogue(arr[0], arr[1]);
            return true;
        }
        return false;
    }

    @Override
    public boolean postUpdate(float dt) {
        //DEV CHEAT CODE
        if (Input.isKeyPressed(java.awt.event.KeyEvent.VK_P)) {
            scene.gotoNextScene();
            return true;
        }

        ++cycles;
        switch (difficulty) {

            case EASY:
            switch (wave) {
                case 0:
                    switch (cycles) {
                        case 12:
                        case 36:
                        case 60:
                            final int k = cycles / 6 * 25;
                            game.addEnemy(new SingleShotEnemy(1, 300 - k, -10, 8, false));
                            game.addEnemy(new SingleShotEnemy(1, 450 + k, -10, 8, false));
                            break;
                        case 66:
                            game.addEnemy(new DropEnemy(1, -10, 10, 8, false));
                            game.addEnemy(new DropEnemy(1, 760, 10, 8, false));
                            break;
                    }
                    if (cycles > 66 && game.enemies.isEmpty()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 1:
                    switch (cycles) {
                        case 12:
                            game.addEnemy(new DropEnemy(1, 30, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 720, -10, 8, false));
                            break;
                        case 564:
                            game.addEnemy(new DropEnemy(1, 30, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 720, -10, 8, false));
                            break;
                        case 24:
                        case 84:
                        case 144:
                        case 204:
                        case 264:
                        case 324:
                        case 384:
                        case 444:
                        case 504:
                            final int offset = (cycles / 6 - 4) / 5;
                            game.addEnemy(new MiniBomberEnemy(1, w1eX[offset + 0], w1eY[offset + 0], 8, 1, w1eS[offset + 0]));
                            game.addEnemy(new MiniBomberEnemy(1, w1eX[offset + 1], w1eY[offset + 1], 8, -1, w1eS[offset + 1]));
                            break;
                    }
                    if (cycles > 564 && game.enemies.isEmpty()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 2:
                    if (cycles == 12) {
                        game.addEnemy(new MB1(10, 375, -10, 20));
                    }
                    if (cycles > 12 && game.enemies.isEmpty()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 3:
                   switch (cycles) {
                        case 12:
                            game.addEnemy(new MB1(10, 375, -10, 20));
                            break;
                        case 24:
                            game.addEnemy(new CircularPathEnemy(1, 750, 50, 8, 100, 1, 0.25f, 1, 100));
                            game.addEnemy(new CircularPathEnemy(1, 0, 50, 8, 100, -1, 0.25f, 3, 100));
                            break;
                    }
                    if (cycles > 24 && game.enemies.isEmpty()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 4:
                    wave++;
                    cycles = 0;
                    bgm.stop();
                    disableDamage();
                    disableInput();
                    game.clearBullets();

                    displayNextPrebossDialogue();
                    break;
                case 5:
                    if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                        if (!displayNextPrebossDialogue()) {
                            wave++;
                            enableDamage();
                            enableInput();
                            resetDialogue();
                            cycles = 0;
                            bgm = (Clip) scene.resources().get("Broken_Soul.wav");
                            if (((GameConfig) scene.resources().get("game.cfg")).bgm) {
                                bgm.setMicrosecondPosition(0);
                                bgm.start();
                                bgm.loop(Clip.LOOP_CONTINUOUSLY);
                            }
                            game.addEnemy(new Level1Easy(300, 375, -10, 20));
                            drift.accelY = -20;
                            drift.accelX = 20;
                            drift.clampDx(0, 50);
                        }
                    }
                    break;
                case 6:
                    if (cycles % 1200 == 0) {
                        switch (++phase) {
                            case 0:
                                drift.accelY = -20;
                                drift.accelX = 20;
                                drift.clampDx(0, 50);
                                break;
                            case 1:
                                drift.accelX = -20;
                                drift.accelY = 20;
                                drift.clampDy(0,50);
                                break;
                            case 2:
                                drift.accelY = -20;
                                drift.clampDx(-50,0);
                                break;
                            case 3:
                                drift.accelX = 20;
                                drift.clampDy(-50,0);
                                break;
                        }
                    }
                    if (cycles > 12 && game.enemies.isEmpty()) {
                        bgm.stop();
                        disableDamage();
                        updateDialogue("Elle", POSTBOSS_MSG);
                        disableInput();
                        game.clearBullets();
                        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                            scene.switchToScene(0);
                        }
                    }
                    break;
            }
            break;

            case NORMAL:
            switch (wave) {
                case 0:
                    switch (cycles) {
                        case 12:
                        case 24:
                        case 36:
                        case 48:
                        case 60:
                            final int k = cycles / 6 * 25;
                            game.addEnemy(new SingleShotEnemy(1, 300 - k, -10, 8, false));
                            game.addEnemy(new SingleShotEnemy(1, 450 + k, -10, 8, false));
                            break;
                        case 66:
                            game.addEnemy(new DropEnemy(1, -10, 10, 8, false));
                            game.addEnemy(new DropEnemy(1, 760, 10, 8, false));
                            break;
                        case 180:
                            game.addEnemy(new CircularPathEnemy(1, 650, -1, 8, 100, 1, 1, 0, 100));
                            game.addEnemy(new CircularPathEnemy(1, 100, -1, 8, 100, -1, 1, 2, 100));
                            break;
                    }
                    if (cycles > 180 && game.enemies.isEmpty()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 1:
                    switch (cycles) {
                        case 12:
                            game.addEnemy(new DropEnemy(1, 30, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 720, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 50, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 700, -10, 8, false));
                            break;
                        case 564:
                            game.addEnemy(new DropEnemy(1, 30, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 720, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 100, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 650, -10, 8, false));   // FALLTHROUGH
                        case 24:
                        case 84:
                        case 144:
                        case 204:
                        case 264:
                        case 324:
                        case 384:
                        case 444:
                        case 504:
                            final int offset = (cycles / 6 - 4) / 5;
                            game.addEnemy(new MiniBomberEnemy(1, w1eX[offset + 0], w1eY[offset + 0], 8, 1, w1eS[offset + 0]));
                            game.addEnemy(new MiniBomberEnemy(1, w1eX[offset + 1], w1eY[offset + 1], 8, -1, w1eS[offset + 1]));
                            break;
                    }
                    if (cycles > 564 && game.enemies.isEmpty()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 2:
                    if (cycles == 12) {
                        game.addEnemy(new MB1(10, 225, -10, 20));
                        game.addEnemy(new MB1(10, 375, -10, 20));
                        game.addEnemy(new MB1(10, 525, -10, 20));
                    }
                    if (cycles > 12 && game.enemies.isEmpty()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 3:
                   switch (cycles) {
                        case 12:
                            game.addEnemy(new MB1(10, 375, -10, 20));
                            break;
                        case 24:
                            game.addEnemy(new CircularPathEnemy(1, 750, 50, 8, 100, 1, 0.25f, 1, 100));
                            game.addEnemy(new CircularPathEnemy(1, 0, 50, 8, 100, -1, 0.25f, 3, 100));
                            break;
                        case 324:
                            game.addEnemy(new CircularPathEnemy(1, 750, 0, 8, 100, 1, 0.25f, 1, 100));
                            game.addEnemy(new CircularPathEnemy(1, 0, 0, 8, 100, -1, 0.25f, 3, 100));
                            game.addEnemy(new CircularPathEnemy(1, 750, 600, 8, 100, -1, 0.25f, 1, 100));
                            game.addEnemy(new CircularPathEnemy(1, 0, 600, 8, 100, 1, 0.25f, 3, 100));
                            break;
                    }
                    if (cycles > 324 && game.enemies.isEmpty()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 4:
                    if (cycles == 12) {
                        tweenRadialGroupPattern(game, w4eX, w4eR);
                    }
                    if (cycles > 324 && game.enemies.isEmpty()) {
                        wave++;
                        cycles = 0;
                        bgm.stop();
                        disableDamage();
                        disableInput();
                        game.clearBullets();

                        displayNextPrebossDialogue();
                    }
                    break;
                case 5:
                    if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                        if (!displayNextPrebossDialogue()) {
                            wave++;
                            enableDamage();
                            enableInput();
                            resetDialogue();
                            cycles = 0;
                            bgm = (Clip) scene.resources().get("Broken_Soul.wav");
                            if (((GameConfig) scene.resources().get("game.cfg")).bgm) {
                                bgm.setMicrosecondPosition(0);
                                bgm.start();
                                bgm.loop(Clip.LOOP_CONTINUOUSLY);
                            }
                            game.addEnemy(new Level1Normal(300, 375, -10, 20));
                            drift.accelY = -20;
                            drift.accelX = 20;
                            drift.clampDx(0, 100);
                        }
                    }
                    break;
                case 6:
                    if (cycles % 1200 == 0) {
                        switch (++phase) {
                            case 0:
                                drift.accelY = -20;
                                drift.accelX = 20;
                                drift.clampDx(0, 100);
                                break;
                            case 1:
                                drift.accelX = -20;
                                drift.accelY = 20;
                                drift.clampDy(0,100);
                                break;
                            case 2:
                                drift.accelY = -20;
                                drift.clampDx(-100,0);
                                break;
                            case 3:
                                drift.accelX = 20;
                                drift.clampDy(-100,0);
                                break;
                        }
                    }
                    if (cycles > 12 && game.enemies.isEmpty()) {
                        bgm.stop();
                        disableDamage();
                        updateDialogue("Elle", POSTBOSS_MSG);
                        disableInput();
                        game.clearBullets();
                        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                            scene.switchToScene(0);
                        }
                    }
                    break;
            }
            break;

            case HARD:
            switch (wave) {
                case 0:
                    switch (cycles) {
                        case 6:
                        case 12:
                        case 18:
                        case 24:
                        case 30:
                        case 36:
                        case 42:
                        case 48:
                        case 54:
                        case 60:
                            int k = cycles / 6 * 25;
                            game.addEnemy(new SingleShotEnemy(1, 300 - k, -10, 8, false));
                            game.addEnemy(new SingleShotEnemy(1, 450 + k, -10, 8, false));
                            break;
                        case 90:
                        case 120:
                        case 150:
                        case 180:
                            game.addEnemy(new DropEnemy(1, -10, 10, 8, false));
                            game.addEnemy(new DropEnemy(1, 760, 10, 8, false));
                            game.addEnemy(new CircularPathEnemy(1, 650, -1, 8, 100, 1, 1, 0, 100));
                            game.addEnemy(new CircularPathEnemy(1, 100, -1, 8, 100, -1, 1, 2, 100));
                            break;
                        case 186:
                        case 192:
                        case 198:
                        case 204:
                        case 210:
                        case 216:
                        case 222:
                        case 228:
                        case 234:
                        case 240:
                            k = (cycles / 6 - 30) * 25;
                            game.addEnemy(new SingleShotEnemy(1, 300 - k, -10, 8, false));
                            game.addEnemy(new SingleShotEnemy(1, 450 + k, -10, 8, false));
                            break;
                    }
                    if (cycles > 180 && game.enemies.isEmpty()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 1:
                    switch (cycles) {
                        case 12:
                            game.addEnemy(new DropEnemy(1, 30, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 720, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 50, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 700, -10, 8, false));
                            break;
                        case 564: // FALLTHROUGH
                            game.addEnemy(new DropEnemy(1, 30, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 720, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 50, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 700, -10, 8, false));
                        case 24:
                        case 84:
                        case 144:
                        case 204:
                        case 264:
                        case 324:
                        case 384:
                        case 444:
                        case 504:
                            final int offset = (cycles / 6 - 4) / 5;
                            game.addEnemy(new MiniBomberEnemy(1, w1eX[offset + 0], w1eY[offset + 0], 8, 1, 100));
                            game.addEnemy(new MiniBomberEnemy(1, w1eX[offset + 1], w1eY[offset + 1], 8, -1, 100));
                            break;
                    }
                    if (cycles > 564 && game.enemies.isEmpty()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 2:
                    switch (cycles) {
                        case 12:
                            game.addEnemy(new MB1(10, 225, -10, 20));
                            game.addEnemy(new MB1(10, 525, -10, 20));
                            game.addEnemy(new MB1(10, 375, -10, 20));
                            break;
                        case 24:
                        case 84:
                        case 144:
                        case 204:
                        case 264:
                        case 324:
                        case 384:
                        case 444:
                        case 504:
                            final int offset = (cycles / 6 - 4) / 5;
                            game.addEnemy(new AdvancedMiniBomberEnemy(1, w1eX[offset + 0], w1eY[offset + 0], 8, 1, w1eS[offset + 0]));
                            game.addEnemy(new AdvancedMiniBomberEnemy(1, w1eX[offset + 1], w1eY[offset + 1], 8, -1, w1eS[offset + 1]));
                            break;
                    }
                    if (cycles > 132 && game.enemies.isEmpty()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 3:
                   switch (cycles) {
                        case 12:
                            game.addEnemy(new AltMB1(10, 375, -10, 20));
                            break;
                        case 24:
                            game.addEnemy(new CircularPathEnemy(1, 750, 50, 8, 100, 1, 0.25f, 1, 100));
                            game.addEnemy(new CircularPathEnemy(1, 0, 50, 8, 100, -1, 0.25f, 3, 100));
                            break;
                        case 324:
                            game.addEnemy(new AltMB1(10, 375, -10, 20));
                            game.addEnemy(new CircularPathEnemy(1, 750, 50, 8, 100, 1, 0.25f, 1, 100));
                            game.addEnemy(new CircularPathEnemy(1, 0, 50, 8, 100, -1, 0.25f, 3, 100));
                            game.addEnemy(new CircularPathEnemy(1, 750, 0, 8, 100, 1, 0.25f, 1, 100));
                            game.addEnemy(new CircularPathEnemy(1, 0, 0, 8, 100, -1, 0.25f, 3, 100));
                            game.addEnemy(new CircularPathEnemy(1, 750, 600, 8, 100, -1, 0.25f, 1, 100));
                            game.addEnemy(new CircularPathEnemy(1, 0, 600, 8, 100, 1, 0.25f, 3, 100));
                            break;
                        case 624:
                            game.addEnemy(new AltMB1(10, 375, -10, 20));
                            game.addEnemy(new CircularPathEnemy(1, 750, 0, 8, 100, 1, 0.25f, 1, 100));
                            game.addEnemy(new CircularPathEnemy(1, 0, 0, 8, 100, -1, 0.25f, 3, 100));
                            game.addEnemy(new CircularPathEnemy(1, 750, 600, 8, 100, -1, 0.25f, 1, 100));
                            game.addEnemy(new CircularPathEnemy(1, 0, 600, 8, 100, 1, 0.25f, 3, 100));
                            break;
                    }
                    if (cycles > 624 && game.enemies.isEmpty()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 4:
                    switch (cycles) {
                        case 12:
                            tweenRadialGroupPattern(game, w4eX, w4eR);
                            break;
                        case 72:
                        case 132:
                        case 198:
                        case 252:
                        case 312:
                        case 378:
                        case 432:
                        case 492:
                        case 558:
                            game.addEnemy(new DropEnemy(1, 30, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 720, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 100, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 650, -10, 8, false));
                            break;
                    }
                    if (cycles > 558 && game.enemies.isEmpty()) {
                        wave++;
                        cycles = 0;
                        bgm.stop();
                        disableDamage();
                        disableInput();
                        game.clearBullets();

                        displayNextPrebossDialogue();
                    }
                    break;
                case 5:
                    if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                        if (!displayNextPrebossDialogue()) {
                            wave++;
                            enableDamage();
                            enableInput();
                            resetDialogue();
                            cycles = 0;
                            bgm = (Clip) scene.resources().get("Broken_Soul.wav");
                            if (((GameConfig) scene.resources().get("game.cfg")).bgm) {
                                bgm.setMicrosecondPosition(0);
                                bgm.start();
                                bgm.loop(Clip.LOOP_CONTINUOUSLY);
                            }
                            game.addEnemy(new Level1Hard(300, 375, -10, 20));
                            drift.accelY = -20;
                            drift.accelX = 20;
                            drift.clampDx(0, 200);
                        }
                    }
                    break;
                case 6:
                    if (cycles % 1200 == 0) {
                        switch (++phase) {
                            case 0:
                                drift.accelY = -20;
                                drift.accelX = 20;
                                drift.clampDx(0, 200);
                                break;
                            case 1:
                                drift.accelX = -20;
                                drift.accelY = 20;
                                drift.clampDy(0,200);
                                break;
                            case 2:
                                drift.accelY = -20;
                                drift.clampDx(-200,0);
                                break;
                            case 3:
                                drift.accelX = 20;
                                drift.clampDy(-200,0);
                                break;
                        }
                    }
                    if (cycles > 12 && game.enemies.isEmpty()) {
                        bgm.stop();
                        disableDamage();
                        updateDialogue("Elle", POSTBOSS_MSG);
                        disableInput();
                        game.clearBullets();
                        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                            scene.switchToScene(0);
                        }
                    }
                    break;
            }
            break;

            case INSANE:
            //DEV CHEAT CODE
            //if (Input.isKeyPressed(KeyEvent.VK_Q)) {
            //    disableDamage();
            //}
            //if (Input.isKeyPressed(KeyEvent.VK_E)) {
            //    enableDamage();
            //}
            switch (wave) {
                case 0:
                    switch (cycles) {
                        case 6:
                        case 12:
                        case 18:
                        case 24:
                        case 30:
                        case 36:
                        case 42:
                        case 48:
                        case 54:
                        case 60:
                            int k = cycles / 6 * 25;
                            game.addEnemy(new SingleShotEnemy(1, 300 - k, 610, 8, true));
                            game.addEnemy(new SingleShotEnemy(1, 450 + k, 610, 8, true));
                            game.addEnemy(new SingleShotEnemy(1, 300 - k, -10, 8, false));
                            game.addEnemy(new SingleShotEnemy(1, 450 + k, -10, 8, false));
                            break;
                        case 90:
                        case 120:
                        case 150:
                        case 180:
                            game.addEnemy(new DropEnemy(1, -10, 10, 8, false));
                            game.addEnemy(new DropEnemy(1, 760, 10, 8, false));
                            game.addEnemy(new CircularPathEnemy(1, 650, -1, 8, 100, 1, 1, 0, 100));
                            game.addEnemy(new CircularPathEnemy(1, 100, -1, 8, 100, -1, 1, 2, 100));
                            break;
                        case 186:
                        case 192:
                        case 198:
                        case 204:
                        case 210:
                        case 216:
                        case 222:
                        case 228:
                        case 234:
                        case 240:
                            k = (cycles / 6 - 30) * 25;
                            game.addEnemy(new SingleShotEnemy(1, 300 - k, 610, 8, true));
                            game.addEnemy(new SingleShotEnemy(1, 450 + k, 610, 8, true));
                            game.addEnemy(new SingleShotEnemy(1, 300 - k, -10, 8, false));
                            game.addEnemy(new SingleShotEnemy(1, 450 + k, -10, 8, false));
                            break;
                        case 270:
                        case 300:
                        case 330:
                        case 360:
                            game.addEnemy(new DropEnemy(1, -10, 10, 8, false));
                            game.addEnemy(new DropEnemy(1, 760, 10, 8, false));
                            game.addEnemy(new CircularPathEnemy(1, 650, -1, 8, 100, 1, 1, 0, 100));
                            game.addEnemy(new CircularPathEnemy(1, 100, -1, 8, 100, -1, 1, 2, 100));
                            break;
                        case 366:
                        case 372:
                        case 378:
                        case 384:
                        case 390:
                        case 396:
                        case 402:
                        case 408:
                        case 414:
                        case 420:
                            k = (cycles / 6 - 40) * 25;
                            game.addEnemy(new SingleShotEnemy(1, 300 - k, 610, 8, true));
                            game.addEnemy(new SingleShotEnemy(1, 450 + k, 610, 8, true));
                            game.addEnemy(new SingleShotEnemy(1, 300 - k, -10, 8, false));
                            game.addEnemy(new SingleShotEnemy(1, 450 + k, -10, 8, false));
                            break;
                        case 450:
                        case 480:
                        case 510:
                        case 540:
                            game.addEnemy(new DropEnemy(1, -10, 10, 8, false));
                            game.addEnemy(new DropEnemy(1, 760, 10, 8, false));
                            game.addEnemy(new CircularPathEnemy(1, 650, -1, 8, 100, 1, 1, 0, 100));
                            game.addEnemy(new CircularPathEnemy(1, 100, -1, 8, 100, -1, 1, 2, 100));
                            break;
                    }
                    if (cycles > 540 && game.enemies.isEmpty()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 1:
                    switch (cycles) {
                        case 24:
                        case 84:
                        case 144:
                        case 204:
                        case 264:
                        case 324:
                        case 384:
                        case 444:
                        case 504:
                        case 564:
                            game.addEnemy(new DropEnemy(1, 30, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 720, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 50, 610, 8, true));
                            game.addEnemy(new DropEnemy(1, 700, 610, 8, true));
                            final int offset = (cycles / 6 - 4) / 5;
                            game.addEnemy(new AdvancedMiniBomberEnemy(1, w1eX[offset + 0], w1eY[offset + 0], 8, 1, 100));
                            game.addEnemy(new AdvancedMiniBomberEnemy(1, w1eX[offset + 1], w1eY[offset + 1], 8, -1, 100));
                            break;
                    }
                    if (cycles > 564 && game.enemies.isEmpty()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 2:
                    switch (cycles) {
                        case 12:
                            game.addEnemy(new MB1(10, 225, -10, 20));
                            game.addEnemy(new MB1(10, 525, -10, 20));
                            game.addEnemy(new AltMB1(10, 375, -10, 20));
                            break;
                        case 24:
                        case 84:
                        case 144:
                        case 204:
                        case 264:
                        case 324:
                        case 384:
                        case 444:
                        case 504:
                            final int offset = (cycles / 6 - 4) / 5;
                            game.addEnemy(new AdvancedMiniBomberEnemy(1, w1eX[offset + 0], w1eY[offset + 0], 8, 1, 100));
                            game.addEnemy(new AdvancedMiniBomberEnemy(1, w1eX[offset + 1], w1eY[offset + 1], 8, -1, 100));
                            break;
                        case 150:
                        case 270:
                        case 390:
                        case 510:
                            game.addEnemy(new AltMB1(10, 375, -10, 20));
                            break;
                    }
                    if (cycles > 132 && game.enemies.isEmpty()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 3:
                   switch (cycles) {
                        case 12:
                            game.addEnemy(new AltMB1(10, 375, -10, 20));
                            break;
                        case 24:
                            game.addEnemy(new CircularPathEnemy(1, 750, 50, 8, 100, 1, 0.25f, 1, 100));
                            game.addEnemy(new CircularPathEnemy(1, 0, 50, 8, 100, -1, 0.25f, 3, 100));
                            game.addEnemy(new AltMB1(10, 225, -10, 20));
                            game.addEnemy(new AltMB1(10, 525, -10, 20));
                            break;
                        case 324:
                            game.addEnemy(new AltMB1(10, 375, -10, 20));
                            game.addEnemy(new AltMB1(10, 225, -10, 20));
                            game.addEnemy(new AltMB1(10, 525, -10, 20));
                            game.addEnemy(new CircularPathEnemy(1, 750, 50, 8, 100, 1, 0.25f, 1, 100));
                            game.addEnemy(new CircularPathEnemy(1, 0, 50, 8, 100, -1, 0.25f, 3, 100));
                            game.addEnemy(new CircularPathEnemy(1, 750, 0, 8, 100, 1, 0.25f, 1, 100));
                            game.addEnemy(new CircularPathEnemy(1, 0, 0, 8, 100, -1, 0.25f, 3, 100));
                            game.addEnemy(new CircularPathEnemy(1, 750, 600, 8, 100, -1, 0.25f, 1, 100));
                            game.addEnemy(new CircularPathEnemy(1, 0, 600, 8, 100, 1, 0.25f, 3, 100));
                            break;
                        case 624:
                            game.addEnemy(new AltMB1(10, 375, -10, 20));
                            game.addEnemy(new AltMB1(10, 225, -10, 20));
                            game.addEnemy(new AltMB1(10, 525, -10, 20));
                            game.addEnemy(new CircularPathEnemy(1, 750, 0, 8, 100, 1, 0.25f, 1, 100));
                            game.addEnemy(new CircularPathEnemy(1, 0, 0, 8, 100, -1, 0.25f, 3, 100));
                            game.addEnemy(new CircularPathEnemy(1, 750, 600, 8, 100, -1, 0.25f, 1, 100));
                            game.addEnemy(new CircularPathEnemy(1, 0, 600, 8, 100, 1, 0.25f, 3, 100));
                            break;
                    }
                    if (cycles > 624 && game.enemies.isEmpty()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 4:
                    switch (cycles) {
                        case 12:
                        case 72:
                        case 132:
                        case 198:
                        case 252:
                        case 312:
                        case 378:
                        case 432:
                        case 492:
                        case 558:
                            game.addEnemy(new DropEnemy(1, 30, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 100, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 720, 610, 8, true));
                            game.addEnemy(new DropEnemy(1, 650, 610, 8, true));
                            game.addEnemy(new SingleShotEnemy(1, 10, 610, 8, true));
                            game.addEnemy(new SingleShotEnemy(1, 740, -10, 8, false));
                            game.addEnemy(new SingleShotEnemy(1, 450, 610, 8, true));
                            game.addEnemy(new SingleShotEnemy(1, 300, -10, 8, false));
                            break;
                        case 150:
                            game.addEnemy(new ShieldTesterEnemy(20, 300, -10, 8, true));
                            game.addEnemy(new ShieldTesterEnemy(20, 450, -10, 8, true));
                            break;
                        case 450:
                            game.addEnemy(new ShieldTesterEnemy(20, 300, -10, 8, true));
                            game.addEnemy(new ShieldTesterEnemy(20, 450, -10, 8, true));
                            break;

                    }
                    if (cycles > 600 && game.enemies.isEmpty()) {
                        wave++;
                        cycles = 0;
                        bgm.stop();
                        disableDamage();
                        disableInput();
                        game.clearBullets();

                        displayNextPrebossDialogue();
                    }
                    break;
                case 5:
                    if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                        if (!displayNextPrebossDialogue()) {
                            wave++;
                            enableDamage();
                            enableInput();
                            resetDialogue();
                            cycles = 0;
                            bgm = (Clip) scene.resources().get("Broken_Soul.wav");
                            if (((GameConfig) scene.resources().get("game.cfg")).bgm) {
                                bgm.setMicrosecondPosition(0);
                                bgm.start();
                                bgm.loop(Clip.LOOP_CONTINUOUSLY);
                            }
                            game.addEnemy(new Level1Insane(300, 375, -10, 20));
                            drift.accelY = -20;
                            drift.accelX = 20;
                            drift.clampDx(0, 200);
                        }
                    }
                    break;
                case 6:
                    if (cycles % 1200 == 0) {
                        switch (++phase) {
                            case 0:
                                drift.accelY = -20;
                                drift.accelX = 20;
                                drift.clampDx(0, 250);
                                break;
                            case 1:
                                drift.accelX = -20;
                                drift.accelY = 20;
                                drift.clampDy(0,250);
                                break;
                            case 2:
                                drift.accelY = -20;
                                drift.clampDx(-250,0);
                                break;
                            case 3:
                                drift.accelX = 20;
                                drift.clampDy(-250,0);
                                break;
                        }
                    }
                    if (cycles > 12 && game.enemies.isEmpty()) {
                        bgm.stop();
                        disableDamage();
                        updateDialogue("Elle", POSTBOSS_MSG);
                        disableInput();
                        game.clearBullets();
                        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                            scene.switchToScene(0);
                        }
                    }
                    break;
                }
            break;
        }
        return true;
    }
}
