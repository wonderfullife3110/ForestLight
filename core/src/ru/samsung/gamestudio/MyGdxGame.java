package ru.samsung.gamestudio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;

import ru.samsung.gamestudio.managers.ForestAudioManager;
import ru.samsung.gamestudio.screens.ForestGameScreen;
import ru.samsung.gamestudio.screens.ForestMenuScreen;
import ru.samsung.gamestudio.screens.ForestSettingsScreen;
import ru.samsung.gamestudio.screens.RestartScreen;

public class MyGdxGame extends Game {

    public FitViewport viewport;
    public OrthographicCamera camera;

    public SpriteBatch batch;
    public Vector3 touch;

    public BitmapFont largeWhiteFont;
    public BitmapFont commonWhiteFont;
    public BitmapFont commonBlackFont;

    // Общие текстуры фона
    public Texture skyTexture;
    public Texture farForestTexture;
    public Texture nearForestTexture;

    // Общая текстура всех кнопок
    public Texture buttonLongTexture;

    public ForestAudioManager forestAudioManager;

    public ForestGameScreen forestGameScreen;
    public RestartScreen restartScreen;
    public ForestMenuScreen forestMenuScreen;
    public ForestSettingsScreen forestSettingsScreen;

    @Override
    public void create() {
        createCameraAndViewport();

        createFonts();

        createSharedTextures();

        forestAudioManager =
                new ForestAudioManager();

        forestGameScreen =
                new ForestGameScreen(this);

        restartScreen =
                new RestartScreen(this);

        forestMenuScreen =
                new ForestMenuScreen(this);

        forestSettingsScreen =
                new ForestSettingsScreen(this);

        setScreen(
                forestMenuScreen
        );
    }

    private void createCameraAndViewport() {
        batch = new SpriteBatch();

        touch = new Vector3();

        camera =
                new OrthographicCamera();

        viewport =
                new FitViewport(
                        GameSettings.SCREEN_WIDTH,
                        GameSettings.SCREEN_HEIGHT,
                        camera
                );

        viewport.apply();

        camera.position.set(
                GameSettings.SCREEN_WIDTH / 2f,
                GameSettings.SCREEN_HEIGHT / 2f,
                0f
        );

        camera.update();
    }

    private void createFonts() {
        largeWhiteFont =
                FontBuilder.generate(
                        48,
                        Color.WHITE,
                        GameResources.FONT_PATH
                );

        commonWhiteFont =
                FontBuilder.generate(
                        24,
                        Color.WHITE,
                        GameResources.FONT_PATH
                );

        commonBlackFont =
                FontBuilder.generate(
                        24,
                        Color.BLACK,
                        GameResources.FONT_PATH
                );
    }

    private void createSharedTextures() {
        skyTexture = new Texture(
                GameResources.BACKGROUND_SKY_IMG_PATH
        );

        farForestTexture = new Texture(
                GameResources.BACKGROUND_FOREST_FAR_IMG_PATH
        );

        nearForestTexture = new Texture(
                GameResources.BACKGROUND_FOREST_NEAR_IMG_PATH
        );

        buttonLongTexture = new Texture(
                GameResources.BUTTON_LONG_BG_IMG_PATH
        );

        skyTexture.setFilter(
                Texture.TextureFilter.Linear,
                Texture.TextureFilter.Linear
        );

        farForestTexture.setFilter(
                Texture.TextureFilter.Linear,
                Texture.TextureFilter.Linear
        );

        nearForestTexture.setFilter(
                Texture.TextureFilter.Linear,
                Texture.TextureFilter.Linear
        );

        buttonLongTexture.setFilter(
                Texture.TextureFilter.Linear,
                Texture.TextureFilter.Linear
        );
    }

    @Override
    public void resize(
            int width,
            int height
    ) {
        viewport.update(
                width,
                height,
                true
        );

        if (getScreen() != null) {
            getScreen().resize(
                    width,
                    height
            );
        }
    }

    @Override
    public void dispose() {
        if (forestGameScreen != null) {
            forestGameScreen.dispose();
        }

        if (restartScreen != null) {
            restartScreen.dispose();
        }

        if (forestMenuScreen != null) {
            forestMenuScreen.dispose();
        }

        if (forestSettingsScreen != null) {
            forestSettingsScreen.dispose();
        }

        if (forestAudioManager != null) {
            forestAudioManager.dispose();
        }

        if (skyTexture != null) {
            skyTexture.dispose();
        }

        if (farForestTexture != null) {
            farForestTexture.dispose();
        }

        if (nearForestTexture != null) {
            nearForestTexture.dispose();
        }

        if (buttonLongTexture != null) {
            buttonLongTexture.dispose();
        }

        if (largeWhiteFont != null) {
            largeWhiteFont.dispose();
        }

        if (commonWhiteFont != null) {
            commonWhiteFont.dispose();
        }

        if (commonBlackFont != null) {
            commonBlackFont.dispose();
        }

        if (batch != null) {
            batch.dispose();
        }

        super.dispose();
    }
}