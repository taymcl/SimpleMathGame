package com.example.simplemathgame

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text
import java.util.*


class MainActivity : AppCompatActivity() {
    //Keep track of the score and the total rounds
    private var score = 0
    private var rounds = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load random numbers immediately
        initialize()
    }

    fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

    fun resetButton(view: View){
        score = 0
        rounds = 0
        tracker.text = "Score: 0/0"
        initialize()
    }

    fun answerButton(view: View) {
        guess.hideKeyboard()
        val answer = guess.text.toString().toIntOrNull()
        val num1 = firstnumber.text.toString().toInt()
        val num2 = secondnumber.text.toString().toInt()
        if (answer == num1 - num2){
            message.text = "Correct!"
            //message.setTextColor(Color.parseColor("#FF000"))
            score++
            rounds++
        } else if (answer == null){
            Toast.makeText(this, "You must enter a number", Toast.LENGTH_SHORT).show()
        } else {
            val diff = num1 - num2
            message.text = "Wrong. $num1 - $num2 is $diff"
            //message.setTextColor(Color.parseColor("#FF000"))
            rounds++
        }
        tracker.text = "Score: $score/$rounds"
        initialize()
        guess.setText("")
    }


    private fun initialize(){
        //Set the numbers to random Ints
        val num1 = generateRandomNumber(90)
        val num2 = generateRandomNumber(90)
        if (num1 > num2) {
            firstnumber.text = "$num1"
            secondnumber.text = "$num2"
        } else {
            firstnumber.text = "$num2"
            secondnumber.text = "$num1"
        }
    }
    private fun generateRandomNumber(range: Int): Int {
        return Random().nextInt(range) + 10
    }
}