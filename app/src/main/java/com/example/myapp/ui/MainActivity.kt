package com.example.myapp.ui
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.myapp.ui.navigation.*
import com.example.myapp.ui.theme.MyappTheme
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyappTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun MainScreen() {
    MyappTheme {

        val navController = rememberNavController()
        val scaffoldState = rememberScaffoldState()
        val scope =  rememberCoroutineScope()

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { TopBar(
                navController =navController,
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            ) },
            bottomBar = { BottomBarNav(navController = navController) },
            drawerContent = {
                DrawerHeader()
                DrawerBody(navController = navController, closeNavDrawer = {
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                })
            }
        ) { paddingValues ->
            NavGraph(
                modifier = Modifier.padding(
                    bottom = paddingValues.calculateBottomPadding()),
                navController = navController
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyappTheme {
        MainScreen()
    }
}