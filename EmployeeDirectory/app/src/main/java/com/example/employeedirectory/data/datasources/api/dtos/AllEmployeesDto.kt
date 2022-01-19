package com.example.employeedirectory.data.datasources.api.dtos

import kotlinx.serialization.SerialName

class AllEmployeesDto(
    @SerialName("employees")
    val employees: List<EmployeeDto>
)
