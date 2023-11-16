package com.example.currencyconverter.screen.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ChartsScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Title")
        Spacer(modifier = Modifier.height(8.dp))
        Drawings()
        Spacer(modifier = Modifier.height(8.dp))
        Graph(points = mapOf(Pair(1, 50), Pair(2, 55), Pair(3, 48)))
        Spacer(modifier = Modifier.height(8.dp))
        Graph(
            points = mapOf(
                Pair(1, 50),
                Pair(2, 55),
                Pair(3, 48),
                Pair(4, 80),
                Pair(5, 20),
                Pair(6, 10),
                Pair(8, 100),
                Pair(9, 72),
                Pair(60, 50)
            )
        )
    }
}

@Composable
private fun Drawings() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Green),
        onDraw = {
            drawLine(Color.Red, Offset(0f, 0f), Offset(200f, 200f))
            drawCircle(Color.Blue, 100f, Offset(x = size.width / 2, y = size.height / 2))
            drawCircle(Color.Yellow, 50f, Offset(x = size.width / 2, y = size.height / 2))
            drawLine(
                color = Color.Red,
                start = Offset(0f, size.height),
                end = Offset(size.width / 2, size.height / 2),
                strokeWidth = 8.dp.toPx()
            )
        }
    )
}

@Composable
private fun Graph(points: Map<Int, Int>) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(2.dp, Color.Black, RoundedCornerShape(16.dp)),
        onDraw = {
            val xPart = size.width / points.maxBy { it.key }.key
            val yPart = size.height / 100

            val list = points.entries.toList().sortedBy { it.key }

            for (i in list.indices) {
                val x = ((list[i].key.toFloat() - 1) * xPart) + 30.dp.toPx()
                val y = size.height - (list[i].value.toFloat() * yPart)
                drawCircle(color = Color.Blue, radius = 4.dp.toPx(), center = Offset(x, y))

                if (i == list.size - 1) break

                val xNext = ((list[i + 1].key.toFloat() - 1) * xPart) + 30.dp.toPx()
                val yNext = size.height - (list[i + 1].value.toFloat() * yPart)

                drawLine(
                    start = Offset(x, y),
                    end = Offset(xNext, yNext),
                    color = Color.Magenta,
                    strokeWidth = 2.dp.toPx()
                )
            }
        }
    )
}
