package com.curie.curie.ui.screens.chat

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

open class ChatViewModel : ViewModel() {

    val messageList by lazy {
        mutableStateListOf<MessageModel>()
    }

    val generativeModel : GenerativeModel = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = Constants.apiKey
    )

    open fun sendMessage(question: String) {
        viewModelScope.launch {
            try {
                val chat = generativeModel.startChat(
                    history = messageList.map {
                        content(it.role) { text(it.message) }
                    }.toList()
                )

                // Adiciona mensagem do usuário
                messageList.add(MessageModel(question, "user"))
                // Adiciona "Typing..."
                messageList.add(MessageModel("Typing....", "model"))

                // Envia mensagem e recebe resposta
                val response = chat.sendMessage(question)

                // Remove "Typing..." do modelo
                if (messageList.isNotEmpty()) {
                    messageList.removeAt(messageList.size - 1)
                }

                // Adiciona resposta real do modelo
                messageList.add(MessageModel(response.text.toString(), "model"))

            } catch (e: Exception) {
                // Remove "Typing..." caso dê erro
                if (messageList.isNotEmpty()) {
                    messageList.removeAt(messageList.size - 1)
                }
                messageList.add(MessageModel("Error: ${e.message}", "model"))
            }
        }
    }

}