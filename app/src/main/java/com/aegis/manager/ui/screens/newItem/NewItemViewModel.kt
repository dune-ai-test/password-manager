package com.aegis.manager.ui.screens.newItem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aegis.manager.data.model.ItemType
import com.aegis.manager.data.model.VaultItem
import com.aegis.manager.data.repo.VaultRepository
import com.aegis.manager.domain.GeneratorOptions
import com.aegis.manager.domain.PasswordGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NewItemState(
    val title: String = "",
    val username: String = "",
    val password: String = "",
    val website: String = "",
    val notes: String = "",
    val suggestion: String = "",
)

@HiltViewModel
class NewItemViewModel @Inject constructor(
    private val repository: VaultRepository,
) : ViewModel() {

    private val generator = PasswordGenerator()

    private val _state = MutableStateFlow(NewItemState(suggestion = generate()))
    val state: StateFlow<NewItemState> = _state.asStateFlow()

    fun onTitleChange(value: String) = _state.update { it.copy(title = value) }
    fun onUsernameChange(value: String) = _state.update { it.copy(username = value) }
    fun onPasswordChange(value: String) = _state.update { it.copy(password = value) }
    fun onWebsiteChange(value: String) = _state.update { it.copy(website = value) }
    fun onNotesChange(value: String) = _state.update { it.copy(notes = value) }

    fun useSuggestion() {
        val fresh = generate()
        _state.update { it.copy(password = fresh, suggestion = fresh) }
    }

    private fun generate(): String = generator.generate(GeneratorOptions(length = 16))

    fun save() {
        val current = _state.value
        if (current.title.isBlank()) return

        viewModelScope.launch {
            repository.upsert(
                VaultItem(
                    type = ItemType.LOGIN,
                    title = current.title.trim(),
                    username = current.username.trim(),
                    password = current.password,
                    website = current.website.trim(),
                    notes = current.notes.trim(),
                    updatedAt = System.currentTimeMillis(),
                ),
            )
        }
    }
}
