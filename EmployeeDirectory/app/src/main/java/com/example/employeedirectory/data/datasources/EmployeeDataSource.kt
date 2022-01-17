package com.example.employeedirectory.data.datasources

import com.example.employeedirectory.data.datasources.dtos.AllEmployeesDto
import kotlinx.coroutines.flow.StateFlow
import retrofit2.http.GET

interface EmployeeDataSource {
    @GET
    suspend fun getEmployees(): StateFlow<AllEmployeesDto?>
}
