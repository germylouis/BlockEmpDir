package com.example.employeedirectory.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeedirectory.data.models.Employee
import com.example.employeedirectory.data.repos.EmployeeRepoImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class EmployeeViewModel(private val employeeRepo: EmployeeRepoImpl?) : ViewModel() {
    private var _uiSuccessState = MutableStateFlow(LatestEmployeesUiState.Success(emptyList()))
    private var _uiErrorState = MutableStateFlow(LatestEmployeesUiState.Error(""))
    var uiState: MutableStateFlow<List<Employee>?> = MutableStateFlow(emptyList())

    /**
     * Init block and Sealed class code from:
     * https://developer.android.com/kotlin/flow/stateflow-and-sharedflow
     */
    init {
        viewModelScope.launch {

        }
    }

    suspend fun getEmployees(): Flow<List<Employee>?> = flow {
        val data = employeeRepo?.getEmployees()?.value
        // _uiSuccessState.value =  MutableStateFlow(LatestEmployeesUiState.Error(""))
        Log.d(TAG, "germ: ${data}: ")
        emit(data)
    }

    class Factory(app: Application, private val employeeRepo: EmployeeRepoImpl?) :
        ViewModelFactory(app) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EmployeeViewModel(employeeRepo) as T
        }
    }


    companion object {
        val TAG: String = EmployeeViewModel::class.java.name
    }
}
