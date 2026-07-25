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

public class ForestMenuScreen extends ScreenAdapter {

    private final MyGdxGame myGdxGame;

    private final ParallaxBackgroundView backgroundView;

    private final TextView titleTextView;
    private final TextView subtitleTextView;
    private final TextView bestScoreTextView;

    private final ButtonView playButtonView;
    private final ButtonView settingsButtonView;
    private final ButtonView exitButtonView;

    public ForestMenuScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        backgroundView = new ParallaxBackgroundView(
                myGdxGame.skyTexture,
                myGdxGame.farForestTexture,
                myGdxGame.nearForestTexture
        );

        titleTextView = new TextView(
                myGdxGame.largeWhiteFont,
                175f,
                1040f,
                "Forest Light"
        );

        subtitleTextView = new TextView(
                myGdxGame.commonWhiteFont,
                180f,
                960f,
                "Fly through the magic forest"
        );

        bestScoreTextView = new TextView(
                myGdxGame.commonWhiteFont,
                275f,
                820f,
                "Best: 0"
        );

        playButtonView = new ButtonView(
                140f,
                610f,
                440f,
                70f,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_LONG_BG_IMG_PATH,
                "Play"
        );

        settingsButtonView = new ButtonView(
                140f,
                500f,
                440f,
                70f,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_LONG_BG_IMG_PATH,
                "Settings"
        );

        exitButtonView = new ButtonView(
                140f,
                390f,
                440f,
                70f,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_LONG_BG_IMG_PATH,
                "Exit"
        );
    }

    @Override
    public void show() {
        backgroundView.reset();

        bestScoreTextView.setText(
                "Best: " + ForestMemoryManager.loadBestScore()
        );

        if (myGdxGame.forestAudioManager != null) {
            myGdxGame.forestAudioManager.playMusic();
        }
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
         * update() здесь не вызываем:
         * фон главного меню должен быть статичным.
         */
        backgroundView.draw(myGdxGame.batch);

        titleTextView.draw(myGdxGame.batch);
        subtitleTextView.draw(myGdxGame.batch);
        bestScoreTextView.draw(myGdxGame.batch);

        playButtonView.draw(myGdxGame.batch);
        settingsButtonView.draw(myGdxGame.batch);
        exitButtonView.draw(myGdxGame.batch);

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

        if (playButtonView.isHit(touch.x, touch.y)) {
            myGdxGame.setScreen(
                    myGdxGame.forestGameScreen
            );
            return;
        }

        if (settingsButtonView.isHit(touch.x, touch.y)) {
            myGdxGame.setScreen(
                    myGdxGame.forestSettingsScreen
            );
            return;
        }

        if (exitButtonView.isHit(touch.x, touch.y)) {
            Gdx.app.exit();
        }
    }

    @Override
    public void dispose() {
        /*
         * backgroundView.dispose() не вызываем:
         * фоновые текстуры общие и удаляются в MyGdxGame.
         */

        playButtonView.dispose();
        settingsButtonView.dispose();
        exitButtonView.dispose();

        titleTextView.dispose();
        subtitleTextView.dispose();
        bestScoreTextView.dispose();
    }
}