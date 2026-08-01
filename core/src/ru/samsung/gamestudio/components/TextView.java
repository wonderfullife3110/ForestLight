package ru.samsung.gamestudio.components;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextView extends View {

    protected final BitmapFont font;
    protected String text;

    private final GlyphLayout glyphLayout;

    public TextView(
            BitmapFont font,
            float x,
            float y,
            String text
    ) {
        super(x, y, 0f, 0f);

        this.font = font;
        this.text = text;

        glyphLayout = new GlyphLayout();

        updateSize();
    }

    @Override
    public void draw(SpriteBatch batch) {
        font.draw(
                batch,
                text,
                x,
                y
        );
    }

    public void setText(String text) {
        this.text = text;
        updateSize();
    }

    public String getText() {
        return text;
    }

    private void updateSize() {
        glyphLayout.setText(font, text);

        width = glyphLayout.width;
        height = glyphLayout.height;
    }

    @Override
    public void dispose() {

    }
}