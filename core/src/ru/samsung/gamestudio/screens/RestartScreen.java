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

public class RestartScreen extends ScreenAdapter {

    private final MyGdxGame myGdxGame;

    private final ParallaxBackgroundView backgroundView;

    private final TextView gameOverTextView;
    private final TextView scoreTextView;
    private final TextView bestScoreTextView;

    private final ButtonView restartButtonView;
    private final ButtonView menuButtonView;

    private int score;

    public RestartScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        backgroundView = new ParallaxBackgroundView(
                myGdxGame.skyTexture,
                myGdxGame.farForestTexture,
                myGdxGame.nearForestTexture
        );

        gameOverTextView = new TextView(
                myGdxGame.largeWhiteFont,
                215f,
                1020f,
                "Game Over"
        );

        scoreTextView = new TextView(
                myGdxGame.commonWhiteFont,
                275f,
                850f,
                "Score: 0"
        );

        bestScoreTextView = new TextView(
                myGdxGame.commonWhiteFont,
                285f,
                780f,
                "Best: 0"
        );

        restartButtonView = new ButtonView(
                140f,
                570f,
                440f,
                70f,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_LONG_BG_IMG_PATH,
                "Restart"
        );

        menuButtonView = new ButtonView(
                140f,
                450f,
                440f,
                70f,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_LONG_BG_IMG_PATH,
                "Menu"
        );
    }

    @Override
    public void show() {
        backgroundView.reset();

        score = myGdxGame.forestGameScreen.getScore();

        ForestMemoryManager.saveBestScore(score);

        scoreTextView.setText(
                "Score: " + score
        );

        bestScoreTextView.setText(
                "Best: " + ForestMemoryManager.loadBestScore()
        );
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
         * Фон на экране проигрыша остаётся статичным,
         * поэтому update() не вызываем.
         */
        backgroundView.draw(
                myGdxGame.batch
        );

        gameOverTextView.draw(
                myGdxGame.batch
        );

        scoreTextView.draw(
                myGdxGame.batch
        );

        bestScoreTextView.draw(
                myGdxGame.batch
        );

        restartButtonView.draw(
                myGdxGame.batch
        );

        menuButtonView.draw(
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

        if (restartButtonView.isHit(
                touch.x,
                touch.y
        )) {
            myGdxGame.setScreen(
                    myGdxGame.forestGameScreen
            );

            return;
        }

        if (menuButtonView.isHit(
                touch.x,
                touch.y
        )) {
            myGdxGame.setScreen(
                    myGdxGame.forestMenuScreen
            );
        }
    }

    @Override
    public void dispose() {
        /*
         * backgroundView не удаляем:
         * фоновые текстуры общие и принадлежат MyGdxGame.
         */

        restartButtonView.dispose();
        menuButtonView.dispose();

        gameOverTextView.dispose();
        scoreTextView.dispose();
        bestScoreTextView.dispose();
    }
}