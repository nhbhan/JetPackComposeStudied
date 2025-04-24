package com.hannhb.myapplication.screen.favourite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hannhb.myapplication.model.Favourite
import com.hannhb.myapplication.repository.WeatherDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val weatherDBRepository: WeatherDBRepository
) : ViewModel() {
    private val _favList = MutableStateFlow<List<Favourite>>(emptyList())
    val favList = _favList.asStateFlow()

    companion object {
        private const val TAG = "FavouriteViewModel"
    }

    // distinctUntilChanged remove duplication data
    init {
        viewModelScope.launch(Dispatchers.IO) {
            weatherDBRepository.getFavourites().distinctUntilChanged()
                .collect{ favourites ->
                    if (favourites.isNullOrEmpty()) {
                        Log.d(TAG, "Favourite List: empty ")
                    } else {
                        _favList.value = favourites
                    }

                }
        }
    }

    suspend fun  insertFavourite(favourite: Favourite) = viewModelScope.launch { weatherDBRepository.insertFav(favourite) }
    suspend fun updateFavourite(favourite: Favourite) = viewModelScope.launch { weatherDBRepository.updateFav(favourite) }
    suspend fun  deleteFavourite(favourite: Favourite) = viewModelScope.launch { weatherDBRepository.deleteFav(favourite) }
    suspend fun deleteAll() = viewModelScope.launch { weatherDBRepository.deleteAllFav() }
    suspend fun getFavById(city: String) = viewModelScope.launch { weatherDBRepository.getFavById(city) }

}