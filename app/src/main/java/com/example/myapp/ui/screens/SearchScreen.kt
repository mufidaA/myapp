package com.example.myapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapp.model.Todo
import com.example.myapp.ui.navigation.common.DefaultButton
import com.example.myapp.ui.theme.MyappTheme
import com.example.myapp.viewmodel.TodoViewModel


@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MyappTheme(/*useSystemUiController = false*/) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            SearchScreen(
                /*query = "liang moi",
                popBackStack = {},
                popUpToLogin = {},*/
            )

        }
    }
}
@Composable
fun SearchScreen(todoViewModel: TodoViewModel = viewModel()) {
    TodoList(todoViewModel.todos)

}
@Composable
fun TodoList(todos: List<Todo>) {

    LazyColumn (
        modifier = Modifier.padding(8.dp)
    ) {
        items(todos) { todo->
            Text (
                text = todo.organisation,
                modifier = Modifier.padding(top = 4.dp,bottom = 4.dp)
            )
            Divider(color = Color.LightGray, thickness = 1.dp)

        }
    }
}