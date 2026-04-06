package com.turlaypi231.androidcarsharing.view

import com.turlaypi231.androidcarsharing.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.turlaypi231.androidcarsharing.model.Car

@Composable
fun CarDetailsSheet(car: Car) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.85f)
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp)
            .padding(bottom = 24.dp)
    ) {
        Text(
            text = car.model,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id=getBatteryIcon(car.fuelLevel)),
                contentDescription = "Battery Level",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "${car.fuelLevel}%",
                style = MaterialTheme.typography.bodyLarge,
                color = if (car.fuelLevel < 20) Color.Red else Color.Unspecified
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* booking logic */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Забронювати")
        }

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Деталі автомобіля",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Номерний знак: ${car.plateNumber}")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Коробка передач: Автомат")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Тариф: ${car.price} грн/година")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Опис: Чистий салон, кондиціонер працює. Будь ласка, залишайте автомобіль на дозволених парковках згідно з правилами сервісу.")
    }
}

@Composable
fun getBatteryIcon(fuelLevel: Int): Int
{
    return when {
        fuelLevel > 90 -> R.drawable.battery_android_frame_full_24px
        fuelLevel > 70 -> R.drawable.battery_android_frame_5_24px
        fuelLevel > 50 -> R.drawable.battery_android_frame_4_24px
        fuelLevel > 35 -> R.drawable.battery_android_frame_3_24px
        fuelLevel > 20 -> R.drawable.battery_android_frame_2_24px
        fuelLevel > 10 -> R.drawable.battery_android_frame_1_24px
        else -> R.drawable.battery_android_alert_24px
    }
}