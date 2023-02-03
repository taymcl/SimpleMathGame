// Code By Taylor McLean
// For CS414 Assignment 1

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
    //Keep track of the score, total rounds, and hints
    private var score = 0 // Initialize score counter
    private var rounds = 0 // Initialize round counter
    private var hints = 3 // Initialize available hints
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initialize() // Load first question
    }

    private fun View.hideKeyboard() { // Function to hide keyboard
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

    @SuppressLint("SetTextI18n")
    fun resetButton(view: View){ // Function for reset button
        score = 0 // Reset score
        rounds = 0 // Reset rounds
        tracker.text = "Score: 0/0" // Display the reset
        message.text = "" // Clear message
        hints = 3 // Reset amount of hints
        hintsleft.text = "Hints Left: $hints" // Display new hint count
        answerbutton.isEnabled = true // Re-enable answer button
        hintbutton.isEnabled = true // Re-enable hint button
        initialize() // Choose new subtraction problem
    }

    @SuppressLint("SetTextI18n")
    fun hintButton(view: View){ // Function for hint button
        val button = "hint" // String to be passed through checkScore function
        if (hints == 0){ // If user is out of hints
            message.text = "You have used all your hints" // Dispay message
            val message = findViewById<TextView>(R.id.message) // Select message ID
            message.setTextColor(Color.parseColor("#7F00FF")) // Set message color to purple
        } else { // If user is not out of hints
            hints-- // Decrement hint count
            val diff = calculateDifference() // Find the current difference of the numbers
            message.text = "You used a hint. The answer was $diff" // Display hint used message
            val message = findViewById<TextView>(R.id.message) // Select message ID
            message.setTextColor(Color.parseColor("#7F00FF")) // Set message color to purple
            score++ // Increase score counter
            rounds++ // Increase round counter
            tracker.text = "Score: $score/$rounds" // Display updated score counter
            hintsleft.text = "Hints Left: $hints" // Display new hint counter
            checkScore(button) // Check current score to see if user has won, lost, or can continue
        }
    }

    @SuppressLint("SetTextI18n")
    fun answerButton(view: View) { // Function for the answer button
        val button = "answer" // String to be passed through checkAnswer button
        guess.hideKeyboard() // Hide keyboard
        val answer = guess.text.toString().toIntOrNull() // Store user input
        val num1 = firstnumber.text.toString().toInt() // Store first number
        val num2 = secondnumber.text.toString().toInt() // Store second number
        when (answer) { // When answer is...
            calculateDifference() -> { // The correct answer
                message.text = "Correct!" // Display correct message
                val message = findViewById<TextView>(R.id.message) // Select message ID
                message.setTextColor(Color.parseColor("#00FF00")) // Change color of message to green
                score++ // Increase score counter
                rounds++ // Increase round counter
                initialize() // Choose new random numbers
            }
            null -> { // If user provided no answer
                Toast.makeText(this, "You must enter a number", Toast.LENGTH_SHORT).show() // Display toast message
                guess.hideKeyboard() // Hide keyboard
                message.text = "" // Clear message text
            }
            else -> { // Answer is wrong
                val diff = calculateDifference() // Find the current difference
                message.text = "Wrong. $num1 - $num2 is $diff" // Display message with the correct answer
                val message = findViewById<TextView>(R.id.message) // Select message ID
                message.setTextColor(Color.parseColor("#FF0000")) // Make message red
                rounds++ // Only increase round counter
                initialize() // Choose new random numbers
            }
        }
        tracker.text = "Score: $score/$rounds" // Display updated score counter
        checkScore(button) // Check to see if user has lost, won, or can continue playing
    }

    private fun calculateDifference(): Int{ // Function that returns difference between two random numbers
        val num1 = firstnumber.text.toString().toInt() // Store first number
        val num2 = secondnumber.text.toString().toInt() // Store second number
        return num1 - num2 // Return the difference
    }

    @SuppressLint("SetTextI18n")
    private fun checkScore(button: String){ // Function to check the score of the user
        if (score == 10){ // If they reach 10 correct answers
            message.text = "Congrats! You won the Simple Math Game" // Display message that user won
            answerbutton.isEnabled = false // Disable answer button
            hintbutton.isEnabled = false // Disable hint button
        } else if (rounds == 20){ // If they reach 20 rounds with less than 10 correct answers
            message.text = "Game Over! Try Again" // Display game over message
            answerbutton.isEnabled = false // Disable answer button
            hintbutton.isEnabled = false // Disable hint button
        } else { // If user has not reached a score of 10 or round of 20
            if (button == "answer"){ // If user had just pressed answer button
                guess.setText("") // Only clear textEdit input
            }else if (button == "hint"){ // If user had just pressed the hint button
                initialize() // Choose two new random numbers
            }
        }
    }

    private fun initialize(){ // Function to choose new random numbers for subtraction questions
        val num1 = (11.. 99).random() // new random Int between 10 and 100
        val num2 = (11.. 99).random() // new random Int between 10 and 100
        if (num1 > num2) { // If first random Int is greater than second random Int
            firstnumber.text = "$num1" // Make the first Int first in equation
            secondnumber.text = "$num2" // Make the second Int second in the equation
        } else { // If second random Int is greater than first random Int, or they are equal
            firstnumber.text = "$num2" // Make the second Int first in the equation
            secondnumber.text = "$num1" // Make the first Int second in the equation
        }
    }
}