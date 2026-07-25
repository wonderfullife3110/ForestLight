package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.GameSettings;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.components.ParallaxBackgroundView;
import ru.samsung.gamestudio.objects.forest.BranchPair;
import ru.samsung.gamestudio.objects.forest.FireflyObject;
import ru.samsung.gamestudio.objects.forest.GoldenFireflyObject;

public class ForestGameScreen extends ScreenAdapter {

    private final MyGdxGame myGdxGame;

    private final ParallaxBackgroundView backgroundView;
    private final BitmapFont scoreFont;

    /*
     * Общие текстуры веток.
     * Загружаются один раз и используются
     * всеми тремя парами препятствий.
     */
    private final Texture topBranchTexture;
    private final Texture bottomBranchTexture;

    private FireflyObject fireflyObject;
    private GoldenFireflyObject goldenFireflyObject;

    private BranchPair[] branchPairs;

    private int score;

    private float bonusSpawnTimer;
    private float nextBonusSpawnTime;

    private boolean gameFinished;

    public ForestGameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        scoreFont = new BitmapFont();
        scoreFont.getData().setScale(2f);

        backgroundView = new ParallaxBackgroundView(
                myGdxGame.skyTexture,
                myGdxGame.farForestTexture,
                myGdxGame.nearForestTexture
        );

        topBranchTexture = new Texture(
                GameResources.BRANCH_TOP_IMG_PATH
        );

        bottomBranchTexture = new Texture(
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
    }

    @Override
    public void show() {
        createOrResetObjects();

        score = 0;
        gameFinished = false;

        bonusSpawnTimer = 0f;

        nextBonusSpawnTime = MathUtils.random(
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
            fireflyObject = new FireflyObject(
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
            branchPairs = new BranchPair[
                    GameSettings.BRANCH_PAIR_COUNT
                    ];

            for (int i = 0; i < branchPairs.length; i++) {
                float startX =
                        GameSettings.FIRST_BRANCH_X
                                + i * GameSettings.BRANCH_DISTANCE;

                branchPairs[i] = new BranchPair(
                        startX,
                        topBranchTexture,
                        bottomBranchTexture
                );
            }

            return;
        }

        for (int i = 0; i < branchPairs.length; i++) {
            float startX =
                    GameSettings.FIRST_BRANCH_X
                            + i * GameSettings.BRANCH_DISTANCE;

            branchPairs[i].reset(startX);
        }
    }

    @Override
    public void render(float delta) {
        /*
         * Ограничиваем слишком большой delta,
         * чтобы объекты не совершали резкий скачок.
         */
        delta = Math.min(
                delta,
                1f / 30f
        );

        if (!gameFinished) {
            handleInput();
            update(delta);
        }

        draw();
    }

    private void handleInput() {
        if (!Gdx.input.justTouched()) {
            return;
        }

        fireflyObject.jump();

        if (myGdxGame.forestAudioManager != null) {
            myGdxGame.forestAudioManager
                    .playWingSound();
        }
    }

    private void update(float delta) {
        fireflyObject.update(delta);

        backgroundView.update(delta);

        updateBranchPairs(delta);

        updateGoldenFirefly(delta);

        if (isGameOver()) {
            finishGame();
            return;
        }

        updateScore();

        resetOutsideBranchPairs();
    }

    private void updateBranchPairs(float delta) {
        float currentSpeed =
                getCurrentBranchSpeed();

        for (BranchPair branchPair : branchPairs) {
            branchPair.update(
                    delta,
                    currentSpeed
            );
        }
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
            myGdxGame.forestAudioManager
                    .playHitSound();
        }

        myGdxGame.setScreen(
                myGdxGame.restartScreen
        );
    }

    private void updateScore() {
        boolean pointReceived = false;

        for (BranchPair branchPair : branchPairs) {
            if (branchPair.canGivePoint(
                    fireflyObject
            )) {
                score++;
                pointReceived = true;
            }
        }

        if (!pointReceived) {
            return;
        }

        if (myGdxGame.forestAudioManager != null) {
            myGdxGame.forestAudioManager
                    .playPointSound();
        }

        setGapSizeForAllBranches(
                getCurrentGapSize()
        );

        System.out.println(
                "POINT! Score = " + score
                        + ", level = " + getCurrentLevel()
                        + ", speed = " + getCurrentBranchSpeed()
                        + ", gap = " + getCurrentGapSize()
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

            branchPair.reset(newX);

            branchPair.setGapSize(
                    getCurrentGapSize()
            );
        }
    }

    private float findRightmostBranchRightX() {
        float rightmostRightX = 0f;

        for (BranchPair branchPair : branchPairs) {
            rightmostRightX = Math.max(
                    rightmostRightX,
                    branchPair.getRightX()
            );
        }

        return rightmostRightX;
    }

    private void setGapSizeForAllBranches(
            float gapSize
    ) {
        for (BranchPair branchPair : branchPairs) {
            branchPair.setGapSize(gapSize);
        }
    }

    private void updateGoldenFirefly(float delta) {
        if (goldenFireflyObject.isActive()) {
            goldenFireflyObject.update(delta);

            if (goldenFireflyObject.collidesWith(
                    fireflyObject
            )) {
                collectGoldenFirefly();
            }

            return;
        }

        bonusSpawnTimer += delta;

        if (bonusSpawnTimer >= nextBonusSpawnTime) {
            goldenFireflyObject.spawn();

            bonusSpawnTimer = 0f;

            nextBonusSpawnTime = MathUtils.random(
                    GameSettings.BONUS_MIN_DELAY,
                    GameSettings.BONUS_MAX_DELAY
            );
        }
    }

    private void collectGoldenFirefly() {
        score += GameSettings.BONUS_SCORE;

        goldenFireflyObject.deactivate();

        bonusSpawnTimer = 0f;

        nextBonusSpawnTime = MathUtils.random(
                GameSettings.BONUS_MIN_DELAY,
                GameSettings.BONUS_MAX_DELAY
        );

        setGapSizeForAllBranches(
                getCurrentGapSize()
        );

        if (myGdxGame.forestAudioManager != null) {
            myGdxGame.forestAudioManager
                    .playBonusSound();
        }

        System.out.println(
                "BONUS! Score = " + score
        );
    }

    private int getCurrentLevel() {
        return score
                / GameSettings.SCORE_FOR_LEVEL_UP;
    }

    private float getCurrentBranchSpeed() {
        float speed =
                GameSettings.START_BRANCH_SPEED
                        + getCurrentLevel()
                        * GameSettings.SPEED_INCREASE_PER_LEVEL;

        return Math.min(
                speed,
                GameSettings.MAX_BRANCH_SPEED
        );
    }

    private float getCurrentGapSize() {
        float gap =
                GameSettings.START_BRANCH_GAP
                        - getCurrentLevel()
                        * GameSettings.GAP_DECREASE_PER_LEVEL;

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

        myGdxGame.batch.end();
    }

    public int getScore() {
        return score;
    }

    @Override
    public void dispose() {
        /*
         * backgroundView не удаляем:
         * фоновые текстуры общие и принадлежат MyGdxGame.
         */

        if (fireflyObject != null) {
            fireflyObject.dispose();
        }

        if (goldenFireflyObject != null) {
            goldenFireflyObject.dispose();
        }

        topBranchTexture.dispose();
        bottomBranchTexture.dispose();

        scoreFont.dispose();
    }
}