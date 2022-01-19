package com.example.employeedirectory.data.repos

import com.example.employeedirectory.data.datasources.EmployeeDataSource
import com.example.employeedirectory.data.datasources.api.NetworkHelper
import com.example.employeedirectory.data.datasources.api.ResultWrapper
import com.example.employeedirectory.data.datasources.api.dtos.toStateFlowAllEmployeesEntity
import com.example.employeedirectory.data.models.AllEmployees
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking

class EmployeeRepoImpl(private val employeeDataSource: EmployeeDataSource, private var jsonType: String) : EmployeeRepo {
    override suspend fun getEmployees(): ResultWrapper<StateFlow<AllEmployees?>> {
        return NetworkHelper.safeApiCall(
            Dispatchers.IO
        ) { runBlocking { employeeDataSource.getEmployees(jsonType).toStateFlowAllEmployeesEntity() } }
    }
}

