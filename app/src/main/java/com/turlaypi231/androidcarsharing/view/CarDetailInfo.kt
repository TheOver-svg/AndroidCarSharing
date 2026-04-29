package com.turlaypi231.androidcarsharing.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.turlaypi231.androidcarsharing.R
import com.turlaypi231.androidcarsharing.model.Car
import com.turlaypi231.androidcarsharing.model.ElectricCar
import com.turlaypi231.androidcarsharing.model.GasolineCar
import com.turlaypi231.androidcarsharing.viewModel.MapViewModel

@Composable
fun CarDetailsSheet(car: Car, mapViewModel: MapViewModel = viewModel(), onBookingSuccess: () -> Unit) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.85f)
            .padding(horizontal = 24.dp)
            .padding(bottom = 16.dp)
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = car.model,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Image(
                    painter = painterResource(id = R.drawable.car_detail),
                    contentDescription = "3D car",
                    modifier = Modifier
                        .height(120.dp)
                        .padding(start = 16.dp)
                        .offset(y = (-10).dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                when (car) {
                    is ElectricCar -> {
                        SpecCard(
                            modifier = Modifier.weight(1f),
                            title = "Заряд",
                            value = "${car.batteryLevel}%",
                            iconRes = getBatteryIcon(car.batteryLevel),
                            valueColor = if (car.batteryLevel < 20) Color.Red else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    is GasolineCar -> {
                        SpecCard(
                            modifier = Modifier.weight(1f),
                            title = "Паливо",
                            value = "${car.fuelLevel}%",
                            iconRes = R.drawable.local_gas_station_24px,
                            valueColor = if (car.fuelLevel < 20) Color.Red else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                SpecCard(
                    modifier = Modifier.weight(1f),
                    title = "КПП",
                    value = car.transmission,
                    iconRes = R.drawable.auto_transmission_24px
                )
                SpecCard(
                    modifier = Modifier.weight(1f),
                    title = "Номер",
                    value = car.plateNumber,
                    iconRes = R.drawable.plate_24px
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Про автомобіль",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = car.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
            )
//            Spacer(modifier= Modifier.height(20.dp))
//            Text(
//                modifier = Modifier.fillMaxWidth(),
//                textAlign = TextAlign.Center,
//                text = "Тарифи",
//                style = MaterialTheme.typography.titleMedium,
//                fontWeight = FontWeight.SemiBold,
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//

            Spacer(modifier = Modifier.height(24.dp))
        }

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 8.dp,
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Тариф",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${car.price} ₴ / год",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Button(
                    onClick = {
                        mapViewModel.reserveCar(car.id) {
                            onBookingSuccess()
                        } },
                    modifier = Modifier
                        .height(50.dp)
                        .padding(start = 16.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Забронювати",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun SpecCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    iconRes: Int? = null,
    valueColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (iconRes != null) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier.size(24.dp).padding(bottom = 4.dp),
                tint = valueColor
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = valueColor
        )
    }
}
@Composable
fun getBatteryIcon(level: Int): Int {
    return when {
        level > 90 -> R.drawable.battery_android_frame_full_24px
        level > 70 -> R.drawable.battery_android_frame_5_24px
        level > 50 -> R.drawable.battery_android_frame_4_24px
        level > 35 -> R.drawable.battery_android_frame_3_24px
        level > 20 -> R.drawable.battery_android_frame_2_24px
        level > 10 -> R.drawable.battery_android_frame_1_24px
        else -> R.drawable.battery_android_alert_24px
    }
}