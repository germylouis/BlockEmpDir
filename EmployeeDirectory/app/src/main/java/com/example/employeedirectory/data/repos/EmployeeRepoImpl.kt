package com.example.employeedirectory.data.repos

import com.example.employeedirectory.data.datasources.EmployeeDataSource
import com.example.employeedirectory.data.datasources.api.NetworkHelper
import com.example.employeedirectory.data.datasources.api.dtos.toStateFlowAllEmployeesEntity
import com.example.employeedirectory.viewmodels.LatestEmployeesUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

class EmployeeRepoImpl(private val employeeDataSource: EmployeeDataSource, private var jsonType: String) :
    EmployeeRepo {
    override suspend fun getEmployees(): MutableStateFlow<LatestEmployeesUiState> {
        val netResp = NetworkHelper.safeApiCall(
            Dispatchers.IO
        ) {
            runBlocking {
                employeeDataSource.getEmployees(jsonType).toStateFlowAllEmployeesEntity()

            }
        }
        return if (!netResp.value?.employees.isNullOrEmpty()) MutableStateFlow(
            LatestEmployeesUiState.Success(netResp.value?.employees)
        ) else MutableStateFlow(
            LatestEmployeesUiState.Error("Error")
        )
    }
}
