package com.example.myapp.ui.screens
import android.graphics.Point
import android.graphics.PointF
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapp.model.Todo
import com.example.myapp.ui.theme.MyappTheme
import com.example.myapp.viewmodel.TodoViewModel
import android.util.Log
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlin.math.max
import kotlin.math.min

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MyappTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            FiguresScreen()
        }
    }
}


@Composable
fun FiguresScreen(todoViewModel: TodoViewModel = viewModel()) {
    val filteredTodos = remember(todoViewModel.todos) { mutableStateListOf<Todo>() }
    val searchText = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = searchText.value,
            onValueChange = { searchText.value = it },
            label = { Text("Search by Organization") },
            modifier = Modifier.fillMaxWidth()
        )

        filteredTodos.clear()
        for (todo in todoViewModel.todos) {
            if (todo.personnel_cost == null) continue
            if (todo.personnel_cost.toInt() < 0) continue
            if (todo.organisation.contains(searchText.value, true)) {
                filteredTodos.add(todo)
            }
        }

        Graph(filteredTodos, modifier = Modifier, paddingSpace = 20.dp, verticalStep = 1.0f)
    }
}

@Composable
fun Graph(todos: List<Todo>,
          modifier : Modifier,
          paddingSpace: Dp,
          verticalStep: Float) {
    val xValues = mutableListOf<Int>()
    val yValues = mutableListOf<Int>()
    val pointsY = mutableListOf<Float>()
    val pointsX = mutableListOf<Float>()

    for (x in 2013..2020 step 1) {
        xValues.add(x)
    }

    for (y in 0..1600000 step 50000) {
        yValues.add(y)
    }

    for(tod in todos){

            pointsY.add(tod.personnel_cost.toFloat())
            pointsX.add(tod.year.toFloat())

    }

    Box(modifier = modifier
        .background(Color.White)
        .padding(horizontal = 8.dp, vertical = 12.dp),
    contentAlignment = Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize(),
        ) {
            val xAxisSpace = (size.width - paddingSpace.toPx()) / xValues.size
            val yAxisSpace = size.height / yValues.size

            /** placing x axis points */
            val textPaint = androidx.compose.ui.graphics.Paint().asFrameworkPaint().apply {
                textSize = 40f
            }
            Log.d( "Graph", "X" )
            var maxX = -1
            var minX = 2013;
            for (i in xValues.indices) {
                maxX = max(xValues[i], maxX);
                minX = min(xValues[i], minX);
                drawContext.canvas.nativeCanvas.drawText(
                    "${xValues[i]}",
                    xAxisSpace * (i + 1),
                    size.height - 30,
                    textPaint
                )
            }
            Log.d( "Graph", "Y" )
            /** placing y axis points */
            var maxY = -1;
            var minY = 1000000;
            for (i in yValues.indices) {
                minY = min(minY, yValues[i]);
                maxY = max(maxY, yValues[i])
                drawContext.canvas.nativeCanvas.drawText(
                    "${yValues[i]}",
                    paddingSpace.toPx() / 2f,
                    size.height - yAxisSpace * (i + 1),
                    textPaint
                )
            }

            /** placing points */
            var sX = maxX - minX
            var sY = maxY - minY
            Log.d( "Graph", "max vals = $maxX, $maxY" )
            Log.d( "Graph", "min vals = $minX, $minY" )
            Log.d( "Graph", "Size = X=${size.width}, Y=${size.height}" )
            for ((index, yVal) in pointsY.withIndex()) {
                if(yVal==0f) continue;

                val x = pointsX.get(index);
                val xPor = (x-minX)/sX
                val yPor = (yVal-minY)/sY
                val x1 = xPor * (xAxisSpace * xValues.size-1) + (xAxisSpace + paddingSpace.toPx())
                val y1 = yPor * (yAxisSpace * yValues.size-1) - (yAxisSpace + paddingSpace.toPx())

                Log.d( "Graph", "raw point = $x, $yVal, $xPor, $yPor" )
                Log.d( "Graph", "calculated point = $x1, $y1" )

                /** drawing circles to indicate all the points */
                drawCircle(
                    color = Color.Red,
                    radius = 10f,
                    center = Offset(x1, y1)
                )
            }
        }
    }}


