package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.GameSettings;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.components.ButtonView;
import ru.samsung.gamestudio.components.ParallaxBackgroundView;
import ru.samsung.gamestudio.components.TextView;
import ru.samsung.gamestudio.objects.forest.BranchPair;
import ru.samsung.gamestudio.objects.forest.FireflyObject;
import ru.samsung.gamestudio.objects.forest.GoldenFireflyObject;

public class ForestGameScreen extends ScreenAdapter {
    private final MyGdxGame myGdxGame;

    private final ParallaxBackgroundView backgroundView;
    private final BitmapFont scoreFont;
    private final Texture topBranchTexture;
    private final Texture bottomBranchTexture;
    private FireflyObject fireflyObject;
    private GoldenFireflyObject goldenFireflyObject;
    private BranchPair[] branchPairs;
    private int score;
    private float bonusSpawnTimer;
    private float nextBonusSpawnTime;
    private boolean gameFinished;
    private boolean paused;
    private final ButtonView pauseButtonView;
    private final ButtonView resumeButtonView;
    private final ButtonView menuButtonView;
    private final TextView pausedTextView;
    public ForestGameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        scoreFont = new BitmapFont();

        scoreFont.getData().setScale(2f);

        backgroundView =
                new ParallaxBackgroundView(
                        myGdxGame.skyTexture,
                        myGdxGame.farForestTexture,
                        myGdxGame.nearForestTexture
                );

        topBranchTexture =
                new Texture(
                        GameResources.BRANCH_TOP_IMG_PATH
                );

        bottomBranchTexture =
                new Texture(
                        GameResources.BRANCH_BOTTOM_IMG_PATH
                );

        topBranchTexture.setFilter(
                Texture.TextureFilter.Linear,
                Texture.TextureFilter.Linear
        );

        bottomBranchTexture.setFilter(
                Texture.TextureFilter.Linear,
                Texture.TextureFilter.Linear
        );

        pauseButtonView =
                new ButtonView(
                        540f,
                        1170f,
                        150f,
                        60f,
                        myGdxGame.commonBlackFont,
                        myGdxGame.buttonLongTexture,
                        "Pause"
                );

        pausedTextView =
                new TextView(
                        myGdxGame.largeWhiteFont,
                        270f,
                        850f,
                        "PAUSED"
                );

        resumeButtonView =
                new ButtonView(
                        140f,
                        620f,
                        440f,
                        70f,
                        myGdxGame.commonBlackFont,
                        myGdxGame.buttonLongTexture,
                        "Resume"
                );

        menuButtonView =
                new ButtonView(
                        140f,
                        500f,
                        440f,
                        70f,
                        myGdxGame.commonBlackFont,
                        myGdxGame.buttonLongTexture,
                        "Menu"
                );
    }

    @Override
    public void show() {
        createOrResetObjects();

        score = 0;

        gameFinished = false;
        paused = false;

        bonusSpawnTimer = 0f;

        nextBonusSpawnTime =
                MathUtils.random(
                        GameSettings.BONUS_MIN_DELAY,
                        GameSettings.BONUS_MAX_DELAY
                );

        setGapSizeForAllBranches(
                GameSettings.START_BRANCH_GAP
        );

        backgroundView.reset();
    }

    private void createOrResetObjects() {
        if (fireflyObject == null) {

            fireflyObject =
                    new FireflyObject(
                            GameResources.FIREFLY_1_IMG_PATH,
                            GameResources.FIREFLY_2_IMG_PATH,
                            GameResources.FIREFLY_3_IMG_PATH
                    );

        } else {

            fireflyObject.reset();
        }

        createOrResetBranchPairs();

        if (goldenFireflyObject == null) {

            goldenFireflyObject =
                    new GoldenFireflyObject();

        } else {

            goldenFireflyObject.deactivate();
        }
    }

    private void createOrResetBranchPairs() {
        if (branchPairs == null) {

            branchPairs =
                    new BranchPair[
                            GameSettings.BRANCH_PAIR_COUNT
                            ];

            for (int i = 0;
                 i < branchPairs.length;
                 i++) {

                float startX =
                        GameSettings.FIRST_BRANCH_X
                                + i
                                * GameSettings.BRANCH_DISTANCE;

                branchPairs[i] =
                        new BranchPair(
                                startX,
                                topBranchTexture,
                                bottomBranchTexture
                        );
            }

            return;
        }

        for (int i = 0;
             i < branchPairs.length;
             i++) {

            float startX =
                    GameSettings.FIRST_BRANCH_X
                            + i
                            * GameSettings.BRANCH_DISTANCE;

            branchPairs[i].reset(
                    startX
            );
        }
    }

    @Override
    public void render(float delta) {
        delta =
                Math.min(
                        delta,
                        1f / 30f
                );

        if (paused) {
            handlePauseInput();
            draw();
            return;
        }

        if (!gameFinished) {

            boolean pausePressed =
                    handleGameInput();

            if (pausePressed) {
                draw();
                return;
            }

            update(
                    delta
            );
        }

        draw();
    }

    private boolean handleGameInput() {
        if (!Gdx.input.justTouched()) {
            return false;
        }

        Vector3 touch =
                new Vector3(
                        Gdx.input.getX(),
                        Gdx.input.getY(),
                        0f
                );

        myGdxGame.viewport.unproject(
                touch
        );

        if (pauseButtonView.isHit(
                touch.x,
                touch.y
        )) {
            paused = true;
            return true;
        }

        fireflyObject.jump();

        if (myGdxGame.forestAudioManager != null) {
            myGdxGame
                    .forestAudioManager
                    .playWingSound();
        }

        return false;
    }

    private void handlePauseInput() {
        if (!Gdx.input.justTouched()) {
            return;
        }

        Vector3 touch =
                new Vector3(
                        Gdx.input.getX(),
                        Gdx.input.getY(),
                        0f
                );

        myGdxGame.viewport.unproject(
                touch
        );

        if (resumeButtonView.isHit(
                touch.x,
                touch.y
        )) {
            paused = false;
            return;
        }

        if (menuButtonView.isHit(
                touch.x,
                touch.y
        )) {
            paused = false;

            myGdxGame.setScreen(
                    myGdxGame.forestMenuScreen
            );
        }
    }

    private void update(float delta) {
        fireflyObject.update(
                delta
        );

        backgroundView.update(
                delta
        );

        float currentSpeed =
                getCurrentBranchSpeed();

        for (BranchPair branchPair : branchPairs) {
            branchPair.update(
                    delta,
                    currentSpeed
            );
        }

        updateGoldenFirefly(
                delta
        );

        if (isGameOver()) {
            finishGame();
            return;
        }

        updateScore();

        resetOutsideBranchPairs();
    }

    private boolean isGameOver() {
        if (fireflyObject.isOutsideScreen()) {
            return true;
        }

        for (BranchPair branchPair : branchPairs) {

            if (branchPair.collidesWith(
                    fireflyObject
            )) {
                return true;
            }
        }

        return false;
    }

    private void finishGame() {
        if (gameFinished) {
            return;
        }

        gameFinished = true;

        if (myGdxGame.forestAudioManager != null) {
            myGdxGame
                    .forestAudioManager
                    .playHitSound();
        }

        myGdxGame.setScreen(
                myGdxGame.restartScreen
        );
    }

    private void updateScore() {
        boolean pointReceived =
                false;

        for (BranchPair branchPair : branchPairs) {

            if (branchPair.canGivePoint(
                    fireflyObject
            )) {

                score++;

                pointReceived =
                        true;
            }
        }

        if (!pointReceived) {
            return;
        }

        if (myGdxGame.forestAudioManager != null) {
            myGdxGame
                    .forestAudioManager
                    .playPointSound();
        }

        setGapSizeForAllBranches(
                getCurrentGapSize()
        );
    }

    private void resetOutsideBranchPairs() {
        for (BranchPair branchPair : branchPairs) {

            if (!branchPair.isOutsideScreen()) {
                continue;
            }

            float newX =
                    findRightmostBranchRightX()
                            + GameSettings.BRANCH_DISTANCE;

            branchPair.reset(
                    newX
            );

            branchPair.setGapSize(
                    getCurrentGapSize()
            );
        }
    }

    private float findRightmostBranchRightX() {
        float rightmost =
                0f;

        for (BranchPair branchPair : branchPairs) {

            rightmost =
                    Math.max(
                            rightmost,
                            branchPair.getRightX()
                    );
        }

        return rightmost;
    }

    private void setGapSizeForAllBranches(
            float gapSize
    ) {
        for (BranchPair branchPair : branchPairs) {

            branchPair.setGapSize(
                    gapSize
            );
        }
    }

    private void updateGoldenFirefly(
            float delta
    ) {
        if (goldenFireflyObject.isActive()) {

            goldenFireflyObject.update(
                    delta
            );

            if (goldenFireflyObject.collidesWith(
                    fireflyObject
            )) {

                collectGoldenFirefly();
            }

            return;
        }

        bonusSpawnTimer +=
                delta;

        if (bonusSpawnTimer
                >= nextBonusSpawnTime) {

            goldenFireflyObject.spawn();

            bonusSpawnTimer =
                    0f;

            nextBonusSpawnTime =
                    MathUtils.random(
                            GameSettings.BONUS_MIN_DELAY,
                            GameSettings.BONUS_MAX_DELAY
                    );
        }
    }

    private void collectGoldenFirefly() {
        score +=
                GameSettings.BONUS_SCORE;

        goldenFireflyObject.deactivate();

        bonusSpawnTimer =
                0f;

        nextBonusSpawnTime =
                MathUtils.random(
                        GameSettings.BONUS_MIN_DELAY,
                        GameSettings.BONUS_MAX_DELAY
                );

        setGapSizeForAllBranches(
                getCurrentGapSize()
        );

        if (myGdxGame.forestAudioManager != null) {
            myGdxGame
                    .forestAudioManager
                    .playBonusSound();
        }
    }

    private int getCurrentLevel() {
        return score
                / GameSettings.SCORE_FOR_LEVEL_UP;
    }

    private float getCurrentBranchSpeed() {
        float speed =
                GameSettings.START_BRANCH_SPEED
                        + getCurrentLevel()
                        * GameSettings
                        .SPEED_INCREASE_PER_LEVEL;

        return Math.min(
                speed,
                GameSettings.MAX_BRANCH_SPEED
        );
    }

    private float getCurrentGapSize() {
        float gap =
                GameSettings.START_BRANCH_GAP
                        - getCurrentLevel()
                        * GameSettings
                        .GAP_DECREASE_PER_LEVEL;

        return Math.max(
                gap,
                GameSettings.MIN_BRANCH_GAP
        );
    }

    private void draw() {
        ScreenUtils.clear(
                0.03f,
                0.07f,
                0.14f,
                1f
        );

        myGdxGame.camera.update();

        myGdxGame.batch.setProjectionMatrix(
                myGdxGame.camera.combined
        );

        myGdxGame.batch.begin();

        backgroundView.draw(
                myGdxGame.batch
        );

        for (BranchPair branchPair : branchPairs) {

            branchPair.draw(
                    myGdxGame.batch
            );
        }

        goldenFireflyObject.draw(
                myGdxGame.batch
        );

        fireflyObject.draw(
                myGdxGame.batch
        );

        scoreFont.draw(
                myGdxGame.batch,
                "Score: " + score,
                20f,
                GameSettings.SCREEN_HEIGHT - 20f
        );

        if (!paused) {
            pauseButtonView.draw(
                    myGdxGame.batch
            );
        }

        if (paused) {
            pausedTextView.draw(
                    myGdxGame.batch
            );

            resumeButtonView.draw(
                    myGdxGame.batch
            );

            menuButtonView.draw(
                    myGdxGame.batch
            );
        }

        myGdxGame.batch.end();
    }

    public int getScore() {
        return score;
    }

    @Override
    public void pause() {
        if (!gameFinished) {
            paused = true;
        }
    }

    @Override
    public void resume() {
        //Игра остаётся на паузе,пока не нажмете Resume/
    }

    @Override
    public void dispose() {
        if (fireflyObject != null) {
            fireflyObject.dispose();
        }

        if (goldenFireflyObject != null) {
            goldenFireflyObject.dispose();
        }

        topBranchTexture.dispose();
        bottomBranchTexture.dispose();

        pauseButtonView.dispose();
        resumeButtonView.dispose();
        menuButtonView.dispose();

        pausedTextView.dispose();

        scoreFont.dispose();
    }
}