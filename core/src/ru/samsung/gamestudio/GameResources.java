package ru.samsung.gamestudio;

/**
 * Пути к ресурсам Forest Light.
 *
 * Все пути считаются относительно android/assets.
 */
public final class GameResources {

    private GameResources() {
        // Запрещаем создание объектов класса.
    }

    // =========================
    // Шрифт
    // =========================

    public static final String FONT_PATH =
            "fonts/main_font.ttf";

    // =========================
    // Фоны
    // =========================

    public static final String BACKGROUND_SKY_IMG_PATH =
            "textures/background_sky.png";

    public static final String BACKGROUND_FOREST_FAR_IMG_PATH =
            "textures/background_forest_far.png";

    public static final String BACKGROUND_FOREST_NEAR_IMG_PATH =
            "textures/background_forest_near.png";

    // =========================
    // Основной светлячок
    // =========================

    public static final String FIREFLY_1_IMG_PATH =
            "textures/firefly_1.png";

    public static final String FIREFLY_2_IMG_PATH =
            "textures/firefly_2.png";

    public static final String FIREFLY_3_IMG_PATH =
            "textures/firefly_3.png";

    // =========================
    // Препятствия
    // =========================

    public static final String BRANCH_TOP_IMG_PATH =
            "textures/branch_top.png";

    public static final String BRANCH_BOTTOM_IMG_PATH =
            "textures/branch_bottom.png";

    // =========================
    // Бонус
    // =========================

    public static final String GOLDEN_FIREFLY_IMG_PATH =
            "textures/golden_firefly.png";

    // =========================
    // Кнопки
    // =========================

    public static final String BUTTON_LONG_BG_IMG_PATH =
            "textures/button_long.png";

    public static final String BUTTON_SHORT_BG_IMG_PATH =
            "textures/button_short.png";

    // =========================
    // Звуки
    // =========================

    public static final String FOREST_MUSIC_PATH =
            "sounds/forest_music.ogg";

    public static final String WING_SOUND_PATH =
            "sounds/wing.wav";

    public static final String POINT_SOUND_PATH =
            "sounds/point.wav";

    public static final String BONUS_SOUND_PATH =
            "sounds/bonus.wav";

    public static final String HIT_SOUND_PATH =
            "sounds/hit.wav";
}