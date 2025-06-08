package data

import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool
import com.example.milionarsemestralka.R

object SoundManager {
    var musicVolume = 0.5f
    var soundVolume = 0.5f
    val ladderActivitySound = SoundPool.Builder().setMaxStreams(1).build()
    val correctAnswerSound = SoundPool.Builder().setMaxStreams(1).build()
    val wrongAnswerSound = SoundPool.Builder().setMaxStreams(1).build()
    var startActivityMusic: MediaPlayer? = null
    var hotSeatActivityMusic: MediaPlayer? = null

    fun initialize(context: Context) {
        startActivityMusic = MediaPlayer.create(context, R.raw.start_activity_music)
        hotSeatActivityMusic = MediaPlayer.create(context, R.raw.hot_seat_music)
        ladderActivitySound.load(context, R.raw.ladder_activity_sound_effect, 1)
        correctAnswerSound.load(context, R.raw.correct_sound_effect, 1)
        wrongAnswerSound.load(context, R.raw.incorrect_sound_effect, 1)
    }

    fun playLadderActivitySound() {
        ladderActivitySound.play(1, soundVolume, soundVolume, 0, 0, 1f)
    }
    fun playCorrectAnswerSound() {
        correctAnswerSound.play(1, soundVolume, soundVolume, 0, 0, 1f)
    }
    fun playWrongAnswerSound() {
        wrongAnswerSound.play(1, soundVolume, soundVolume, 0, 0, 1f)
    }
    fun playStartActivitySound() {
        startActivityMusic?.isLooping = true
        startActivityMusic?.setVolume(musicVolume, musicVolume)
        startActivityMusic?.start()
    }
    fun stopStartActivitySound() {
        startActivityMusic?.stop()
        startActivityMusic?.release()
    }
    fun playHotSeatActivitySound() {
        hotSeatActivityMusic?.isLooping = true
        hotSeatActivityMusic?.setVolume(musicVolume, musicVolume)
        hotSeatActivityMusic?.start()
    }
    fun stopHotSeatActivitySound() {
        hotSeatActivityMusic?.stop()
        hotSeatActivityMusic?.release()
    }



}