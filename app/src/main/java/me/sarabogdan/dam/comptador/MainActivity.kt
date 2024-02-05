package me.sarabogdan.dam.comptador

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import android.view.animation.AnimationUtils


class MainActivity : ComponentActivity() {

    private val INITIAL_TIME = 10

    internal lateinit var tapMeButton : Button
    internal lateinit var counterTextView : TextView
    internal lateinit var timeTextView : TextView
    internal var counter = 0
    internal var time =  INITIAL_TIME

    internal var appStarted = false
    internal lateinit var countdownTimer : CountDownTimer
    internal val initialCountDownTimer : Long = time.toLong()*1000
    internal val intervalCountDownTimer : Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initCountdown()

        tapMeButton = findViewById(R.id.tapMeButton)
        timeTextView = findViewById(R.id.timeTextView)
        counterTextView = findViewById(R.id.counterTextView)

        val fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade)
        tapMeButton.startAnimation(fadeAnimation)
        timeTextView.startAnimation(fadeAnimation)
        counterTextView.startAnimation(fadeAnimation)

        counterTextView.text = getString(R.string.counterText, counter)
        tapMeButton.setOnClickListener{
            if (!appStarted){
                startGame()
            }
            incrementCounter()
        }

        timeTextView.text = getString(R.string.timeText, time)
    }

    private fun startGame() {
        countdownTimer.start()
        appStarted = true
    }

    private fun initCountdown(){
        countdownTimer = object : CountDownTimer(initialCountDownTimer, intervalCountDownTimer){
            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished / 1000
                timeTextView.text = getString(R.string.timeText, timeLeft)
            }

            override fun onFinish() {
                endGame()
            }
        }
    }
    private fun incrementCounter(){
        counter += 1
        counterTextView.text = getString(R.string.counterText, counter)
    }

    private fun endGame(){
        val fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.blink)
        timeTextView.startAnimation(fadeAnimation)

        Toast.makeText(this, getString(R.string.endGame), Toast.LENGTH_LONG).show()
        resetGame()
    }

    private fun resetGame(){
        counter = 0
        timeTextView.text = time.toString()
        time = INITIAL_TIME
        initCountdown()
        appStarted = false
    }
}