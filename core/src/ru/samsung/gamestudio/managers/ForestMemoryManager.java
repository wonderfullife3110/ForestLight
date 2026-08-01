package ru.samsung.gamestudio.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public final class ForestMemoryManager {
    private static final String STORAGE_NAME = "Forest Light Saves";
    private static final String BEST_SCORE_KEY = "bestScore";
    private static final String MUSIC_ON_KEY = "musicOn";
    private static final String SOUND_ON_KEY = "soundOn";
    private ForestMemoryManager() {
    }
    private static Preferences getPreferences() {
        return Gdx.app.getPreferences(STORAGE_NAME);
    }
    public static int loadBestScore() {
        return getPreferences().getInteger(BEST_SCORE_KEY, 0);
    }
    public static void saveBestScore(int score) {
        Preferences preferences = getPreferences();
        int currentBestScore = preferences.getInteger(BEST_SCORE_KEY, 0);
        if (score > currentBestScore) {
            preferences.putInteger(BEST_SCORE_KEY, score);
            preferences.flush();
        }
    }
    public static void clearBestScore() {
        Preferences preferences = getPreferences();
        preferences.remove(BEST_SCORE_KEY);
        preferences.flush();
    }

    public static boolean loadIsMusicOn() {
        return getPreferences().getBoolean(MUSIC_ON_KEY, true);
    }

    public static void saveMusicSettings(boolean isOn) {
        Preferences preferences = getPreferences();
        preferences.putBoolean(MUSIC_ON_KEY, isOn);
        preferences.flush();
    }

    public static boolean loadIsSoundOn() {
        return getPreferences().getBoolean(SOUND_ON_KEY, true);
    }

    public static void saveSoundSettings(boolean isOn) {
        Preferences preferences = getPreferences();
        preferences.putBoolean(SOUND_ON_KEY, isOn);
        preferences.flush();
    }
}