package com.curie.curie.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.curie.curie.ui.theme.BlueNavy
import com.curie.curie.ui.theme.CurieTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarefaTopBar(
    query: String,
    onQueryChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = {}
) {
    var active by remember { mutableStateOf(false) }

    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {
            onSearch(it)
            active = false
        },
        active = active,
        onActiveChange = { active = it },
        placeholder = { Text("Buscar tarefas...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar"
            )
        },
        trailingIcon = {
            if (query.isNotEmpty() || active) {
                androidx.compose.material3.IconButton(onClick = {
                    onQueryChange("")  // limpa a busca
                    active = false      // fecha a search bar
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Fechar busca"
                    )
                }
            }
        },
        colors = SearchBarDefaults.colors(
            containerColor = Color.White,
            dividerColor = BlueNavy
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        // Aqui você pode mostrar sugestões de busca
        Text(
            text = "Exemplo de sugestão",
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewTarefaTopBar() {
    CurieTheme {
        var query by remember { mutableStateOf("") }

        TarefaTopBar(
            query = query,
            onQueryChange = { query = it }
        )
    }
}
