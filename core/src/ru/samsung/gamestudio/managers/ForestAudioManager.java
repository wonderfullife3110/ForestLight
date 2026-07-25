package ru.samsung.gamestudio.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import ru.samsung.gamestudio.GameResources;

public class ForestAudioManager {

    private final Music backgroundMusic;

    private final Sound wingSound;
    private final Sound pointSound;
    private final Sound bonusSound;
    private final Sound hitSound;

    private boolean musicOn;
    private boolean soundOn;

    public ForestAudioManager() {
        backgroundMusic = Gdx.audio.newMusic(
                Gdx.files.internal(
                        GameResources.FOREST_MUSIC_PATH
                )
        );

        wingSound = Gdx.audio.newSound(
                Gdx.files.internal(
                        GameResources.WING_SOUND_PATH
                )
        );

        pointSound = Gdx.audio.newSound(
                Gdx.files.internal(
                        GameResources.POINT_SOUND_PATH
                )
        );

        bonusSound = Gdx.audio.newSound(
                Gdx.files.internal(
                        GameResources.BONUS_SOUND_PATH
                )
        );

        hitSound = Gdx.audio.newSound(
                Gdx.files.internal(
                        GameResources.HIT_SOUND_PATH
                )
        );

        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.20f);

        musicOn =
                ForestMemoryManager.loadIsMusicOn();

        soundOn =
                ForestMemoryManager.loadIsSoundOn();

        if (musicOn) {
            backgroundMusic.play();
        }
    }

    public void playMusic() {
        if (musicOn
                && !backgroundMusic.isPlaying()) {

            backgroundMusic.play();
        }
    }

    public void stopMusic() {
        if (backgroundMusic.isPlaying()) {
            backgroundMusic.stop();
        }
    }

    public void updateMusicFlag() {
        musicOn =
                ForestMemoryManager.loadIsMusicOn();

        if (musicOn) {
            playMusic();
        } else {
            stopMusic();
        }
    }

    public void updateSoundFlag() {
        soundOn =
                ForestMemoryManager.loadIsSoundOn();
    }

    public void playWingSound() {
        if (soundOn) {
            wingSound.play(0.25f);
        }
    }

    public void playPointSound() {
        if (soundOn) {
            pointSound.play(0.35f);
        }
    }

    public void playBonusSound() {
        if (soundOn) {
            bonusSound.play(0.45f);
        }
    }

    public void playHitSound() {
        if (soundOn) {
            hitSound.play(0.40f);
        }
    }

    public boolean isMusicOn() {
        return musicOn;
    }

    public boolean isSoundOn() {
        return soundOn;
    }

    public void dispose() {
        backgroundMusic.dispose();

        wingSound.dispose();
        pointSound.dispose();
        bonusSound.dispose();
        hitSound.dispose();
    }
}