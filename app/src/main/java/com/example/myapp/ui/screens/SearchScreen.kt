package com.example.myapp.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext


@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MyappTheme(/*useSystemUiController = false*/) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            SearchScreen(
                todoViewModel = viewModel(),
                context = LocalContext.current
            )
        }
    }
}

@Composable
fun SearchScreen(todoViewModel: TodoViewModel, context: Context) {
    val searchQuery = remember { mutableStateOf("") }
    val filteredTodos = remember(todoViewModel.todos, searchQuery.value) {
        todoViewModel.todos.filter {
            it.organisation.contains(searchQuery.value, ignoreCase = true)
        }.distinctBy { it.organisation }
    }

    Column {
        TextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            placeholder = { Text("Search by organization name") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )

        TodoList(filteredTodos, context)
    }
}

@Composable
fun TodoList(todos: List<Todo>, context: Context) {
    LazyColumn(
        modifier = Modifier.padding(8.dp)
    ) {
        items(todos) { todo ->
            ClickableText(
                text = AnnotatedString(todo.organisation),
                onClick = {
                    val message = "Personnel amount: ${todo.personnel_amount}\n" +
                            "Personnel cost: ${todo.personnel_cost} €\n" +
                            "Personnel effectivity: ${todo.personnel_effectivity}"
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
            Divider(color = Color.LightGray, thickness = 1.dp)
        }
    }
}


