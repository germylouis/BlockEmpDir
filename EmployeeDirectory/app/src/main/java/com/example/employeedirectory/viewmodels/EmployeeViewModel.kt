package com.example.employeedirectory.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeedirectory.data.models.Employee
import com.example.employeedirectory.data.repos.EmployeeRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EmployeeViewModel(private val employeeRepo: EmployeeRepoImpl) : ViewModel() {
    private val _uiState = MutableStateFlow(LatestEmployeesUiState.Success(emptyList()))
    val uiState: StateFlow<LatestEmployeesUiState> = _uiState

    /**
     * Init block and Sealed class code from:
     * https://developer.android.com/kotlin/flow/stateflow-and-sharedflow
     */
    init {
        viewModelScope.launch {
            employeeRepo.getEmployees()
                // Update View with the latest employees
                // Writes to the value property of MutableStateFlow,
                // adding a new element to the flow and updating all
                // of its collectors
                ?.collect { employees ->
                    _uiState.value = LatestEmployeesUiState.Success(employees?.employees)
                }
        }
    }


    class Factory(app: Application, private val employeeRepo: EmployeeRepoImpl) : ViewModelFactory(app) {
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
