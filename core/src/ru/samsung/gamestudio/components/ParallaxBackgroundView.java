package ru.samsung.gamestudio.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.samsung.gamestudio.GameSettings;

public class ParallaxBackgroundView {

    private final Texture skyTexture;
    private final Texture farTexture;
    private final Texture nearTexture;

    private float farX1;
    private float farX2;

    private float nearX1;
    private float nearX2;

    public ParallaxBackgroundView(
            Texture skyTexture,
            Texture farTexture,
            Texture nearTexture
    ) {
        this.skyTexture = skyTexture;
        this.farTexture = farTexture;
        this.nearTexture = nearTexture;

        reset();
    }

    public void update(float delta) {
        farX1 -= GameSettings.FAR_BACKGROUND_SPEED * delta;
        farX2 -= GameSettings.FAR_BACKGROUND_SPEED * delta;

        nearX1 -= GameSettings.NEAR_BACKGROUND_SPEED * delta;
        nearX2 -= GameSettings.NEAR_BACKGROUND_SPEED * delta;

        if (farX1 <= -GameSettings.SCREEN_WIDTH) {
            farX1 = farX2 + GameSettings.SCREEN_WIDTH;
        }

        if (farX2 <= -GameSettings.SCREEN_WIDTH) {
            farX2 = farX1 + GameSettings.SCREEN_WIDTH;
        }

        if (nearX1 <= -GameSettings.SCREEN_WIDTH) {
            nearX1 = nearX2 + GameSettings.SCREEN_WIDTH;
        }

        if (nearX2 <= -GameSettings.SCREEN_WIDTH) {
            nearX2 = nearX1 + GameSettings.SCREEN_WIDTH;
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
                skyTexture,
                0f,
                0f,
                GameSettings.SCREEN_WIDTH,
                GameSettings.SCREEN_HEIGHT
        );

        batch.draw(
                farTexture,
                farX1,
                0f,
                GameSettings.SCREEN_WIDTH,
                GameSettings.SCREEN_HEIGHT
        );

        batch.draw(
                farTexture,
                farX2,
                0f,
                GameSettings.SCREEN_WIDTH,
                GameSettings.SCREEN_HEIGHT
        );

        batch.draw(
                nearTexture,
                nearX1,
                0f,
                GameSettings.SCREEN_WIDTH,
                GameSettings.SCREEN_HEIGHT
        );

        batch.draw(
                nearTexture,
                nearX2,
                0f,
                GameSettings.SCREEN_WIDTH,
                GameSettings.SCREEN_HEIGHT
        );
    }

    public void reset() {
        farX1 = 0f;
        farX2 = GameSettings.SCREEN_WIDTH;

        nearX1 = 0f;
        nearX2 = GameSettings.SCREEN_WIDTH;
    }
}