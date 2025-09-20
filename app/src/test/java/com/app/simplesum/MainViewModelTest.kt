package com.app.simplesum

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.app.simplesum.domain.excaptions.BlankInputException
import com.app.simplesum.domain.excaptions.InvalidInputException
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {
    @get:Rule
    val mainRule = MainCoroutineRule()
    private lateinit var viewModel: MainViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = MainViewModel()
    }

    @Test
    fun `calculate sum when valid numbers`() {
        val viewModel = MainViewModel()

        viewModel.setFirstNumber("2")
        viewModel.setSecondNumber("3")
        viewModel.onCalculateClick()

        assertThat(viewModel.sum.value).isEqualTo("5")
    }

    @Test
    fun `calculate sum with invalid numbers`() {
        val viewModel = MainViewModel()

        viewModel.setFirstNumber("abc")
        viewModel.setSecondNumber("3")
        viewModel.onCalculateClick()

        assertThat(viewModel.resultMessage.value).isEqualTo(InvalidInputException().message)
    }

    @Test
    fun `calculate sum with empty numbers`() {
        val viewModel = MainViewModel()

        viewModel.setFirstNumber("")
        viewModel.setSecondNumber("")
        viewModel.onCalculateClick()

        assertThat(viewModel.resultMessage.value).isEqualTo(BlankInputException().message)
    }

    @Test
    fun enabled_when_both_numbers_set() =
        runTest {
            val obs = Observer<Boolean> { }
            try {
                viewModel.isCalculateEnabled.observeForever(obs)

                viewModel.setFirstNumber("2")
                assertThat(viewModel.isCalculateEnabled.value).isEqualTo(false)

                viewModel.setSecondNumber("3")
                assertThat(viewModel.isCalculateEnabled.value).isEqualTo(true)
            } finally {
                viewModel.isCalculateEnabled.removeObserver(obs)
            }
        }
}
