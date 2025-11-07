package com.curie.curie.ui.screens.carreira.comportamento

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.curie.curie.data.api.TokenStorage

class ComportamentoViewModelFactory(
    private val tokenStorage: TokenStorage
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComportamentoViewModel::class.java)) {
            return ComportamentoViewModel(tokenStorage) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}