package com.example.simplemathgame

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    //Keep track of the score and the total rounds
    private var score = 0
    private var rounds = 0
    private var hints = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load random numbers immediately
        initialize()
    }

    private fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

    @SuppressLint("SetTextI18n")
    fun resetButton(view: View){
        score = 0
        rounds = 0
        tracker.text = "Score: 0/0"
        message.text = ""
        hints = 3
        hintsleft.text = "Hints Left: $hints"
        answerbutton.isEnabled = true
        hintbutton.isEnabled = true
        initialize()
    }

    @SuppressLint("SetTextI18n")
    fun hintButton(view: View){
        val button = "hint"
        if (hints == 0){
            message.text = "You have used all your hints"
            val message = findViewById<TextView>(R.id.message)
            message.setTextColor(Color.parseColor("#7F00FF"))
        } else {
            hints--
            val diff = calculateDifference()
            message.text = "You used a hint. The answer was $diff"
            val message = findViewById<TextView>(R.id.message)
            message.setTextColor(Color.parseColor("#7F00FF"))
            score++
            rounds++
            tracker.text = "Score: $score/$rounds"
            hintsleft.text = "Hints Left: $hints"
            checkScore(button)
        }
    }

    @SuppressLint("SetTextI18n")
    fun answerButton(view: View) {
        val button = "answer"
        guess.hideKeyboard()
        val answer = guess.text.toString().toIntOrNull()
        val num1 = firstnumber.text.toString().toInt()
        val num2 = secondnumber.text.toString().toInt()
        when (answer) {
            calculateDifference() -> {
                message.text = "Correct!"
                val message = findViewById<TextView>(R.id.message)
                message.setTextColor(Color.parseColor("#00FF00"))
                score++
                rounds++
                initialize()
            }
            null -> {
                Toast.makeText(this, "You must enter a number", Toast.LENGTH_SHORT).show()
                guess.hideKeyboard()
                message.text = ""
            }
            else -> {
                val diff = calculateDifference()
                message.text = "Wrong. $num1 - $num2 is $diff"
                val message = findViewById<TextView>(R.id.message)
                message.setTextColor(Color.parseColor("#FF0000"))
                rounds++
                initialize()
            }
        }
        tracker.text = "Score: $score/$rounds"
        checkScore(button)
    }

    private fun calculateDifference(): Int{
        val num1 = firstnumber.text.toString().toInt()
        val num2 = secondnumber.text.toString().toInt()
        return num1 - num2
    }

    @SuppressLint("SetTextI18n")
    private fun checkScore(button: String){
        if (score == 10){
            message.text = "Congrats! You won the Simple Math Game"
            answerbutton.isEnabled = false
            hintbutton.isEnabled = false
        } else if (rounds == 20){
            message.text = "Game Over! Try Again"
            answerbutton.isEnabled = false
            hintbutton.isEnabled = false
        } else {
            if (button == "answer"){
                guess.setText("")
            }else if (button == "hint"){
                initialize()
            }
        }

    }


    private fun initialize(){
        //Set the numbers to random Ints
        val num1 = (10.. 100).random()
        val num2 = (10.. 100).random()
        if (num1 > num2) {
            firstnumber.text = "$num1"
            secondnumber.text = "$num2"
        } else {
            firstnumber.text = "$num2"
            secondnumber.text = "$num1"
        }
    }
}