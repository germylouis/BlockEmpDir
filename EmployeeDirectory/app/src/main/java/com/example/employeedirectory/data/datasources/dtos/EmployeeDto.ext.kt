package com.example.employeedirectory.data.datasources.dtos

import com.example.employeedirectory.data.models.Employee

fun EmployeeDto.toEmployeeEntity(): Employee{
    return Employee(
        uuid,
        full_name,
        phone_number,
        email_address,
        biography,
        photo_url_small,
        photo_url_large,
        team,
        employee_type
    )
}
