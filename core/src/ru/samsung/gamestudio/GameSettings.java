package ru.samsung.gamestudio;

public final class GameSettings {

    private GameSettings() {
    }

    // =========================
    // Экран
    // =========================

    public static final int SCREEN_WIDTH = 720;
    public static final int SCREEN_HEIGHT = 1280;

    // =========================
    // Основной светлячок
    // =========================

    public static final float FIREFLY_START_X = 170f;
    public static final float FIREFLY_START_Y = 640f;

    public static final float FIREFLY_WIDTH = 84f;
    public static final float FIREFLY_HEIGHT = 64f;

    public static final float FIREFLY_FRAME_DURATION = 0.15f;

    // =========================
    // Физика светлячка
    // =========================

    public static final float GRAVITY = -1050f;
    public static final float JUMP_VELOCITY = 470f;
    public static final float MAX_FALL_VELOCITY = -650f;

    // =========================
    // Ветки
    // =========================

    public static final float BRANCH_WIDTH = 135f;
    public static final float BRANCH_HEIGHT = 600f;

    public static final float START_BRANCH_GAP = 500f;
    public static final float MIN_BRANCH_GAP = 235f;

    public static final float START_BRANCH_SPEED = 205f;
    public static final float MAX_BRANCH_SPEED = 340f;

    /*
     * Количество одновременно существующих
     * пар веток.
     */
    public static final int BRANCH_PAIR_COUNT = 3;

    /*
     * Горизонтальное расстояние между парами.
     */
    public static final float BRANCH_DISTANCE = 430f;

    public static final float MIN_GAP_CENTER_Y = 300f;
    public static final float MAX_GAP_CENTER_Y = 980f;

    /*
     * Координата первой пары веток при старте.
     */
    public static final float FIRST_BRANCH_X =
            SCREEN_WIDTH + 150f;

    // =========================
    // Усложнение
    // =========================

    public static final int SCORE_FOR_LEVEL_UP = 5;

    public static final float SPEED_INCREASE_PER_LEVEL = 20f;
    public static final float GAP_DECREASE_PER_LEVEL = 20f;

    // =========================
    // Параллакс
    // =========================

    public static final float FAR_BACKGROUND_SPEED = 15f;
    public static final float NEAR_BACKGROUND_SPEED = 75f;

    // =========================
    // Золотой светлячок
    // =========================

    public static final float BONUS_WIDTH = 70f;
    public static final float BONUS_HEIGHT = 70f;

    public static final float BONUS_SPEED = 170f;

    public static final float BONUS_MIN_Y = 220f;
    public static final float BONUS_MAX_Y = 1060f;

    public static final float BONUS_MIN_DELAY = 8f;
    public static final float BONUS_MAX_DELAY = 15f;

    public static final int BONUS_SCORE = 5;
}