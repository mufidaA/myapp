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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
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
    Graph(todoViewModel.todos, modifier = Modifier, paddingSpace = 20.dp, verticalStep = 1.0f)
}

@Composable
fun Graph(todos: List<Todo>,
          modifier : Modifier,
//    xValues: List<Int>,
//    yValues: List<Int>,
//    points: List<Float>,
          paddingSpace: Dp,
          verticalStep: Float) {
    val xValues = mutableListOf<Int>()
    val yValues = mutableListOf<Int>()
    val pointsY = mutableListOf<Float>()
    val pointsX = mutableListOf<Float>()
    for (x in arrayOf(
        2013, 2014, 2015, 2016, 2017, 2018,
        2019, 2020
    )) {
        xValues.add(x)
    }
    for (y in arrayOf(
        0, 50000, 100000, 150000, 200000, 250000, 300000,
        350000, 400000, 450000, 500000, 550000, 650000, 700000,
        750000, 800000, 950000, 1000000, 1100000, 1150000, 1200000,
        1250000, 1300000, 1350000, 1400000, 1450000, 1500000, 1550000,
        1600000
    )) {
        yValues.add(y)
    }

    for(tod in todos){
        if(tod.personnel_cost == null) continue
        else if (tod.personnel_cost.toInt() < 0 ) continue
        else if (tod.organisation == "Oulun seudun ympäristöt.laitos"){
            pointsY.add(tod.personnel_cost.toFloat())
            pointsX.add(tod.year.toFloat())
        }
        else {continue}

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


