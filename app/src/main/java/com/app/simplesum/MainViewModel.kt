package com.app.simplesum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.simplesum.domain.InputValidator

class MainViewModel : ViewModel() {
    val firstNumber = MutableLiveData("")
    val secondNumber = MutableLiveData("")
    val sum = MutableLiveData("")
    val resultMessage = MutableLiveData("")

    val isCalculateEnabled: LiveData<Boolean> =
        MediatorLiveData<Boolean>().apply {
            fun update() {
                value = !firstNumber.value.isNullOrBlank() &&
                    !secondNumber.value.isNullOrBlank()
            }
            value = false // ✅ initial state
            addSource(firstNumber) { update() }
            addSource(secondNumber) { update() }
        }

    fun setFirstNumber(input: String) {
        firstNumber.value = input.trim()
    }

    fun setSecondNumber(input: String) {
        secondNumber.value = input.trim()
    }

    fun onCalculateClick() {
        val fn = InputValidator.validate(firstNumber.value!!)
        val sn = InputValidator.validate(secondNumber.value!!)

        val hasError = listOf(fn, sn).find { it.error != null }
        if (hasError != null) {
            resultMessage.value = hasError.error?.message.orEmpty()
            return
        }

        sum.value = (fn.number.toInt() + sn.number.toInt()).toString()
    }
}
