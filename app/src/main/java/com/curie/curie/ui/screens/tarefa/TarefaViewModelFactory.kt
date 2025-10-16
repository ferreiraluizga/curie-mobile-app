package com.curie.curie.ui.screens.tarefa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.curie.curie.data.api.TokenStorage

class TarefaViewModelFactory(
    private val tokenStorage: TokenStorage
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TarefaViewModel::class.java)) {
            return TarefaViewModel(tokenStorage) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
