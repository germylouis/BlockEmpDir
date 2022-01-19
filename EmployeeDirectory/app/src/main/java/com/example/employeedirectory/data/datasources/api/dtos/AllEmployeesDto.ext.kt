package com.example.employeedirectory.data.datasources.api.dtos

import com.example.employeedirectory.data.models.AllEmployees
import com.example.employeedirectory.data.models.Employee
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun AllEmployeesDto.toAllEmployeesEntity(): AllEmployees {
    val employeesList: MutableList<Employee> = ArrayList()
    for (emps in employees) {
        val employee = Employee(
            emps.uuid,
            emps.full_name,
            emps.phone_number,
            emps.email_address,
            emps.biography,
            emps.photo_url_small,
            emps.photo_url_large,
            emps.team,
            emps.employee_type
        )
        employeesList.add(employee)

    }
    return AllEmployees(employeesList)
}

fun AllEmployeesDto.toStateFlowAllEmployeesEntity(): StateFlow<AllEmployees> {
    val employeesList: MutableList<Employee> = ArrayList()
    val stateFlowAllEmployees: StateFlow<AllEmployees>
    for (emps in employees) {
        val employee = Employee(
            emps.uuid,
            emps.full_name,
            emps.phone_number,
            emps.email_address,
            emps.biography,
            emps.photo_url_small,
            emps.photo_url_large,
            emps.team,
            emps.employee_type
        )
        employeesList.add(employee)

    }
    stateFlowAllEmployees = MutableStateFlow(AllEmployees(employeesList))
    return stateFlowAllEmployees
}
