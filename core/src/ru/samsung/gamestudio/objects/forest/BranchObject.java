package ru.samsung.gamestudio.objects.forest;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class BranchObject {

    private final Texture texture;

    private float x;
    private float y;

    private final float width;
    private final float height;

    private final Rectangle bounds;

    public BranchObject(
            Texture texture,
            float x,
            float y,
            float width,
            float height
    ) {
        this.texture = texture;

        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        bounds = new Rectangle();

        updateBounds();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
                texture,
                x,
                y,
                width,
                height
        );
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;

        updateBounds();
    }

    private void updateBounds() {
        bounds.set(
                x,
                y,
                width,
                height
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

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRightX() {
        return x + width;
    }

    /*
     * dispose() отсутствует специально.
     *
     * Текстуры общие для всех трёх пар веток,
     * поэтому освобождаются один раз
     * в ForestGameScreen.
     */
}