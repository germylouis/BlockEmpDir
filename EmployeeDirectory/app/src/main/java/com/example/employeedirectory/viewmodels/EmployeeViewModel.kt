package com.example.employeedirectory.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeedirectory.data.datasources.api.ResultWrapper
import com.example.employeedirectory.data.models.Employee
import com.example.employeedirectory.data.repos.EmployeeRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EmployeeViewModel(private val employeeRepo: EmployeeRepoImpl?) : ViewModel() {
    private val _uiState = MutableStateFlow(LatestEmployeesUiState.Success(emptyList()))
    val uiState: StateFlow<LatestEmployeesUiState> = _uiState

    /**
     * Init block and Sealed class code from:
     * https://developer.android.com/kotlin/flow/stateflow-and-sharedflow
     */
    init {
        viewModelScope.launch {
            when (val response = employeeRepo?.getEmployees()) {
                is ResultWrapper.NetworkError -> {}
                is ResultWrapper.GenericError -> {}
                is ResultWrapper.Success -> {
                    response.value?.collect {
                        _uiState.value = LatestEmployeesUiState.Success(it?.employees)
                    }
                }
                else -> {}
            }
        }
    }


    class Factory(app: Application, private val employeeRepo: EmployeeRepoImpl?) : ViewModelFactory(app) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EmployeeViewModel(employeeRepo) as T
        }
    }

    sealed class LatestEmployeesUiState {
        data class Success(val employees: List<Employee>?) : LatestEmployeesUiState()
        data class Error(val exception: Throwable) : LatestEmployeesUiState()
    }

    companion object {
        val TAG: String = EmployeeViewModel::class.java.name
    }
}
