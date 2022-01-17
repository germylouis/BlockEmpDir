package com.example.employeedirectory.data.datasources

import com.example.employeedirectory.data.datasources.dtos.AllEmployeesDto
import retrofit2.http.GET

interface EmployeeDataSource {
    @GET("employees.json")
    suspend fun getEmployees(): AllEmployeesDto?
}
