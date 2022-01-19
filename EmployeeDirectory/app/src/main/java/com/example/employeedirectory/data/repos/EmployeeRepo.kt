package com.example.employeedirectory.data.repos

import com.example.employeedirectory.data.datasources.api.ResultWrapper
import com.example.employeedirectory.data.models.AllEmployees
import com.example.employeedirectory.viewmodels.EmployeeViewModel
import com.example.employeedirectory.viewmodels.LatestEmployeesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface EmployeeRepo {
    suspend fun getEmployees():MutableStateFlow<LatestEmployeesUiState>
}
