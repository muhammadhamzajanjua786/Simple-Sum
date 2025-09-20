package com.app.simplesum.domain

data class InputValidationResult(
    val number: String = "",
    val error: Exception? = null,
)
