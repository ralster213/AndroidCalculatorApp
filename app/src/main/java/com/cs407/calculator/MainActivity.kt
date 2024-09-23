package com.cs407.calculator
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var editText1: EditText
    private lateinit var editText2: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize EditText fields
        editText1 = findViewById(R.id.editTextNumberSigned1)
        editText2 = findViewById(R.id.editTextNumberSigned2)

        // Initialize buttons
        val addButton: Button = findViewById(R.id.addButton)
        val subtractButton: Button = findViewById(R.id.subtractButton)
        val multiplyButton: Button = findViewById(R.id.multiplyButton)
        val divideButton: Button = findViewById(R.id.divideButton)

        // Set click listeners
        addButton.setOnClickListener { performOperation("+") }
        subtractButton.setOnClickListener { performOperation("-") }
        multiplyButton.setOnClickListener { performOperation("*") }
        divideButton.setOnClickListener { performOperation("/") }
    }

    private fun performOperation(operation: String) {
        val num1Str = editText1.text.toString()
        val num2Str = editText2.text.toString()

        if (num1Str.isEmpty() || num2Str.isEmpty()) {
            Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val num1 = num1Str.toInt()
            val num2 = num2Str.toInt()

            val result = when (operation) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "*" -> num1 * num2
                "/" -> {
                    if (num2 == 0) {
                        Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show()
                        return
                    }
                    num1 / num2
                }
                else -> throw IllegalArgumentException("Invalid operation")
            }

            val intent = Intent(this, CalculatorActivity::class.java).apply {
                putExtra("RESULT", result.toString())
            }
            startActivity(intent)

        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Please enter valid integers", Toast.LENGTH_SHORT).show()
        }
    }
}