package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var textResult: TextView
    lateinit var textCalculation: TextView

    var state: Int = 1
    var op: Int = 0
    var op1: Int = 0
    var op2: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textResult = findViewById(R.id.text_result)
        textCalculation = findViewById(R.id.text_calculation)

        val buttonIds = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5,
            R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.buttonAdd,
            R.id.buttonMinus, R.id.buttonMulti, R.id.buttonDivision, R.id.buttonEqual,
            R.id.buttonCE, R.id.buttonC, R.id.buttonBS
        )

        for (id in buttonIds) {
            findViewById<Button>(id).setOnClickListener(this)
        }
    }

    override fun onClick(p0: View?) {
        val id = p0?.id
        when (id) {
            R.id.button0 -> addDigit(0)
            R.id.button1 -> addDigit(1)
            R.id.button2 -> addDigit(2)
            R.id.button3 -> addDigit(3)
            R.id.button4 -> addDigit(4)
            R.id.button5 -> addDigit(5)
            R.id.button6 -> addDigit(6)
            R.id.button7 -> addDigit(7)
            R.id.button8 -> addDigit(8)
            R.id.button9 -> addDigit(9)
            R.id.buttonAdd -> setOperation(1) // Addition
            R.id.buttonMinus -> setOperation(2) // Subtraction
            R.id.buttonMulti -> setOperation(3) // Multiplication
            R.id.buttonDivision -> setOperation(4) // Division
            R.id.buttonEqual -> calculateResult()
            R.id.buttonCE -> resetCalculator() // Clear everything
            R.id.buttonC -> clearCurrentInput() // Clear current input
            R.id.buttonBS -> backspace() // Remove the last digit
        }
    }

    private fun addDigit(c: Int) {
        if (state == 1) {
            op1 = op1 * 10 + c
            textResult.text = "$op1"
            textCalculation.text = "$op1"
        } else {
            op2 = op2 * 10 + c
            textResult.text = "$op2"
            textCalculation.text = "$op1 ${getOperationSymbol(op)} $op2"
        }
    }


    private fun setOperation(operation: Int) {
        if (state == 1) {
            op = operation
            state = 2
            textCalculation.text = "$op1 ${getOperationSymbol(op)}"
        }
    }
    private fun getOperationSymbol(operation: Int): String {
        return when (operation) {
            1 -> "+"
            2 -> "-"
            3 -> "x"
            4 -> "/"
            else -> ""
        }
    }


    private fun calculateResult() {
        var result = 0
        when (op) {
            1 -> result = op1 + op2
            2 -> result = op1 - op2
            3 -> result = op1 * op2
            4 -> if (op2 != 0) {
                result = op1 / op2
            } else {
                textResult.text = "Error"
                textCalculation.text = ""
                return
            }
        }
        textResult.text = "$result"

        op1 = result
        
        op2 = 0
        state = 1
    }



    private fun resetCalculator() {
        state = 1
        op1 = 0
        op2 = 0
        op = 0
        textResult.text = "0"
        textCalculation.text = ""
    }

    private fun clearCurrentInput() {
        if (state == 1) {
            op1 = 0
            textResult.text = "0"
        } else {
            op2 = 0
            textResult.text = "0"
        }
        textCalculation.text = ""
    }

    private fun backspace() {
        if (state == 1 && op1 != 0) {
            op1 /= 10
            textResult.text = "$op1"
        } else if (state == 2 && op2 != 0) {
            op2 /= 10
            textResult.text = "$op2"
        }
    }
}