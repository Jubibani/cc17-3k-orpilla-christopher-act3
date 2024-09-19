package com.example.diceroller

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import android.widget.Button
import android.widget.ImageView
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    private lateinit var button: Button
    private lateinit var imageView: ImageView
    private val handler = Handler(Looper.getMainLooper())
    private var isRunning = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        imageView = findViewById(R.id.imageView) // Updated to reference ImageView

        // Set the default dice face
        imageView.setImageResource(R.drawable.dice_1)

        button.setOnClickListener {
            if (!isRunning) {
                startSlotMachine()
            }
        }
    }

    private fun startSlotMachine() {
        isRunning = true
        val duration = 1000L // Duration for the slot effect
        val interval = 50L // Interval between number changes
        var elapsedTime = 0L

        val runnable = object : Runnable {
            override fun run() {
                if (elapsedTime < duration) {
                    val randomNumber = Random.nextInt(1, 7) // Generate a random number between 1 and 6
                    // Set the ImageView source to a random dice face drawable
                    imageView.setImageResource(getDiceDrawableId(randomNumber))
                    handler.postDelayed(this, interval)
                    elapsedTime += interval
                } else {
                    val finalNumber = Random.nextInt(1, 7)
                    // Set the ImageView source to the final dice face drawable
                    imageView.setImageResource(getDiceDrawableId(finalNumber))
                    isRunning = false
                }
            }
        }

        handler.post(runnable)
    }

    // Function to get the drawable resource ID for the dice face
    private fun getDiceDrawableId(face: Int): Int {
        return when (face) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            6 -> R.drawable.dice_6
            else -> R.drawable.dice_1 // Default face
        }
    }
}
