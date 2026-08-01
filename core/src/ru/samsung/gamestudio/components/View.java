package ru.samsung.gamestudio.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class View {
    protected float x;
    protected float y;
    protected float width;
    protected float height;

    protected View(
            float x,
            float y,
            float width,
            float height
    ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void draw(SpriteBatch batch);

    public boolean isHit(float touchX, float touchY) {
        return touchX >= x
                && touchX <= x + width
                && touchY >= y
                && touchY <= y + height;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
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

    public abstract void dispose();
}