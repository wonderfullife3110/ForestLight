package ru.samsung.gamestudio.objects.forest;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.GameSettings;

public class GoldenFireflyObject {

    private final Texture texture;
    private final Rectangle bounds;

    private float x;
    private float y;

    private boolean active;

    public GoldenFireflyObject() {
        texture = new Texture(
                GameResources.GOLDEN_FIREFLY_IMG_PATH
        );

        bounds = new Rectangle();

        deactivate();
    }

    public void spawn() {
        x = GameSettings.SCREEN_WIDTH
                + GameSettings.BONUS_WIDTH;

        y = MathUtils.random(
                GameSettings.BONUS_MIN_Y,
                GameSettings.BONUS_MAX_Y
        );

        active = true;

        updateBounds();
    }

    public void update(float delta) {
        if (!active) {
            return;
        }

        x -= GameSettings.BONUS_SPEED * delta;

        updateBounds();

        if (x + GameSettings.BONUS_WIDTH < 0f) {
            deactivate();
        }
    }

    public void draw(SpriteBatch batch) {
        if (!active) {
            return;
        }

        batch.draw(
                texture,
                x,
                y,
                GameSettings.BONUS_WIDTH,
                GameSettings.BONUS_HEIGHT
        );
    }

    public boolean collidesWith(
            FireflyObject fireflyObject
    ) {
        return active
                && bounds.overlaps(
                fireflyObject.getBounds()
        );
    }

    public void deactivate() {
        active = false;

        x = -GameSettings.BONUS_WIDTH;
        y = 0f;

        updateBounds();
    }

    public boolean isActive() {
        return active;
    }

    private void updateBounds() {
        float horizontalPadding =
                GameSettings.BONUS_WIDTH * 0.2f;

        float verticalPadding =
                GameSettings.BONUS_HEIGHT * 0.2f;

        bounds.set(
                x + horizontalPadding,
                y + verticalPadding,
                GameSettings.BONUS_WIDTH
                        - horizontalPadding * 2f,
                GameSettings.BONUS_HEIGHT
                        - verticalPadding * 2f
        );
    }

    public void dispose() {
        texture.dispose();
    }
}