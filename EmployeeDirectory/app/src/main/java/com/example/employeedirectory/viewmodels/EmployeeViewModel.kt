package com.example.employeedirectory.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.employeedirectory.data.repos.EmployeeRepoImpl

class EmployeeViewModel(private val employeeRepo: EmployeeRepoImpl) : ViewModel() {


    class Factory(app: Application, private val employeeRepo: EmployeeRepoImpl) : ViewModelFactory(app) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EmployeeViewModel(employeeRepo) as T
        }
    }

    companion object {
        val TAG: String = EmployeeViewModel::class.java.name
    }
}
