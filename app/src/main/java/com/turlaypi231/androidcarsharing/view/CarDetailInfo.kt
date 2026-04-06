package com.turlaypi231.androidcarsharing.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.turlaypi231.androidcarsharing.model.Car

@Composable
fun CarDetailsSheet(car: Car) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(text = car.model, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Заряд: ${car.fuelLevel}%", color = if (car.fuelLevel < 20) Color.Red else Color.Green)
        Text(text = "Ціна за годину: ${car.price}$", color = Color.Green)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* booking logic */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Забронювати")
        }
    }
}

//@Preview(showSystemUi = true)
//@Composable
//fun Preview()
//{
//    CarDetailsSheet()
//}