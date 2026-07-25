package ru.samsung.gamestudio.objects.forest;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import ru.samsung.gamestudio.GameSettings;

public class BranchPair {

    private final BranchObject topBranch;
    private final BranchObject bottomBranch;

    private float x;
    private float gapCenterY;
    private float gapSize;

    private boolean pointReceived;

    public BranchPair(
            float startX,
            Texture topTexture,
            Texture bottomTexture
    ) {
        x = startX;
        gapSize = GameSettings.START_BRANCH_GAP;

        gapCenterY = MathUtils.random(
                GameSettings.MIN_GAP_CENTER_Y,
                GameSettings.MAX_GAP_CENTER_Y
        );

        bottomBranch = new BranchObject(
                bottomTexture,
                x,
                0f,
                GameSettings.BRANCH_WIDTH,
                GameSettings.BRANCH_HEIGHT
        );

        topBranch = new BranchObject(
                topTexture,
                x,
                0f,
                GameSettings.BRANCH_WIDTH,
                GameSettings.BRANCH_HEIGHT
        );

        pointReceived = false;

        updateBranchPositions();
    }

    public void update(float delta, float speed) {
        x -= speed * delta;
        updateBranchPositions();
    }

    private void updateBranchPositions() {
        float bottomBranchY =
                gapCenterY
                        - gapSize / 2f
                        - GameSettings.BRANCH_HEIGHT;

        float topBranchY =
                gapCenterY
                        + gapSize / 2f;

        bottomBranch.setPosition(
                x,
                bottomBranchY
        );

        topBranch.setPosition(
                x,
                topBranchY
        );
    }

    public void draw(SpriteBatch batch) {
        bottomBranch.draw(batch);
        topBranch.draw(batch);
    }

    public boolean isOutsideScreen() {
        return getRightX() < 0f;
    }

    public void reset(float newX) {
        x = newX;

        gapCenterY = MathUtils.random(
                GameSettings.MIN_GAP_CENTER_Y,
                GameSettings.MAX_GAP_CENTER_Y
        );

        pointReceived = false;

        updateBranchPositions();
    }

    public boolean canGivePoint(FireflyObject fireflyObject) {
        boolean completelyPassed =
                getRightX() < fireflyObject.getX();

        if (!pointReceived && completelyPassed) {
            pointReceived = true;
            return true;
        }

        return false;
    }

    public boolean collidesWith(FireflyObject fireflyObject) {
        Rectangle fireflyBounds =
                fireflyObject.getBounds();

        float fireflyLeft = fireflyBounds.x;
        float fireflyRight =
                fireflyBounds.x + fireflyBounds.width;

        float fireflyBottom = fireflyBounds.y;
        float fireflyTop =
                fireflyBounds.y + fireflyBounds.height;

        float branchLeft = x;
        float branchRight =
                x + GameSettings.BRANCH_WIDTH;

        boolean overlapsHorizontally =
                fireflyRight > branchLeft
                        && fireflyLeft < branchRight;

        if (!overlapsHorizontally) {
            return false;
        }

        float gapBottom =
                gapCenterY - gapSize / 2f;

        float gapTop =
                gapCenterY + gapSize / 2f;

        return fireflyBottom < gapBottom
                || fireflyTop > gapTop;
    }

    public float getRightX() {
        return x + GameSettings.BRANCH_WIDTH;
    }

    public float getGapSize() {
        return gapSize;
    }

    public void setGapSize(float newGapSize) {
        gapSize = newGapSize;
        updateBranchPositions();
    }
}