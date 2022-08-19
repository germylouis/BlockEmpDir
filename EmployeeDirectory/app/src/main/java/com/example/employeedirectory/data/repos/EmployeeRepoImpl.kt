package com.example.employeedirectory.data.repos

import com.example.employeedirectory.data.datasources.EmployeeDataSource
import com.example.employeedirectory.data.datasources.api.NetworkHelper
import com.example.employeedirectory.data.datasources.api.dtos.toStateFlowAllEmployeesEntity
import com.example.employeedirectory.data.models.Employee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

class EmployeeRepoImpl(
    private val employeeDataSource: EmployeeDataSource,
    private var jsonType: String
) :
    EmployeeRepo {
    override suspend fun getEmployees(): MutableStateFlow<List<Employee>?> {
        val netResp = NetworkHelper.safeApiCall(
            Dispatchers.IO
        ) {
            runBlocking {
                employeeDataSource.getEmployees(jsonType).toStateFlowAllEmployeesEntity()
            }
        }
        return MutableStateFlow(netResp.value?.employees)
    }
}
