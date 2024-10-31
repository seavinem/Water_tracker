package com.example.watertracker.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watertracker.data.source.database.WaterEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WaterViewModel @Inject constructor(
    private val waterRepository: WaterRepository
) : ViewModel() {

    val totalAmount = MutableLiveData(0)
    val allPortions = MutableLiveData<List<WaterEntity>>()

    init {
        // Выполнение запроса к базе данных на фоновом потоке
        viewModelScope.launch(IO) {
            val amount = waterRepository.getTotalAmount()
            totalAmount.postValue(amount)  // Обновление через postValue для выполнения на основном потоке
        }
    }

    fun addWater(value: Int) {
        val oldValue = totalAmount.value ?: 0
        totalAmount.postValue(oldValue + value)  // Используем postValue для фонового потока
        viewModelScope.launch {
            waterRepository.savePortion(amount = value)
        }
    }

    fun getAllPortion() {
        viewModelScope.launch(IO) {
            val portions = waterRepository.getAllPortion()
            allPortions.postValue(portions)  // Используем postValue
        }
    }
}