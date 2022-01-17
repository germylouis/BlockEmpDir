package com.example.employeedirectory.data.repos

import com.example.employeedirectory.data.models.AllEmployees

interface EmployeeRepo {
    suspend fun getEmployees(): AllEmployees?
}
