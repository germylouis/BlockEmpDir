package com.example.employeedirectory.data.repos

import com.example.employeedirectory.data.datasources.EmployeeDataSource
import com.example.employeedirectory.data.datasources.dtos.toStateFlowAllEmployeesEntity
import com.example.employeedirectory.data.models.AllEmployees
import kotlinx.coroutines.flow.StateFlow

class EmployeeRepoImpl(private var employeeDataSource: EmployeeDataSource?) : EmployeeRepo {
    override suspend fun getEmployees(): StateFlow<AllEmployees?>? {
        return employeeDataSource?.getEmployees()?.value?.toStateFlowAllEmployeesEntity()
    }
}
