package com.curie.curie.ui.screens.chat

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.curie.curie.ui.theme.BlueDark
import com.curie.curie.ui.theme.BlueLight
import com.curie.curie.R
import com.curie.curie.ui.theme.Typography
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.collections.reversed
@Composable
fun ChatScreen(modifier: Modifier = Modifier, viewModel: ChatViewModel) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = BlueDark,
        darkIcons = false
    )
    Column(modifier = modifier.imePadding()) {
        AppHeader()
        MessageList(
            modifier = Modifier.weight(1f),
            messageList = viewModel.messageList
        )
        MessageInput(
            onMessageSend = {
                viewModel.sendMessage(it)
            }
        )
    }
}

@Composable
fun MessageList(modifier: Modifier = Modifier, messageList: List<MessageModel>) {
    val listState = rememberLazyListState()

    // ✅ Scrolla automaticamente quando uma nova mensagem é adicionada
    LaunchedEffect(messageList.size) {
        if (messageList.isNotEmpty()) {
            listState.animateScrollToItem(0)
        }
    }

    if (messageList.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Em que posso te ajudar hoje?", fontSize = 18.sp, color = Color.LightGray)
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            reverseLayout = true,
            state = listState // ✅ usa o estado
        ) {
            items(messageList.reversed()) {
                MessageRow(messageModel = it)
            }
        }
    }
}


@Composable
fun MessageRow(messageModel: MessageModel) {
    val isModel = messageModel.role == "model"

    // Define o shape do balão: todas arredondadas, exceto a "ponta"
    val bubbleShape = if (isModel) {
        RoundedCornerShape(
            topStart = 12.dp,
            topEnd = 12.dp,
            bottomEnd = 12.dp,
            bottomStart = 0.dp // ponta reta
        )
    } else {
        RoundedCornerShape(
            topStart = 12.dp,
            topEnd = 12.dp,
            bottomStart = 12.dp,
            bottomEnd = 0.dp // ponta reta
        )
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .align(if (isModel) Alignment.BottomStart else Alignment.BottomEnd)
                    .padding(
                        start = if (isModel) 8.dp else 70.dp,
                        end = if (isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(bubbleShape)
                    .background(if (isModel) BlueLight else BlueDark)
                    .padding(16.dp)
            ) {
                SelectionContainer {
                    Text(
                        text = messageModel.message,
                        fontWeight = FontWeight.W500,
                        color = Color.White
                    )
                }
            }
        }
    }
}


@Composable
fun MessageInput(onMessageSend: (String) -> Unit) {
    var message by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Caixa arredondada
        Box(
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(Color(0xFFF2F2F2)) // cinza claro
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                // Campo de texto
                TextField(
                    value = message,
                    onValueChange = { message = it },
                    placeholder = {
                        Text(
                            text = "Digite sua mensagem...",
                            color = Color.Gray
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = BlueDark,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedPlaceholderColor = Color.Gray,
                        unfocusedPlaceholderColor = Color.Gray
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                )

                // Botão dentro do campo
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            BlueDark,
                            RoundedCornerShape(topEnd = 28.dp, bottomEnd = 28.dp)
                        ) // azul
                        .clickable {
                            if (message.isNotEmpty()) {
                                onMessageSend(message)
                                message = ""
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Enviar",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun AppHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp) // altura maior para caber a logo e textos
    ) {
        // Imagem de fundo
        Image(
            painter = painterResource(id = R.drawable.chat_background),
            contentDescription = "Header Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Conteúdo sobre a imagem
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo do app
            Image(
                painter = painterResource(id = R.drawable.c_logo_nobackground), // substitua pela sua logo
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(48.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Título "Chat"
            Text(
                text = "CURIE Assistant",
                color = Color.White,
                style = Typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Subtítulo explicativo
            Text(
                text = "Converse com a inteligência artificial e receba ajuda para organizar, pesquisar e potencializar seus estudos",
                color = Color.White,
                style = Typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}


// Fake ViewModel apenas para preview
// ViewModel fake para preview
class PreviewChatViewModel : ChatViewModel() {
    init {
        // Simula algumas mensagens
        messageList.add(MessageModel("Oi, tudo bem?", "user"))
        messageList.add(MessageModel("Olá! Como posso ajudar?", "model"))
        messageList.add(MessageModel("Quero aprender Compose.", "user"))
        messageList.add(MessageModel("Perfeito! Vamos começar.", "model"))
    }

    // Sobrescreve sendMessage para não chamar API
    override fun sendMessage(question: String) {
        // Apenas adiciona a mensagem do usuário e resposta fake
        messageList.add(MessageModel(question, "user"))
        messageList.add(MessageModel("Resposta automática do preview", "model"))
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatScreenPreview() {
    val previewViewModel = PreviewChatViewModel()
    ChatScreen(
        modifier = Modifier.fillMaxSize(),
        viewModel = previewViewModel
    )
}

