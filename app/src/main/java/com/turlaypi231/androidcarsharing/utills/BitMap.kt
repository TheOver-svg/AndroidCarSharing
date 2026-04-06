package com.turlaypi231.androidcarsharing.utills

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    // Дістаємо векторну картинку з ресурсів
    val vectorDrawable = ContextCompat.getDrawable(context, vectorResId) ?: return null

    // Встановлюємо розміри (можна помножити на коефіцієнт, якщо іконка замала)
    vectorDrawable.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)

    // Створюємо порожній растровий "холст"
    val bitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // Малюємо наш вектор на цьому "холсті"
    val canvas = Canvas(bitmap)
    vectorDrawable.draw(canvas)

    // Повертаємо готовий для карти формат
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}