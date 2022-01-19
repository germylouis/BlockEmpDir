package com.example.employeedirectory.viewmodels

import com.example.employeedirectory.data.models.Employee

sealed class LatestEmployeesUiState {
    data class Success(var employees: List<Employee>?) : LatestEmployeesUiState()
    data class Error(var exception: String) : LatestEmployeesUiState()
    object Loading : LatestEmployeesUiState()
}
