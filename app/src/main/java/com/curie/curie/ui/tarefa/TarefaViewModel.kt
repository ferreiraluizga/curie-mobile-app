package com.curie.curie.ui.tarefa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curie.curie.data.api.RetrofitClient
import com.curie.curie.data.model.Tarefa
import kotlinx.coroutines.launch

class TarefaViewModel : ViewModel() {

    private val _tarefas = MutableLiveData<List<Tarefa>>()
    val tarefas: LiveData<List<Tarefa>> = _tarefas

    private val _mensagem = MutableLiveData<String>()
    val mensagem: LiveData<String> = _mensagem

}