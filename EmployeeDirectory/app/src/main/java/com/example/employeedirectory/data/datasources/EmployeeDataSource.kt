package com.example.employeedirectory.data.datasources

import com.example.employeedirectory.data.datasources.api.dtos.AllEmployeesDto
import retrofit2.http.GET
import retrofit2.http.Path

interface EmployeeDataSource {
    @GET("{jsonType}")
    suspend fun getEmployees(@Path(value = "jsonType", encoded = true) jsonType: String): AllEmployeesDto
}
