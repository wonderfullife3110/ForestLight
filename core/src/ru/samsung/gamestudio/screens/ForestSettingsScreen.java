package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.components.ButtonView;
import ru.samsung.gamestudio.components.ParallaxBackgroundView;
import ru.samsung.gamestudio.components.TextView;
import ru.samsung.gamestudio.managers.ForestMemoryManager;

public class ForestSettingsScreen extends ScreenAdapter {

    private final MyGdxGame myGdxGame;

    private final ParallaxBackgroundView backgroundView;

    private final TextView titleTextView;

    private final ButtonView musicButtonView;
    private final ButtonView soundButtonView;
    private final ButtonView clearScoreButtonView;
    private final ButtonView backButtonView;

    public ForestSettingsScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        backgroundView = new ParallaxBackgroundView(
                myGdxGame.skyTexture,
                myGdxGame.farForestTexture,
                myGdxGame.nearForestTexture
        );

        titleTextView = new TextView(
                myGdxGame.largeWhiteFont,
                230f,
                1020f,
                "Settings"
        );

        musicButtonView = new ButtonView(
                140f,
                740f,
                440f,
                70f,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_LONG_BG_IMG_PATH,
                ""
        );

        soundButtonView = new ButtonView(
                140f,
                630f,
                440f,
                70f,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_LONG_BG_IMG_PATH,
                ""
        );

        clearScoreButtonView = new ButtonView(
                140f,
                520f,
                440f,
                70f,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_LONG_BG_IMG_PATH,
                "Clear best score"
        );

        backButtonView = new ButtonView(
                140f,
                370f,
                440f,
                70f,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_LONG_BG_IMG_PATH,
                "Back"
        );
    }

    @Override
    public void show() {
        backgroundView.reset();
        updateButtonTexts();
    }

    @Override
    public void render(float delta) {
        handleInput();

        myGdxGame.camera.update();

        myGdxGame.batch.setProjectionMatrix(
                myGdxGame.camera.combined
        );

        ScreenUtils.clear(Color.BLACK);

        myGdxGame.batch.begin();

        /*
         * update() не вызываем:
         * фон экрана настроек остаётся статичным.
         */
        backgroundView.draw(
                myGdxGame.batch
        );

        titleTextView.draw(
                myGdxGame.batch
        );

        musicButtonView.draw(
                myGdxGame.batch
        );

        soundButtonView.draw(
                myGdxGame.batch
        );

        clearScoreButtonView.draw(
                myGdxGame.batch
        );

        backButtonView.draw(
                myGdxGame.batch
        );

        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (!Gdx.input.justTouched()) {
            return;
        }

        Vector3 touch = new Vector3(
                Gdx.input.getX(),
                Gdx.input.getY(),
                0f
        );

        myGdxGame.viewport.unproject(touch);

        if (musicButtonView.isHit(
                touch.x,
                touch.y
        )) {
            toggleMusic();
            return;
        }

        if (soundButtonView.isHit(
                touch.x,
                touch.y
        )) {
            toggleSound();
            return;
        }

        if (clearScoreButtonView.isHit(
                touch.x,
                touch.y
        )) {
            clearBestScore();
            return;
        }

        if (backButtonView.isHit(
                touch.x,
                touch.y
        )) {
            myGdxGame.setScreen(
                    myGdxGame.forestMenuScreen
            );
        }
    }

    private void toggleMusic() {
        boolean newMusicValue =
                !ForestMemoryManager.loadIsMusicOn();

        ForestMemoryManager.saveMusicSettings(
                newMusicValue
        );

        if (myGdxGame.forestAudioManager != null) {
            myGdxGame.forestAudioManager
                    .updateMusicFlag();
        }

        updateButtonTexts();
    }

    private void toggleSound() {
        boolean newSoundValue =
                !ForestMemoryManager.loadIsSoundOn();

        ForestMemoryManager.saveSoundSettings(
                newSoundValue
        );

        if (myGdxGame.forestAudioManager != null) {
            myGdxGame.forestAudioManager
                    .updateSoundFlag();
        }

        updateButtonTexts();
    }

    private void clearBestScore() {
        ForestMemoryManager.clearBestScore();

        clearScoreButtonView.setText(
                "Best score cleared"
        );
    }

    private void updateButtonTexts() {
        boolean musicOn =
                ForestMemoryManager.loadIsMusicOn();

        boolean soundOn =
                ForestMemoryManager.loadIsSoundOn();

        musicButtonView.setText(
                "Music: " + stateToText(musicOn)
        );

        soundButtonView.setText(
                "Sound: " + stateToText(soundOn)
        );

        clearScoreButtonView.setText(
                "Clear best score"
        );
    }

    private String stateToText(boolean enabled) {
        return enabled ? "ON" : "OFF";
    }

    @Override
    public void dispose() {
        /*
         * backgroundView не удаляем:
         * фоновые текстуры общие и принадлежат MyGdxGame.
         */

        musicButtonView.dispose();
        soundButtonView.dispose();
        clearScoreButtonView.dispose();
        backButtonView.dispose();

        titleTextView.dispose();
    }
}