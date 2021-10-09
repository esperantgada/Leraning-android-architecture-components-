package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    //For timer
    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 10000L
    }

    private val timer : CountDownTimer

    private val _currentTime = MutableLiveData<Long>()
    val currentTime : LiveData<Long>
        get() = _currentTime


    // The current word
     val word = MutableLiveData<String>()

    // The current score
     val score = MutableLiveData<Int>()

    private lateinit var wordList: MutableList<String>

     //Using liveData to encapsulate
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish : LiveData<Boolean>
        get() = _eventGameFinish


    init {
        _eventGameFinish.value = false
        Log.i("GameViewModel","GameViewModel Created")
        resetList()
        nextWord()
        score.value = 0

        //A timer which triggers the end of the game
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished/ ONE_SECOND)
            }

            override fun onFinish() {
                _eventGameFinish.value = true
            }
        }

        timer.start()

    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        Log.i("GameViewModel","GameViewModel destroyed")
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "baby",
            "man",
            "football",
            "shape",
            "goods",
            "piano",
            "health",
            "street",
            "toy",
            "door",
            "bag",
            "joy",
            "beautiful",
            "desk",
            "computer",
            "desktop",
            "queen",
            "hospital",
            "android",
            "basketball",
            "android studio",
            "cat",
            "bug",
            "change",
            "snail",
            "debug",
            "soup",
            "salt",
            "program",
            "calendar",
            "sad",
            "calculator",
            "code",
            "desk",
            "guitar",
            "house",
            "programming",
            "home",
            "drugstore",
            "railway",
            "shop",
            "zebra",
            "data",
            "keys",
            "world",
            "country",
            "jelly",
            "car",
            "kif",
            "crow",
            "drugs",
            "parents",
            "trade",
            "bag",
            "roll",
            "bubble",
            "Esperant",
            "bal",
            "trip",
            "France",
            "life"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
     private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
        word.value = wordList.removeAt(0)

    }

    /** Methods for buttons presses **/

     fun onSkip() {
        score.value = (score.value)?.minus(1)
        nextWord()
    }

     fun onCorrect() {
        score.value = (score.value)?.plus(1)
        nextWord()
    }

    /**Allow to avoid bad behavior of gameFinished function when rotating the screen
     *
     */
    fun onFinishComplete(){
        _eventGameFinish.value = false
    }

}