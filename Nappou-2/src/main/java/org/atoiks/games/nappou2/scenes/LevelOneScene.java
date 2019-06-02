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

import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import java.util.Random;

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.SceneManager;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.entities.shield.IShield;

import org.atoiks.games.nappou2.entities.*;
import org.atoiks.games.nappou2.entities.enemy.*;
import org.atoiks.games.nappou2.entities.bullet.*;

import org.atoiks.games.nappou2.GameConfig;
import org.atoiks.games.nappou2.SaveData;

import org.atoiks.games.nappou2.spawner.FishSpawner;

import static org.atoiks.games.nappou2.Utils.mb1;
import static org.atoiks.games.nappou2.Utils.altMb1;
import static org.atoiks.games.nappou2.Utils.miniBomberEnemy;
import static org.atoiks.games.nappou2.Utils.singleShotEnemy;
import static org.atoiks.games.nappou2.Utils.circularPathEnemy;
import static org.atoiks.games.nappou2.Utils.advancedMiniBomberEnemy;
import static org.atoiks.games.nappou2.Utils.tweenRadialGroupPattern;

import org.atoiks.games.nappou2.Difficulty;

import static org.atoiks.games.nappou2.entities.Message.HorizontalAlignment;

public final class LevelOneScene extends AbstractGameScene {

    private static final Message[] PREBOSS_MSG = {
        new Message("ELLE.png", HorizontalAlignment.RIGHT, "ELLE", "Why are you here?"),
        new Message("LUMA.png", HorizontalAlignment.LEFT, "LUMA", "Oh you know, humans."),
        new Message("ELLE.png", HorizontalAlignment.RIGHT, "ELLE", "I no longer find joy in another's pain."),
        new Message("CAI.png", HorizontalAlignment.CENTER, "CAI", "Why so moody?"),
        new Message("ELLE.png", HorizontalAlignment.RIGHT, "ELLE", "..."),
        new Message("LUMA.png", HorizontalAlignment.LEFT, "LUMA", "Yeah, give me a few centuries and things will be back to normal!"),
        new Message("ELLE.png", HorizontalAlignment.RIGHT, "ELLE", "You haven't changed at all Luma!"),
        new Message(null      , HorizontalAlignment.RIGHT, "ELLE", "You took everything away from me. Do you know how much I suffered?"),
    };

    private static final Message POSTBOSS_MSG = new Message("ELLE.png", HorizontalAlignment.RIGHT, "ELLE", "I just want to go home...");

    private int cycles;
    private int wave;
    private Clip bgm;
    private int phase;
    private Difficulty dif;

    private int prebossMsgPhase;

    private final Random rnd = new Random();

    // loop frame for level
    private static final int LEVEL_LOOP = 1229110;

    // loop frame for BOSS
    private static final int BOSS_LOOP = 1222000;


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
    public void enter(final String prevSceneId) {
        super.enter(prevSceneId);

        drift.clampSpeed(0,0,0,0);

        clearMessage();
        cycles = 0;
        wave = 0;
        phase = 0;

        prebossMsgPhase = -1;

        final SaveData sData = ResourceManager.get("./saves.dat");
        dif = sData.getDif();

        final GameConfig cfg = ResourceManager.get("./game.cfg");

        game.player = new Player(GAME_BORDER / 2, HEIGHT / 6 * 5, sData.getSheild());
        game.player.setHp(cfg.challengeMode ? 1 : 5);
        game.setScore(0);

        bgm = ResourceManager.get("/music/Level_One.wav");
        if (cfg.bgm) {
            bgm.setMicrosecondPosition(0);
            bgm.start();
            bgm.setLoopPoints(LEVEL_LOOP, -1);
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        }

        switch (sData.getCheck()){
            case 3:
                wave = 5;
                break;
            default:
                //Do nothing
                break;
        }
    }

    @Override
    public void leave() {
        // Stop bgm just in case we forgot
        bgm.stop();
        super.leave();
    }

    private boolean displayNextPrebossDialogue() {
        if (++prebossMsgPhase < PREBOSS_MSG.length) {
            displayMessage(PREBOSS_MSG[prebossMsgPhase]);
            return true;
        }

        // no more dialogs, must clear out player portrait
        clearMessage();
        return false;
    }

    @Override
    public boolean postUpdate(float dt) {
        //DEV CHEAT CODES
        //if (Input.isKeyPressed(java.awt.event.KeyEvent.VK_P)) {
        //    return SceneManager.switchToScene("LevelTwoScene");
        //}
        //if (Input.isKeyPressed(java.awt.event.KeyEvent.VK_Q)) {
        //    disableDamage();
        // }
        //if (Input.isKeyPressed(java.awt.event.KeyEvent.VK_E)) {
        //    enableDamage();
        //}
        //if (Input.isKeyPressed(java.awt.event.KeyEvent.VK_O)) {
        //    final SaveData sData = ResourceManager.get("./saves.dat");
        //    sData.setCheck(0);
        //}

        ++cycles;
        switch (dif) {

            case EASY:
            switch (wave) {
                case 0:
                    switch (cycles) {
                        //Fish group 1
                        case 400:
                            game.addEnemySpawner(new FishSpawner(375, 10, -10, 0, 500, 7 * (float) Math.PI / 12, 1000, false));
                            game.addEnemySpawner(new FishSpawner(375, 10, -10, 0, 500, 5 * (float) Math.PI / 12, 1000, true));
                            break;

                        //Fish group 2
                        case 455:
                            game.addEnemySpawner(new FishSpawner(760, 0, 50, 10, 500, (float) Math.PI, 100, false));
                            game.addEnemySpawner(new FishSpawner(-10, 0, 550, 10, 500, 0, 100, true));
                            game.addEnemySpawner(new FishSpawner(760, 0, 400, 10, 500, (float) Math.PI, 100, false));
                            game.addEnemySpawner(new FishSpawner(-10, 0, 200, 10, 500, 0, 100, true));
                    }
                    if (cycles > 605 && game.noMoreEnemies()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 1:
                    switch (cycles) {
                        case 50:
                        case 100:
                        case 150:
                        case 200:
                        case 250:
                        case 300:
                        case 350:
                        case 400:
                        case 450:
                        case 500:
                        case 550:
                        case 600:
                        case 650:
                        case 700:
                        case 750:
                        case 800:
                        case 850:
                        case 900:
                        case 950:
                        case 1000:
                        case 1050:
                        case 1100:
                        case 1150:
                        case 1200:
                        case 1250:
                        case 1300:
                        case 1350:
                        case 1400:
                        case 1450:
                        case 1500:
                        case 1550:
                        case 1600:
                        case 1650:
                        case 1700:
                        case 1750:
                        case 1800:
                            game.addEnemy(new FishPart(1, 750 * rnd.nextFloat(), 615, 20 * rnd.nextFloat() + 10, 900 * rnd.nextFloat() + 100, 3 * (float) Math.PI / 2, 900 * rnd.nextFloat() + 100, 10, rnd.nextBoolean()));
                            break;
                    }
                    if (cycles > 1800 && game.noMoreEnemies()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 2:
                    if (cycles == 40) {
                        game.addEnemy(new Ripple(10, 375, -10, 20, 500, (float) Math.PI / 2));
                        game.addEnemySpawner(new FishSpawner(700, 10, 610, 0, 250, 3 * (float) Math.PI / 2, 100, false));
                        game.addEnemySpawner(new FishSpawner(50, 10, 610, 0, 250, 3 * (float) Math.PI / 2, 100, true));
                    }
                    if (cycles > 40 && game.noMoreEnemies()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 3:
                   switch (cycles) {
                        case 40:
                            game.addEnemy(new Squirts(10, 275, -10, 20));
                            game.addEnemy(new Squirts(10, 475, -10, 20));
                            break;
                    }
                    if (cycles > 80 && game.noMoreEnemies()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 4:
                    switch (cycles) {
                        case 40:
                        case 440:
                        case 840:
                        case 1240:
                        case 1640:
                        case 2040:
                            game.addEnemy(new StreamBeam(1, 10, 610, 8, true));
                            game.addEnemy(new StreamBeam(1, 375, -10, 8, false));
                            game.addEnemy(new StreamBeam(1, 740, 610, 8, true));
                            break;
                    }
                    if (cycles > 2040 && game.noMoreEnemies()) {
                        final SaveData sData = ResourceManager.get("./saves.dat");
                        sData.setCheck(3);
                        wave++;
                        cycles = 0;
                    }
                    break;

                    /*
                case 4:
                    wave++;
                    cycles = 0;
                    bgm.stop();
                    disableDamage();
                    shouldSkipPlayerUpdate(true);
                    game.clearBullets();
                    bgm = ResourceManager.get("/music/Level_One_Boss.wav");
                    if (ResourceManager.<GameConfig>get("./game.cfg").bgm) {
                        bgm.setMicrosecondPosition(0);
                        bgm.start();
                        bgm.setLoopPoints(BOSS_LOOP, -1);
                        bgm.loop(Clip.LOOP_CONTINUOUSLY);
                    }
                    displayNextPrebossDialogue();
                    break;
                case 5:
                    if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                        if (!displayNextPrebossDialogue()) {
                            wave++;
                            enableDamage();
                            shouldSkipPlayerUpdate(false);
                            cycles = 0;
                            game.addEnemy(new Level1Easy(300, 375, -10, 20));
                            drift.accelY = -20;
                            drift.accelX = 20;
                            drift.clampDx(0, 50);
                        }
                    }
                    break;
                case 6:
                    if (cycles % 4000 == 0) {
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
                    if (cycles > 40 && game.noMoreEnemies()) {
                        bgm.stop();
                        disableDamage();
                        shouldSkipPlayerUpdate(true);
                        game.clearBullets();
                        displayMessage(POSTBOSS_MSG);
                        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                            // Ask for name, and have PromptNameScene switch scene for us to $prompt.trans
                            SceneManager.resources().put("prompt.trans", "TitleScene");
                            return SceneManager.switchToScene("SaveHighscoreScene");
                        }
                    }
                    break;*/
            }
            break;

            case NORMAL:
            switch (wave) {
                case 0:
                    switch (cycles) {
                        case 40:
                        case 80:
                        case 120:
                        case 160:
                        case 200:
                            final int k = cycles * 5 / 4;
                            game.addEnemy(singleShotEnemy(1, 300 - k, -10, 8, false));
                            game.addEnemy(singleShotEnemy(1, 450 + k, -10, 8, false));
                            break;
                        case 220:
                            game.addEnemy(new DropEnemy(1, -10, 10, 8, false));
                            game.addEnemy(new DropEnemy(1, 760, 10, 8, false));
                            break;
                        case 600:
                            game.addEnemy(circularPathEnemy(1, 650, -1, 8, 100, 1, 1, 0, 100));
                            game.addEnemy(circularPathEnemy(1, 100, -1, 8, 100, -1, 1, 2, 100));
                            break;
                    }
                    if (cycles > 600 && game.noMoreEnemies()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 1:
                    switch (cycles) {
                        case 40:
                            game.addEnemy(new DropEnemy(1, 30, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 720, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 50, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 700, -10, 8, false));
                            break;
                        case 1880:
                            game.addEnemy(new DropEnemy(1, 30, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 720, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 100, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 650, -10, 8, false));   // FALLTHROUGH
                        case 80:
                        case 280:
                        case 480:
                        case 680:
                        case 880:
                        case 1080:
                        case 1280:
                        case 1480:
                        case 1680:
                            final int offset = (cycles / 4 / 5 - 4) / 5;
                            game.addEnemy(miniBomberEnemy(1, w1eX[offset + 0], w1eY[offset + 0], 8, 1, w1eS[offset + 0]));
                            game.addEnemy(miniBomberEnemy(1, w1eX[offset + 1], w1eY[offset + 1], 8, -1, w1eS[offset + 1]));
                            break;
                    }
                    if (cycles > 1880 && game.noMoreEnemies()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 2:
                    if (cycles == 40) {
                        game.addEnemy(mb1(10, 225, -10, 20));
                        game.addEnemy(mb1(10, 375, -10, 20));
                        game.addEnemy(mb1(10, 525, -10, 20));
                    }
                    if (cycles > 40 && game.noMoreEnemies()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 3:
                   switch (cycles) {
                        case 40:
                            game.addEnemy(mb1(10, 375, -10, 20));
                            break;
                        case 80:
                            game.addEnemy(circularPathEnemy(1, 750, 50, 8, 100, 1, 0.25f, 1, 100));
                            game.addEnemy(circularPathEnemy(1, 0, 50, 8, 100, -1, 0.25f, 3, 100));
                            break;
                        case 680:
                            game.addEnemy(circularPathEnemy(1, 750, 0, 8, 100, 1, 0.25f, 1, 100));
                            game.addEnemy(circularPathEnemy(1, 0, 0, 8, 100, -1, 0.25f, 3, 100));
                            game.addEnemy(circularPathEnemy(1, 750, 600, 8, 100, -1, 0.25f, 1, 100));
                            game.addEnemy(circularPathEnemy(1, 0, 600, 8, 100, 1, 0.25f, 3, 100));
                            break;
                    }
                    if (cycles > 680 && game.noMoreEnemies()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 4:
                    if (cycles == 40) {
                        tweenRadialGroupPattern(game, w4eX, w4eR);
                    }
                    if (cycles > 680 && game.noMoreEnemies()) {
                        wave++;
                        cycles = 0;
                        bgm.stop();
                        disableDamage();
                        shouldSkipPlayerUpdate(true);
                        game.clearBullets();
                        bgm = ResourceManager.get("/music/Level_One_Boss.wav");
                        if (ResourceManager.<GameConfig>get("./game.cfg").bgm) {
                            bgm.setMicrosecondPosition(0);
                            bgm.start();
                            bgm.setLoopPoints(BOSS_LOOP, -1);
                            bgm.loop(Clip.LOOP_CONTINUOUSLY);
                        }
                        displayNextPrebossDialogue();
                    }
                    break;
                case 5:
                    if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                        if (!displayNextPrebossDialogue()) {
                            wave++;
                            enableDamage();
                            shouldSkipPlayerUpdate(false);
                            cycles = 0;
                            game.addEnemy(new Level1Normal(300, 375, -10, 20));
                            drift.accelY = -20;
                            drift.accelX = 20;
                            drift.clampDx(0, 100);
                        }
                    }
                    break;
                case 6:
                    if (cycles % 4000 == 0) {
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
                    if (cycles > 40 && game.noMoreEnemies()) {
                        bgm.stop();
                        disableDamage();
                        shouldSkipPlayerUpdate(true);
                        game.clearBullets();
                        displayMessage(POSTBOSS_MSG);
                        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                            // Ask for name, and have PromptNameScene switch scene for us to $prompt.trans
                            SceneManager.resources().put("prompt.trans", "TitleScene");
                            return SceneManager.switchToScene("SaveHighscoreScene");
                        }
                    }
                    break;
            }
            break;

            case HARD:
            switch (wave) {
                case 0:
                    switch (cycles) {
                        case 20:
                        case 40:
                        case 60:
                        case 80:
                        case 100:
                        case 120:
                        case 140:
                        case 160:
                        case 180:
                        case 200:
                            int k = cycles / 4 * 5;
                            game.addEnemy(singleShotEnemy(1, 300 - k, -10, 8, false));
                            game.addEnemy(singleShotEnemy(1, 450 + k, -10, 8, false));
                            break;
                        case 300:
                        case 400:
                        case 500:
                        case 600:
                            game.addEnemy(new DropEnemy(1, -10, 10, 8, false));
                            game.addEnemy(new DropEnemy(1, 760, 10, 8, false));
                            game.addEnemy(circularPathEnemy(1, 650, -1, 8, 100, 1, 1, 0, 100));
                            game.addEnemy(circularPathEnemy(1, 100, -1, 8, 100, -1, 1, 2, 100));
                            break;
                        case 620:
                        case 640:
                        case 660:
                        case 680:
                        case 700:
                        case 720:
                        case 740:
                        case 760:
                        case 780:
                        case 800:
                            k = (cycles / 4 / 5 - 30) * 25;
                            game.addEnemy(singleShotEnemy(1, 300 - k, -10, 8, false));
                            game.addEnemy(singleShotEnemy(1, 450 + k, -10, 8, false));
                            break;
                    }
                    if (cycles > 800 && game.noMoreEnemies()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 1:
                    switch (cycles) {
                        case 40:
                            game.addEnemy(new DropEnemy(1, 30, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 720, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 50, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 700, -10, 8, false));
                            break;
                        case 1880: // FALLTHROUGH
                            game.addEnemy(new DropEnemy(1, 30, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 720, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 50, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 700, -10, 8, false));
                        case 80:
                        case 280:
                        case 480:
                        case 680:
                        case 880:
                        case 1080:
                        case 1280:
                        case 1480:
                        case 1680:
                            final int offset = (cycles / 4 / 5 - 4) / 5;
                            game.addEnemy(miniBomberEnemy(1, w1eX[offset + 0], w1eY[offset + 0], 8, 1, 100));
                            game.addEnemy(miniBomberEnemy(1, w1eX[offset + 1], w1eY[offset + 1], 8, -1, 100));
                            break;
                    }
                    if (cycles > 1880 && game.noMoreEnemies()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 2:
                    switch (cycles) {
                        case 40:
                            game.addEnemy(mb1(10, 225, -10, 20));
                            game.addEnemy(mb1(10, 525, -10, 20));
                            game.addEnemy(mb1(10, 375, -10, 20));
                            break;
                        case 80:
                        case 280:
                        case 480:
                        case 680:
                        case 880:
                        case 1080:
                        case 1280:
                        case 1480:
                        case 1680:
                            final int offset = (cycles / 4 / 5 - 4) / 5;
                            game.addEnemy(advancedMiniBomberEnemy(1, w1eX[offset + 0], w1eY[offset + 0], 8, 1, w1eS[offset + 0]));
                            game.addEnemy(advancedMiniBomberEnemy(1, w1eX[offset + 1], w1eY[offset + 1], 8, -1, w1eS[offset + 1]));
                            break;
                    }
                    if (cycles > 1680 && game.noMoreEnemies()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 3:
                   switch (cycles) {
                        case 40:
                            game.addEnemy(altMb1(10, 375, -10, 20));
                            break;
                        case 80:
                            game.addEnemy(circularPathEnemy(1, 750, 50, 8, 100, 1, 0.25f, 1, 100));
                            game.addEnemy(circularPathEnemy(1, 0, 50, 8, 100, -1, 0.25f, 3, 100));
                            break;
                        case 1080:
                            game.addEnemy(altMb1(10, 375, -10, 20));
                            game.addEnemy(circularPathEnemy(1, 750, 50, 8, 100, 1, 0.25f, 1, 100));
                            game.addEnemy(circularPathEnemy(1, 0, 50, 8, 100, -1, 0.25f, 3, 100));
                            game.addEnemy(circularPathEnemy(1, 750, 0, 8, 100, 1, 0.25f, 1, 100));
                            game.addEnemy(circularPathEnemy(1, 0, 0, 8, 100, -1, 0.25f, 3, 100));
                            game.addEnemy(circularPathEnemy(1, 750, 600, 8, 100, -1, 0.25f, 1, 100));
                            game.addEnemy(circularPathEnemy(1, 0, 600, 8, 100, 1, 0.25f, 3, 100));
                            break;
                        case 2080:
                            game.addEnemy(altMb1(10, 375, -10, 20));
                            game.addEnemy(circularPathEnemy(1, 750, 0, 8, 100, 1, 0.25f, 1, 100));
                            game.addEnemy(circularPathEnemy(1, 0, 0, 8, 100, -1, 0.25f, 3, 100));
                            game.addEnemy(circularPathEnemy(1, 750, 600, 8, 100, -1, 0.25f, 1, 100));
                            game.addEnemy(circularPathEnemy(1, 0, 600, 8, 100, 1, 0.25f, 3, 100));
                            break;
                    }
                    if (cycles > 2080 && game.noMoreEnemies()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 4:
                    switch (cycles) {
                        case 40:
                            tweenRadialGroupPattern(game, w4eX, w4eR);
                            break;
                        case 240:
                        case 440:
                        case 640:
                        case 840:
                        case 1040:
                        case 1240:
                        case 1440:
                        case 1640:
                        case 1840:
                            game.addEnemy(new DropEnemy(1, 30, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 720, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 100, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 650, -10, 8, false));
                            break;
                    }
                    if (cycles > 1840 && game.noMoreEnemies()) {
                        wave++;
                        cycles = 0;
                        bgm.stop();
                        disableDamage();
                        shouldSkipPlayerUpdate(true);
                        game.clearBullets();
                        bgm = ResourceManager.get("/music/Level_One_Boss.wav");
                        if (ResourceManager.<GameConfig>get("./game.cfg").bgm) {
                            bgm.setMicrosecondPosition(0);
                            bgm.start();
                            bgm.setLoopPoints(BOSS_LOOP, -1);
                            bgm.loop(Clip.LOOP_CONTINUOUSLY);
                        }
                        displayNextPrebossDialogue();
                    }
                    break;
                case 5:
                    if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                        if (!displayNextPrebossDialogue()) {
                            wave++;
                            enableDamage();
                            shouldSkipPlayerUpdate(false);
                            cycles = 0;
                            game.addEnemy(new Level1Hard(300, 375, -10, 20));
                            drift.accelY = -20;
                            drift.accelX = 20;
                            drift.clampDx(0, 200);
                        }
                    }
                    break;
                case 6:
                    if (cycles % 4000 == 0) {
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
                    if (cycles > 40 && game.noMoreEnemies()) {
                        bgm.stop();
                        disableDamage();
                        shouldSkipPlayerUpdate(true);
                        game.clearBullets();
                        displayMessage(POSTBOSS_MSG);
                        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                            // Ask for name, and have PromptNameScene switch scene for us to $prompt.trans
                            SceneManager.resources().put("prompt.trans", "TitleScene");
                            return SceneManager.switchToScene("SaveHighscoreScene");
                        }
                    }
                    break;
            }
            break;

            case INSANE:
            switch (wave) {
                case 0:
                    switch (cycles) {
                        case 20:
                        case 40:
                        case 60:
                        case 80:
                        case 100:
                        case 120:
                        case 140:
                        case 160:
                        case 180:
                        case 200:
                            int k = cycles / 4 * 5;
                            game.addEnemy(singleShotEnemy(1, 300 - k, 610, 8, true));
                            game.addEnemy(singleShotEnemy(1, 450 + k, 610, 8, true));
                            game.addEnemy(singleShotEnemy(1, 300 - k, -10, 8, false));
                            game.addEnemy(singleShotEnemy(1, 450 + k, -10, 8, false));
                            break;
                        case 300:
                        case 400:
                        case 500:
                        case 600:
                            game.addEnemy(new DropEnemy(1, -10, 10, 8, false));
                            game.addEnemy(new DropEnemy(1, 760, 10, 8, false));
                            game.addEnemy(circularPathEnemy(1, 650, -1, 8, 100, 1, 1, 0, 100));
                            game.addEnemy(circularPathEnemy(1, 100, -1, 8, 100, -1, 1, 2, 100));
                            break;
                        case 620:
                        case 640:
                        case 660:
                        case 680:
                        case 700:
                        case 720:
                        case 740:
                        case 760:
                        case 780:
                        case 800:
                            k = (cycles / 4 / 5 - 30) * 25;
                            game.addEnemy(singleShotEnemy(1, 300 - k, 610, 8, true));
                            game.addEnemy(singleShotEnemy(1, 450 + k, 610, 8, true));
                            game.addEnemy(singleShotEnemy(1, 300 - k, -10, 8, false));
                            game.addEnemy(singleShotEnemy(1, 450 + k, -10, 8, false));
                            break;
                        case 900:
                        case 1000:
                        case 1100:
                        case 1200:
                            game.addEnemy(new DropEnemy(1, -10, 10, 8, false));
                            game.addEnemy(new DropEnemy(1, 760, 10, 8, false));
                            game.addEnemy(circularPathEnemy(1, 650, -1, 8, 100, 1, 1, 0, 100));
                            game.addEnemy(circularPathEnemy(1, 100, -1, 8, 100, -1, 1, 2, 100));
                            break;
                        case 1220:
                        case 1240:
                        case 1260:
                        case 1280:
                        case 1300:
                        case 1320:
                        case 1340:
                        case 1360:
                        case 1380:
                        case 1400:
                            k = (cycles / 4 / 5 - 40) * 25;
                            game.addEnemy(singleShotEnemy(1, 300 - k, 610, 8, true));
                            game.addEnemy(singleShotEnemy(1, 450 + k, 610, 8, true));
                            game.addEnemy(singleShotEnemy(1, 300 - k, -10, 8, false));
                            game.addEnemy(singleShotEnemy(1, 450 + k, -10, 8, false));
                            break;
                        case 1500:
                        case 1600:
                        case 1700:
                        case 1800:
                            game.addEnemy(new DropEnemy(1, -10, 10, 8, false));
                            game.addEnemy(new DropEnemy(1, 760, 10, 8, false));
                            game.addEnemy(circularPathEnemy(1, 650, -1, 8, 100, 1, 1, 0, 100));
                            game.addEnemy(circularPathEnemy(1, 100, -1, 8, 100, -1, 1, 2, 100));
                            break;
                    }
                    if (cycles > 1800 && game.noMoreEnemies()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 1:
                    switch (cycles) {
                        case 40:
                        case 140:
                        case 240:
                        case 340:
                        case 440:
                        case 540:
                        case 640:
                        case 740:
                        case 840:
                        case 940:
                            game.addEnemy(new DropEnemy(1, 30, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 720, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 50, 610, 8, true));
                            game.addEnemy(new DropEnemy(1, 700, 610, 8, true));
                            final int offset = (cycles / 4 / 5 - 4) / 5;
                            game.addEnemy(advancedMiniBomberEnemy(1, w1eX[offset + 0], w1eY[offset + 0], 8, 1, 100));
                            game.addEnemy(advancedMiniBomberEnemy(1, w1eX[offset + 1], w1eY[offset + 1], 8, -1, 100));
                            break;
                    }
                    if (cycles > 940 && game.noMoreEnemies()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 2:
                    switch (cycles) {
                        case 40:
                            game.addEnemy(mb1(10, 225, -10, 20));
                            game.addEnemy(mb1(10, 525, -10, 20));
                            game.addEnemy(altMb1(10, 375, -10, 20));
                            break;
                        case 80:
                        case 280:
                        case 380:
                        case 480:
                        case 580:
                        case 680:
                        case 780:
                        case 880:
                        case 980:
                            final int offset = (cycles / 4 / 5 - 4) / 5;
                            game.addEnemy(advancedMiniBomberEnemy(1, w1eX[offset + 0], w1eY[offset + 0], 8, 1, 100));
                            game.addEnemy(advancedMiniBomberEnemy(1, w1eX[offset + 1], w1eY[offset + 1], 8, -1, 100));
                            break;
                        case 1000:
                        case 1100:
                        case 1200:
                        case 1300:
                            game.addEnemy(altMb1(10, 375, -10, 20));
                            break;
                    }
                    if (cycles > 1300 && game.noMoreEnemies()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 3:
                   switch (cycles) {
                        case 40:
                            game.addEnemy(altMb1(10, 375, -10, 20));
                            break;
                        case 80:
                            game.addEnemy(circularPathEnemy(1, 750, 50, 8, 100, 1, 0.25f, 1, 100));
                            game.addEnemy(circularPathEnemy(1, 0, 50, 8, 100, -1, 0.25f, 3, 100));
                            game.addEnemy(altMb1(10, 225, -10, 20));
                            game.addEnemy(altMb1(10, 525, -10, 20));
                            break;
                        case 1080:
                            game.addEnemy(altMb1(10, 375, -10, 20));
                            game.addEnemy(altMb1(10, 225, -10, 20));
                            game.addEnemy(altMb1(10, 525, -10, 20));
                            game.addEnemy(circularPathEnemy(1, 750, 50, 8, 100, 1, 0.25f, 1, 100));
                            game.addEnemy(circularPathEnemy(1, 0, 50, 8, 100, -1, 0.25f, 3, 100));
                            game.addEnemy(circularPathEnemy(1, 750, 0, 8, 100, 1, 0.25f, 1, 100));
                            game.addEnemy(circularPathEnemy(1, 0, 0, 8, 100, -1, 0.25f, 3, 100));
                            game.addEnemy(circularPathEnemy(1, 750, 600, 8, 100, -1, 0.25f, 1, 100));
                            game.addEnemy(circularPathEnemy(1, 0, 600, 8, 100, 1, 0.25f, 3, 100));
                            break;
                        case 2080:
                            game.addEnemy(altMb1(10, 375, -10, 20));
                            game.addEnemy(altMb1(10, 225, -10, 20));
                            game.addEnemy(altMb1(10, 525, -10, 20));
                            game.addEnemy(circularPathEnemy(1, 750, 0, 8, 100, 1, 0.25f, 1, 100));
                            game.addEnemy(circularPathEnemy(1, 0, 0, 8, 100, -1, 0.25f, 3, 100));
                            game.addEnemy(circularPathEnemy(1, 750, 600, 8, 100, -1, 0.25f, 1, 100));
                            game.addEnemy(circularPathEnemy(1, 0, 600, 8, 100, 1, 0.25f, 3, 100));
                            break;
                    }
                    if (cycles > 2080 && game.noMoreEnemies()) {
                        wave++;
                        cycles = 0;
                    }
                    break;
                case 4:
                    switch (cycles) {
                        case 40:
                        case 240:
                        case 440:
                        case 640:
                        case 840:
                        case 1040:
                        case 1240:
                        case 1440:
                        case 1640:
                        case 1840:
                            game.addEnemy(new DropEnemy(1, 30, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 100, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 720, 610, 8, true));
                            game.addEnemy(new DropEnemy(1, 650, 610, 8, true));
                            game.addEnemy(singleShotEnemy(1, 10, 610, 8, true));
                            game.addEnemy(singleShotEnemy(1, 740, -10, 8, false));
                            game.addEnemy(singleShotEnemy(1, 450, 610, 8, true));
                            game.addEnemy(singleShotEnemy(1, 300, -10, 8, false));
                            break;
                    }
                    if (cycles > 1840 && game.noMoreEnemies()) {
                        wave++;
                        cycles = 0;
                        bgm.stop();
                        disableDamage();
                        shouldSkipPlayerUpdate(true);
                        game.clearBullets();
                        bgm = ResourceManager.get("/music/Level_One_Boss.wav");
                        if (ResourceManager.<GameConfig>get("./game.cfg").bgm) {
                            bgm.setMicrosecondPosition(0);
                            bgm.start();
                            bgm.setLoopPoints(BOSS_LOOP, -1);
                            bgm.loop(Clip.LOOP_CONTINUOUSLY);
                        }
                        displayNextPrebossDialogue();
                    }
                    break;
                case 5:
                    if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                        if (!displayNextPrebossDialogue()) {
                            wave++;
                            enableDamage();
                            shouldSkipPlayerUpdate(false);
                            cycles = 0;
                            game.addEnemy(new Level1Insane(300, 375, -10, 20));
                            drift.accelY = -20;
                            drift.accelX = 20;
                            drift.clampDx(0, 200);
                        }
                    }
                    break;
                case 6:
                    if (cycles % 4000 == 0) {
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
                    if (cycles > 40 && game.noMoreEnemies()) {
                        bgm.stop();
                        disableDamage();
                        shouldSkipPlayerUpdate(true);
                        game.clearBullets();
                        displayMessage(POSTBOSS_MSG);
                        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                            // Ask for name, and have PromptNameScene switch scene for us to $prompt.trans
                            SceneManager.resources().put("prompt.trans", "TitleScene");
                            return SceneManager.switchToScene("SaveHighscoreScene");
                        }
                    }
                    break;
                }
            break;
        }
        return true;
    }
}
