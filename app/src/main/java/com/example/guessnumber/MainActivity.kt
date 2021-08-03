package com.example.guessnumber

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.guessnumber.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = "TTT"
    private var max = 100
    private var min = 10
    private var chance = 6
    private var randomNumber: Int = 0
    private var isWon: Boolean = false
    private var isLower: Boolean = false
    private var isHigher: Boolean = false
    private var isInvalid: Boolean = false
    private val intervalText = "Number is between $min and $max. Can you guess it?"
    private val gameEnd = "Game Over"

    /*    private val lowerText = "Lower. You have $chance guesses left"
        private val higherText = "Higher. You have $chance guesses left"
        private val gameWon = "You guessed it! The number was $randomNumber."
        private val gameLost = "You lost. The number was $randomNumber."*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        randomNumber = generateNumber()
        Log.d(TAG, "onCreate: $randomNumber")
    }

    fun onSubmit(v: View) {
        if (binding.editTextNumber.text.isNotEmpty()) {
            val guessUser = binding.editTextNumber.text.toString().toInt()
            binding.editTextNumber.text.clear()
            var inter = ""
            var result = ""
            checkAnswer(guessUser)
            when {
                isWon -> {
                    inter = gameEnd
                    result = "You guessed it! The number was $randomNumber."
                    isWon = false
                    binding.buttonSubmit.visibility = View.INVISIBLE
                }
                isLower -> {
                    inter = "Number is between $min and $max. Can you guess it?"
                    result = "Lower. You have $chance guesses left"
                    isLower = false
                }
                isHigher -> {
                    inter = "Number is between $min and $max. Can you guess it?"
                    result = "Higher. You have $chance guesses left"
                    isHigher = false
                }
                isInvalid -> {
                    inter = "Number is between $min and $max. Can you guess it?"
                    result = "Invalid number rangeh "
                    isInvalid = false
                }
                else -> {
                    inter = gameEnd
                    result = "You lost. The number was $randomNumber."
                }
            }
            binding.textViewInterval.text = inter
            binding.textViewResult.text = result
        }
    }

    //Invalid number range
    //
    //
    //You lost. The number was 53.
    // You gessed it! The number was 55.
    private fun generateNumber(): Int = (min..max).random()
    private fun checkAnswer(int: Int) {
        chance--
        if (randomNumber == int) {
            isWon = true
        } else if (int in (randomNumber + 1) until max) {
            max = int
            isLower = true
        } else if (int in min until (randomNumber - 1)) {
            min = int
            isHigher = true
        } else {
            isInvalid = true
        }
    }

    fun onReset(view: View) {
        recreate()
    }

    fun onExit(view: View) {
        finish()
    }
}