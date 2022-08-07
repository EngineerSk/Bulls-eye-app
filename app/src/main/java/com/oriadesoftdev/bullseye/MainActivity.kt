package com.oriadesoftdev.bullseye

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.oriadesoftdev.bullseye.databinding.ActivityMainBinding
import java.lang.Math.abs
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var sliderValue = 0
    private var targetValue = Random.nextInt(1, 100)
    private var totalScore = 0
    private var gameRound = 1
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startNewGame()
        binding.hitMeButton.setOnClickListener {
            showResult()
            totalScore += pointsForCurrentRound()
            binding.gameScoreTextView?.text = totalScore.toString()
        }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            /**
             * Notification that the progress level has changed. Clients can use the fromUser parameter
             * to distinguish user-initiated changes from those that occurred programmatically.
             *
             * @param seekBar The SeekBar whose progress has changed
             * @param progress The current progress level. This will be in the range min..max where min
             * and max were set by [SeekBar.setMin] and
             * [SeekBar.setMax], respectively. (The default values for
             * min is 0 and max is 100.)
             * @param fromUser True if the progress change was initiated by the user.
             */
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                sliderValue = progress
            }

            /**
             * Notification that the user has started a touch gesture. Clients may want to use this
             * to disable advancing the seekbar.
             * @param seekBar The SeekBar in which the touch gesture began
             */
            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            /**
             * Notification that the user has finished a touch gesture. Clients may want to use this
             * to re-enable advancing the seekbar.
             * @param seekBar The SeekBar in which the touch gesture began
             */
            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
        binding.startOverButton?.setOnClickListener {
            startNewGame()
        }

        binding.infoButton?.setOnClickListener {
            navigateToAboutPage()
        }
    }

    private fun pointsForCurrentRound(): Int {
        val maxScore = 100
        val difference = differenceAmount()
        var bonus = 0
        if (difference == 0)
            bonus = 100
        else if (difference == 1)
            bonus = 50
        return maxScore - difference + bonus
    }

    private fun navigateToAboutPage(){
        startActivity(Intent(this, AboutActivity::class.java))
    }

    private fun newTargetValue() = Random.nextInt(1,100)

    private fun startNewGame() {
        sliderValue = 50
        targetValue = newTargetValue()
        totalScore = 0
        gameRound = 1
        binding.apply {
            gameRoundTextView?.text = gameRound.toString()
            gameScoreTextView?.text = totalScore.toString()
            targetTextView.text = targetValue.toString()
            seekBar.progress = sliderValue
        }
    }

    private fun showResult() {
        val dialogMessage =
            getString(R.string.result_dialog_message, sliderValue, pointsForCurrentRound())
//        val dialogMessage = getString(R.string.slider_value_message_format, sliderValue)
        val builder = AlertDialog.Builder(this)
        builder.setMessage(dialogMessage)
        builder.setTitle(alertTitle())
        builder.setPositiveButton(R.string.result_dialog_button_text) { dialog, _ ->
            dialog.dismiss()
            targetValue = newTargetValue()
            binding.targetTextView.text = targetValue.toString()
            gameRound++
            binding.gameRoundTextView?.text = gameRound.toString()
        }
        builder.create().show()
    }

    private fun differenceAmount() = kotlin.math.abs(targetValue - sliderValue)

    private fun alertTitle(): String {
        val difference = differenceAmount()
        return when {
            difference == 0 -> getString(R.string.title1)
            difference < 5 -> getString(R.string.title2)
            difference <= 10 -> getString(R.string.title3)
            else -> getString(R.string.title4)
        }
    }
}