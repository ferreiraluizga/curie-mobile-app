// CarreiraViewModelFactory.kt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.curie.curie.ai.GeminiClient
import com.curie.curie.data.api.TokenStorage
import com.curie.curie.ui.screens.carreira.analise.CarreiraViewModel

class CarreiraViewModelFactory(
    private val tokenStorage: TokenStorage,
    private val geminiClient: GeminiClient
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarreiraViewModel::class.java)) {
            return CarreiraViewModel(tokenStorage, geminiClient) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}