package com.hannhb.myapplication.screen.setting

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hannhb.myapplication.model.Unit
import com.hannhb.myapplication.repository.WeatherDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val weatherDBRepository: WeatherDBRepository
) : ViewModel() {
    private val _settings =  MutableStateFlow<List<Unit>>(emptyList())
    val settings = _settings.asStateFlow()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            weatherDBRepository.getSettings().distinctUntilChanged()
                .collect{listUnits->
                    if (listUnits.isNullOrEmpty()) {
                        Log.d("Setting Screen", "List Unit is empty ")
                    } else {
                        _settings.value = listUnits
                    }

                }
        }

    }

    fun insertUnit(unit: Unit) = viewModelScope.launch {
        weatherDBRepository.insertUnit(unit)
    }

    fun updateUnit(unit: Unit) = viewModelScope.launch {
        weatherDBRepository.updateUnit(unit)
    }

    fun deleteAllUnit() = viewModelScope.launch {
        weatherDBRepository.deleteAllUnits()
    }
}