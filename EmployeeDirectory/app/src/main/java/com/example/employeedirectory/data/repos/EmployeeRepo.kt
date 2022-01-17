package com.example.employeedirectory.data.repos

import com.example.employeedirectory.data.models.AllEmployees
import kotlinx.coroutines.flow.StateFlow

interface EmployeeRepo {
    suspend fun getEmployees(): StateFlow<AllEmployees?>?
}
