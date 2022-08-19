package com.example.employeedirectory.data.repos

import com.example.employeedirectory.data.models.Employee
import com.example.employeedirectory.viewmodels.LatestEmployeesUiState
import kotlinx.coroutines.flow.MutableStateFlow

interface EmployeeRepo {
    suspend fun getEmployees(): MutableStateFlow<List<Employee>?>
}
