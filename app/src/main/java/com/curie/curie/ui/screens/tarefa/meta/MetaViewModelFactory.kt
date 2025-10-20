package com.curie.curie.ui.screens.tarefa.meta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.curie.curie.data.api.TokenStorage

class MetaViewModelFactory(
    private val tokenStorage: TokenStorage
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MetaViewModel::class.java)) {
            return MetaViewModel(tokenStorage) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
