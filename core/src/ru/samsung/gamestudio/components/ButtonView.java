package ru.samsung.gamestudio.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonView extends View {

    private final Texture texture;
    private final BitmapFont font;

    private String text;

    private float textX;
    private float textY;

    private final GlyphLayout glyphLayout;

    public ButtonView(
            float x,
            float y,
            float width,
            float height,
            BitmapFont font,
            String texturePath,
            String text
    ) {
        super(x, y, width, height);

        this.font = font;
        this.text = text;

        texture = new Texture(texturePath);
        glyphLayout = new GlyphLayout();

        updateTextPosition();
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(
                texture,
                x,
                y,
                width,
                height
        );

        if (text != null && !text.isEmpty()) {
            font.draw(
                    batch,
                    text,
                    textX,
                    textY
            );
        }
    }

    public void setText(String text) {
        this.text = text;
        updateTextPosition();
    }

    private void updateTextPosition() {
        if (text == null) {
            text = "";
        }

        glyphLayout.setText(font, text);

        textX = x + (width - glyphLayout.width) / 2f;
        textY = y + (height + glyphLayout.height) / 2f;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        updateTextPosition();
    }

    @Override
    public void dispose() {
        texture.dispose();

    }
}