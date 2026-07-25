package ru.samsung.gamestudio.objects.forest;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import ru.samsung.gamestudio.GameSettings;

public class FireflyObject {

    private final Texture texture1;
    private final Texture texture2;
    private final Texture texture3;

    private final Animation<TextureRegion> animation;
    private final Rectangle bounds;

    private float animationTime;

    private float x;
    private float y;

    private float velocityY;
    private float rotation;

    private final float width;
    private final float height;

    public FireflyObject(
            String texturePath1,
            String texturePath2,
            String texturePath3
    ) {
        texture1 = new Texture(texturePath1);
        texture2 = new Texture(texturePath2);
        texture3 = new Texture(texturePath3);

        Array<TextureRegion> frames = new Array<>();

        frames.add(new TextureRegion(texture1));
        frames.add(new TextureRegion(texture2));
        frames.add(new TextureRegion(texture3));

        animation = new Animation<>(
                GameSettings.FIREFLY_FRAME_DURATION,
                frames,
                Animation.PlayMode.LOOP_PINGPONG
        );

        width = GameSettings.FIREFLY_WIDTH;
        height = GameSettings.FIREFLY_HEIGHT;

        bounds = new Rectangle();

        reset();
    }

    public void update(float delta) {
        animationTime += delta;

        velocityY += GameSettings.GRAVITY * delta;

        if (velocityY < GameSettings.MAX_FALL_VELOCITY) {
            velocityY = GameSettings.MAX_FALL_VELOCITY;
        }

        y += velocityY * delta;

        if (velocityY > 0f) {
            rotation = 20f;
        } else {
            rotation = Math.max(
                    -35f,
                    rotation - 90f * delta
            );
        }

        updateBounds();
    }

    public void jump() {
        velocityY = GameSettings.JUMP_VELOCITY;
    }

    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame =
                animation.getKeyFrame(animationTime, true);

        batch.draw(
                currentFrame,
                x,
                y,
                width / 2f,
                height / 2f,
                width,
                height,
                1f,
                1f,
                rotation
        );
    }

    public boolean isOutsideScreen() {
        return y + height < 0f
                || y > GameSettings.SCREEN_HEIGHT;
    }

    private void updateBounds() {
        float horizontalPadding = width * 0.25f;
        float verticalPadding = height * 0.25f;

        bounds.set(
                x + horizontalPadding,
                y + verticalPadding,
                width - horizontalPadding * 2f,
                height - verticalPadding * 2f
        );
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRightX() {
        return x + width;
    }

    public void reset() {
        x = GameSettings.FIREFLY_START_X;
        y = GameSettings.FIREFLY_START_Y;

        velocityY = 0f;
        rotation = 0f;
        animationTime = 0f;

        updateBounds();
    }

    public void dispose() {
        texture1.dispose();
        texture2.dispose();
        texture3.dispose();
    }
}