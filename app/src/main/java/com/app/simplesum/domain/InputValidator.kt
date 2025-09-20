package com.app.simplesum.domain

import com.app.simplesum.domain.excaptions.BlankInputException
import com.app.simplesum.domain.excaptions.InvalidInputException

object InputValidator {
    fun validate(input: String): InputValidationResult =
        when {
            input.isBlank() ->
                InputValidationResult(error = BlankInputException())

            input.any { it.isLetter() || it.isLetterOrDigit().not() } ->
                InputValidationResult(error = InvalidInputException())

            else -> {
                val number = input.trim()
                InputValidationResult(number = number)
            }
        }
}
