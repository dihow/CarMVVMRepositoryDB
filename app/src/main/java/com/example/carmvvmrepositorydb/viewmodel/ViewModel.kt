package com.example.carmvvmrepositorydb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carmvvmrepositorydb.model.Car
import com.example.carmvvmrepositorydb.model.CarRepository
import kotlinx.coroutines.launch

class CarViewModel (private val repository: CarRepository) : ViewModel() {
    val allCars = repository.allCars

    fun insertCar(car: Car) = viewModelScope.launch {
        repository.insertCar(car)
    }

    fun deleteAllCars() = viewModelScope.launch {
        repository.deleteAllCars()
    }
}