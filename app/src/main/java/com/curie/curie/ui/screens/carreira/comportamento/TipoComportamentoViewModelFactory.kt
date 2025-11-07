package com.curie.curie.ui.screens.carreira.comportamento

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.curie.curie.data.api.TokenStorage
import kotlin.jvm.java

class TipoComportamentoViewModelFactory(
    private val tokenStorage: TokenStorage
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TipoComportamentoViewModel::class.java)) {
            return TipoComportamentoViewModel(tokenStorage) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}