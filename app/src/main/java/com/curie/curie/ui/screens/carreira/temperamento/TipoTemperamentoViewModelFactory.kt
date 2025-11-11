package com.curie.curie.ui.screens.carreira.temperamento

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.ui.screens.carreira.comportamento.TipoComportamentoViewModel
import kotlin.jvm.java

class TipoTemperamentoViewModelFactory(
    private val tokenStorage: TokenStorage
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TipoTemperamentoViewModel::class.java)) {
            return TipoTemperamentoViewModel(tokenStorage) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}