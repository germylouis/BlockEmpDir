package com.example.employeedirectory.data.datasources.api.dtos

import kotlinx.serialization.SerialName

data class EmployeeDto(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("full_name")
    val full_name: String,
    @SerialName("phone_number")
    val phone_number: Long,
    @SerialName("email_address")
    val email_address: String,
    @SerialName("biography")
    val biography: String,
    @SerialName("photo_url_small")
    val photo_url_small: String,
    @SerialName("photo_url_large")
    val photo_url_large: String,
    @SerialName("team")
    val team: String,
    @SerialName("employee_type")
    val employee_type: String
)
