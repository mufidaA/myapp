package com.example.myapp.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapp.model.Todo
import com.example.myapp.ui.theme.MyappTheme
import com.example.myapp.viewmodel.TodoViewModel
import androidx.compose.material.Snackbar


@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MyappTheme(/*useSystemUiController = false*/) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            SearchScreen(
                todoViewModel = viewModel()
            )
        }
    }
}

@Composable
fun SearchScreen(todoViewModel: TodoViewModel) {
    val searchQuery = remember { mutableStateOf("") }
    val filteredTodos = remember(todoViewModel.todos, searchQuery.value) {
        todoViewModel.todos.filter {
            it.organisation.contains(searchQuery.value, ignoreCase = true)
        }
    }

    Column {
        TextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            placeholder = { Text("Search by organization name") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )

        TodoList(filteredTodos)
    }
}

@Composable
fun TodoList(todos: List<Todo>) {
    val messageToShow = remember { mutableStateOf("") }
    Box {
        LazyColumn(
            modifier = Modifier.padding(8.dp)
        ) {
            items(todos) { todo ->
                ClickableText(
                    text = AnnotatedString(todo.organisation + " info dated: " + todo.month + "." +todo.year ),
                    onClick = {
                        val message = "${todo.organisation} has: \n"+
                                "Personnel amount of: ${todo.personnel_amount}\n" +
                                "with Personnel cost of: ${todo.personnel_cost} €\n" +
                                "and Personnel effectivity of: ${todo.personnel_effectivity}"
                        messageToShow.value = message
                    },
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                )
                Divider(color = Color.LightGray, thickness = 1.dp)
            }
        }
        if (messageToShow.value.isNotEmpty()) {
            Snackbar(
                action = {
                    TextButton(onClick = { messageToShow.value = "" }) {
                        Text(text = "Dismiss")
                    }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = messageToShow.value)
            }
        }
    }
}


