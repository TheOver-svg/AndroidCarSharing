package com.turlaypi231.androidcarsharing.viewModel

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.turlaypi231.androidcarsharing.model.Car
import com.turlaypi231.androidcarsharing.view.MapUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MapViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState = _uiState.asStateFlow()

    fun onClickCar(car: Car)
    {
        _uiState.update { it.copy(selectedCar = car) }
    }

    fun onDismissBottomSheet()
    {
        _uiState.update { it.copy(selectedCar = null) }
    }
}