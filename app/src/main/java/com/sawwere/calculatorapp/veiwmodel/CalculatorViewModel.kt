package com.sawwere.calculatorapp.veiwmodel

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField

class CalculatorViewModel: BaseObservable(), Calculator {
    override var display = ObservableField<String>()

    private var operand1: Double? = null
    private var currentOperator: Operation? = null
    private val currentNumber = StringBuilder()
    private val maxDigits = 15

    override fun addDigit(dig: Int) {
        if (currentNumber.length >= maxDigits) {
            return
        }
        if (currentNumber.isEmpty() && dig == 0) {
            return
        }
        if (currentNumber.toString() == "0") {
            currentNumber.clear()
        }

        currentNumber.append(dig)
        updateScreen()
    }

    override fun addPoint() {
        if (currentNumber.contains(".")){
            return
        }
        if (currentNumber.isEmpty()) {
            currentNumber.append("0.")
        } else {
            currentNumber.append(".")
        }
        updateScreen()
    }

    override fun addOperation(op: Operation) {
        if (currentNumber.isEmpty() && operand1 == null) {
            return
        }

        if (operand1 == null) {
            operand1 = currentNumber.toString().toDoubleOrNull() ?: return
            currentOperator = op
            currentNumber.clear()
            updateScreen()
        } else {
            if (currentNumber.isNotEmpty()) {
                val operand2 = currentNumber.toString().toDoubleOrNull() ?: return
                val result = performOperation(operand1!!, operand2, currentOperator)
                operand1 = result
                currentNumber.clear()
                updateScreen()
            }
            currentOperator = op
        }
    }

    private fun performOperation(a: Double, b: Double, operator: Operation?): Double {
        return when (operator) {
            Operation.ADD -> a + b
            Operation.SUB -> a - b
            Operation.MUL -> a * b
            Operation.DIV-> if (b != 0.0) a / b else Double.NaN
            else -> b
        }
    }


    override fun compute() {
        if (currentNumber.isEmpty() && operand1 == null) return
        if (currentNumber.isNotEmpty()) {
            val operand2 = currentNumber.toString().toDoubleOrNull() ?: return
            operand1 = if (operand1 == null) {operand2} else performOperation(operand1!!, operand2, currentOperator)
            currentNumber.clear()
            updateScreen()
        }
        currentOperator = null
    }

    override fun clear() {
        currentNumber.clear()
        display.set("")
    }

    override fun reset() {
        operand1 = null
        currentOperator = null
        currentNumber.clear()
        display.set("")
    }

    private fun updateScreen() {
        val displayText = if (currentNumber.isNotEmpty()) {
                currentNumber.toString()
            } else {
                operand1?.let {
                    if (it.isNaN()) "Error"
                    else if (it % 1 == 0.0) it.toLong().toString()
                    else it.toString()
                } ?: "0"
            }
        display.set(displayText)
    }
}