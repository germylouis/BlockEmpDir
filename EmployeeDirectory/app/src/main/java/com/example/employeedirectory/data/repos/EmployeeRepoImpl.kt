package com.example.employeedirectory.data.repos

import com.example.employeedirectory.data.datasources.EmployeeDataSource
import com.example.employeedirectory.data.models.AllEmployees
import toAllEmployeesEntity

class EmployeeRepoImpl(private var employeeDataSource: EmployeeDataSource?) : EmployeeRepo {
    override suspend fun getEmployees(): AllEmployees? {
        return employeeDataSource?.getEmployees()?.toAllEmployeesEntity()
    }
}
