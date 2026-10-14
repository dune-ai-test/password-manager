package com.aegis.manager.ui.screens.generator

import androidx.lifecycle.ViewModel
import com.aegis.manager.domain.GeneratorMode
import com.aegis.manager.domain.GeneratorOptions
import com.aegis.manager.domain.PasswordGenerator
import com.aegis.manager.domain.Strength
import com.aegis.manager.domain.StrengthEstimator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class GeneratorState(
    val password: String = "",
    val strength: Strength = Strength.WEAK,
    val length: Int = 20,
    val uppercase: Boolean = true,
    val lowercase: Boolean = true,
    val numbers: Boolean = true,
    val symbols: Boolean = true,
    val avoidLookAlikes: Boolean = false,
    val memorable: Boolean = false,
)

@HiltViewModel
class GeneratorViewModel @Inject constructor() : ViewModel() {

    private val generator = PasswordGenerator()

    private val _state = MutableStateFlow(GeneratorState())
    val state: StateFlow<GeneratorState> = _state.asStateFlow()

    init {
        regenerate()
    }

    fun regenerate() = _state.update { current ->
        val options = current.toOptions()
        val password = generator.generate(options)
        current.copy(
            password = password,
            strength = StrengthEstimator.estimate(password),
        )
    }

    fun onLengthChange(value: Float) {
        _state.update { it.copy(length = value.toInt()) }
        regenerate()
    }

    fun onUppercaseChange(value: Boolean) = change { it.copy(uppercase = value) }
    fun onLowercaseChange(value: Boolean) = change { it.copy(lowercase = value) }
    fun onNumbersChange(value: Boolean) = change { it.copy(numbers = value) }
    fun onSymbolsChange(value: Boolean) = change { it.copy(symbols = value) }
    fun onAvoidLookAlikesChange(value: Boolean) = change { it.copy(avoidLookAlikes = value) }
    fun onMemorableChange(value: Boolean) = change { it.copy(memorable = value) }

    private fun change(transform: (GeneratorState) -> GeneratorState) {
        _state.update(transform)
        regenerate()
    }

    private fun GeneratorState.toOptions() = GeneratorOptions(
        length = length,
        uppercase = uppercase,
        lowercase = lowercase,
        numbers = numbers,
        symbols = symbols,
        avoidLookAlikes = avoidLookAlikes,
        mode = if (memorable) GeneratorMode.MEMORABLE else GeneratorMode.RANDOM,
    )
}